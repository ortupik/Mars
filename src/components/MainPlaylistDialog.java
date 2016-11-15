package components;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JApplet;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class MainPlaylistDialog extends JDialog {
	public MainPlaylistDialog(JFrame frame){
		super(frame);
		
		JApplet applet = new MainPlaylistClass();
	    applet.init();
	    getContentPane().add(applet.getContentPane(),BorderLayout.CENTER );
	    getContentPane().setSize(applet.getContentPane().getSize());
	    
	}
	

}
