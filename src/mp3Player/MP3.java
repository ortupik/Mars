package mp3Player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;


public class MP3 {
	
  private String mp3File;
  private String [] paths ;
  private String extension;
  private Boolean isMP3;
  private Mp3File	mp3file ;
  private static JLabel  mp3Pic ;
  private String globalpic;
  private static int mp3length;
  
  //ofMp3info method
  private static String Track, Artist,Title,Album,Year,Genre,Comment,Composer,Publisher,originalArtist,albumArtist,Copyright,URL,Encoder,bitrate,sampleRate;
  private static String  mp3Details;


  public MP3(String mediaPath){
	  addAlbumpic(mediaPath);
  }
	
	
	public void setmp3Length(int length){
		 mp3length = length;		
	}
	
	public int getmp3Length(){		 
		return mp3length;
	}
	
	public void addAlbumpic(String mp3Path){
	
		globalpic = mp3Path;
		try {			
		mp3file = new Mp3File(mp3Path);				
		 if (mp3file.hasId3v2Tag()) {
			  ID3v2 id3v2Tag = mp3file.getId3v2Tag();
			  byte[] imageData = id3v2Tag.getAlbumImage();
			  if (imageData != null) {
			    String mimeType = id3v2Tag.getAlbumImageMimeType();
			    // Write image to file - can determine appropriate file extension from the mime type
			    BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
			    
			    Builder<BufferedImage> finalImage =	Thumbnails.of(img).size(500, 400);
			    ImageIcon iconSong = new ImageIcon(finalImage.asBufferedImage());
			    setMp3label(new JLabel (iconSong));			   
			    mp3Info();
			    ImageIO.write(img, "png", new File("E://tempMp3//mp3Image.png"));
			   
			  }
			}
	} catch (UnsupportedTagException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InvalidDataException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	public void setMp3label(JLabel mp3Label){
		MP3.mp3Pic = mp3Label;
	}
	public JLabel getMp3label(){		
		return mp3Pic;	
	}
	
	public void  mp3Info(){
		 mp3length = (int) mp3file.getLengthInSeconds();
	     setmp3Length(mp3length);
	    
		if (mp3file.hasId3v1Tag()) {
			Id3v1Tag();
			}
		if (mp3file.hasId3v2Tag()) {		
			Id3v2Tag();
		 }
		
	}
	public String [] getDetails(){
		String mp3InfoA[]= {Title,Artist,Album};
		
		
		return mp3InfoA;
	}
	public  void Id3v1Tag(){
		String mp3Details;
		 ID3v1 id3v1Tag = mp3file.getId3v1Tag();

		 bitrate = "Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)");
		 sampleRate = "Sample rate: " + mp3file.getSampleRate() + " Hz";	 
		 Track = "Track: " + id3v1Tag.getTrack();
		 Artist = "Artist: " + id3v1Tag.getArtist();
		 Title = "Title: " + id3v1Tag.getTitle();
		 Album = "Album: " + id3v1Tag.getAlbum();
		 Year = "Year: " + id3v1Tag.getYear();
		 Genre = "Genre: " + id3v1Tag.getGenre() + " (" + id3v1Tag.getGenreDescription() + ")";
		 Comment = "Comment: " + id3v1Tag.getComment();
		 mp3Details =Album +"\n"+ Artist +"\n"+ Title+"\n"+ Track+"\n"+  Year+"\n"+ Genre+"\n"+ bitrate+"\n"+sampleRate+"\n"+ Comment;
		 MP3.mp3Details  = mp3Details; 
	}
   public void Id3v2Tag(){
	   String mp3Details;
	   ID3v2 id3v2Tag = mp3file.getId3v2Tag();
	   
	     bitrate = "Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)");
		 sampleRate = "Sample rate: " + mp3file.getSampleRate() + " Hz";	 
	    Track = "Track: " + id3v2Tag.getTrack();
		 Artist = "Artist: " + id3v2Tag.getArtist();
		 Title = "Title: " + id3v2Tag.getTitle();
		 Album = "Album: " + id3v2Tag.getAlbum();
		 Year = "Year: " + id3v2Tag.getYear();
		 Genre = "Genre: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")";
		 Comment = "Comment: " + id3v2Tag.getComment();
		 Composer = "Composer: " + id3v2Tag.getComposer();
		 Publisher = "Publisher: " + id3v2Tag.getPublisher();
		 originalArtist = "Original artist: " + id3v2Tag.getOriginalArtist();
		 albumArtist = "Album artist: " + id3v2Tag.getAlbumArtist();
		 Copyright = "Copyright: " + id3v2Tag.getCopyright();
		 URL = "URL: " + id3v2Tag.getUrl();
		 Encoder = "Encoder: " + id3v2Tag.getEncoder();
	
	 mp3Details = Album +"\n"+ Artist +"\n"+ Title+"\n"+ Track+"\n"+  Year+"\n"+ Genre+"\n"+ bitrate+"\n"+sampleRate+"\n"+ Comment +"\n"+ Composer +"\n"+ Publisher+"\n"+ originalArtist+"\n"+  albumArtist+"\n"+ Copyright+"\n"+URL+"\n"+ Copyright+"\n"+Encoder;
	 MP3.mp3Details  = mp3Details; 
	 byte[] albumImageData = id3v2Tag.getAlbumImage();
		  if (albumImageData != null) {
		  //  System.out.println("Album image mime type: " + id3v2Tag.getAlbumImageMimeType());
	    }
	
      }	
     public String getMp3details(){
	    return mp3Details;
    }
	}
	

