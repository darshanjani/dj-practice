package com.dj.vaadintest.components;

import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

/**
 * Created by Darshan on 8/8/2017.
 */
@SpringUI (path = "/accordion")
public class Accordions extends UI {
	@Override
	protected void init(VaadinRequest vaadinRequest) {
		// Create the accordion
		Accordion accordion = new Accordion();

// Create the first tab, specify caption when adding
		Layout tab1 = new VerticalLayout(); // Wrap in a layout
		tab1.addComponent(new Label("Hello World"));
		tab1.addComponent(new Label("Hello World 2"));
		accordion.addTab(tab1, "Collapsible panel");

		setContent(accordion);
	}
}
