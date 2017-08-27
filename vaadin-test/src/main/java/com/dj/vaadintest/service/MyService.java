package com.dj.vaadintest.service;

import com.dj.vaadintest.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Darshan on 8/6/2017.
 */
@Service
public class MyService {
	public String sayHello() {
		return "Hello, World";
	}

	public Stream<Person> getPersons(int offset, int limit) {
		List<Person> persons = new ArrayList<>();
		for (int i = 1; i <= 1000; i++) {
			persons.add(new Person("John " + i, "Doe " + i, "john.doe" + i + "@gmail.com"));
		}
//		persons.add(new Person("John", "Doe", "john.doe@gmail.com"));
//		persons.add(new Person("Jane", "Doe", "jane.doe@gmail.com"));
		return persons.stream().skip(offset).limit(limit);
	}

	public int getPersonCount() {
		return 1000;
	}
}
