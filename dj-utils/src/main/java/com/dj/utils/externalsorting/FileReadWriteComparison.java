package com.dj.utils.externalsorting;

import com.dj.utils.common.Timer;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Darshan on 8/6/16.
 */
public class FileReadWriteComparison {
	private static final String FILE_TO_READ = "target" + File.separator + "gen_data.csv";
	private static final String FILE_TO_WRITE = "target" + File.separator + "gen_data_out.csv";
	private static final int BYTE_SIZE_1_KB = 1024;
	private static final int BYTE_SIZE_1_MB = BYTE_SIZE_1_KB * BYTE_SIZE_1_KB;
	private static final int BYTE_ARRAY_SIZE = 8 * BYTE_SIZE_1_KB;
	private static final int BIG_FILE_SIZE = 10 * BYTE_SIZE_1_MB;

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		new FileReadWriteComparison().normalBufferedReadWrite();
//		new FileReadWriteComparison().fileChannelWithByteBuffer();
//		new FileReadWriteComparison().bufferedReadNormalWrite();
		long end = System.currentTimeMillis();
		System.out.format("Time taken: %d%n", (end - start));
	}

	private void normalBufferedReadWrite() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(FILE_TO_READ));
		BufferedWriter out = new BufferedWriter(new FileWriter(FILE_TO_WRITE));
		String line;
		while ( (line = br.readLine()) != null) {
			out.write(line);
			out.write(System.lineSeparator());
		}
		out.close();
		br.close();
	}

	private void bufferedReadNormalWrite() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(FILE_TO_READ));
		FileWriter out = new FileWriter(FILE_TO_WRITE);
		String line;
		while ( (line = br.readLine()) != null) {
			out.write(line);
			out.write(System.lineSeparator());
		}
		out.close();
		br.close();
	}

	private void fileChannelWithByteBuffer() throws Exception {
		FileChannel rChannel = new RandomAccessFile(FILE_TO_READ, "r").getChannel();
		ByteBuffer rBuf = ByteBuffer.allocate(BYTE_SIZE_1_MB);
		FileWriter writer = new FileWriter(new File(FILE_TO_WRITE));
		StringBuilder sb = new StringBuilder();
		int bytesRead = -1;
		while ((bytesRead = rChannel.read(rBuf)) != -1) {
			if (bytesRead > 0) {
				for (int i = 0; i < rBuf.array().length; i++) {
					if ((char)rBuf.array()[i] == '\r') {
						i++;
						writer.write(sb.toString());
						writer.write(System.lineSeparator());
						sb = new StringBuilder();
					} else {
						sb.append((char)rBuf.array()[i]);
					}
				}
				rBuf.clear();
			}
		}
		if (sb.length() > 0) {
			writer.write(sb.toString());
			writer.write(System.lineSeparator());
		}
		writer.close();
		rChannel.close();
	}
}
