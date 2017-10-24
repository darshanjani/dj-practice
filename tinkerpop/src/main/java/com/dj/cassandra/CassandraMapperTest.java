package com.dj.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.dj.cassandra.accessor.NodeAccessor;
import com.dj.cassandra.accessor.RelationshipAccessor;
import com.dj.cassandra.model.Node;
import com.dj.cassandra.model.Relationship;
import com.google.common.util.concurrent.ListenableFuture;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CassandraMapperTest {

	private static Cluster cluster;
	private static Session session;
	private static MappingManager mappingManager;

	@BeforeClass
	public static void beforeClass() {
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		Metadata metadata = cluster.getMetadata();
		System.out.println("metadata.getClusterName() = " + metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			System.out.printf("Datacenter: %s, Host: %s, Rack: %s\n",
					host.getDatacenter(), host.getAddress(), host.getRack());
		}
		session = cluster.connect("darshan");
		mappingManager = new MappingManager(session);
	}

	@AfterClass
	public static void afterClass() {
		cluster.close();
		session.close();
	}

	@Test
	public void aTestConnectivity() {
		session.execute("select * from nodes").getColumnDefinitions().asList().forEach(definition -> {
			System.out.println(definition.getName() + "::" + definition.getType());
		});
	}

	@Test
	public void insertNode() {
		Mapper<Node> mapper = mappingManager.mapper(Node.class);
		RelationshipAccessor accessor = mappingManager.createAccessor(RelationshipAccessor.class);

		Node agreement = createAgreement();
		Node t1 = createTrade("1");
		mapper.save(t1);

		Relationship rel = agreement.addEdge(t1, "ASSIGNED");
		mapper.save(agreement);
		accessor.insert(rel.getFrom().getContextId(),
				rel.getType(),
				rel.getFrom().getId(),
				rel.getFrom().getType(),
				rel.getTo().getId(),
				rel.getTo().getType()
				);

		Node t2 = createTrade("2");
		rel = agreement.addEdge(t2, "ASSIGNED");


		mapper.save(t2);
		accessor.insert(rel.getFrom().getContextId(),
				rel.getType(),
				rel.getFrom().getId(),
				rel.getFrom().getType(),
				rel.getTo().getId(),
				rel.getTo().getType()
		);

		assertEquals(0, agreement.getIncomingCount());
		assertEquals(2, agreement.getOutgoingCount());
		assertEquals(1, t1.getIncomingCount());
		assertEquals(0, t1.getOutgoingCount());
	}

	private Node createAgreement() {
		Node node = new Node();
		node.setContextId("2017-10-25");
		node.setId("A1");
		node.setType("Agreement");
		node.setAvailable(false);
		node.put("ucn", "U1");
		node.put("description", "Agreement 1");
		return node;
	}

	private Node createTrade(String tradeNum) {
		Node node = new Node();
		node.setContextId("2017-10-25");
		node.setId("T" + tradeNum);
		node.setType("Trade");
		node.setAvailable(false);
		node.put("feed", "PYR1912ALL");
		node.put("desc", "Trade " + tradeNum);
		return node;
	}

	@Test
	public void readNodesAndRelationships() throws ExecutionException, InterruptedException {
		String contextId = "2017-10-25";
		Mapper<Node> mapper = mappingManager.mapper(Node.class);
		NodeAccessor nodeAccessor = mappingManager.createAccessor(NodeAccessor.class);
		RelationshipAccessor relationshipAccessor = mappingManager.createAccessor(RelationshipAccessor.class);

		ListenableFuture<Result<Node>> nodesFuture = nodeAccessor.getAllAsync();
		Map<Node, Node> nodes = new HashMap<>();
		nodesFuture.get().all().stream().forEach(node -> nodes.put(node, node));

		relationshipAccessor.getAllAsync(contextId).get().all().forEach(action -> {
			Node sourceNodeKey = new Node();
			sourceNodeKey.setContextId(contextId);
			sourceNodeKey.setId(action.getSourceNodeId());
			sourceNodeKey.setType(action.getSourceNodeType());

			Node targetNodeKey = new Node();
			targetNodeKey.setContextId(contextId);
			targetNodeKey.setId(action.getTargetNodeId());
			targetNodeKey.setType(action.getTargetNodeType());

			Node sourceNode = nodes.get(sourceNodeKey);
			Node targetNode = nodes.get(targetNodeKey);

			sourceNode.addEdge(targetNode, action.getRelationshipType());
			nodes.put(sourceNode, sourceNode);
			nodes.put(targetNode, targetNode);
		});


		assertEquals(0, nodes.get(createAgreement()).getIncomingCount());
		assertEquals(2, nodes.get(createAgreement()).getOutgoingCount());
		assertEquals(1, nodes.get(createTrade("1")).getIncomingCount());
		assertEquals(0, nodes.get(createTrade("1")).getOutgoingCount());
	}
}
