package com.dj.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by Darshan on 7/2/2017.
 */
public class FileReader {
	BufferedReader br = null;
	String line = null;

	public FileReader(String fileName) throws Exception {
		br = new BufferedReader(new InputStreamReader(FileReader.class.getResourceAsStream(fileName)));
	}

	public boolean hasNext() throws Exception {
		line = br.readLine();
		return line != null;
	}

	public String next() {
		return line;
	}

	public void close() throws Exception {
		br.close();
	}
}
