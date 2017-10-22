package com.dj.drools.license;

import java.util.Date;

import lombok.Data;

@Data
public class Application {
	private Date dateApplied;
	private boolean valid;
	
	public Application(Date dateApplied) {
		this.dateApplied = dateApplied;
		this.valid = true;
	}
	
}
