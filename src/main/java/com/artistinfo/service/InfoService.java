package com.artistinfo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.artistinfo.bean.Artist;

@Service
public class InfoService {

	ArrayList<Artist> artistInfoList = new ArrayList<Artist>();
	CloseableHttpClient httpclient;
	private static Logger logger = LoggerFactory.getLogger(InfoService.class);

	public double getAverageWords(String artist) {
		Artist artistBean = readXMLResource(artist.replaceAll("\\s", "-"));
		logger.info("Artist " + artistBean.getName());
		HashMap<String, String> lyricsMap = new HashMap<String, String>();

		for (String title : artistBean.getTitles()) {
			lyricsMap.put(title, fetchLyrics(artist, title));

		}

		double totalWords = 0;

		for (String lyrics : lyricsMap.values()) {
			logger.info(" lyrics " + lyrics);
			String cleanLyrics = lyrics.replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("\t", " ")
					.replaceAll(",", " ");
			String[] wordsArray = cleanLyrics.split("\\s+");
			totalWords += wordsArray.length;
		}
		logger.info("totalWords " + totalWords);

		double averageWords = 0;
		if (lyricsMap.size() != 0) {
			logger.debug("lyricsMap.size() " + lyricsMap.size());
			averageWords = totalWords / lyricsMap.size();
		}

		return averageWords;

	}

	private CloseableHttpClient getHTTPClient() {

		if (httpclient == null) {
			httpclient = HttpClientBuilder.create().build();
		}
		return httpclient;
	}

	// Make a REST call to lyrics.ovh
	private String fetchLyrics(String artist, String title) {

		StringBuilder lyrics = new StringBuilder();

		try {
			
			URL url = new URL("https://api.lyrics.ovh/v1/" + artist.replaceAll(" ", "%20") + "/" + title.replaceAll(" ", "%20"));
			logger.info(url.toString());
			HttpGet request = new HttpGet(url.toString());

			CloseableHttpResponse response = getHTTPClient().execute(request);

			// TODO Read the data as a JSON object instead of plain text
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String newline = "";
			while ((newline = rd.readLine()) != null) {
				lyrics.append(newline);
			}
			response.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return lyrics.toString().substring(11, lyrics.length() - 2).replace("\\r\\n", " ");
	}

	// Read an XML resource to return the list of titles for an artist. This can be
	// easily replaced to a database call or a REST API call etc
	public Artist readXMLResource(String artist) {
		Artist artistBean = null;
		try {
			ClassPathResource res = new ClassPathResource(artist.replaceAll(" ","-")  + ".xml");

			JAXBContext jaxbContext = JAXBContext.newInstance(Artist.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			artistBean = (Artist) jaxbUnmarshaller.unmarshal(res.getFile());
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new IllegalArgumentException("Unable to get the titles for the artist.",e);
		}
		return artistBean;
	}
}
