package com.dj.utils.externalsorting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static com.dj.utils.common.CommonDjUtils.*;

/**
 * Created by Darshan on 8/7/16.
 */
public class SortAndMergeSortedFiles {

	public void sortAndMergeAlreadySortedFiles(Path inputFile1, Path inputFile2, Path outPath) throws IOException {
		try (
				BufferedReader br1 = Files.newBufferedReader(inputFile1);
				BufferedReader br2 = Files.newBufferedReader(inputFile2);
				BufferedWriter out = Files.newBufferedWriter(outPath);
		) {
			String line1 = br1.readLine(), line2 = br2.readLine();
			String outLine;
			readFiles:
			while (true) {
				if (areBothInputBuffersConsumed(line1, line2)) {
					break readFiles;
				}
				if (isEmpty(line1)) {
					outLine = line2;
				} else if (isEmpty(line2)) {
					outLine = line1;
				} else {
					outLine = returnSmallerOfBothStrings(line1.trim(), line2.trim());
				}
				if (outLine.equals(line1)) {
					line1 = br1.readLine();
				} else {
					line2 = br2.readLine();
				}
				out.write(outLine + NEWLINE);
			}
		}
	}

	private boolean areBothInputBuffersConsumed(String line1, String line2) {
		return isEmpty(line1) && isEmpty(line2);
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
