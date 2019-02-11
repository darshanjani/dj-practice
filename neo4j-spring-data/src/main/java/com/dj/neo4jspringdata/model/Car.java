package com.dj.neo4jspringdata.model;

import lombok.*;
import org.neo4j.ogm.annotation.*;

import java.util.List;

import static org.neo4j.ogm.annotation.Relationship.*;

@NodeEntity
@Data
@NoArgsConstructor
@ToString
public class Car {

	@Id
	@GeneratedValue
	private Long id;

	private String brand;
	private String model;

}
