package com.dj.controller;

import com.datastax.driver.core.ColumnDefinitions;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.dj.model.Customer;
import com.dj.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Darshan on 5/9/2017.
 */
@RestController
public class CustomerController {

	@Autowired
	private CustomerRepository repo;

	@Autowired
	private Session session;

	@GetMapping("/")
	public String home() {
		return "Cassandra app is working";
	}

	@GetMapping("/showTable")
	@ResponseBody
	public String showTable() {
		ResultSet rows = session.execute("select * from customer");
		ColumnDefinitions columnDefinitions = rows.getColumnDefinitions();
		StringBuilder builder = new StringBuilder();
		int ctr = 0;
		for (ColumnDefinitions.Definition definition : columnDefinitions) {
			builder.append("Column ").append(ctr++).append(" has name: ").append(definition.getName()).append(" with type: ").append(definition.getType()).append("\n");
		}
		return builder.toString();
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
	public Customer searchByFirstName() throws  Exception {
		CompletableFuture<Customer> customerFuture = repo.findByFirstName("Darshan");
		customerFuture.thenAccept(this::printCustomer);
		return customerFuture.get();
	}

	private void printCustomer(Customer customer) {
		System.out.println(customer);
	}

	@GetMapping("/searchByLast")
	@ResponseBody
	public List<Customer> searchByLastName() {
		return repo.findByLastName("Jani");
	}
}
