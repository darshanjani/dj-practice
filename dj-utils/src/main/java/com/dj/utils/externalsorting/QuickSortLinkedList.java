package com.dj.utils.externalsorting;

import java.util.LinkedList;

/**
 * Created by Darshan on 7/27/16.
 */
public class QuickSortLinkedList implements QuickSort {
	private LinkedList<String> list;
	private int length;

	@Override
	public void sort(Object items) {
		if (!(items instanceof LinkedList)) {
			throw new IllegalArgumentException("Please pass LinkedList<String> as input");
		}
		LinkedList<String> inputList = (LinkedList<String>) items;
		if (inputList == null || inputList.size() == 0) return;
		list = inputList;
		length = inputList.size();
		quickSort(0, length - 1);
	}

	private void quickSort(int lowerIndex, int higherIndex) {
		int i = lowerIndex;
		int j = higherIndex;

		//calculate pivot - mostly middle of index
		String pivot = list.get(lowerIndex + (higherIndex - lowerIndex) / 2);
		String pivotKey = extractFieldToCompare(pivot);
		while (i <= j) {
			while (extractFieldToCompare(list.get(i)).compareTo(pivotKey) < 0) {
				i++;
			}
			while (extractFieldToCompare(list.get(j)).compareTo(pivotKey) > 0) {
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
		String temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
	}

	public static void main(String[] args) {
		QuickSortLinkedList sorter = new QuickSortLinkedList();
		LinkedList<String> strings = new LinkedList<>();
		strings.add(",,,,APPLE"); strings.add(",,,,CAT"); strings.add(",,,,BALL"); strings.add(",,,,DOG");
		System.out.println("Unsorted: " + strings);
		sorter.sort(strings);
		System.out.println("Sorted: " + strings);
	}
}
