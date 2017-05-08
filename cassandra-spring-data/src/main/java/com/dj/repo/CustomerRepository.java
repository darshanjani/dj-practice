package com.dj.repo;

import com.dj.model.Customer;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Darshan on 5/9/2017.
 */
public interface CustomerRepository extends CrudRepository<Customer, String> {

	@Query("select * from customer where firstname = ?0 allow filtering")
	public Customer findByFirstName(String firstName);

	@Query("select * from customer where lastname = ?0 allow filtering")
	public List<Customer> findByLastName(String lastName);
}
