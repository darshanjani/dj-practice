package com.dj.service;

import com.dj.model.Actual;
import com.dj.model.ModelCreator;

public class HierarchyCreation {
	public static void main(String[] args) throws Exception {
		new HierarchyCreation().startProcessing();
	}

	private void startProcessing() throws Exception {
		Actual actual = ModelCreator.createActual();
		Actual actual2 = ModelCreator.createActual();
		System.out.println(actual);
		System.out.println(actual.equals(actual2));
	}


}
