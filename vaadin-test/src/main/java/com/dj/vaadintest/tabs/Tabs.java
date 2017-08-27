package com.dj.vaadintest.tabs;

import com.vaadin.annotations.Theme;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

/**
 * Created by Darshan on 8/7/2017.
 */
@Theme("valo")
@SpringUI(path = "/tabs")
public class Tabs extends UI {
	@Override
	protected void init(VaadinRequest vaadinRequest) {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(false);
		Panel label = new Panel("Application Title");
		label.setWidth("100%");
		layout.addComponent(label);
		TabSheet tabsheet = new TabSheet();
		layout.addComponent(tabsheet);


		VerticalLayout tab1 = new VerticalLayout();
		tab1.addComponent(new Label("Tab 1 Label"));
		tabsheet.addTab(tab1, "Tab 1");
		setContent(layout);

		VerticalLayout tab2 = new VerticalLayout();
		tabsheet.addTab(tab2, "Logout");
		setContent(layout);
	}
}
