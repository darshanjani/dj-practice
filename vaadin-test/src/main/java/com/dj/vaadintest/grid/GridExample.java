package com.dj.vaadintest.grid;

import com.dj.vaadintest.VaadinTestApplication;
import com.dj.vaadintest.model.Person;
import com.dj.vaadintest.service.MyService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Darshan on 8/6/2017.
 */
@Theme("valo")
@SpringUI(path="/grid")
public class GridExample extends UI {
	@Autowired
	MyService service;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		Grid<Person> grid = new Grid<>();

		grid.addColumn(Person::getFirstName).setCaption("First Name");
		grid.addColumn(Person::getLastName).setCaption("Last Name");
		grid.addColumn(Person::getEmail).setCaption("Email");
		grid.setDataProvider(
				(sortOrder, offset, limit) -> service.getPersons(offset, limit),
				() -> service.getPersonCount()
		);
//		grid.setItems(service.getPersons());
		setContent(grid);
	}
}
