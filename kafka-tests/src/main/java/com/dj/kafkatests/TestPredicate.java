package com.dj.kafkatests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class TestPredicate {
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("producer", "PBSD");
		map.put("region", "EM");
		map.put("account", "2000");

		List<Map<String, String>> list = new ArrayList<>();
		list.add(map);

		list.stream().filter(getFilter()).forEach(entry -> System.out.println(entry));
	}

	private static Predicate<Map<String, String>> getFilter() {
		Predicate<Map<String, String>> producerPredicate = new Predicate<Map<String, String>>() {
			@Override
			public boolean test(Map<String, String> stringStringMap) {
				return stringStringMap.get("producer").equals("PBSD");
			}
		};

		Predicate<Map<String, String>> regionPredicate = new Predicate<Map<String, String>>() {
			@Override
			public boolean test(Map<String, String> stringStringMap) {
				return stringStringMap.get("region").equals("NA");
			}
		};

		Predicate<Map<String, String>> accountPredicate = new Predicate<Map<String, String>>() {
			@Override
			public boolean test(Map<String, String> stringStringMap) {
				return stringStringMap.keySet()
						.stream()
						.filter(key -> key.equals("account"))
						.map(key -> Integer.parseInt(stringStringMap.get(key)))
						.filter(val -> val > 1000 && val < 2000)
						.count() > 0;
			}
		};

		return (producerPredicate.or(regionPredicate)).and(accountPredicate);
	}
}
