package com.java11.ex01;

import java.util.List;

public class NewFeatures {

	public static void main(String[] args) {
		List<Integer> lists = List.of(1, 2, 3);
		lists.stream().forEach(System.out::println);
	}
}
