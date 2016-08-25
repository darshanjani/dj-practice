package com.dj.utils;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class App
{
	public static void main(String args[]) throws Exception {
		int max = 1_000_000;
		List<String> values = new ArrayList<>(max);
		for (int i = 0; i < max; i++) {
			values.add(UUID.randomUUID().toString());
		}

		long t0 = System.nanoTime();
		long count = values.parallelStream().sorted().count();
		long t1 = System.nanoTime();
		long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
		System.out.println(count + "~~" + millis);
		ZoneId.getAvailableZoneIds().forEach(System.out::println);
	}

}