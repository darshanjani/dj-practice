package com.dj.cassandra.model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Table(keyspace = "darshan", name = "nodes")
@EqualsAndHashCode(exclude = {"incoming", "outgoing", "incomingCount", "outgoingCount", "nodeData", "available"})
@ToString(exclude = {"incoming", "outgoing", "nodeData", "available"})
public class Node {
	@Transient private Set<Relationship> incoming = new HashSet<>();
	@Transient private Set<Relationship> outgoing = new HashSet<>();

	@Transient private int incomingCount;
	@Transient private int outgoingCount;

	@Column(name = "context_id")
	private String contextId;

	@Column(name = "node_id")
	private String id;

	@Column(name = "node_type")
	private String type;

	@Column(name = "state")
	private boolean available;

	@Column(name = "node_data")
	private Map<String, String> nodeData = new HashMap<>();

	public void put(String key, String value) {
		nodeData.put(key, value);
	}

	@Transient
	public Set<String> getKeys() {
		return nodeData.keySet();
	}

	@Transient
	public String get(String key) {
		return nodeData.get(key);
	}

	@Transient
	public Relationship addEdge(Node target, String relationshipType) {
		Relationship relationship = new Relationship();
		relationship.setFrom(this);
		relationship.setType(relationshipType);
		relationship.setTo(target);
		this.outgoing.add(relationship);
		this.outgoingCount++;
		target.incoming.add(relationship);
		target.incomingCount++;
		return relationship;
	}
}
