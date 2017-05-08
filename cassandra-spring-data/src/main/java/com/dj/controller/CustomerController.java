package com.dj.controller;

import com.dj.model.Customer;
import com.dj.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by Darshan on 5/9/2017.
 */
@RestController
public class CustomerController {

	@Autowired
	private CustomerRepository repo;

	@GetMapping("/")
	public String home() {
		return "Cassandra app is working";
	}

	@GetMapping("/insert")
	@ResponseBody
	public Customer insert() {
		Set<String> hobbies = new HashSet<>(Arrays.asList(new String[]{"reading", "writing"}));
		Customer customer = new Customer(UUID.randomUUID(), "Darshan", "Jani", hobbies);
		repo.save(customer);
		return customer;
	}

	@GetMapping("/searchByFirst")
	@ResponseBody
	public Customer searchByFirstName() {
		return repo.findByFirstName("Darshan");
	}

	@GetMapping("/searchByLast")
	@ResponseBody
	public List<Customer> searchByLastName() {
		return repo.findByLastName("Jani");
	}
}
