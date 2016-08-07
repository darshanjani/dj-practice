package com.dj.utils.externalsorting;

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
//		new FileReadWriteComparison().normalBufferedReadWrite();
		new FileReadWriteComparison().fileChannelWithMappedByteBuffer();
		long end = System.currentTimeMillis();
		System.out.format("Time taken: %d%n", (end - start));
	}

	private void normalBufferedReadWrite() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(FILE_TO_READ));
		BufferedWriter out = new BufferedWriter(new FileWriter(FILE_TO_WRITE));
		String line;
		while ( (line = br.readLine()) != null) {
			out.write(line);
			out.write("\n");
		}
		out.close();
		br.close();
	}

	private void fileChannelWithMappedByteBuffer() throws Exception {
		FileChannel rChannel = new RandomAccessFile(FILE_TO_READ, "r").getChannel();
		ByteBuffer rBuf = ByteBuffer.allocate(BIG_FILE_SIZE);

		FileChannel wChannel = new FileOutputStream(FILE_TO_WRITE, true).getChannel();
//		ByteBuffer wrBuf = ByteBuffer.allocate(BIG_FILE_SIZE);
//		ByteBuffer wrBuf = wChannel.map(FileChannel.MapMode.READ_WRITE, 0, BIG_FILE_SIZE);

		int numBytesRead = 0;
		long totalBytesRead = 0;
		while( (numBytesRead = rChannel.read(rBuf)) != -1) {
			if (numBytesRead ==0) continue;
			System.out.format("Bytes read: %d%n", numBytesRead);
//			Thread.sleep(1000);
			rBuf.flip();
			totalBytesRead += numBytesRead;
//			rChannel.position(totalBytesRead);
			wChannel.write(rBuf);
			rBuf.clear();
		}

//		MappedByteBuffer mb = rChannel.map(FileChannel.MapMode.READ_ONLY, 0, rChannel.size());
//		byte[] bArray = new byte[BYTE_ARRAY_SIZE];
//		int nGet;
//		int checkSum = 0;
//		while (mb.hasRemaining()) {
//			nGet = Math.min(mb.remaining(), BYTE_ARRAY_SIZE);
//			checkSum += nGet;
//			mb.get(bArray, 0, nGet);
//			if (checkSum > BIG_FILE_SIZE) {
//				wrBuf.clear();
//				checkSum = 0;
//			}
//			wrBuf.put(bArray, 0, nGet);
//		}
		wChannel.close();
		rChannel.close();
	}
}
