package com.artistinfo.bean;

import java.util.ArrayList;
import javax.xml.bind.annotation.*;


@XmlRootElement
public class Artist {
	
	String name;
	
    ArrayList<String> titles;
    
    @XmlAttribute(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@XmlElementWrapper(name = "titles")
    @XmlElement(name = "title")
	public ArrayList<String> getTitles() {
		return titles;
	}
	public void setTitles(ArrayList<String> titles) {
		this.titles = titles;
	}

	


} 
