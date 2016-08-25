package com.dj.utils.common;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Darshan on 8/10/16.
 */
public class ReadLineWithByteBuffer {
	public static void main(String[] args) throws Exception {
		Path inputFile = Paths.get("target/gen_data.csv");
		FileChannel ic = new RandomAccessFile(inputFile.toFile(), "r").getChannel();
		ByteBuffer buf = ByteBuffer.allocate(130);
		StringBuilder sb = new StringBuilder();
		int bytesRead = -1;
		while ((bytesRead = ic.read(buf)) != -1) {
			if (bytesRead > 0) {
				for (int i = 0; i < buf.array().length; i++) {
					if ((char)buf.array()[i] == '\r') {
						i++;
						System.out.println("Line: " + sb.toString() + "~~");
						sb = new StringBuilder();
					} else {
						sb.append((char)buf.array()[i]);
					}
				}
				buf.clear();
			}
		}
		if (sb.length() > 0) {
			System.out.println("LeftOver: " + sb.toString());
		}
	}
}
