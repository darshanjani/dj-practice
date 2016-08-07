package com.dj.utils.mergesort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InversionCount {
	public static final boolean DEBUG = false;
	public static final String FILE_PATH = "C:\\Learning\\Coursera\\Algorithms";
	public static final String INPUT_FILE_NAME = "IntegerArray.txt";
	
	static long inversionCount = 0;
	
	public static void main(String[] args) {
		InversionCount ms = new InversionCount();
		//int[] inputArr = {1,3,5,2,4,6};
		//int[] inputArr = {6,5,4,3,2,1};
		//int[] inputArr = {8,7,6,5,4,3,2,1};
		//int[] inputArr = {10,9,8,7,6,5,4,3,2,1};
		int[] inputArr = readFile();
		if (DEBUG) System.out.println(print("Initial Input: ", inputArr));
		System.out.println("Input arr len: " + inputArr.length);
		int[] outputArr = ms.divideSortMerge(inputArr);
		if (DEBUG) System.out.println(print("Final Output: ", outputArr));
		System.out.format("Inversion count: %d%n", inversionCount);
	}
	
	private static int[] readFile() {
		int[] result = null;
		File dir = new File(FILE_PATH);
		File inpFile = new File(dir, INPUT_FILE_NAME);
		BufferedReader reader = null;
		List<Integer> inpList = new ArrayList<Integer>();
		try {
			reader = new BufferedReader(new FileReader(inpFile));
			String str;
			while ( (str = reader.readLine()) != null) {
				inpList.add(Integer.valueOf(str));
			}
			result = new int[inpList.size()];
			for (int i = 0; i < inpList.size(); i++) {
				result[i] = inpList.get(i);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) { 
				try {
					reader.close();
				} catch(IOException ie) {}
			}
		}
		return result;
	}
	
	public int[] divideSortMerge(int[] input) {
		int[][] divided = divide(input);
		int[] left = sort(divided[0]);
		int[] right = sort(divided[1]);
		return merge(left, right);
	}
	
	int[][] divide(int[] input) {
		//The result will be 2 dimensional array.
		//The first dimension i.e. index 0 - Will hold the left side of integers from input array
		//The right dimension i.e. index 1 - Will hold the right side of integers from input array
		int result[][] = new int[2][];
		
		//Get the left length as an integer value of division by 2
		int leftLen = input.length/2;
		
		//If length of the array is odd, then add 1 to the division by 2.
		int rightLen = (input.length %2 == 0) ? (input.length/2) : ((input.length/2)+1);
		
		if (DEBUG) System.out.format("Left length: %d%n", leftLen);
		if (DEBUG) System.out.format("Right length: %d%n", rightLen);
		result[0] = copy(input, 0, leftLen);
		if (DEBUG) System.out.println(print("Left arr", result[0]));
		result[1] = copy(input, leftLen, rightLen);
		if (DEBUG) System.out.println(print("Right arr", result[1]));
		return result;
	}
	
	int[] sort(int[] input) {
		if (DEBUG) System.out.println("Sorting: " + print("", input));
		int[] result = new int[2];
		if (input.length == 0 || input.length == 1) {
			return input;
		} else if (input.length > 2) {
			result = divideSortMerge(input);
		} else {
			if (input[0] < input[1]) {
				return input;
			} else {
				result[0] = input[1];
				result[1] = input[0];
				inversionCount++;
			}
		}
		return result;
	}
	
	int[] merge(int[] leftArray, int[] rightArray) {
		//System.out.println("Merging: " + print(" Left = ", A) + print(", Right = ", B));
		int[] merged = new int[leftArray.length + rightArray.length];
		int i = 0, j = 0;
		for (int k = 0; k < merged.length; k++) {
			if (i != leftArray.length && j != rightArray.length) 
			{//Neither i nor j have reached the end of the arrays, so we can safely compare and merge
				if (leftArray[i] < rightArray[j]) { 
					merged[k] = leftArray[i++];
				} else {
					//Only subtracting j since j is a zero based index,
					//otherwise, if j started with 1, then we will need to add 1
					//(B.length - j) returns no. of pending element in LEFT array
					inversionCount += leftArray.length - i;
					merged[k] = rightArray[j++];
				}
			} 
			else if (i == leftArray.length) 
			{
				merged[k] = rightArray[j++];
			} 
			else if (j == rightArray.length) 
			{
				merged[k] = leftArray[i++];
			}
		}
		if (DEBUG) System.out.println("Merged: " 
			+ print("Left = ", leftArray) 
			+ " with " 
			+ print("Right = ", rightArray)
			+ " equals "
			+ print("Final = ", merged)
			+ ", with Inversion Count: " + inversionCount
		);
		return merged;
	}
	
	int[] copy(int[] input, int offset, int length) {
		int[] result = new int[length];
		for (int i = offset, ctr = 0; i < offset + length; i++) {
			result[ctr++] = input[i];
		}
		return result;
	}
	
	static String print(String arrayName, int[] arr) {
		StringBuffer sb = new StringBuffer(arrayName);
		sb.append("[");
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]);
			if (i != arr.length -1) sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
}
