package com.dj.utils.bulkdata;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Created by Darshan on 8/6/16.
 */
public class BulkDataGenerator {
	private static final long NO_OF_ROWS_TO_GENERATE = 5_000_000;
	private static final int ROWS_PADDING = Long.toString(NO_OF_ROWS_TO_GENERATE).length();
	private static final String OUTPUT_FILE = "target/gen_data.csv";
	private String[] currencies = new String[]{"ALL", "EUR", "GBP", "DZD", "USD", "AOA", "XCD", "ARS", "AMD", "AWG"};
	private Random random = new Random();

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		new BulkDataGenerator().start();
		long end = System.currentTimeMillis();
		System.out.format("Bulk Data generation took %d millis%n", (end - start));
	}

	private void start() throws Exception {
		try (BufferedWriter br = Files.newBufferedWriter(Paths.get(OUTPUT_FILE))) {
			for (int i = 0; i < NO_OF_ROWS_TO_GENERATE; i++) {
				br.write(
					String.format(
						"%s,%s,%s,%s,%s,%s,%s,%s%n",
						String.format("%0" + ROWS_PADDING + "d", i),
						"FIELD2",
						"FIELD3",
						"FIELD4",
						currencies[random.nextInt(currencies.length)],
						"FIELD6",
						"FIELD7",
						"FIELD8"
					)
				);
			}
		}
	}
}
