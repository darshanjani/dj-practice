package com.dj.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ModelCreator {
	public static Actual createActual() {
		Actual actual = new Actual();
		actual.setAct1("act1");
		actual.setAct2("act2");
		actual.setAct3("act3");
		actual.setAct4(new Date());
		Composition composition = new Composition();
		composition.setComp1("comp1.1");
		composition.setComp2("comp1.2");
		actual.setComposition(composition);
		Composition composition2 = new Composition();
		composition.setComp1("comp2.1");
		composition.setComp2("comp2.2");
		List<Composition> compositions = Arrays.asList(composition, composition2);
		actual.setCompositions(compositions);
		actual.setBase1("base1");
		actual.setBase2(1);
		actual.setBase3(2L);
		actual.setBase4(3.3D);
		actual.setBase5(4.4F);
		actual.setBase6(true);
		actual.setBase7(5);
		actual.setBase8(6L);
		actual.setBase9(7.7F);
		actual.setBase10(8.8D);
		actual.setSuper1("super1");
		actual.setSuper2("super2");
		actual.setSuper3("super3");
		return actual;
	}
}
