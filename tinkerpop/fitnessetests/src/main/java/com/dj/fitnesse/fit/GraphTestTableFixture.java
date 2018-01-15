package com.dj.fitnesse.fit;

import com.dj.graph.Graph;
import com.dj.model.Node;
import fit.TableFixture;

public class GraphTestTableFixture extends TableFixture {

	private Graph SUT = new Graph();

	@Override
	protected void doStaticTable(int rows) {
		for (int i = 1; i <= Integer.parseInt(getArgs()[0]); i++) {
			String nodeType = getText(0, 0);
			Node node = new Node("someDate", nodeType, getText(0, i));
			System.out.println(node);
			SUT.addVertex(node);
		}

		for (int i = 1; i <= Integer.parseInt(getArgs()[1]); i++) {
			String nodeType = getText(1, 0);
			Node node = new Node("someDate", nodeType, getText(1, i));
			System.out.println(node);
			SUT.addVertex(node);
		}

		int expectedTotal = Integer.parseInt(getText(2, 1));
		if (expectedTotal == SUT.size()) {
			right(2,1);
		} else {
			wrong(2, 1, "Actual: " + SUT.size());
		}
	}
}
