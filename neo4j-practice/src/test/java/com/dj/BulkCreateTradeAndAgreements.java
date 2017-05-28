package com.dj;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.driver.v1.*;
import org.springframework.util.StopWatch;

import java.util.concurrent.ThreadLocalRandom;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * Created by Darshan on 5/28/2017.
 */
public class BulkCreateTradeAndAgreements {

	private static Driver driver;
	private static final String cobDate = "2017-05-28";

	@BeforeClass
	public static void beforeClass() {
		driver = GraphDatabase.driver(Constants.NEO_URL, AuthTokens.basic(Constants.NEO_USERNAME, Constants.NEO_PASSWORD));
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

		Session session = null;
		try {
			Transaction tx = null;
			for (int tradeCtr = 1; tradeCtr <= 1_000_000; tradeCtr++) {
				int randomAgreementNumber = ThreadLocalRandom.current().nextInt(1, 60001);
				String agreementNumberKey = "A" + randomAgreementNumber;
				String tradeNumberKey = "T" + tradeCtr;

				if (tradeCtr % 1000 == 0) {
					tx.success();
					tx.close();
					tx = null;
					session.close();
					session = null;
				}

				if (tx == null || session == null || tradeCtr % 1000 == 0) {
					session = driver.session();
					tx = session.beginTransaction();
				}
				insertTradeAndAgreementWithRelationship(agreementNumberKey, tradeNumberKey, tx);
							}
		} finally {
			if (session != null) session.close();
		}
		stopWatch.stop();
		System.out.println(stopWatch.prettyPrint());
	}

	private void insertTradeAndAgreementWithRelationship(String agreementNumberKey, String tradeNumberKey, Transaction tx) {
		String createTradeAndAgreement =
//				"match (d:" + Labels.CobDate.name() + " {name: {cobDate} }) " +
						"merge (t:" + Labels.Trade.name() + " {name: {tr}, cobDate: {cobDate} }) " +
						"merge (a:" + Labels.Agreement.name() + " {name: {ag} }) " +
						"merge (a)-[:" + RelType.DEPENDS.name() + "]->(t) ";
//						"merge (d)-[:" + RelType.HAS.name() + "]->(t) ";
		tx.run(createTradeAndAgreement
				, parameters("ag", agreementNumberKey, "tr", tradeNumberKey, "cobDate", cobDate));
	}

}
