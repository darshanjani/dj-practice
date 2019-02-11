package com.dj.neo4jspringdata.service;

import com.dj.neo4jspringdata.model.Person;
import com.dj.neo4jspringdata.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class PersonService {

	private final PersonRepository personRepository;

	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Transactional(readOnly = true)
	public Person findByName(String name) {
		return personRepository.findByName(name, 1);
	}

	@Transactional(readOnly = true)
	public Collection<Person> findByNameLike(String name) {
		return personRepository.findByNameContaining(name);
	}

	public Collection<Person> graphOfPerson(String name) {
		return personRepository.personWithCar(name);
	}
}
