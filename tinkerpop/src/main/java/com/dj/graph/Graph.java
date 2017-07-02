package com.dj.graph;

import com.dj.model.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Darshan on 7/2/2017.
 */
public class Graph {
	private Map<Node, Node> repo = new HashMap<>();

	private static final Graph graph = new Graph();
	public static Graph open() {
		return graph;
	}

	public void addVertex(Node node) {
		repo.put(node, node);
	}

	public Stream<Node> hasLabel(String type) {
		return repo.values().parallelStream()
				.filter(node -> node.getType().equals(type));
	}

	public Optional<Node> getNode(String cobDate, String type, String id) {
		return Optional.ofNullable(repo.get(new Node(cobDate, type, id)));
	}

	public void clear() {
		repo.clear();
	}
}
