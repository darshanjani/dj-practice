package com.dj;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.driver.v1.*;
import org.springframework.util.StopWatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * Created by Darshan on 5/28/2017.
 */
public class BulkCreateTradeAndAgreementsMultiThreaded {

	private static Driver driver;
	private static final String cobDate = "2017-05-28";

	@BeforeClass
	public static void beforeClass() {
		Config config = Config.build().withMaxIdleSessions(1000).toConfig();
		driver = GraphDatabase.driver(Constants.NEO_URL, AuthTokens.basic(Constants.NEO_USERNAME, Constants.NEO_PASSWORD), config);
	}

	@AfterClass
	public static void afterClass() {
		driver.close();
	}

	@Test
	public void createTradeAndAgreementsInBulk() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("createTradesAndAgreements");

		try (Session session = driver.session()) {
			String createCobDateNode =
					"merge (d:" + Labels.CobDate.name() + " {name: {cobDate} })";
			session.run(createCobDateNode
					, parameters("cobDate", cobDate));
		}

		ExecutorService executorService = Executors.newFixedThreadPool(1000);
		for (int tradeCtr = 1; tradeCtr <= 100_000; tradeCtr++) {
			int randomAgreementNumber = ThreadLocalRandom.current().nextInt(1, 60001);
			String agreementNumberKey = "A" + randomAgreementNumber;
			String tradeNumberKey = "T" + tradeCtr;
			executorService.submit(new InsertTrade(agreementNumberKey, tradeNumberKey));
		}
		executorService.shutdown();
		executorService.awaitTermination(100, TimeUnit.MINUTES);

		stopWatch.stop();
		System.out.println(stopWatch.prettyPrint());
	}

	class InsertTrade implements Runnable {
		String agreementNumberKey = null;
		String tradeNumberKey = null;

		public InsertTrade(String agreementNumberKey, String tradeNumberKey) {
			this.agreementNumberKey = agreementNumberKey;
			this.tradeNumberKey = tradeNumberKey;
		}

		@Override
		public void run() {
			try (Session session = driver.session()) {
				insertTradeAndAgreementWithRelationship(agreementNumberKey, tradeNumberKey, session);
			}
		}
	}

	private void insertTradeAndAgreementWithRelationship(String agreementNumberKey, String tradeNumberKey, Session session) {
		String createTradeAndAgreement =
//				"match (d:" + Labels.CobDate.name() + " {name: {cobDate} }) " +
						"merge (t:" + Labels.Trade.name() + " {name: {tr}, cobDate: {cobDate} }) " +
						"merge (a:" + Labels.Agreement.name() + " {name: {ag} }) " +
						"merge (a)-[:" + RelType.DEPENDS.name() + "]->(t) ";
//						"merge (d)-[:" + RelType.HAS.name() + "]->(t) ";
		session.run(createTradeAndAgreement
				, parameters("ag", agreementNumberKey, "tr", tradeNumberKey, "cobDate", cobDate));
	}

}
