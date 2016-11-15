package components;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JApplet;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class FControlDialog extends JDialog {


	public FControlDialog(JFrame frame){
		super(frame);
		

		JApplet applet2 = new FloatingControlsClass();
	    applet2.init();
	    getContentPane().add(applet2.getContentPane(),BorderLayout.CENTER );
	    getContentPane().setSize(applet2.getContentPane().getSize());
		
	}

}
