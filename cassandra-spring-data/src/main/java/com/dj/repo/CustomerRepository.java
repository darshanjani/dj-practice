package com.dj.repo;

import com.dj.model.Customer;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by Darshan on 5/9/2017.
 */
public interface CustomerRepository extends CassandraRepository<Customer> {

	@Query("select * from customer where firstname = ?0 allow filtering")
	@Async
	public CompletableFuture<Customer> findByFirstName(String firstName);

	@Query("select * from customer where lastname = ?0")
	public List<Customer> findByLastName(String lastName);
}
