package com.dj.utils.externalsorting;

import com.dj.utils.common.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.dj.utils.common.CommonDjUtils.*;

/**
 * Created by Darshan on 8/7/16.
 */
public class FileChunker {
	private static final Logger logger = LoggerFactory.getLogger(FileChunker.class);
	private static final int THREAD_POOL_SIZE = 30;

	private AtomicLong totalSortingTime = new AtomicLong();
	private CurrencyComparator comparator = new CurrencyComparator();

	private String outputFolder;
	private int chunkSize;

	public static class FileChunkerBuilder {
		String out;
		int chunk;

		public FileChunkerBuilder withOutputFolder(String outputFolder) {
			this.out = outputFolder;
			return this;
		}

		public FileChunkerBuilder withChunkSize(int chunkSize) {
			this.chunk = chunkSize;
			return this;
		}

		public FileChunker build() {
			if (isEmpty(out) || chunk <= 0) {
				throw new IllegalArgumentException("Please specify outputFolder and chunkSize");
			}
			FileChunker chunker = new FileChunker();
			chunker.outputFolder = out;
			chunker.chunkSize = chunk;
			return chunker;
		}
	}

	public int chunkOriginalFileInParts(Path inputFile) throws Exception {
		Timer chunkingTimer = Timer.newTimer();

		int fileChunkCounter = 0, chunkUsed = 0, noOfChunks = 0;
		Path outputFile = Paths.get(outputFolder + SEP + fileChunkCounter + ".txt");
		outputFile.toFile().getParentFile().mkdirs();
		LinkedList<String> entries = new LinkedList<String>();
		ExecutorService fixedPoolExecutor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

		logger.info("Chunking started");
		try (
				BufferedReader inputFileReader = Files.newBufferedReader(inputFile);
		) {
			String line = null;
			while ((line = inputFileReader.readLine()) != null) {
				//+2 for the newline character
				if ((line.length() + 2 + chunkUsed) > chunkSize) {
					MultiThreadedSorterAndWriter sorterAndWriter = new MultiThreadedSorterAndWriter(new LinkedList<>(entries), fileChunkCounter);
					fixedPoolExecutor.submit(sorterAndWriter);
//					sortEntriesAndWriteToFile(entries, fileChunkCounter);
					chunkUsed = 0;
					fileChunkCounter++;
					entries.clear();
					noOfChunks++;
					outputFile = Paths.get(outputFolder + SEP + fileChunkCounter + ".txt");
//					chunkWriter = new FileWriter(outputFile.toFile(), true);
				}
				chunkUsed += line.length();
				entries.add(line);
			}
			if (entries.size() > 0) {
				MultiThreadedSorterAndWriter sorterAndWriter = new MultiThreadedSorterAndWriter(new LinkedList<>(entries), fileChunkCounter);
				fixedPoolExecutor.submit(sorterAndWriter);
				noOfChunks++;
				entries.clear();
			}
		}
		logger.info("Chunking end");
		shutdownAndAwaitTermination(fixedPoolExecutor);
		logger.debug("Chunking done::: Parts: {}, Time: {} ms, Sorting: {} ms", noOfChunks, chunkingTimer.time(), totalSortingTime.get());
		return noOfChunks;
	}

	private void shutdownAndAwaitTermination(ExecutorService fixedPoolExecutor) throws InterruptedException {
		fixedPoolExecutor.shutdown();
		fixedPoolExecutor.awaitTermination(1, TimeUnit.MINUTES);
	}

	private class MultiThreadedSorterAndWriter implements Runnable {
		private LinkedList<String> inputList = null;
		private int fileNum = -1;

		public MultiThreadedSorterAndWriter(LinkedList<String> inputList, int fileNum) {
			this.inputList = inputList;
			this.fileNum = fileNum;
		}

		@Override
		public void run() {
			Path outputFile = Paths.get(outputFolder + SEP + fileNum + ".txt");
			try (FileWriter chunkWriter = new FileWriter(outputFile.toFile(), true);) {
				Timer sortingTimer = Timer.newTimer();
				Collections.sort(inputList, comparator);
				totalSortingTime.addAndGet(sortingTimer.time());
				for (String eachLine : inputList) {
					chunkWriter.write((eachLine + NEWLINE));
				}
			} catch (IOException e) {
				logger.error("Error occurred in thread while sorting and writing to file", e);
			}
		}
	}
}
