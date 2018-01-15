package com.dj.cassandra.accessor;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.dj.cassandra.model.CassandraRelationship;
import com.google.common.util.concurrent.ListenableFuture;

@Accessor
public interface RelationshipAccessor {

	@Query("insert into relationships(context_id, relationship_type, source_node_id, " +
			"source_node_type, target_node_id, target_node_type) " +
			"values(:context_id, :relationship_type, :source_node_id, " +
			":source_node_type, :target_node_id, :target_node_type)")
	ResultSet insert(
			@Param("context_id") String context_id,
			@Param("relationship_type") String relationship_type,
			@Param("source_node_id") String source_node_id,
			@Param("source_node_type") String source_node_type,
			@Param("target_node_id") String target_node_id,
			@Param("target_node_type") String target_node_type
	);

	@Query("select * from relationships where context_id = ?")
	ListenableFuture<Result<CassandraRelationship>> getAllAsync(String contextId);
}
