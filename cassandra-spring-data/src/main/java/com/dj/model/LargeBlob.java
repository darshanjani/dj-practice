package com.dj.model;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Table;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.cassandra.mapping.PrimaryKey;

import java.nio.ByteBuffer;

@Table(name="large_object_blob", keyspace = "darshan")
@Data
@ToString
public class LargeBlob {
	@PrimaryKey
	private String context_id;
	@ClusteringColumn
	private int record_num;

	private ByteBuffer object;
}
