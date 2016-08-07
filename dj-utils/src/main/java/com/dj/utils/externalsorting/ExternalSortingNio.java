package com.dj.utils.externalsorting;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Darshan on 7/27/16.
 */
public class ExternalSortingNio {
	private static final int MEMORY_SIZE_IN_BYTES = 50000;
	private static final String SEP = File.separator;

	private static final int BYTE_SIZE_1_KB = 1024;
	private static final int BYTE_SIZE_1_MB = BYTE_SIZE_1_KB * BYTE_SIZE_1_KB;
	private static final int BYTE_ARRAY_SIZE = 8 * BYTE_SIZE_1_KB;
	private static final int BIG_FILE_SIZE = 10 * BYTE_SIZE_1_MB;

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		new ExternalSortingNio().sortExternal();
		long end = System.currentTimeMillis();
		System.out.format("Sorting took %d millis%n", (end - start));
	}

	public void sortExternal() throws Exception {
		Path inputFile = Paths.get("target" + SEP + "gen_data.csv");
		int noOfParts = 0;
		int fileChunkCounter = 0;
		int chunkUsed = 0;

		long chunkingStart = System.currentTimeMillis();
		try {
			Path pathToZeroPass = Paths.get("target" + SEP + String.format("%05d",0));
			if (Files.exists(pathToZeroPass)) {
				Files.list(pathToZeroPass).forEach((cons) -> {
					try {
						Files.deleteIfExists(cons.getFileName());
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
			Files.deleteIfExists(pathToZeroPass);
			Files.createDirectory(pathToZeroPass);
		} catch (Exception e) {
			//Ignore
		}

		Path outputFile = Paths.get("target" + SEP + String.format("%05d", 0) + SEP + fileChunkCounter + ".txt");
		FileChannel rChannel = new RandomAccessFile(inputFile.toFile(), "r").getChannel();
		ByteBuffer rBuf = ByteBuffer.allocate(BIG_FILE_SIZE);
		System.out.println(outputFile.toFile().getAbsolutePath());
		FileChannel wChannel = new FileOutputStream(outputFile.toFile(), true).getChannel();
		LinkedList<String> builder = new LinkedList<String>();
		CurrencyComparator comparator = new CurrencyComparator();
		try {
			int numBytesRead = 0;
			long totalBytesRead = 0;
			while( (numBytesRead = rChannel.read(rBuf)) != -1) {
				if (numBytesRead == 0) continue;
				rBuf.flip();
				int lastNewlinePosition = 0;
				byte c;
				int charsInLine = 0;
				System.out.format("Num Read: %d, position: %d, capacity: %d%n", numBytesRead, rBuf.position(), rBuf.capacity());
				for (int i = 0; i < numBytesRead; i++) {
					c = rBuf.get();
					charsInLine++;
					if (c == "\n".getBytes()[0]) {
//						System.out.format("Found newline: %d, %d%n", i - charsInLine + 1, charsInLine);
						byte[] dest = new byte[charsInLine];
						for (int k = 0, j = i - charsInLine + 1; j < charsInLine; j++) {
							dest[k++] = rBuf.array()[j];
						}
						builder.add(new String(dest));
						charsInLine = 0;
						lastNewlinePosition = i;
					}
				}
//					int remaining = 0;
//					if (rBuf.hasRemaining()) {
//						remaining = rBuf.remaining();
//						System.out.format("Remaining bytes: %d, position: %d, capacity: %d%n", remaining, rBuf.position(), rBuf.capacity());
//						byte[] dest = new byte[remaining];
//						rBuf.get(dest, rBuf.position(), rBuf.capacity());
//						rBuf.clear();
//						rBuf.wrap(dest);
//					} else {
//						rBuf.clear();
//					}
//					totalBytesRead += numBytesRead;
					rBuf.clear();
					fileChunkCounter++;

					long sortStart = System.currentTimeMillis();
				System.out.println("Builder Size: " + builder.size());
//					Collections.sort(builder, comparator);
					long sortEnd = System.currentTimeMillis();
	//				System.out.format("Sorting %d lines in LinkedList took %d millis%n", builder.size(), (sortEnd - sortStart));
					for (String eachLine : builder) {
						wChannel.write(ByteBuffer.wrap((eachLine + "\n").getBytes()));
					}
					builder.clear();
					noOfParts++;
					closeChannel(wChannel);

				fileChunkCounter++;
				noOfParts++;
				outputFile = Paths.get("target" + SEP + String.format("%05d", 0) + SEP + fileChunkCounter + ".txt");
				wChannel = new FileOutputStream(outputFile.toFile(), true).getChannel();
			}
		} finally {
			builder.clear();
			closeChannel(rChannel);
		}
		long chunkingEnd = System.currentTimeMillis();
		System.out.format("Chunking took %d millis%n", (chunkingEnd - chunkingStart));

		int noOfPasses = (int) Math.ceil(Math.log(noOfParts) / Math.log(2)); //sqrt since we are comparing only 2 files at a time
		System.out.format("No. of binary passes required to sort %d are: %d%n", noOfParts, noOfPasses);
		for (int pass = 1; pass <= noOfPasses; pass++) {
//			if (pass > 1) System.exit(0);
			long passStart = System.currentTimeMillis();

			//Create folder for each pass.
			try {
				final int finalPass = pass;
				Path pathToPass = Paths.get("target" + SEP + String.format("%05d",pass));
				if (Files.exists(pathToPass)) {
					Files.list(pathToPass).forEach((path) -> {
						try {
//							System.out.println("Deleting file " + Paths.get("target" + SEP + finalPass + SEP + path.getFileName()).toAbsolutePath());
							Files.deleteIfExists(Paths.get("target" + SEP + finalPass + SEP + path.getFileName()));
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				}
//				Files.deleteIfExists(pathToPass);
				Files.createDirectory(Paths.get("target" + SEP + String.format("%05d",pass)));
			} catch (FileAlreadyExistsException e) {
				//Ignore
			}

			int previousPassNum = pass - 1;
			int noOfFilesInPreviousPass = new File("target" + SEP + String.format("%05d",previousPassNum)).listFiles().length;
			System.out.format("No of input files in pass: %s are %s%n", previousPassNum, noOfFilesInPreviousPass);
			for (int i = 0, outFileCounter = 0; i < noOfFilesInPreviousPass; ) {

				Path path1 = Paths.get("target" + SEP + String.format("%05d",previousPassNum) + SEP + i + ".txt");
				Path path2 = Paths.get("target" + SEP + String.format("%05d",previousPassNum) + SEP + (i + 1) + ".txt");
				if (Files.notExists(path1)) {
//					System.out.println(path1.getFileName() + " path1 does not exist");
					path1 = Paths.get("target" + SEP + "NUL");
				} else {
//					System.out.println("Read file 1: " + path1.toAbsolutePath());
				}
				if (Files.notExists(path2)) {
//					System.out.println(path2.getFileName() + " path2 does not exist");
					path2 = Paths.get("target" + SEP + "NUL");
				} else {
//					System.out.println("Read file 2: " + path2.toAbsolutePath());
				}

				try (
						BufferedReader br1 = Files.newBufferedReader(path1);
						BufferedReader br2 = Files.newBufferedReader(path2);
				) {
					Path outPath = Paths.get("target" + SEP + String.format("%05d",pass) + SEP + outFileCounter + ".txt");
					try (BufferedWriter out = Files.newBufferedWriter(outPath)) {
						String line1 = br1.readLine(), line2 = br2.readLine();
						String outLine;
						readFiles:
						while (true) {
							if ((line1 == null || line1.trim().length() == 0) && (line2 == null || line2.trim().length() == 0)) {
								//Both input buffers are empty. Go to next pass
								break readFiles;
							}
							if (line1 == null || line1.trim().length()==0) {
								outLine = line2;
//								System.out.println("Choosing line2 since line1 is empty");
							} else if (line2 == null || line2.trim().length()==0) {
								outLine = line1;
//								System.out.println("Choosing line1 since line2 is empty");
							} else {
								outLine = returnSmallerOfBothStrings(line1.trim(), line2.trim());
//								System.out.format("Choosing %s out of %s and %s%n", outLine, line1, line2);
							}
//							System.out.println("outLine: " + outLine);
							if (outLine.equals(line1)) {
								line1 = br1.readLine();
							} else {
								line2 = br2.readLine();
							}
							out.write(outLine + "\n");
						}
					}
					i += 2;
					outFileCounter++;
				}
			}
			long passEnd = System.currentTimeMillis();
			System.out.println("Pass " + String.format("%05d",pass) + " took " + (passEnd - passStart) + " millis");
		}

	}

	private void closeWriter(BufferedWriter writer) throws Exception {
		if (writer != null) {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				//Ignore
			}
		}
	}

	private void closeChannel(FileChannel channel) throws Exception {
		if (channel != null) {
			try {
				channel.close();
			} catch (IOException e) {
				//Ignore
			}
		}
	}

	private String returnSmallerOfBothStrings(String line1, String line2) {
		String compare1 = extractFieldToCompare(line1);
		String compare2 = extractFieldToCompare(line2);
		int compareResult = compare1.compareTo(compare2);
		String result = null;
		if (compareResult > 0) {
			result = line2;
		} else {
			//Line1 is either equal to or lesser than line2
			result = line1;
		}
//		System.out.format("Compare1: %s, Compare2: %s, Result: %s%n", compare1, compare2, result);
		return result;
	}

	private String extractFieldToCompare(String s) {
		return s.split(",")[4];
	}
}
