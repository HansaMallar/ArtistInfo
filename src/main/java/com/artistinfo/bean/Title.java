package com.artistinfo.bean;

public class Title {

	String name;
	int words = -1;
	
	public Title(String name, int words) {
		this.name = name;
		this.words = words;
		
	}
	
	public Title(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWords() {
		return words;
	}

	public void setWords(int words) {
		this.words = words;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Title)) {
			return false;
		}

		Title t = (Title) o;
		return this.name.equalsIgnoreCase(t.getName());

	}

}
