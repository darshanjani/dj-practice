package com.dj.drools.license;

import lombok.Data;

@Data
public class Applicant {
	private String name;
	private int age;
	private boolean valid;
	
	public Applicant(String name, int age) {
		this.name = name;
		this.age = age;
		this.valid = true;
	}
	
}
