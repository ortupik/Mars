package lyrics;


import java.util.ArrayList;
import java.util.List;

import com.omt.lyrics.SearchLyrics;
import com.omt.lyrics.beans.LyricsServiceBean;
import com.omt.lyrics.exception.SearchLyricsException;

import mp3Player.MP3;




public class Lyrics {
	private static List <String> songLyrics;
	 private static String  Artist,Title,Album;
	 private static String [] mp3Details;

	public static List getLyrics() {
		songLyrics =  new ArrayList <String>();
		mp3Details = new MP3(null).getDetails();
		Title = mp3Details[0];
		Artist = mp3Details[1];
		Album = mp3Details[2];
		
		SearchLyrics searchLyrics = new SearchLyrics();
		LyricsServiceBean bean = new LyricsServiceBean();
		bean.setSongName(Title.substring(7, Title.length()));
		bean.setSongArtist(Artist.substring(8, Artist.length()));
		bean.setSongAlbum(Artist.substring(8, Artist.length()));
		
		List<com.omt.lyrics.beans.Lyrics> lyrics;
		
		try{
			lyrics = searchLyrics.searchLyrics(bean);
			if(!songLyrics.isEmpty()){
				songLyrics.clear();
			}
			for(com.omt.lyrics.beans.Lyrics lyric: lyrics){
				
				songLyrics.add(lyric.getText());
				
			}
			
			
			
		}catch(SearchLyricsException e){
			
		}
		System.out.println(Title.substring(7, Title.length())+Artist.substring(8, Artist.length())+Album.substring(7, Album.length()));
           
            new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new ManageDb().insertLyrics(songLyrics);
				}
				
			}).start();
            return songLyrics;
	}

}
