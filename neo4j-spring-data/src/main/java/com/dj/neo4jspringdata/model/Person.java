package com.dj.neo4jspringdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
@Data
@ToString
@NoArgsConstructor
public class Person {
	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private int born;

	@Relationship(type = "LOVES")
	private List<Person> loves = new ArrayList<>();

	@Relationship(type = "DRIVES")
	private List<Car> drives = new ArrayList<>();

	@Relationship(type = "OWNS")
	private List<Car> owns = new ArrayList<>();
}
