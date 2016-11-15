package components;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class AboutDialog extends JDialog {
	public AboutDialog(JFrame frame){
		super(frame,"MARS MEDIA PLAYER",true);
		
		JApplet applet = new AboutClass();
	    applet.init();
	    getContentPane().add(applet.getContentPane(),BorderLayout.CENTER );
	    getContentPane().setSize(applet.getContentPane().getSize());
		
	}
}
