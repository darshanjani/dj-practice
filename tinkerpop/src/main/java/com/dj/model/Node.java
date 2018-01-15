package com.dj.model;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Darshan on 7/2/2017.
 */
public class Node {

	private String cobDate;
	private String type;
	private String id;
	private boolean available = false;
	Map<String, Object> data = new HashMap<>();
	private Set<Relationship> out = new HashSet<>();
	private Set<Relationship> in = new HashSet<>();

	public Node(String cobDate, String type, String id) {
		if (cobDate.isEmpty() || type.isEmpty() || id.isEmpty())
			throw new IllegalArgumentException("cobDate, Type or ID cannot be null");
		this.cobDate = cobDate;
		this.type = type;
		this.id = id;
	}

	public String getCobDate() {
		return cobDate;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Node node = (Node) o;

		if (!cobDate.equals(node.cobDate)) return false;
		if (!type.equals(node.type)) return false;
		return id.equals(node.id);
	}

	@Override
	public int hashCode() {
		int result = cobDate.hashCode();
		result = 31 * result + type.hashCode();
		result = 31 * result + id.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Node{" +
				"cobDate='" + cobDate + '\'' +
				", type='" + type + '\'' +
				", id='" + id + '\'' +
				", available=" + available +
				'}';
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void addEdge(String relationType, Node to) {
		Relationship relationship = new Relationship(this, relationType, to);
		out.add(relationship);
		to.in.add(relationship);
	}

	public Stream<Node> out(String relationType) {
		return out.stream()
				.filter(relationship -> relationship.getType().equals(relationType))
				.map(relationship -> relationship.getTo());
	}

	public Stream<Node> out() {
		return out.stream()
				.map(relationship -> relationship.getTo());

	}

	public Stream<Node> in() {
		return in.stream()
				.map(relationship -> relationship.getFrom());
	}

	public Stream<Node> in(String relationType) {
		return in.stream()
				.filter(relationship -> relationship.getType().equals(relationType))
				.map(relationship -> relationship.getFrom());
	}

	public boolean isReadyToProcess() {
		Iterator<Node> iterator = out.stream().map(rel -> rel.getTo()).iterator();
		boolean ready = true;
		while (iterator.hasNext()) {
			Node node = iterator.next();
			if (!node.isAvailable()) {
				ready = false;
				break;
			}
		}
		return ready;
	}
}
