package com.dj;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * Created by Darshan on 5/28/2017.
 */
public class CreateNode {
	private final Driver driver;

	public CreateNode(Driver driver) {
		this.driver = driver;
	}

	public void addNode(String label, String nodeName) {
		try (Session session = driver.session()) {
			try (Transaction tx = session.beginTransaction()) {
				tx.run("create (a:" + label + " { name: {name} })"
						, parameters(Constants.NODE_PROPERTY_NAME, nodeName));
				tx.success();
			}
		}
	}
}
