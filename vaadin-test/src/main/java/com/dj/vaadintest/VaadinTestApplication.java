package com.dj.vaadintest;

import com.dj.vaadintest.model.Person;
import com.dj.vaadintest.service.MyService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class VaadinTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaadinTestApplication.class, args);
	}

	@Theme("valo")
	@SpringUI(path="/hello")
	public static class VaadinUI extends UI {
		@Autowired
		MyService service;

		@Override
		protected void init(VaadinRequest vaadinRequest) {
			VerticalLayout layout = new VerticalLayout();
			TextField textField = new TextField();
			Button button = new Button("Greet Service");
			button.addClickListener(e -> {
				Notification.show(service.sayHello());
				layout.addComponent(new Label(textField.getValue()));
			});

			layout.addComponent(textField);
			layout.addComponent(button);
			setContent(layout);
		}
	}
}
