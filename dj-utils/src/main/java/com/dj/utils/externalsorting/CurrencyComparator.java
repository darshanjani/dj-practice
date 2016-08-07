package com.dj.utils.externalsorting;

import java.util.Comparator;

/**
 * Created by Darshan on 8/6/16.
 */
public class CurrencyComparator implements Comparator<String> {
	@Override
	public int compare(String s1, String s2) {
		return extractFieldToCompare(s1).compareTo(extractFieldToCompare(s2));
	}

	private String extractFieldToCompare(String s) {
		return s.split(",")[4];
	}
}
