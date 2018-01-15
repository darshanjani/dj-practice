package com.dj.cassandra.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = {"persisted"})
public class Relationship {
	private Node from;
	private String type;
	private Node to;
	private boolean persisted = false;
}
