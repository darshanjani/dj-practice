package com.dj.utils.externalsorting;

import com.dj.utils.common.Timer;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.dj.utils.common.CommonDjUtils.SEP;

/**
 * Created by Darshan on 7/27/16.
 */
public class ExternalSorting {
	private static final Logger logger = LoggerFactory.getLogger(ExternalSorting.class);

	private static final int BYTE_SIZE_1_KB = 1024;
	private static final int BYTE_SIZE_1_MB = BYTE_SIZE_1_KB * BYTE_SIZE_1_KB;
	private static final int CHUNK_SIZE = 10 * BYTE_SIZE_1_MB;
	private static final int OUTPUT_FOLDER_PADDING_LENGTH = 2;

	private static final String BASE_FOLDER = "target" + SEP;
	private static final String INPUT_FILE = "gen_data.csv";
	public static final int THREAD_POOL_SIZE = 20;
	private static final int INITIAL_PASS_NUMBER = 0;

	private FileChunker fileChunker = new FileChunker.FileChunkerBuilder().withChunkSize(CHUNK_SIZE).withOutputFolder(BASE_FOLDER + getFolderNamePaddedWithZeros(INITIAL_PASS_NUMBER)).build();

	public static void main(String[] args) throws Exception {
		ExternalSorting externalSorter = new ExternalSorting();
		externalSorter.cleanupFoldersBeforeStart();

		Timer mainTimer = Timer.newTimer();
		externalSorter.sortExternal();
		logger.info("Sorting took {} ms", mainTimer.time());
	}

	public void sortExternal() throws Exception {
		Path inputFile = Paths.get(BASE_FOLDER + INPUT_FILE);
		int noOfParts = fileChunker.chunkOriginalFileInParts(inputFile);
		int noOfPasses = getBinaryNoOfPassesForGivenParts(noOfParts);
		SortAndMergeSortedFiles sorterAndMerger = new SortAndMergeSortedFiles();

		for (int currentPassNum = 1; currentPassNum <= noOfPasses; currentPassNum++) {
			Timer eachPassTimer = Timer.newTimer();
			Files.createDirectory(Paths.get(BASE_FOLDER + getFolderNamePaddedWithZeros(currentPassNum)));
			int previousPassNum = currentPassNum - 1;
			int noOfFilesInPreviousPass = new File(BASE_FOLDER + getFolderNamePaddedWithZeros(previousPassNum)).listFiles().length;
			ExecutorService multiThreadedExecutor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

			filesInEachPass:
			for (int i = 0, outFileCounter = 0; i < noOfFilesInPreviousPass; ) {

				final Path inputFile1 = Paths.get(BASE_FOLDER + getFolderNamePaddedWithZeros(previousPassNum) + SEP + i + ".txt");
				final Path inputFile2 = Paths.get(BASE_FOLDER + getFolderNamePaddedWithZeros(previousPassNum) + SEP + (i + 1) + ".txt");
				final Path outPath = Paths.get(BASE_FOLDER + getFolderNamePaddedWithZeros(currentPassNum) + SEP + outFileCounter + ".txt");

				//Handle odd no. of files in previous pass. In this case, just copy over the input file 1 as output file
				if (Files.notExists(inputFile2)) {
					logger.info("Since there are odd files in previous pass, hence copying {} to {} as is.", inputFile1.toAbsolutePath(), outPath.toAbsolutePath());
					Files.copy(inputFile1, outPath, StandardCopyOption.REPLACE_EXISTING);
					i += 2;
					outFileCounter++;
					continue filesInEachPass;
				}

				multiThreadedExecutor.submit(new Runnable() {
					@Override
					public void run() {
						try {
							sorterAndMerger.sortAndMergeAlreadySortedFiles(inputFile1, inputFile2, outPath);
						} catch (IOException e) {
							logger.error("Error while merging {} and {}", inputFile1.toAbsolutePath(), inputFile2.toAbsolutePath());
							logger.error("Exception trace", e);
						}
					}
				});

				i += 2;
				outFileCounter++;
			}
			multiThreadedExecutor.shutdown();
			multiThreadedExecutor.awaitTermination(1, TimeUnit.MINUTES);
			logger.info("Files to process: {}, Pass: {}, Time Taken: {} ms", noOfFilesInPreviousPass, getFolderNamePaddedWithZeros(currentPassNum), eachPassTimer.time());
		}

	}

	private int getBinaryNoOfPassesForGivenParts(int noOfParts) {
		int noOfPasses = (int) Math.ceil(Math.log(noOfParts) / Math.log(2));
		logger.debug("No. of binary passes required to sort {} are: {}", noOfParts, noOfPasses);
		return noOfPasses;
	}

	private void cleanupFoldersBeforeStart() throws IOException {
		Timer cleanupTimer = Timer.newTimer();
		for (int passNumber = 0; passNumber < 30; passNumber++) {
			FileUtils.deleteDirectory(Paths.get(BASE_FOLDER + getFolderNamePaddedWithZeros(passNumber)).toFile());
		}
		logger.debug("Time taken to clean target folders: {} ms", cleanupTimer.time());
	}

	private String getFolderNamePaddedWithZeros(int passNumber) {
		return String.format("%0" + OUTPUT_FOLDER_PADDING_LENGTH +"d", passNumber);
	}
}
