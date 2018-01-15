package com.dj.fitnesse.fit;

import com.dj.graph.Graph;
import com.dj.model.Node;
import fit.ColumnFixture;

public class GraphTestColumnFixture extends ColumnFixture {
	private Graph SUT = GraphSUT.INSTANCE.SUT();

	public String cobDate;
	public String nodeType;
	public String nodeId;
	public String action;

	public long countByLabel() {
		if ("add".equals(action)) {
			SUT.addVertex(new Node(cobDate, nodeType, nodeId));
		}
		if ("delete".equals(action)) {
			SUT.removeVertex(new Node(cobDate, nodeType, nodeId));
		}
		return SUT.hasLabel(nodeType).count();
	}

	public long total() {
		return SUT.size();
	}
}
