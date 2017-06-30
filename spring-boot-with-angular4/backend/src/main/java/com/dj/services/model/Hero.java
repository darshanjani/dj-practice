package com.dj.services.model;

/**
 * Created by Darshan on 6/23/2017.
 */
public class Hero {
	private int id;
	private String name;

	public Hero(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Hero() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Hero user = (Hero) o;

		if (id != user.id) return false;
		return name != null ? name.equals(user.name) : user.name == null;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Hero{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
