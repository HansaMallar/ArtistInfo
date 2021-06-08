package com.artistinfo.service.dataobject;

import java.util.List;

import com.artistinfo.bean.Artist;
import com.artistinfo.bean.Title;

public class ArtistDO {

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
	
	public ArtistDO() {}
	
	public ArtistDO(Artist bean) {
		this.name = bean.getName();
		this.titles = bean.getTitles();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ArtistDO)) {
			return false;
		}

		ArtistDO a = (ArtistDO) o;
		return this.name.equalsIgnoreCase(a.getName());

	}
}
