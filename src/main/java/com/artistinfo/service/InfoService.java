package com.artistinfo.service;

import java.util.ArrayList;

import com.artistinfo.service.dataobject.ArtistDO;

public interface InfoService {
	
	public double getAverageWords(String artist) ;

	public ArrayList<ArtistDO> getSampleData();
}
