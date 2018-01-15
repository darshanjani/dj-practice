package com.dj.model;

/**
 * Created by Darshan on 7/2/2017.
 */
public class Relationship {
	private Node from;
	private String type;
	private Node to;

	public Relationship(Node from, String type, Node to) {
		this.from = from;
		this.type = type;
		this.to = to;
	}

	public Node getFrom() {
		return from;
	}

	public String getType() {
		return type;
	}

	public Node getTo() {
		return to;
	}
}
