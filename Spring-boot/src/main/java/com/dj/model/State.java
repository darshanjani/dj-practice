package com.dj.model;

public class State {
	private String id;
	private String name;
	private String region;

	public State(String id, String name, String region) {
		this.id = id;
		this.name = name;
		this.region = region;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}
