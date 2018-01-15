package com.dj.fitnesse.slim;

import com.dj.graph.Graph;
import com.dj.model.Node;

public class ResetAndCheckOperationsViaScript {
	private Graph SUT = GraphSUT.INSTANCE.SUT();

	public void resetTheGraph() {
		GraphSUT.INSTANCE.reset();
		SUT = GraphSUT.INSTANCE.SUT();
	}

	public void addNode(String type, String id, String cobDate) {
		SUT.addVertex(new Node(cobDate, type, id));
	}

	public long graphSize() {
		return SUT.size();
	}
}
