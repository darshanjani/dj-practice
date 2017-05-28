package com.dj;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * Created by Darshan on 5/28/2017.
 */
public class TestQueries {

	static Driver driver;

	@BeforeClass
	public static void beforeClass() {
		driver = GraphDatabase.driver(Constants.NEO_URL, AuthTokens.basic(Constants.NEO_USERNAME, Constants.NEO_PASSWORD));
	}

	@AfterClass
	public static void afterClass() {
		driver.close();
	}

	@Test
	public void testVariousQueries() {
		deleteAllDataInDb();
		createCobDate();
		createNineTransactions();
		createThreeAgreements();
		linkCobDateToEachTrade();
		linkAgreementToTrade();
		markStatusOfSomeTradesCompleteOverTime();
		checkAgreementIsFullyReadyForT1();
		checkAgreementIsFullyReadyForT7();
	}

	private void deleteAllDataInDb() {
		try (Session session = driver.session()) {
			session.run("match (n) detach delete n");
		}
	}

	private void createCobDate() {
		try (Session session = driver.session()) {
			session.run("create (d:CobDate {name: \"2017-05-28\"})");
		}
	}

	private void createNineTransactions() {
		try (Session session = driver.session()) {
			for (int i = 1; i <= 9; i++) {
				session.run("create (d:Trade {name: \"T" + i + "\", status: \"INCOMPLETE\"})");
			}
		}
	}

	private void createThreeAgreements() {
		try (Session session = driver.session()) {
			for (int i = 1; i <= 3; i++) {
				session.run("create (a:Agreement {name: \"A" + i + "\", status: \"NOT_PROCESSED\"})");
			}
		}
	}

	private void linkCobDateToEachTrade() {
		try (Session session = driver.session()) {
			for (int i = 1; i <= 9; i++) {
				session.run("match (d:CobDate {name: \"2017-05-28\"}) match (t:Trade {name: \"T" + i + "\"}) create (d)-[:HAS]->(t)");
			}
		}
	}

	private void linkAgreementToTrade() {
		try (Session session = driver.session()) {
			for (int i = 1; i <= 3; i++) {
				session.run("match (d:CobDate {name: \"2017-05-28\"})-->(t:Trade {name: \"T" + (i * 3) + "\"}) match (a:Agreement {name: \"A" + i + "\"}) create (a)-[:DEPENDS]->(t)");
				session.run("match (d:CobDate {name: \"2017-05-28\"})-->(t:Trade {name: \"T" + (i * 3 - 1) + "\"}) match (a:Agreement {name: \"A" + i + "\"}) create (a)-[:DEPENDS]->(t)");
				session.run("match (d:CobDate {name: \"2017-05-28\"})-->(t:Trade {name: \"T" + (i * 3 - 2) + "\"}) match (a:Agreement {name: \"A" + i + "\"}) create (a)-[:DEPENDS]->(t)");
			}
		}
	}

	private void markStatusOfSomeTradesCompleteOverTime() {
		try (Session session = driver.session()) {
			session.run("match (d:CobDate {name: \"2017-05-28\"})-[:HAS]->(t:Trade {name: \"T1\"}) set t.status = 'COMPLETE'");
			session.run("match (d:CobDate {name: \"2017-05-28\"})-[:HAS]->(t:Trade {name: \"T7\"}) set t.status = 'COMPLETE'");
			session.run("match (d:CobDate {name: \"2017-05-28\"})-[:HAS]->(t:Trade {name: \"T8\"}) set t.status = 'COMPLETE'");
			session.run("match (d:CobDate {name: \"2017-05-28\"})-[:HAS]->(t:Trade {name: \"T9\"}) set t.status = 'COMPLETE'");
		}
	}

	private void checkAgreementIsFullyReadyForT1() {
		try (Session session = driver.session()) {
			StatementResult result = session.run("match (d:CobDate {name: \"2017-05-28\"})-[:HAS]->(t:Trade {name: \"T1\"})<-[:DEPENDS]-(a:Agreement)-[:DEPENDS]->(t2:Trade) where not t2.status = 'INCOMPLETE' and not t.status = 'INCOMPLETE' return distinct a");
			Assert.assertFalse(result.hasNext());
		}
	}

	private void checkAgreementIsFullyReadyForT7() {
		try (Session session = driver.session()) {
			StatementResult result = session.run("match (d:CobDate {name: \"2017-05-28\"})-[:HAS]->(t:Trade {name: \"T7\"})<-[:DEPENDS]-(a:Agreement)-[:DEPENDS]->(t2:Trade) where not t2.status = 'INCOMPLETE' and not t.status = 'INCOMPLETE' return distinct a");
			Assert.assertTrue(result.hasNext());
			if (result.hasNext()) {
				Record record = result.next();
				Value value = record.get("a");
				Assert.assertEquals("Only A3 should be returned", "A3", value.get("name").asString());
			}
		}
	}
}
