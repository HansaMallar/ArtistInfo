package com.artistinfo.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.artistinfo.bean.Artist;
import com.artistinfo.bean.Title;
import com.artistinfo.data.service.LyricsService;
import com.artistinfo.service.InfoService;
import com.artistinfo.service.dataobject.ArtistDO;

@Service
public class InfoServiceImpl implements InfoService {

	ArrayList<Artist> artistInfoList = new ArrayList<Artist>();

	private static Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);

	public InfoServiceImpl() {
		init();
	}

	private void init() {
		Artist artist1 = new Artist(), artist2 = new Artist(), artist3 = new Artist(), artist4 = new Artist();

		artist1.setName("Ed Sheeran");
		artist1.setTitles(Arrays.asList(new Title("Shape of You"), new Title("Perfect")));
		artistInfoList.add(artist1);

		artist2.setName("Elbow");
		artist2.setTitles(Arrays.asList	(new Title("One Day Like This"), new Title("Grounds for Divorce"),
				new Title("Leaders of the Free World")));
		artistInfoList.add(artist2);

		artist3.setName("Coldplay");
		artist3.setTitles(Arrays.asList(new Title("Adventure of a Lifetime"), new Title("A Rush of Blood to the Head"),
				new Title("Something Just Like This")));
		artistInfoList.add(artist3);


	}

	@Override
	public double getAverageWords(String artist) {

		int index = artistInfoList.indexOf(new Artist(artist));
		if (index == -1) {
			throw new IllegalArgumentException("Artist information not available.");
		}

		Artist artistBean = artistInfoList.get(index);
		logger.info("Artist " + artistBean.getName());

		double totalWords = 0;
		LyricsService lyricsService = new LyricsService();
		for (Title title : artistBean.getTitles()) {

			if (title.getWords() <= 0) {
				String lyrics = lyricsService.fetchLyrics(artistBean.getName(), title.getName());

				//logger.info(" lyrics " + lyrics);
				String cleanLyrics = lyrics.replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("\t", " ")
						.replaceAll(",", " ");
				String[] wordsArray = cleanLyrics.split("\\s+");
				title.setWords(wordsArray.length);
			}
			totalWords += title.getWords();
		}
		logger.info("totalWords " + totalWords);

		double averageWords = 0;
		if (artistBean.getTitles().size() != 0) {

			averageWords = totalWords / artistBean.getTitles().size();
		}

		return Math.round(averageWords);
	}

	@Override
	public ArrayList<ArtistDO> getSampleData() {
		ArrayList<ArtistDO> resultObj = new ArrayList<ArtistDO>();
		
		for (Artist bean : artistInfoList)
		{
			resultObj.add(new ArtistDO(bean) );
		}
		return resultObj;
	}

	/*
	 * public double getAverageWords(String artist) { Artist artistBean =
	 * readXMLResource(artist.replaceAll("\\s", "-"));
	 * 
	 * logger.info("Artist " + artistBean.getName()); HashMap<String, String>
	 * lyricsMap = new HashMap<String, String>();
	 * 
	 * for (String title : artistBean.getTitles()) { lyricsMap.put(title,
	 * fetchLyrics(artist, title));
	 * 
	 * }
	 * 
	 * double totalWords = 0;
	 * 
	 * for (String lyrics : lyricsMap.values()) { logger.info(" lyrics " + lyrics);
	 * String cleanLyrics = lyrics.replaceAll("\n", " ").replaceAll("\r",
	 * " ").replaceAll("\t", " ") .replaceAll(",", " "); String[] wordsArray =
	 * cleanLyrics.split("\\s+"); totalWords += wordsArray.length; }
	 * logger.info("totalWords " + totalWords);
	 * 
	 * double averageWords = 0; if (lyricsMap.size() != 0) {
	 * 
	 * averageWords = totalWords / lyricsMap.size(); }
	 * 
	 * return Math.round(averageWords);
	 * 
	 * }
	 */

	// Read an XML resource to return the list of titles for an artist. This can be
	// easily replaced to a database call or a REST API call etc
	/*
	 * public Artist readXMLResource(String artist) { Artist artistBean = null; try
	 * { ClassPathResource res = new ClassPathResource(artist.replaceAll(" ","-") +
	 * ".xml");
	 * 
	 * JAXBContext jaxbContext = JAXBContext.newInstance(Artist.class); Unmarshaller
	 * jaxbUnmarshaller = jaxbContext.createUnmarshaller(); artistBean = (Artist)
	 * jaxbUnmarshaller.unmarshal(res.getFile()); } catch (Exception e) {
	 * 
	 * e.printStackTrace(); throw new
	 * IllegalArgumentException("Unable to get the titles for the artist.",e); }
	 * return artistBean; }
	 */
}
