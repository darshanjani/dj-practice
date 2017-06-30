package com.dj.services.model;

/**
 * Created by Darshan on 6/23/2017.
 */
public class HeroName {
	private String name;

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

		HeroName heroName = (HeroName) o;

		return name != null ? name.equals(heroName.name) : heroName.name == null;
	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "HeroName{" +
				"name='" + name + '\'' +
				'}';
	}
}
