package com.dj.cassandra.accessor;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import com.dj.cassandra.model.Node;
import com.google.common.util.concurrent.ListenableFuture;

@Accessor
public interface NodeAccessor {

	@Query("select * from nodes")
	ListenableFuture<Result<Node>> getAllAsync();
}
