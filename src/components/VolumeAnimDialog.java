package components;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;

public class VolumeAnimDialog extends JDialog {

	public VolumeAnimDialog(JFrame frame){
		super(frame);
		

		JApplet applet = new VolumeAnimClass();
	    applet.init();
	    applet.getContentPane().setBackground(new Color(0,0,0,0));
	    getContentPane().add(applet.getContentPane(),BorderLayout.CENTER );
	    getContentPane().setSize(applet.getContentPane().getSize());
		
	}

}
