package com.dj.utils.externalsorting;

import java.util.Arrays;

/**
 * Created by Darshan on 7/27/16.
 */
public class QuickSortArray implements QuickSort {
	private String[] array;
	private int length;

	@Override
	public void sort(Object items) {
		if (!(items instanceof String[])) {
			throw new IllegalArgumentException("Please pass String[] array as input");
		}
		String[] inputArr = (String[])items;
		if (inputArr == null || inputArr.length == 0) return;
		array = inputArr;
		length = inputArr.length;
		quickSort(0, length - 1);
	}

	private void quickSort(int lowerIndex, int higherIndex) {
		int i = lowerIndex;
		int j = higherIndex;

		//calculate pivot - mostly middle of index
		String pivot = array[lowerIndex + (higherIndex - lowerIndex) / 2];
		String pivotKey = extractFieldToCompare(pivot);
		while (i <= j) {
			while (extractFieldToCompare(array[i]).compareTo(pivotKey) < 0) {
				i++;
			}
			while (extractFieldToCompare(array[j]).compareTo(pivotKey) > 0) {
				j--;
			}
			if (i <= j) {
				exchange(i, j);
				i++;
				j--;
			}
		}
		if (lowerIndex < j) {
			quickSort(lowerIndex, j);
		}
		if (i < higherIndex) {
			quickSort(i, higherIndex);
		}
	}

	private String extractFieldToCompare(String s) {
		return s.split(",")[4];
	}

	private void exchange(int i, int j) {
		String temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	public static void main(String[] args) {
		QuickSort sorter = new QuickSortArray();
		String[] strings = {"APPLE", "CAT", "BALL", "DOG"};
		System.out.println(Arrays.asList(strings));
		sorter.sort(strings);
		System.out.println(Arrays.asList(strings));
	}
}
