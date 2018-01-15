package com.dj.fitnesse.fit;

import com.dj.graph.Graph;
import com.dj.model.Node;
import fitlibrary.DoFixture;

public class GraphTestDoFixture extends DoFixture {
	private Graph SUT = GraphSUT.INSTANCE.SUT();

	public void addNode(String type, String id, String cobDate) {
		SUT.addVertex(new Node(cobDate, type, id));
	}

	public boolean checkGraphSize(long size) {
		System.out.println("Size is: " + size);
		System.out.println("Graph size is: " + SUT.size());
		return size == SUT.size();
	}
}
