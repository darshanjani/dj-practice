package com.dj.fitnesse.fit;

import com.dj.graph.Graph;
import fitlibrary.SetUpFixture;

public class GraphTestSetupFixture extends SetUpFixture {
	private Graph SUT = GraphSUT.INSTANCE.SUT();

	public void reset(boolean value) {
		if (value) {
			GraphSUT.INSTANCE.reset();
		}
	}
}
