package com.dj.cassandra.model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;
import lombok.Data;

@Data
@Table(keyspace = "darshan", name = "relationships")
public class CassandraRelationship {
	@Column(name = "context_id") private String contextId;
	@Column(name = "relationship_type") private String relationshipType;
	@Column(name = "source_node_id") private String sourceNodeId;
	@Column(name = "source_node_type") private String sourceNodeType;
	@Column(name = "target_node_id") private String targetNodeId;
	@Column(name = "target_node_type") private String targetNodeType;
}
