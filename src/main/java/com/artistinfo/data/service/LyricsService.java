package com.artistinfo.data.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LyricsService {
	private static Logger logger = LoggerFactory.getLogger(LyricsService.class);


	CloseableHttpClient httpclient;
	
	public String fetchLyrics(String artist, String title) {

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

	private CloseableHttpClient getHTTPClient() {

		if (httpclient == null) {
			httpclient = HttpClientBuilder.create().build();
		}
		return httpclient;
	}
}
