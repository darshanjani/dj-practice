package com.dj.fitnesse.slim;

import com.dj.graph.Graph;
import com.dj.model.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DoesTheGraphContainAllTrades {
	private Graph SUT = GraphSUT.INSTANCE.SUT();

	public List<Object> query() {
		return SUT.hasLabel("Trade")
				.map(node -> Arrays.asList(
						Arrays.asList("type", node.getType()),
						Arrays.asList("id", node.getId())
						)
				)
				.collect(Collectors.toList());
	}
}
