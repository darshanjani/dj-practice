package com.dj;

import org.springframework.util.StopWatch;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

/**
 * Created by Darshan on 5/28/2017.
 */
public class TradeAgreementGenerator {

	public static void main(String[] args) throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("generate");
		Map<String, Set<String>> agreementTradeMapping = new HashMap<>();
		for (int tradeCtr = 1; tradeCtr <= 10_000_000; tradeCtr++) {
			int randomAgreementNumber = ThreadLocalRandom.current().nextInt(1, 60001);
			String agreementNumberKey = "A" + randomAgreementNumber;
			Set<String> tradeSet = null;
			if (agreementTradeMapping.containsKey(agreementNumberKey)) {
				tradeSet = agreementTradeMapping.get(agreementNumberKey);
			} else {
				tradeSet = new HashSet<>();
			}
			tradeSet.add("T" + tradeCtr);
			agreementTradeMapping.put(agreementNumberKey, tradeSet);
		}
		System.out.println(agreementTradeMapping.keySet().size());
		stopWatch.stop();
		stopWatch.start("writeToFile");
		try (BufferedWriter br = Files.newBufferedWriter(Paths.get("C:\\temp\\agreement_trade.csv"))) {
			agreementTradeMapping.keySet().stream().forEach(new Consumer<String>() {
				@Override
				public void accept(String key) {
					try {
						br.write(key + "," + agreementTradeMapping.get(key).size() + "\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		stopWatch.stop();
		System.out.println(stopWatch.prettyPrint());
	}
}
