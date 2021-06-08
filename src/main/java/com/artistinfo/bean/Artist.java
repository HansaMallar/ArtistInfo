package com.artistinfo.bean;

import java.util.List;

public class Artist {

	String name;

	List<Title> titles;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Title> getTitles() {
		return titles;
	}

	public void setTitles(List<Title> titles) {
		this.titles = titles;
	}
	
	public Artist() {}
	
	public Artist(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Artist)) {
			return false;
		}

		Artist a = (Artist) o;
		return this.name.equalsIgnoreCase(a.getName());

	}
}
