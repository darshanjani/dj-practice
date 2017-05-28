package com.dj;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;


/**
 * Created by Darshan on 5/28/2017.
 */
public class CreateAgreements {

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
	public void createAgreements() {
		CreateNode createNode = new CreateNode(driver);
		createNode.addNode(Labels.Agreement.name(), "A1");
		createNode.addNode(Labels.Trade.name(), "T1");
	}
}
