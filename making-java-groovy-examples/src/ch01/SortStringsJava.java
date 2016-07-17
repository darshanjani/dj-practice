package ch01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortStringsJava {
	public static void main(String[] args) {
		List<String> strings = new ArrayList<String>();
		strings.add("This");
		strings.add("is");
		strings.add("a");
		strings.add("string");
		strings.add("list");
		
		Collections.sort(strings);
		System.out.println(strings);
        System.out.println(new StringSorter().sortByDecreasingLength(strings));
	}
}
