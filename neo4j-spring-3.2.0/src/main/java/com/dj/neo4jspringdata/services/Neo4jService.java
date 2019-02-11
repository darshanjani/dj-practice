package com.dj.neo4jspringdata.services;

import org.neo4j.driver.v1.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletionStage;

import static org.neo4j.driver.v1.Values.parameters;

@Service
public class Neo4jService {

	@Autowired
	private Driver driver;

	public String createContext(String contextId) {
		try ( Session session = driver.session() )
		{
			String contextSaveMessage = session.writeTransaction( new TransactionWork<String>()
			{
				@Override
				public String execute( Transaction tx )
				{
					StatementResult result = tx.run( "CREATE (c:Context) " +
									"SET c.context_id = $contextId " +
									"RETURN c.context_id + ', from node ' + id(c)",
							parameters( "contextId", contextId) );
					return result.single().get( 0 ).asString();
				}
			} );
			System.out.println( contextSaveMessage );
			return contextSaveMessage;
		}
	}

	public String createProfile(String contextId, int profileCount) {
		try ( Session session = driver.session() )
		{
			List<Map<String, Object>> props = new ArrayList<>();
			for (int i = 0; i < profileCount; i++) {
				Map<String, Object> prof = new HashMap<>();
				prof.put("name", "P" + i);
				props.add(prof);
			}

			String query = "unwind $props as properties " +
					" create (p:Profile) " +
					" set p = properties " +
					" with p" +
					" match (c:Context) where c.context_id = $contextId " +
					" merge (c)-[:HAS]-(p) ";

			Map<String, Object> params = new HashMap<>();
			params.put("props", props);
			params.put("contextId", contextId);

			session.writeTransaction(tx -> tx.run(query, params));
//				session.runAsync("CREATE (p:Profile) " +
//								"SET p.profile_name = $profileId",
//						parameters("profileId", "P" + i));

//				session.runAsync("match (c:Context),(p:Profile) where c.context_id = $contextId " +
//								" AND p.profile_name = $profileId " +
//								"create (c)-[:HAS]->(p)",
//						parameters("contextId", contextId, "profileId", "P" + i));
			return "";
		}
	}

}
