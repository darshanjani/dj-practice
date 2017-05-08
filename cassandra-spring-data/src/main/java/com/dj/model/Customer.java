package com.dj.model;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Set;
import java.util.UUID;

/**
 * Created by Darshan on 5/9/2017.
 */
@Table
public class Customer {
	@PrimaryKey
	private UUID id;

	private String firstName;
	private String lastName;
	private Set<String> hobbies;

	public Customer(UUID id, String firstName, String lastName, Set<String> hobbies) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hobbies = hobbies;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", hobbies=" + hobbies +
				'}';
	}

	public UUID getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Set<String> getHobbies() {
		return hobbies;
	}
}
