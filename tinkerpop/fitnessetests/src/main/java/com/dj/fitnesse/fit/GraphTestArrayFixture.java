package com.dj.fitnesse.fit;

import com.dj.graph.Graph;
import com.dj.model.Node;
import fitlibrary.ArrayFixture;

import java.util.List;
import java.util.stream.Collectors;

public class GraphTestArrayFixture extends ArrayFixture {

	private Graph SUT = GraphSUT.INSTANCE.SUT();

	public GraphTestArrayFixture() {
		List<Node> nodes = SUT.hasLabel("Trade")
//						.map(node -> node.getId())
				.collect(Collectors.toList());
		System.out.println("Nodes are: " + nodes);
		setActualCollection(nodes);
	}
}
