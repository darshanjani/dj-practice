package com.dj.neo4jspringdata.controller;

import com.dj.neo4jspringdata.model.Person;
import com.dj.neo4jspringdata.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/")
public class PersonController {

	private final PersonService personService;

	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping
	@RequestMapping("person/{name}")
	@ResponseBody
	public Person getPersonByName(@PathVariable("name") String name) {
		return personService.findByName(name);
	}

	@GetMapping
	@RequestMapping("personLike/{name}")
	@ResponseBody
	public Collection<Person> getPersonByNameLike(@PathVariable("name") String name) {
		return personService.findByNameLike(name);
	}
}
