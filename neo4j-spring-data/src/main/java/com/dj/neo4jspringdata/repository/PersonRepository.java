package com.dj.neo4jspringdata.repository;

import com.dj.neo4jspringdata.model.Person;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Collection;

public interface PersonRepository extends Neo4jRepository<Person, Long> {
	Person findByName(String name, @Depth int depth);
	Collection<Person> findByNameContaining(String name);

	@Query("match (p:Person{name:{0}})-[d:DRIVES]-(c:Car) return p,d,c")
	Collection<Person> personWithCar(String name);
}
