package com.dj.utils.mergesort;

public class MergeSort {
	public static final boolean DEBUG = true;

	public static void main(String[] args) {
		MergeSort ms = new MergeSort();
		//int[] inputArr = {5, 2, 7, 3, 1, 6, 8, 4, 9};
		int[] inputArr = {1,2,3,4,5,6,7,8,9,10};
		//int[] inputArr = {10,9,8,7,6,5,4,3,2,1,2,1};
		//int[] inputArr = {1};
		//int[] inputArr = {1,2};
		//int[] inputArr = {1,2,3};
		System.out.println(print("Initial Input: ", inputArr));
		int[] outputArr = ms.divideSortMerge(inputArr);
		System.out.println(print("Final Output: ", outputArr));
	}

    public int[] divideSortMerge(int[] input) {
        int[][] divided = divide(input);
        int[] left = sort(divided[0]);
        int[] right = sort(divided[1]);
        return merge(left, right);
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
			}
		}
		return result;
	}
	
	int[] merge(int[] A, int[] B) {
		//System.out.println("Merging: " + print(" Left = ", A) + print(", Right = ", B));
		int[] merged = new int[A.length + B.length];
		int i = 0, j = 0;
		for (int k = 0; k < merged.length; k++) {
			if (i != A.length && j != B.length) 
			{//Neither i nor j have reached the end of the arrays, so we can safely compare and merge
				if (A[i] < B[j]) 
					merged[k] = A[i++];
				else
					merged[k] = B[j++];
			} 
			else if (i == A.length) 
			{
				merged[k] = B[j++];
			} else if (j == B.length) {
				merged[k] = A[i++];
			}
		}
		if (DEBUG) System.out.println("Merged: " 
			+ print("Left = ", A) 
			+ " with " 
			+ print("Right = ", B)
			+ " equals "
			+ print("Final = ", merged)
		);
		return merged;
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
