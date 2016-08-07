package com.dj.utils.externalsorting;

import com.dj.utils.common.Timer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Darshan on 8/6/16.
 */
public class Test {
	private static final int SIZE_1_MB = 1 * 1024 * 1024;
	private static final int SIZE_2_MB = 2 * 1024 * 1024;
	private static final String IN_FILE = "target" + File.separator + "gen_data.csv";
	private static final String OUT_FILE = "target" + File.separator + "gen_data_out.txt";

	/*public static void main(String[] args) throws Exception {
		Timer t = Timer.newTimer();
		try (
				SeekableByteChannel sbc = Files.newByteChannel(Paths.get(OUT_FILE));
		        FileChannel wChannel = new RandomAccessFile("target" + File.separator + "gen_data_out.txt", "rw").getChannel()
		) {
			ByteBuffer rBuf = ByteBuffer.allocate(SIZE_1_MB);
			ByteBuffer wBuf = ByteBuffer.allocate(SIZE_1_MB);
			StringBuffer line = new StringBuffer();
			while (sbc.read(rBuf) > 0) {
				rBuf.flip();
				for (int i = 0; i < rBuf.limit(); i++) {
					char c = rBuf.getChar();
					if (c == '\r') {
//						line.append(c);
						wBuf.putChar(c);
//						wChannel.write(ByteBuffer.wrap(line.toString().getBytes()));
						line = new StringBuffer();
					} else {
						wBuf.putChar(c);
						line.append(c);
					}
				}
//				wChannel.write(ByteBuffer.wrap(line.toString().getBytes()));
				wBuf.put(line.toString().getBytes());
				wChannel.write(wBuf);
				wBuf.clear();
				rBuf.clear();
			}
		}
		System.out.println("Took: " + t.time() + " millis");
	}*/

	public static void main(String[] args) throws Exception {
		FileWriter fr = new FileWriter(OUT_FILE);
		for (int i = 0; i < 1000000; i++) {
			fr.write("This is a string " + i + System.lineSeparator());
		}
		fr.close();
	}
}
