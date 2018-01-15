package com.dj.fitnesse.slim;

import com.dj.graph.Graph;
import com.dj.model.Node;

public class CanIAddAndDeleteNodesInGraph {
	private Graph SUT = GraphSUT.INSTANCE.SUT();

	private String cobDate;
	private String nodeType;
	private String nodeId;
	private String action;

	public void setCobDate(String cobDate) {
		this.cobDate = cobDate;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void execute() {
		if ("add".equals(action)) {
			SUT.addVertex(new Node(cobDate, nodeType, nodeId));
		}
		if ("delete".equals(action)) {
			SUT.removeVertex(new Node(cobDate, nodeType, nodeId));
		}
	}

	public long countByLabel() {
		return SUT.hasLabel(nodeType).count();
	}

	public long total() {
		return SUT.size();
	}
}
