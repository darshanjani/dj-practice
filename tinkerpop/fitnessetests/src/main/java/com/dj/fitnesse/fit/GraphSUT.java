package com.dj.fitnesse.fit;

import com.dj.graph.Graph;

public enum GraphSUT {
	INSTANCE;

	private static Graph graph = new Graph();

	public Graph SUT() {
		return graph;
	}

	public void reset() {
		graph.clear();
	}

}
