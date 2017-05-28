package com.dj;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * Created by Darshan on 5/28/2017.
 */
public class CreateRelationships {

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
	public void createRelationship() {
		try (Session session = driver.session()) {
			try (Transaction tx = session.beginTransaction()) {
				tx.run("match (a:" + Labels.Agreement.name() + " {name: {ag} }) " +
								"match (t:" + Labels.Trade.name() + " {name: {tr} }) " +
								"create (a)-[:" + RelType.DEPENDS.name() + "]->(t)"
						, parameters("ag", "A1", "tr", "T1"));
				tx.success();
			}
		}
	}
}
