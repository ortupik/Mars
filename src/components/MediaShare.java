package components;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import mp3Player.MP3;
import mp3Player.MainStage;
import mp3Player.Mp3DetailApplet;
import player.MainPlayer;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventAdapter;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;


 /* Media Player in Java based on LIBVLC libraries see vlcj 
 * Open Source Licence GPLv3
 * @author Chris Kipruto 
 * Copyright 2014 - 2015 
 * */

public class MediaShare extends JApplet implements WindowListener, NativeKeyListener, ActionListener {
	private static Boolean isRunning = false;

	// MEDIA PLAYER COMPONENTS
	protected static EmbeddedMediaPlayer mediaPlayerX;
	protected static EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent1;
	private static MediaList mediaList1;
	private static MediaPlayerFactory mediaPlayerFactory1;
	protected static MediaListPlayer mediaListPlayer1;

	protected static EmbeddedMediaPlayer mediaPlayer2;
	protected EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent2;
	protected static MediaList mediaList2;
	private MediaPlayerFactory mediaPlayerFactory2;
	protected static MediaListPlayer mediaListPlayer2;

	protected static EmbeddedMediaPlayer mediaPlayer3;
	protected EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent3;
	private static MediaList mediaList3;
	private MediaPlayerFactory mediaPlayerFactory3;
	protected static MediaListPlayer mediaListPlayer3;

	protected static EmbeddedMediaPlayer mediaPlayer4;
	protected EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent4;
	private static MediaList mediaList4;
	private MediaPlayerFactory mediaPlayerFactory4;
	protected static MediaListPlayer mediaListPlayer4;

	private static int j = 0;

	private static String options;
	// canvases
	protected static Canvas frame1;
	protected static Canvas frame2;
	protected static Canvas frame3;
	protected static Canvas frame4;
	private static int count;

	private static JApplet statusApplet = new StatusBarController();
	private static Timer statusBarTimer;
	private static Stage mainStage, primaryStage;

	private static Boolean eventStatus = true;
	protected static Boolean isPlaylistActivated = true;
	protected static Boolean isDockedPlaylistActivated = false;
	private static Boolean isMaximised = false;
	private static Boolean isItup = true;
	private static boolean AlwaysOntop = false;
	@FXML
	private Text currentTime;

	protected static String mediaPath = "";

	private static String mediaPathName = "";
	private static JTextPane statusBar;
	private static JPanel panel, panel2, panel3, panel4;
	protected static Timer timer;
	private static Timer timerOPacity;
	private static FilenameFilter mediaFilter;
	private boolean isShiftPressed;
	private static int countSnapshot;

	protected static int height;
	protected static int width;
	private JPanel controls;

	private ObjectOutputStream outputStream1;
	private static ObjectInputStream inputStream1, inputStream3;
	private ServerSocket server1;
	private ServerSocket server2, server3;
	private Socket connection1;
	private String androidcommand;
	private String androidMedia = "";
	private static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

	private boolean mousePressedPlaying = false;
	private JLabel playLabel, pauseLabel;
	private Boolean setupComplete = false;

	private static boolean isScreenSplit = false;
	private static boolean isFirstSplit = false;
	private static boolean isSecondSplit = false;

	protected static String timeLabel;
	private static ComponentResizer cr = new ComponentResizer();

	private static final int JFXPANEL_WIDTH_INT = 300;
	private static final int JFXPANEL_HEIGHT_INT = 250;
	private static JFXPanel fxContainer;
	private static JInternalFrame topPanel;
	private static JButton DisplayMediatitle;
	private static Border emptyBorder;
	private static JSlider positionSlider;
	private static int xMouse;
	private static int yMouse;
	protected static JPanel TopFrame;
	protected static JInternalFrame menuPanel;
	private static JMenuBar menuBar;
	protected static JApplet applet;
	protected static JApplet applet2, applet3;
	protected static JFrame mainFrame;
	private static MediaShare mediaShare;
	protected static int playlistCount = 10;
	private static HashMap<String, String> playlistMap = new HashMap<String, String>();
	private static ArrayList<String> playlist = new ArrayList<String>();

	private static GridBagConstraints gbc = new GridBagConstraints();
	private static GraphicsDevice myDevice;
	private static Cursor resizecursor;
	private static Stage primaryStage1;
	private static Boolean isStreaming = false;

	private static int currentIndex = 0;
	private static JPanel mp3Panel;
	protected static JInternalFrame frame = new JInternalFrame();
	protected static JInternalFrame spectrumFrame = new JInternalFrame();
	protected static Boolean isPlayerEstablished = false;
	private static Boolean isFloatingControlsactivated = false;
	protected static Boolean fileType;
	private static Stage floatingStage;
	private static FControlDialog f;
	private static JPanel mainPanel;
	protected static boolean isConnected = false;
	private static boolean isVolumeAnimationInitialized = false;
	private static VolumeAnimDialog v;
	private static AboutDialog a;
	private static int volume = 100;
	private static JSplitPaneWithZeroSizeDivider topSplitPlane = new JSplitPaneWithZeroSizeDivider();
	private static JSplitPaneWithZeroSizeDivider bottomSplitPlane = new JSplitPaneWithZeroSizeDivider();
	private static JSplitPaneWithZeroSizeDivider mainSplitPlane = new JSplitPaneWithZeroSizeDivider();
	private static JPopupMenu popup;

	private static boolean isMP3;

	public static void setDisplayMediatitle(JButton DisplayMediatitle) {
		MediaShare.DisplayMediatitle = DisplayMediatitle;
	}

	public static JButton getDisplayMediatitle() {
		return DisplayMediatitle;
	}

	public static Component getMainFrame() {
		return mainFrame;
	}

	public static void setResizeCursor(Cursor cursor) {
		resizecursor = cursor;

	}

	public static void setTitle(String mediaPathName) {
		mainFrame.setTitle("Media Share - " + mediaPathName);
	}

	public MediaShare() {

		mainFrame.setIconImage(new ImageIcon(getClass().getResource("/res/icon.png")).getImage());
	}

	public static void startServer() {
		ServerSocket server;
		try {
			server = new ServerSocket(8870, 1);
			System.out.println("Server started ...");
			mainFrame.setVisible(true);
			while (true) {
				Socket socket2 = server.accept();
				System.out.println("Server acceped ...");
				ObjectInputStream ois = new ObjectInputStream(socket2.getInputStream());
				try {
					String details[] = (String[]) ois.readObject();
					final String[] finalDetails = details;
					mediaPath = finalDetails[0];
					mediaPathName = finalDetails[1];
					playByPath(mediaPath);
					getDisplayMediatitle().setText(mediaPathName);
					setTitle(mediaPathName);
					System.out.println(mediaPath + " fom startClient playing in startServer");
					if (mainFrame.getState() == JFrame.ICONIFIED) {
						System.out.println("Iconified");
						mainFrame.setExtendedState(0);

					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			System.out.println("Already started");
			startClient();
			System.exit(0);

		}

	}

	public static void startClient() {
		try {
			Socket socket = new Socket(InetAddress.getLocalHost(), 8870);
			if (socket.isConnected()) {
				System.out.println("It is connected");
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				String[] details = new String[2];
				details[0] = mediaPath;
				details[1] = mediaPathName;
				oos.writeObject(details);
				System.out.println(mediaPath + "has been sent fom startClient");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

		height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		emptyBorder = BorderFactory.createEmptyBorder();

		mainFrame = new JFrame();

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setUndecorated(true);
		// mainFrame.setAlwaysOnTop(true);
		mainFrame.setBounds(0, 0, width * 7 / 12, height * 8 / 12);// width*2/3,height*3/41200,749
		mainFrame.setLocation(100, 100);
		mainFrame.setLayout(new BorderLayout());
		mediaShare = new MediaShare();
		mediaShare.framework();
		mainFrame.addWindowListener(mediaShare);
		mainFrame.setResizable(true);

		cr.setMaximumSize(new Dimension(width, height));
		cr.setMinimumSize(new Dimension(550, 59));
		cr.setSnapSize(new Dimension(5, 5));
		// cr.registerComponent(mainFrame);

		StringBuilder sb = new StringBuilder();
		for (String ss : args) {
			sb.append(ss);
		}
		
		//COMMENT this if you export the program as jar/exe/or passing parameters to the main method
		 
		 String currentPath = new File("").getAbsolutePath();
		mediaPath = currentPath+"\\video test.mp4";
		

		//UNCOMMENT this if you export the program as jar/exe/or passing parameters to the main method 
	    // mediaPath = sb.toString();

		if (mediaPath.equals("")) {
			mediaPathName = "No Media";
			setTitle(mediaPathName);
		} else {
			mediaPathName = new File(mediaPath.trim()).getName().trim();
			setTitle(mediaPathName);
		}

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				mediaShare.mp3Instance();
				mediaShare.initComponents();
				mediaShare.createPopupMenu();

			}

		});
		t2.start();
		try {
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Boolean detectMp3() {

		
		System.out.println(mediaPath);
		String extension = mediaPath.substring(mediaPath.length() - 3, mediaPath.length());
		if (extension.equals("mp3")) {

			isMP3 = true;
		} else {
			isMP3 = false;
		}
		return isMP3;
	}

	public void mp3Instance() {
		fileType = detectMp3();
		// fileType =false;
		if (fileType == true) {
			new MP3(mediaPath);
			loadmp3gui();

		} else {
			Thread tt = new Thread(new Runnable() {

				@Override
				public void run() {

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int pl = new Random().nextInt(500);
					player(0);

					// mediaPlayerX = new MainPlayer().getM();

				}

			});
			tt.start();
			Thread t2 = new Thread(new Runnable() {

				@Override
				public void run() {

					loadvideoGui();
					// System.exit(0);

				}

			});
			t2.start();

			Thread t4 = new Thread(new Runnable() {

				@Override
				public void run() {

					mediaShare.initStuff();

				}

			});
			t4.start();

		}

	}

	public void startOpacityTimer() {
		timerOPacity.start();
		mainFrame.setOpacity(0.85f);

	}

	public void stopOpacityTimer() {
		timerOPacity.stop();
		mainFrame.setOpacity(1.0f);
	}

	public void initComponents() {

		timerOPacity = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				stopOpacityTimer();

			}
		});
		timerOPacity.setRepeats(false);

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// try {
				// UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");}
				// catch (Exception ex) {ex.printStackTrace();}

				TopFrame = new JPanel();
				TopFrame.setLayout(new BorderLayout());
				TopFrame.setBorder(emptyBorder);

				topPanel = new JInternalFrame();
				topPanel.setResizable(true);
				topPanel.setLayout(new BorderLayout());
				((javax.swing.plaf.basic.BasicInternalFrameUI) topPanel.getUI()).setNorthPane(null);
				((javax.swing.plaf.basic.BasicInternalFrameUI) topPanel.getUI()).setWestPane(null);
				((javax.swing.plaf.basic.BasicInternalFrameUI) topPanel.getUI()).setEastPane(null);
				((javax.swing.plaf.basic.BasicInternalFrameUI) topPanel.getUI()).setSouthPane(null);

				topPanel.setBorder(emptyBorder);
				topPanel.setBackground(new Color(229, 227, 224));
				// topPanel.setBackground(Color.BLACK);

				// cr.registerComponent(topPanel);

				topPanel.addMouseMotionListener(new MouseMotionListener() {

					@Override
					public void mouseDragged(java.awt.event.MouseEvent e) {
						int x = e.getXOnScreen();
						int y = e.getYOnScreen();

						startOpacityTimer();

						mainFrame.setLocation(x - xMouse, y - yMouse);

					}

					@Override
					public void mouseMoved(java.awt.event.MouseEvent e) {
						xMouse = e.getX();
						yMouse = e.getY();

					}

				});

				JPanel titlePanel = new JPanel();
				// titlePanel.setBackground(Color.BLACK);
				titlePanel.setPreferredSize(new Dimension(300, 22));
				titlePanel.setLayout(null);
				ImageIcon iconI = new ImageIcon(getClass().getResource("/res/iconTop.png"));
				JLabel icon = new JLabel(iconI);
				icon.setBounds(0, 0, 18, 18);
				titlePanel.add(icon);

				JLabel Title = new JLabel("Marz Media Player");
				Title.setFocusable(false);
				Title.setForeground(Color.BLACK);
				Title.setFont(new Font("Lucida Handwriting", Font.PLAIN, 12));
				Title.setPreferredSize(new Dimension(300, 22));
				Title.setBounds(25, 0, 300, 22);
				Title.setBackground(Color.BLACK);
				titlePanel.add(Title);
				topPanel.add(titlePanel, BorderLayout.WEST);
				// cr.registerComponent(Title);

				JPanel conpanel = new JPanel();
				conpanel.setPreferredSize(new Dimension(200, 18));
				conpanel.setLayout(null);
				// conpanel.setBackground(Color.BLACK);
				topPanel.add(conpanel, BorderLayout.EAST);
				// cr.registerComponent(topPanel);
				// try {
				// UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");}
				// catch (Exception ex) {ex.printStackTrace();}
				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (Exception e) {

				}
				// try
				// {UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");}
				// catch (Exception ew) {ew.printStackTrace();}
				// ImageIcon walIcon = new ImageIcon("/res/wal2.jpg");
				// JLabel wal = new JLabel(walIcon );
				setDisplayMediatitle(new JButton());

				getDisplayMediatitle().setText(mediaPathName);
				getDisplayMediatitle().setFocusable(false);
				getDisplayMediatitle().setForeground(new Color(10, 102, 250));//
				getDisplayMediatitle().setFont(new Font("Tahoma", Font.BOLD, 12));
				// getDisplayMediatitle().setBackground(new Color(10, 102,
				// 250));//
				// getDisplayMediatitle().setBounds(conpanel.getWidth()+440,0,
				// 600,21);
				getDisplayMediatitle().setBorder(emptyBorder);
				// getDisplayMediatitle().setRolloverEnabled(false);
				getDisplayMediatitle().setPreferredSize(new Dimension(700, 18));
				// getDisplayMediatitle().setBackground(Color.BLACK);
				getDisplayMediatitle().setForeground(Color.BLACK);

				topPanel.add(getDisplayMediatitle(), BorderLayout.CENTER);
				// cr.registerComponent(getDisplayMediatitle());
				getDisplayMediatitle().addMouseMotionListener(new MouseMotionListener() {

					@Override
					public void mouseDragged(java.awt.event.MouseEvent e) {
						if (fileType) {
							int x = e.getXOnScreen();
							int y = e.getYOnScreen();
							mainFrame.setLocation(x - xMouse - titlePanel.getWidth(), y - yMouse);
						}
						if (!mediaPlayerX.isFullScreen() && !isMaximised) {
							int x = e.getXOnScreen();
							int y = e.getYOnScreen();
							mainFrame.setLocation(x - xMouse - titlePanel.getWidth(), y - yMouse);

							Thread t = new Thread(new Runnable() {

								@Override
								public void run() {

									if (fileType == false) {
										startOpacityTimer();
										stopVolumeAnimation();
									}
								}

							});
							t.start();
						}
					}

					@Override
					public void mouseMoved(java.awt.event.MouseEvent e) {
						xMouse = e.getX();
						yMouse = e.getY();

					}

				});

				ImageIcon minimI = new ImageIcon(getClass().getResource("/res/newest/minim.png"));
				JLabel minim = new JLabel(minimI);
				minim.setBounds(144, 1, 16, 16);
				conpanel.add(minim, BorderLayout.LINE_START);
				minim.addMouseListener(new MouseAdapter() {

					public void mouseClicked(java.awt.event.MouseEvent ev) {
						eventStatus = false;
						mainFrame.setState(mainFrame.ICONIFIED);

					}

				});

				ImageIcon maximI = new ImageIcon(getClass().getResource("/res/newest/maxim.png"));
				JLabel maxim = new JLabel(maximI);
				maxim.setBounds(163, 1, 16, 16);
				conpanel.add(maxim);
				maxim.addMouseListener(new MouseAdapter() {

					public void mouseClicked(java.awt.event.MouseEvent ev) {
						if (isMaximised) {
							mainFrame.setExtendedState(JFrame.NORMAL);
							isMaximised = false;
							cr.registerComponent(mainFrame);
							cr.registerComponent(panel);
							cr.registerComponent(frame1);
						} else {
							mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
							isMaximised = true;
							cr.deregisterComponent(mainFrame);
							cr.deregisterComponent(panel);
							cr.deregisterComponent(frame1);
						}

					}

				});

				ImageIcon closeI = new ImageIcon(getClass().getResource("/res/newest/close.png"));
				JLabel close = new JLabel(closeI);
				close.setBounds(182, 1, 18, 16);
				conpanel.add(close);
				close.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(java.awt.event.MouseEvent ev) {
						// f.historyFile();
						System.exit(0);

					}
				}

				);

				topPanel.setVisible(true);
				TopFrame.setVisible(true);
				TopFrame.add(topPanel, BorderLayout.NORTH);

				menuBar = new Init().menu();
				menuPanel = new JInternalFrame();
				((javax.swing.plaf.basic.BasicInternalFrameUI) menuPanel.getUI()).setNorthPane(null);
				((javax.swing.plaf.basic.BasicInternalFrameUI) menuPanel.getUI()).setWestPane(null);
				((javax.swing.plaf.basic.BasicInternalFrameUI) menuPanel.getUI()).setEastPane(null);
				((javax.swing.plaf.basic.BasicInternalFrameUI) menuPanel.getUI()).setSouthPane(null);

				menuPanel.setLayout(new BorderLayout());
				menuPanel.setBorder(emptyBorder);
				menuPanel.setOpaque(true);
				menuPanel.add(menuBar);
				menuPanel.setVisible(true);
				menuPanel.setFocusable(true);
				TopFrame.add(menuPanel, BorderLayout.SOUTH);

				mainFrame.add(TopFrame, BorderLayout.NORTH);

			}

		});
		t1.start();

		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Thread t3 = new Thread(new Runnable() {

			@Override
			public void run() {

				applet = new ControlsClass();
				applet.init();
				applet.start();
				mainFrame.add(applet.getContentPane(), BorderLayout.SOUTH);

			}

		});
		t3.start();

	}

	public void loadmp3gui() {
		mp3Panel = new JPanel();
		mainFrame.add(mp3Panel, BorderLayout.CENTER);
		mp3Panel.setBackground(Color.BLACK);

		mainFrame.setSize(new Dimension(width * 3 / 4, height * 3 / 4));
		mediaPath = "E://lyrics//royals.mp3";
		applet2 = new MainStage(mediaPath);
		applet2.init();
		applet2.start();
		mp3Panel.add(applet2.getContentPane(), BorderLayout.NORTH);

		mp3Panel.add(new MainStage(mediaPath).loadMp3gui(), BorderLayout.CENTER);

		JApplet appletMp3 = new Mp3DetailApplet();
		appletMp3.init();
		mainFrame.add(appletMp3.getContentPane(), BorderLayout.AFTER_LAST_LINE);
		mainFrame.setOpacity(0.90f);
		mainFrame.setVisible(true);

	}

	public void loadvideoGui() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setLayout(new BorderLayout());
		mainFrame.add(mainPanel, BorderLayout.CENTER);

		Border empty = BorderFactory.createEmptyBorder();
		// MatteBorder titled = BorderFactory.createMatteBorder(10, 10, 10,10,
		// new ImageIcon(getClass().getResource("/res/border.PNG")));
		TitledBorder titled = BorderFactory.createTitledBorder(empty, "Screen 1");
		titled.setTitleFont(new Font("Century Gothic", Font.BOLD, 12));
		titled.setTitleColor(Color.WHITE);
		titled.setBorder(emptyBorder);

		TitledBorder titled2 = BorderFactory.createTitledBorder(empty, "Screen 2");
		titled2.setTitleFont(new Font("Century Gothic", Font.BOLD, 12));
		titled2.setTitleColor(Color.WHITE);

		TitledBorder titled3 = BorderFactory.createTitledBorder(empty, "Screen 3");
		titled3.setTitleFont(new Font("Century Gothic", Font.BOLD, 12));
		titled3.setTitleColor(Color.WHITE);

		TitledBorder titled4 = BorderFactory.createTitledBorder(empty, "Screen 4");
		titled4.setTitleFont(new Font("Century Gothic", Font.BOLD, 12));
		titled4.setTitleColor(Color.WHITE);

		panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setLayout(new BorderLayout());
		// panel.setBorder(titled);

		panel2 = new JPanel();
		panel2.setBackground(Color.BLACK);
		panel2.setLayout(new BorderLayout());
		panel2.setBorder(titled2);

		panel3 = new JPanel();
		panel3.setBackground(Color.BLACK);
		panel3.setLayout(new BorderLayout());
		panel3.setBorder(titled3);

		panel4 = new JPanel();
		panel4.setBackground(Color.BLACK);
		panel4.setLayout(new BorderLayout());
		panel4.setBorder(titled4);

		frame1 = new Canvas();
		frame1.setBackground(Color.BLACK);

		frame2 = new Canvas();
		frame2.setBackground(Color.BLACK);

		frame3 = new Canvas();
		frame3.setBackground(Color.BLACK);

		frame4 = new Canvas();
		frame4.setBackground(Color.BLACK);

		panel.add(frame1, BorderLayout.CENTER);
		panel2.add(frame2, BorderLayout.CENTER);
		panel3.add(frame3, BorderLayout.CENTER);
		panel4.add(frame4, BorderLayout.CENTER);

		Dimension minimumSize = new Dimension(new Dimension(200, 200));

		topSplitPlane.setBorder(null);
		topSplitPlane.setDividerSize(-1);
		topSplitPlane.setLeftComponent(panel);
		panel.setMinimumSize(minimumSize);
		topSplitPlane.setPreferredSize(new Dimension(mainPanel.getWidth(), mainPanel.getHeight()));
		topSplitPlane.setOpaque(false);

		mainSplitPlane.setBorder(null);
		mainSplitPlane.setDividerSize(-1);
		mainSplitPlane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mainSplitPlane.setTopComponent(topSplitPlane);
		topSplitPlane.setMinimumSize(minimumSize);
		mainSplitPlane.setPreferredSize(new Dimension(mainPanel.getWidth(), mainPanel.getHeight()));
		mainSplitPlane.setOpaque(false);
		mainPanel.add(mainSplitPlane);

		startServer();

	}

	public void createPopupMenu() {

		popup = new JPopupMenu();
		if (ToolTipManager.sharedInstance().isLightWeightPopupEnabled()) {
			ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
		}
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);

		ToolTipManager.sharedInstance().setEnabled(false);

		// Create the popup menu.

		ImageIcon icon = createImageIcon("/res/xmp.png");
		ImageIcon iconPlay = createImageIcon("/res/newest/play.png");
		ImageIcon iconPause = createImageIcon("/res/newest/pause.png");
		ImageIcon iconNext = createImageIcon("/res/next.PNG");
		ImageIcon iconPrev = createImageIcon("/res/prev.PNG");
		ImageIcon iconstop = createImageIcon("/res/newest/stop.png");
		ImageIcon iconPlaylist = createImageIcon("/res/newest/playlist.png");
		ImageIcon iconExit = createImageIcon("/res/newest/close.png");
		ImageIcon iconShare = createImageIcon("/res/newest/share.png");
		ImageIcon iconReceive = createImageIcon("/res/newest/receive.png");
		ImageIcon iconSave = createImageIcon("/res/newest/save.png");
		ImageIcon iconfullscreen = createImageIcon("/res/newest/fullscreen.png");
		ImageIcon iconSpectrum = createImageIcon("/res/newest/spectrum.png");
		ImageIcon iconSnapshot = createImageIcon("/res/hover/snapshot.png");
		ImageIcon iconEqualizer = createImageIcon("/res/hover/extended.png");
		ImageIcon iconPref = createImageIcon("/res/newest/playlist1.png");
		ImageIcon iconAndroid = createImageIcon("/res/newest/android.png");
		ImageIcon iconYoutube = createImageIcon("/res/newest/youtube.png");
		ImageIcon iconWifi = createImageIcon("/res/newest/wifi.png");
		ImageIcon iconVideo = createImageIcon("/res/newest/video.png");
		ImageIcon iconAudio = createImageIcon("/res/newest/audio.png");
		ImageIcon iconUpload = createImageIcon("/res/newest/upload.png");
		ImageIcon checkIcon = createImageIcon("/res/checkmark.png");
		ImageIcon iconScreen = createImageIcon("/res/newest/screen.png");
		ImageIcon iconSplitVer = createImageIcon("/res/newest/vert.png");
		ImageIcon iconSplitHor = createImageIcon("/res/newest/hor.png");
		ImageIcon iconduplicate = createImageIcon("/res/newest/duplicate.png");
		ImageIcon iconMerge = createImageIcon("/res/newest/merge.png");
		ImageIcon iconAbout = createImageIcon("/res/newest/about.png");
		ImageIcon iconRepeat = createImageIcon("/res/newest/repeat.png");

		JMenu menu = new JMenu();
		JMenuItem menuItem;

		menuItem = new JMenuItem("Pause");
		menuItem.setIcon(iconPause);
		menuItem.addActionListener(this);
		popup.add(menuItem);
		menuItem.setBackground(new Color(0, 0, 0, 0));

		menuItem = new JMenuItem("Next", iconNext);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		popup.add(menuItem);

		menuItem = new JMenuItem("Previous", iconPrev);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		popup.add(menuItem);

		menuItem = new JMenuItem("Stop", iconstop);
		menuItem.addActionListener(this);
		popup.add(menuItem);

		menuItem = new JMenuItem("Fullscreen", iconfullscreen);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		popup.add(menuItem);

		menuItem = new JMenuItem("Playlist", iconPlaylist);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		popup.add(menuItem);

		menuItem = new JMenuItem("Snapshot                      ", iconSnapshot);
		menuItem.addActionListener(this);
		popup.add(menuItem);

		menu = new JMenu("Audio");
		menu.setIcon(iconAudio);
		menuItem = new JMenuItem("Audio Spectrum", iconSpectrum);
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Equalizer", iconEqualizer);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		popup.add(menu);

		menu = new JMenu("Playback");
		menuItem = new JMenuItem("Repeat", iconRepeat);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Normal", checkIcon);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		popup.add(menu);

		menu = new JMenu("Video");
		menu.setIcon(iconVideo);
		menuItem = new JMenuItem("Always on top");
		menuItem.setEnabled(true);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		popup.add(menu);

		menu = new JMenu("Screen");
		menu.setIcon(iconScreen);
		menuItem = new JMenuItem("Duplicate this Screen", iconduplicate);
		menuItem.setEnabled(true);
		menuItem.addActionListener(this);
		menu.add(menuItem);

		JMenu splitScreen = new JMenu("Split Screen");
		splitScreen.setEnabled(true);
		splitScreen.addActionListener(this);

		menuItem = new JMenuItem("Horizontal Split", iconSplitHor);
		menuItem.setEnabled(true);
		menuItem.addActionListener(this);
		splitScreen.add(menuItem);

		menuItem = new JMenuItem("Vertical Split", iconSplitVer);
		menuItem.setEnabled(true);
		menuItem.addActionListener(this);
		splitScreen.add(menuItem);
		menu.add(splitScreen);

		JMenu mergeScreen = new JMenu("Merge Screen");
		mergeScreen.setIcon(iconMerge);
		mergeScreen.setEnabled(true);
		mergeScreen.addActionListener(this);
		menu.add(mergeScreen);
		popup.add(menu);

		menu = new JMenu("Network");
		menu.setIcon(iconWifi);
		popup.add(menu);

		menuItem = new JMenuItem("Android Controller", iconAndroid);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Share Video", iconShare);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Receive Stream Video", iconReceive);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Save Current Video", iconSave);
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();
		menu = new JMenu("Youtube");
		menu.setIcon(iconYoutube);
		menuItem = new JMenuItem("Download video", iconSave);
		menuItem.setEnabled(false);
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Share this Video", iconShare);
		menuItem.setEnabled(false);
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Upload Video", iconUpload);
		menuItem.setEnabled(false);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		popup.add(menu);

		menuItem = new JMenuItem("Preferences", iconPref);
		menuItem.addActionListener(this);
		popup.add(menuItem);

		menuItem = new JMenuItem("About", iconAbout);
		menuItem.addActionListener(this);
		popup.add(menuItem);

		menuItem = new JMenuItem("Themes", iconPref);
		menuItem.addActionListener(this);
		popup.add(menuItem);

		menuItem = new JMenuItem("Exit", iconExit);
		menuItem.addActionListener(this);
		popup.add(menuItem);

		menu.addSeparator();

		MouseListener popupListener = new PopupListener(popup);
		MouseMotionListener popupMouseMotionListener = new PopupListener(popup);
		frame1.addMouseMotionListener(popupMouseMotionListener);
		frame1.addMouseListener(popupListener);
		frame2.addMouseMotionListener(popupMouseMotionListener);
		frame2.addMouseListener(popupListener);
	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		ImageIcon iconPlay = createImageIcon("/res/menu/play.png");
		ImageIcon checkIcon = createImageIcon("/res/checkmark.png");
		ImageIcon iconPause = createImageIcon("/res/newest/pause.png");
		switch (source.getText()) {

		case "Pause":
			source.setText("Play");
			mediaPlayerX.pause();
			source.setIcon(iconPlay);

			break;

		case "Play":
			mediaPlayerX.play();
			source.setText("Pause");
			source.setIcon(iconPause);

			break;
		case "Duplicate this Screen":
			startDuplicateScreen();
			break;
		case "Stop":
			mediaPlayerX.stop();
			break;
		case "Next":
			mediaListPlayer1.playNext();
			break;
		case "Previous":
			mediaListPlayer1.playPrevious();
			break;
		case "Repeat":
			mediaListPlayer1.setMode(MediaListPlayerMode.REPEAT);
			break;
		case "Normal":
			mediaListPlayer1.setMode(MediaListPlayerMode.LOOP);
			break;

		case "Horizontal Split":
			setupSplits("hor");
			break;
		case "Vertical Split":
			setupSplits("ver");
			break;
		case "Android Controller":
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						Parent root;
						root = FXMLLoader.load(getClass().getResource("/streaming/res/androidController.fxml"));
						Stage primaryStage = new Stage();
						primaryStage.initStyle(StageStyle.UTILITY);
						primaryStage.setResizable(false);
						primaryStage.setScene(new Scene(root, 667, 392));
						primaryStage.setTitle("Play,Stream & Share - Media Share");
						primaryStage.centerOnScreen();
						primaryStage.setAlwaysOnTop(true);
						// primaryStage.initStyle(StageStyle.UTILITY);
						primaryStage.show();

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			});
			break;
		case "Exit":
			System.exit(0);
			break;
		case "About":
			a = new AboutDialog(mainFrame);
			a.setResizable(false);
			// a.setUndecorated(true);
			a.getRootPane().setOpaque(false);
			// a.getContentPane().setBackground(new Color(0,0,0,0));
			// a.setBackground(new Color(0,0,0,0));
			a.setSize(600, 492);
			a.setLocation(mainFrame.getX() + 150, mainFrame.getY() + 150);
			a.setVisible(true);
			break;
		case "Snapshot                      ":
			File dir = new File(System.getProperty("user.home") + "\\Videos\\MediaShare\\snapshots");
			dir.mkdirs();
			String savePath = dir.getAbsolutePath();

			count++;

			mediaPlayerX.saveSnapshot(new File(savePath + "\\" + "snapshot" + count + ".png"));
			count++;
			break;
		case "Share Video":
			getCurrentMediaPath();

			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						Parent root;
						root = FXMLLoader.load(getClass().getResource("/streaming/res/share.fxml"));
						Stage primaryStage = new Stage();
						primaryStage.initStyle(StageStyle.UTILITY);
						primaryStage.setResizable(false);
						primaryStage.setScene(new Scene(root, 667, 392));
						primaryStage.setTitle("Play,Stream & Share - Media Share");
						primaryStage.centerOnScreen();
						primaryStage.setAlwaysOnTop(true);
						// primaryStage.initStyle(StageStyle.UTILITY);
						primaryStage.show();

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			});

			break;
		case "Receive Stream Video":
			mediaPlayerX.playMedia("rtsp://192.168.173.1:8050/1");
			// playStream("192.168.173.1","8050", "1");
			System.out.println("Playing stream");
			/*
			 * Platform.runLater(new Runnable(){
			 * 
			 * @Override public void run() { try { Parent root; root =
			 * FXMLLoader.load(getClass().getResource(
			 * "/streaming/res/receiveDialog.fxml")); Stage primaryStage = new
			 * Stage(); primaryStage.initStyle(StageStyle.UTILITY);
			 * primaryStage.setResizable(false); primaryStage.setScene(new
			 * Scene(root, 667, 410)); primaryStage.setTitle(
			 * "Play,Stream & Share - Media Share");
			 * primaryStage.centerOnScreen(); primaryStage.setAlwaysOnTop(true);
			 * 
			 * primaryStage.centerOnScreen(); primaryStage.show(); } catch
			 * (IOException e1) { e1.printStackTrace(); } }
			 * 
			 * });
			 */
			break;

		case "Save Current Video":
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						Parent root;
						root = FXMLLoader.load(getClass().getResource("/share/res/pcReceive.fxml"));
						primaryStage1 = new Stage();
						primaryStage1.initStyle(StageStyle.UTILITY);
						primaryStage1.setResizable(false);
						primaryStage1.setScene(new Scene(root, 667, 410));
						primaryStage1.setTitle("Play,Stream & Share - Media Share");
						primaryStage1.centerOnScreen();
						primaryStage1.setAlwaysOnTop(true);

						primaryStage1.centerOnScreen();
						primaryStage1.show();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			});

			break;

		case "Audio Spectrum":
			applet3.init();
			// for 2
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 2;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weighty = 0.5;
			gbc.weightx = 0.5;
			panel.add(spectrumFrame, gbc);
			spectrumFrame.setVisible(true);
			panel.revalidate();
			mainFrame.revalidate();

			break;
		case "Always on top":

			if (AlwaysOntop == false) {
				mainFrame.setAlwaysOnTop(true);
				source.setIcon(checkIcon);
				AlwaysOntop = true;
			} else {
				mainFrame.setAlwaysOnTop(false);
				source.setIcon(null);
				AlwaysOntop = false;
			}
			break;

		case "Equalizer":
			mediaPlayerX.setAdjustVideo(true);

			// JOptionPane.showMessageDialog(mainFrame,"Sorry Equalizer is not
			// supported Yet","Play,Stream & Share - Media
			// Share",JOptionPane.ERROR_MESSAGE,new
			// ImageIcon(getClass().getResource("/res/xmp.png")));
			Platform.runLater(new Runnable() {
				@Override
				public void run() {

					Parent root;
					try {
						root = FXMLLoader.load(getClass().getResource("/res/equalizer/equalizer.fxml"));
						Scene scene = new Scene(root, 600, 263);
						Stage primaryStage = new Stage();
						// scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
						primaryStage.setScene(scene);
						primaryStage.setTitle("Play,Stream & Share - Media Share");
						primaryStage.centerOnScreen();
						primaryStage.initStyle(StageStyle.UTILITY);
						primaryStage.setResizable(false);
						primaryStage.setAlwaysOnTop(true);
						primaryStage.show();

					} catch (IOException ee) {
						// TODO Auto-generated catch block
						ee.printStackTrace();
					}
				}

			});
			break;
		case "Preferences":

			Platform.runLater(new Runnable() {
				@Override
				public void run() {

					Parent root;
					try {
						root = FXMLLoader.load(getClass().getResource("/res/preferences.fxml"));
						Scene scene = new Scene(root, 736, 545);
						Stage primaryStage = new Stage();
						// scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
						primaryStage.setScene(scene);
						primaryStage.setTitle("Play,Stream & Share - Media Share");
						primaryStage.centerOnScreen();
						primaryStage.initStyle(StageStyle.UTILITY);
						primaryStage.setResizable(false);
						primaryStage.setAlwaysOnTop(true);
						primaryStage.show();

					} catch (IOException ee) {
						// TODO Auto-generated catch block
						ee.printStackTrace();
					}
				}

			});
			break;

		case "Fullscreen":
			fullscreen();
			break;
		case "Playlist":
			clickL();
			break;

		}
	}

	public static void closeStage() {
		primaryStage1.close();
	}

	public static void playStream(String ipadress, String port, String screen) {

		mediaPlayerX.playMedia(("rtsp://" + ipadress + ":" + port + "/" + screen));
		getDisplayMediatitle().setText("Streaming - " + ipadress);

	}

	public static String getCurrentMediaPath() {

		return mediaPath;
	}

	class PopupListener extends MouseAdapter {
		JPopupMenu popup;

		PopupListener(JPopupMenu popupMenu) {
			popup = popupMenu;
		}

		public void mouseMoved(MouseEvent e) {
			if (new ComponentResizer().getisBeingReaized() == false) {
				if (popup.getComponent().getCursor().getType() != Cursor.DEFAULT_CURSOR) {
					popup.setCursor(Cursor.getDefaultCursor());

				} else {

				}
			} else {
				if (!mediaPlayerX.isFullScreen()) {
					popup.setCursor(resizecursor);
				} else {
					popup.setCursor(Cursor.getDefaultCursor());
				}

			}
		}

		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e) {

			if (e.isPopupTrigger()) {
				// popup.show(e.getComponent(), e.getX()-popup.getWidth()/2,
				// e.getY()-popup.getHeight()/2);
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = MediaShare.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public void initStuff() {

		applet2 = new PlaylistClass();
		applet2.init();

		((javax.swing.plaf.basic.BasicInternalFrameUI) frame.getUI()).setNorthPane(null);
		((javax.swing.plaf.basic.BasicInternalFrameUI) frame.getUI()).setWestPane(null);
		((javax.swing.plaf.basic.BasicInternalFrameUI) frame.getUI()).setEastPane(null);
		((javax.swing.plaf.basic.BasicInternalFrameUI) frame.getUI()).setSouthPane(null);
		frame.setBorder(emptyBorder);
		frame.add(applet2.getContentPane(), BorderLayout.CENTER);
		frame.setSize(applet2.getContentPane().getSize());

		applet3 = new MainStage(mediaPath);

		((javax.swing.plaf.basic.BasicInternalFrameUI) spectrumFrame.getUI()).setNorthPane(null);
		((javax.swing.plaf.basic.BasicInternalFrameUI) spectrumFrame.getUI()).setWestPane(null);
		((javax.swing.plaf.basic.BasicInternalFrameUI) spectrumFrame.getUI()).setEastPane(null);
		((javax.swing.plaf.basic.BasicInternalFrameUI) spectrumFrame.getUI()).setSouthPane(null);
		spectrumFrame.setBorder(emptyBorder);
		spectrumFrame.add(applet3.getContentPane(), BorderLayout.CENTER);
		spectrumFrame.setSize(applet3.getContentPane().getSize());

		timer = new Timer(3000, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				stopTimer();

			}
		});
		timer.setRepeats(false);

		statusBarTimer = new Timer(2000, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				mainFrame.remove(statusApplet.getContentPane());
				mainFrame.revalidate();

			}
		});
		statusBarTimer.setRepeats(false);

		Timer volumeTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				stopVolumeAnimation();

			}
		});
		volumeTimer.setRepeats(false);

		Canvas c[] = { frame1, frame2, frame3, frame4 };

		for (int i = 0; i < c.length; i++) {
			j = i;
			c[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {

					new MainPlayer().setCuurentMediaPlayer(j);

					if (e.getClickCount() == 2) {
						fullscreen();
					}
				}

				public void mousePressed(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						popup.show(e.getComponent(), e.getX(), e.getY());
					}
				}
			});

			c[i].addMouseWheelListener(new MouseWheelListener() {

				public void mouseWheelMoved(MouseWheelEvent e) {

					int notches = e.getWheelRotation() * 6;

					if (notches < 0) {
						if (mediaPlayerX.getVolume() < 200)
							mediaPlayerX.setVolume(mediaPlayerX.getVolume() - notches);
						volume = mediaPlayerX.getVolume();
						initiateVolumeAnimation();
						volumeTimer.start();
						new VolumeAnimClass().updateVolume();
						v.revalidate();
						v.repaint();
						v.invalidate();

					} else {

						mediaPlayerX.setVolume(mediaPlayerX.getVolume() - notches);
						mediaPlayerX.setMarqueeText(String.valueOf(mediaPlayerX.getVolume()));
						mediaPlayerX.setMarqueeTimeout(3000);
						volume = mediaPlayerX.getVolume();
						initiateVolumeAnimation();
						volumeTimer.start();
						new VolumeAnimClass().updateVolume();
						v.revalidate();
						v.repaint();
						v.invalidate();

					}
					if (volumeTimer.isRunning()) {
						volumeTimer.restart();
					}

					// new VolumeAnim().setBarColor(mediaPlayer1.getVolume()/2);

				}

			});

			c[i].addMouseMotionListener(new MouseMotionAdapter() {

				@Override
				public void mouseDragged(java.awt.event.MouseEvent e) {

					if (!mediaPlayerX.isFullScreen() && !isMaximised) {
						int x = e.getXOnScreen();
						int y = e.getYOnScreen();
						mainFrame.setLocation(x - xMouse, y - yMouse);

						Thread t = new Thread(new Runnable() {

							@Override
							public void run() {
								if (fileType == false) {
									startOpacityTimer();
									stopVolumeAnimation();
								}
							}

						});
						t.start();
					}
				}

				@Override
				public void mouseMoved(MouseEvent e) {
					xMouse = e.getX();
					yMouse = e.getY();

					if (isPlayerEstablished == true) {
						if (mediaPlayerX.isFullScreen()) {
							if (!timer.isRunning()) {
								mainFrame.setCursor(Cursor.getDefaultCursor());
								mainFrame.revalidate();
								startTimer();
								timer.start();

							} else {
								timer.restart();
							}

						}
						if (new ComponentResizer().getisBeingReaized() == false) {
							if (mainFrame.getCursorType() != Cursor.DEFAULT_CURSOR) {
								mainFrame.setCursor(Cursor.getDefaultCursor());
							} else {

							}
						} else {
							if (!mediaPlayerX.isFullScreen()) {
								mainFrame.setCursor(resizecursor);
							} else {
								mainFrame.setCursor(Cursor.getDefaultCursor());
							}
						}

					}
				}

			}

			);

			c[i].setDropTarget(new DropTarget() {
				public synchronized void drop(DropTargetDropEvent evt) {
					isPlayerEstablished = true;

					try {
						evt.acceptDrop(DnDConstants.ACTION_REFERENCE);
						List<File> droppedFiles = (List<File>) evt.getTransferable()
								.getTransferData(DataFlavor.javaFileListFlavor);
						for (File file : droppedFiles) {
							System.out.println("File path is '" + file.getAbsolutePath() + "'.");
							mediaPath = file.getAbsolutePath();
							mediaPlayerX.playMedia(file.getAbsolutePath());// "rtsp://192.168.43.252:8554/voice"
							fullscreen();
							fullscreen();
							mediaPathName = file.getName();
							setTitle(mediaPathName);
							getDisplayMediatitle().setText(mediaPathName);
							StringBuilder sb = new StringBuilder();
							sb.append(file.getName().toUpperCase().trim());
							sb.append("     STREAMING - ");
							String parent = new File(file.getAbsolutePath().trim()).getParentFile().getAbsolutePath();
							sb.append(parent.toUpperCase().trim());
							String text = sb.toString();

						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}

				}
			});
		}
	}

	public static int getvolume() {
		return volume;
	}

	public String getCurrentPath() {
		java.io.File file = new java.io.File("lib");
		String path = file.getAbsolutePath();
		String only_path = path.substring(0, path.lastIndexOf('\\'));
		System.out.println("complete absolute path is " + path);
		System.out.println("absolute path is " + only_path);
		return path;
	}

	public void framework() {

		 String currentPath = new File("").getAbsolutePath();
		 String libPath = currentPath+"\\lib";

		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "D:\\Coding\\Workspace2016\\TestVideo\\lib");
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

	}

	public void player(int media) {

		embeddedMediaPlayerComponent1 = new EmbeddedMediaPlayerComponent();
		mediaPlayerFactory1 = embeddedMediaPlayerComponent1.getMediaPlayerFactory();
		mediaPlayerX = mediaPlayerFactory1
				.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(new MediaShare().mainFrame));
		mediaPlayerX.setVideoSurface(mediaPlayerFactory1.newVideoSurface(frame1));

		// embeddedMediaPlayerComponent1.setCursorEnabled(false);
		mediaPlayerX.setEnableMouseInputHandling(false);
		mediaPlayerX.setEnableKeyInputHandling(false);

		// new VideoMouseMovementDetector(frame1, 10000,
		// embeddedMediaPlayerComponent1);

		mediaListPlayer1 = mediaPlayerFactory1.newMediaListPlayer();
		mediaList1 = mediaPlayerFactory1.newMediaList();

		mediaListPlayer1.setMode(MediaListPlayerMode.LOOP);

		// mediaPath = "D:\\Music\\Basto - Again And Again (Official Video HD) -
		// YouTube.mp4";
		mediaPlayerX.setVolume(100);

		// options = formatStreamOptions("192.168.43.252", "8050", "1");
		mediaPlayerX.playMedia(mediaPath);// "rtsp://192.168.43.252:8554/voice"

		// mainFrame.setSize(
		// mediaPlayer1.getVideoSurfaceContents().getWidth(),mediaPlayer1.getVideoSurfaceContents().getHeight()-80);

		executorService.scheduleAtFixedRate(new UpdateRunnable(mediaPlayerX), 0L, 1L, TimeUnit.SECONDS);

		Timer playlistTimer = new Timer(3000, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				initializePlaylist(1);

			}
		});
		playlistTimer.setRepeats(false);
		playlistTimer.start();

		// initializePlaylist(1);

		// getDisplayMediatitle().setText(.getDuration()+" giu");

		// vol.setText("200");

		mediaListPlayer1.setMediaPlayer(mediaPlayerX);
		mediaListPlayer1.setMediaList(mediaList1);
		// mediaListPlayer1.playItem(0);
		// System.out.println("playing");

		mediaListPlayer1.addMediaListPlayerEventListener(new MediaListPlayerEventAdapter() {
			public void nextItem(MediaListPlayer mlp, libvlc_media_t item, String itemM) {
				String rawName = itemM.replaceAll("%20", " ").replaceAll("%28", "(").replaceAll("%29", ")")
						.replaceAll("%27", "'").replaceAll("%26", "&").replaceAll("/", "\\\\").replaceAll("%5B", "[")
						.replaceAll("%5D", "]").replaceAll("%3F", "강");
				final String finalString = rawName.substring(8, rawName.length());
				System.out.println(finalString);
				final String work = System.getProperty("user.dir");
				// final String work ="D://Music";

				int unwantedString = work.length() + 9;
				final String finalStringDisplay = rawName.substring(unwantedString, rawName.length());
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {

						getDisplayMediatitle().setText(finalStringDisplay);

						StringBuilder sb = new StringBuilder();
						sb.append(finalStringDisplay.toUpperCase().trim());
						sb.append("     STREAMING - ");
						String parent = new File(mediaPath).getParentFile().getAbsolutePath();
						sb.append(parent.toUpperCase().trim());
						String text = sb.toString();

						// statusBar.setText(text);

					}
				});
			}
		});

		/*****************************/
	}

	public static void setMediaFilter(FilenameFilter mediaFilter) {
		MediaShare.mediaFilter = mediaFilter;
	}

	public static FilenameFilter getMediaFilter() {
		return mediaFilter;
	}

	public static EmbeddedMediaPlayer getMediaPlayer1() {
		return mediaPlayerX;
	}

	public static void setMediaPlayer1(EmbeddedMediaPlayer mediaPlayer1) {
		MediaShare.mediaPlayerX = mediaPlayer1;
	}

	public static void addStreamcapabilities(String mediaPathP) {
		// mediaPlayer1.addMediaOptions(options,
		// ":no-sout-rtp-sap,:no-sout-standard-sap,:sout:all,:sout-keep");
		// mediaPlayer1.setStandardMediaOptions(options,
		// ":no-sout-rtp-sap,:no-sout-standard-sap,:sout:all,:sout-keep");
		isStreaming = true;

		float pos = mediaPlayerX.getPosition();
		mediaPlayerX.stop();
		mediaList1.clear();
		initializePlaylist(1);

		try {
			options = formatStreamOptionsRTSP(InetAddress.getLocalHost().getHostAddress().trim(), "8050", "1");
			mediaPlayerX.playMedia(mediaPath, options);
			System.out.println("playing " + mediaPath + "options");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// mediaPlayer1.setPosition(pos);
	}

	public static void playByPath(String mediapath) {
		mediaPlayerX.playMedia(mediapath);

		setupTrayIcon(mediapath);
	}

	public static void play(String mediapathname) {
		isPlayerEstablished = true;

		String path = (playlistMap.get((mediapathname.trim())));
		mediaPath = path.trim();
		// JOptionPane.showMessageDialog(null, path);
		mediaPlayerX.playMedia(path.trim());// "rtsp://192.168.43.252:8554/voice"
		setTitle(mediaPathName);
		mediaPathName = mediapathname;
		// JOptionPane.showMessageDialog(null, mediaList1.items().get(0).mrl());

		/*
		 * for (int i = 0; i < mediaList1.size(); i++){
		 * if(mediaList1.items().get(i).mrl().equals(mediaPath)){ currentIndex =
		 * i; mediaListPlayer1.playItem(currentIndex);
		 * 
		 * } }
		 */

		setupTrayIcon(mediaPathName);

	}

	public static void updateVolume(int vol) {
		mediaPlayerX.setVolume(vol);

	}

	public static void setPos(float positionValue) {
		mediaPlayerX.setPosition(positionValue);

	}

	public static void initializePlaylist(int option) {

		Thread playlistThread = new Thread(new Runnable() {
			@Override
			public void run() {

				DefaultListModel listModel = new DefaultListModel();

				final String work = System.getProperty("user.dir");
				// final String work ="D://Music";

				setMediaFilter(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						String lowerCasename = name.toLowerCase();
						if (lowerCasename.endsWith(".vob")) {

							return true;

						} else if (lowerCasename.endsWith(".mp4")) {
							return true;

						} else if (lowerCasename.endsWith(".mp3 files")) {
							return true;

						} else if (lowerCasename.endsWith(".mp3")) {

							return true;

						} else if (lowerCasename.endsWith(".avi")) {
							return true;

						} else if (lowerCasename.endsWith(".3gp")) {
							return true;

						} else if (lowerCasename.endsWith(".mpeg3")) {
							return true;

						} else if (lowerCasename.endsWith(".mpeg4")) {
							return true;

						} else if (lowerCasename.endsWith(".mpeg2")) {
							return true;

						} else if (lowerCasename.endsWith(".webm")) {
							return true;

						} else if (lowerCasename.endsWith(".VOB")) {
							return true;

						} else if (lowerCasename.endsWith(".flv")) {
							return true;

						} else if (lowerCasename.endsWith(".mkv")) {
							return true;

						} else {
							return false;
						}
					}
				});

				File currentDirectory = new File(work);
				File[] flist1 = currentDirectory.listFiles(getMediaFilter());

				for (File file1 : flist1) {
					if (file1.isFile()) {
						playlist.add(file1.getName());
						playlistMap.put(file1.getName(), file1.getAbsolutePath());
						switch (option) {

						case 1:
							if (isStreaming == false) {
								mediaList1.addMedia(file1.getAbsolutePath());
							} else {
								mediaList1.addMedia(file1.getAbsolutePath(), options);
							}
							break;
						case 2:

							mediaList2.addMedia(file1.getAbsolutePath());
							break;
						case 3:

							mediaList3.addMedia(file1.getAbsolutePath());
							break;
						case 4:
							mediaList4.addMedia(file1.getAbsolutePath());
							break;
						default:
							if (isStreaming == false) {
								mediaList1.addMedia(file1.getAbsolutePath());
							} else {
								mediaList1.addMedia(file1.getAbsolutePath(), options);
							}
							break;
						}

						// mediaList2.addMedia(file1.getAbsolutePath());
						// mediaList3.addMedia(file1.getAbsolutePath());
						// mediaList4.addMedia(file1.getAbsolutePath());

					}
				}

				// mediaShare.androidCommandServer();

			}

		});
		playlistThread.start();

	}

	@Override
	public void init() {
		fxContainer = new JFXPanel();

		// fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT,
		// JFXPANEL_HEIGHT_INT));
		add(fxContainer, BorderLayout.CENTER);
		// create JavaFX scene
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				createScene();
			}
		});

	}

	private void createScene() {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/res/controls.fxml"));

			Scene scene = new Scene(root);

			fxContainer.setScene(scene);
			// new SinglePlayer().startControls();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startDuplicateScreen() {
		addStreamcapabilities(mediaPath);
		setupSplits("hor");
	}

	private static String formatStreamOptionsRTSP(String serverAddress, String serverPort, String id) {
		StringBuilder sb = new StringBuilder(60);
		sb.append(":sout=#rtp{sdp=rtsp://@");
		sb.append(serverAddress);
		sb.append(":");
		sb.append(serverPort);
		sb.append("/");
		sb.append(id);
		sb.append("}");
		return sb.toString();
	}

	private static String formatStreamOptions(String serverAddress, String serverPort, String id) {
		StringBuilder sb = new StringBuilder(60);
		sb.append(":sout=#duplicate{dst=display,dst=rtp{dst=");
		sb.append(serverAddress);
		sb.append(",port=");
		sb.append(serverPort);
		sb.append(",mux=ts}}");

		return sb.toString();
	}

	private static String formatRtpStream(String serverAddress, int serverPort) {
		StringBuilder sb = new StringBuilder(60);
		sb.append(":sout=#rtp{dst=");
		sb.append(serverAddress);
		sb.append(",port=");
		sb.append(serverPort);
		sb.append(",mux=ts}");
		return sb.toString();
	}

	/****************************
	 * RECEIVING ANDROID COMMANDS
	 ************************************/

	protected void startPlaylistServer() {
		try {
			server3 = new ServerSocket(5656, 1);
			Socket connection3 = server3.accept();
			ObjectOutputStream outputStream3 = new ObjectOutputStream(connection3.getOutputStream());
			inputStream3 = new ObjectInputStream(connection3.getInputStream());
			outputStream3.writeObject(playlist);
			outputStream3.flush();
			showCommand("\n Playlist Streams are now setup \n ");
			receivePlaylistRequest();
		} catch (IOException e) {
			showCommand("/n Server <Receiving Android  Command>>> ended connection !\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	protected void androidCommandServer() {
		try {
			server1 = new ServerSocket(6767, 3);
			server2 = new ServerSocket(6868, 3);

			showCommand("<Receiving Android  Command>>> Activated");

			try {

				setUpAndroidCommandStreams();
				receiveAndroidCommands();

				showCommand("/n Server <ACCEPTING Android  Command>>> !");
				isConnected = true;

			} catch (EOFException | ClassNotFoundException eofException) {
				showCommand(
						"/n Server <Receiving Android  Command>>> ended connection !\n" + eofException.getMessage());
			} finally {
				// outputStream1.close();
				// inputStream1.close();
				// inputStream3.close();
				// server1.close();
			}

		} catch (IOException ioE) {
			ioE.printStackTrace();
		}

	}

	public void setUpAndroidCommandStreams() throws IOException, ClassNotFoundException {

		Socket connection2 = server2.accept();
		ObjectOutputStream outputStream2 = new ObjectOutputStream(connection2.getOutputStream());

		String pcName = InetAddress.getLocalHost().getHostName().toString();
		outputStream2.writeObject(pcName.toUpperCase());
		outputStream2.flush();

		connection1 = server1.accept();
		outputStream1 = new ObjectOutputStream(connection1.getOutputStream());
		inputStream1 = new ObjectInputStream(connection1.getInputStream());

		setupComplete = true;

		showCommand("\n Streams are now setup \n ");

	}

	/**
	 * This method is for recieving android commands to the player controls
	 * 
	 * @throws ClassNotFoundException
	 ***/

	private void receivePlaylistRequest() {

		if (androidMedia.equals("")) {
			System.out.println("empty string from playlist");
		}
		do {
			try {
				androidMedia = (String) inputStream3.readObject();
				System.out.println("playlist media " + androidMedia);
				play(androidMedia);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} while (!androidMedia.equals("END000"));
	}

	public void receiveAndroidCommands() throws IOException {

		do {
			try {
				androidcommand = (String) inputStream1.readObject();
				switch (androidcommand) {
				case "next":
					mediaListPlayer1.playNext();
					break;
				case "prev":
					mediaListPlayer1.playPrevious();
					break;
				case "stop":
					mediaPlayerX.stop();
					break;
				case "play":
					mediaPlayerX.pause();
					break;

				case "fullscreen":
					fullscreen();
					break;

				case "forward":
					mediaPlayerX.skip(15000);
					break;
				case "backward":
					mediaPlayerX.skip(-15000);
					break;
				case "activateStream":
					// addStreamcapabilities(mediaPath);
					break;
				default:
					mediaPlayerX.setPosition(Float.parseFloat(androidcommand) / 1000.0f);
					break;

				}

				showCommand("\n" + androidcommand);
			} catch (ClassNotFoundException cnf) {
				cnf.printStackTrace();
			}

		} while (!androidcommand.equals("END"));

	}

	private void showCommand(final String text) {
		System.out.println(text);
	}

	public static void startTimer() {
		if (mediaPlayerX.isFullScreen()) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {

					if (isPlaylistActivated == true && isScreenSplit == false) {
						initiateFloatingControlClass();
						mainFrame.setCursor(Cursor.getDefaultCursor());
						/*
						 * mainFrame.add(applet.getContentPane(),BorderLayout.
						 * SOUTH); mainFrame.revalidate();For docked playlist
						 */
					} else if (isScreenSplit == true) {
						mainFrame.add(applet.getContentPane(), BorderLayout.SOUTH);
						mainFrame.revalidate();
					} else {
						timer.stop();

						closeFLoatingStage();
					}
				}

			});

		}

	}

	public void stopTimer() {
		if (mediaPlayerX.isFullScreen()) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					if (isScreenSplit == true) {
						mainFrame.remove(applet.getContentPane());
						mainFrame.revalidate();
					}

					BufferedImage cursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
					Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0),
							"blankCursor");
					mainFrame.setCursor(blankCursor);

					closeFLoatingStage();

					timer.stop();
				}

			});

		}

	}

	private void setSliderBasedPosition() {
		if (!mediaPlayerX.isSeekable()) {
			return;
		}

		float positionValue = positionSlider.getValue() / 1000.0f;
		if (positionValue > 0.99f) {
			positionValue = 0.99f;
		}

		mediaPlayerX.setPosition(positionValue);

	}

	private void updateUIState() {

		long time = mediaPlayerX.getTime();
		int position = (int) (mediaPlayerX.getPosition() * 1000.0f);

		updateTime(time);
		// updatePosition(position);

	}

	private void updateTime(long millis) {

		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		if (minutes < 60) {
			timeLabel = String.format("%02d:%02d", ((minutes >= 0 && minutes < 60) ? minutes : minutes % 60),
					((seconds >= 0 && seconds < 60) ? seconds : seconds % 60));
					// timeLabel.setText(s);
					// timeLabel.setBounds(50, 910, 50, 20);
					// System.out.println(timeLabel);

			// currentTime.setText(timeLabel);
			new ControlsClass().displayCurrentTime(timeLabel);
			new MainPlaylistClass().displayCurrentTime(timeLabel);
			new FloatingControlsClass().displayCurrentTime(timeLabel);

		} else {
			String s = String.format("%02d:%02d:%02d", hours, ((minutes >= 0 && minutes < 60) ? minutes : minutes % 60),
					((seconds >= 0 && seconds < 60) ? seconds : seconds % 60));
			// timeLabel.setText(s);
			// timeLabel.setBounds(50, 910, 75, 20);

		}

	}

	public static void stopVolumeAnimation() {
		// v.hide();
		// v.setVisible(false);
	}

	public void initiateVolumeAnimation() {
		if (isVolumeAnimationInitialized == false) {
			isVolumeAnimationInitialized = true;
			v = new VolumeAnimDialog(mainFrame);
			v.setUndecorated(true);
			v.getRootPane().setOpaque(false);
			v.getContentPane().setBackground(new Color(0, 0, 0, 0));
			v.setBackground(new Color(0, 0, 0, 0));
			v.setSize(30, 510);
			v.setLocation((int) mainFrame.getX() + (frame1.getWidth() - 100),
					mainFrame.getY() + (frame1.getHeight() - 400) / 2);
			v.setVisible(true);
			// v.show();
		} else {
			v.setLocation((int) mainFrame.getX() + (frame1.getWidth() - 100),
					mainFrame.getY() + (frame1.getHeight() - 400) / 2);
			v.setVisible(true);
			// v.show();
		}

	}

	public static void setupTrayIcon(String mediaPathName) {

		if (eventStatus == false) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (isItup) {
						new TrayIconClass().createAndShowGUI();
						isItup = false;
					}
					new TrayIconClass().getTrayIcon().displayMessage("Media Share", "" + mediaPathName,
							TrayIcon.MessageType.INFO);
				}
			});
		}
	}

	// Creates playlist
	public void clickL() {

		if (playlistCount == 10) {
			mainFrame.add(frame, BorderLayout.EAST);
		}

		if (isPlaylistActivated == true) {
			playlistCount++;
			if (playlistCount % 2 == 0) {
				isDockedPlaylistActivated = false;
				frame.setVisible(false);
				mainFrame.revalidate();
			} else if (playlistCount % 2 == 1) {
				if (isDockedPlaylistActivated == false) {
					isDockedPlaylistActivated = true;
					frame.setVisible(true);
					mainFrame.revalidate();
				}
			}
		} else {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					isPlaylistActivated = true;
					isDockedPlaylistActivated = false;
					playlistCount = 100;
					new PlaylistClass().closePlayslistStage();

				}

			});

		}

	}

	private void PanelsOne() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				mediaPlayerX = new MainPlayer().getM();
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.gridwidth = 2;
				gbc.gridheight = 1;
				gbc.fill = GridBagConstraints.BOTH;
				gbc.weighty = 0.5;
				gbc.weightx = 0.5;
				frame1.setSize(new Dimension(panel.getWidth(), panel.getHeight() / 2));
				mainPanel.add(panel, gbc);

				panel.revalidate();
				panel2.revalidate();
				mainPanel.revalidate();
			}

		});

	}

	private void PanelsTwo() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				mediaPlayerX = new MainPlayer().getM();
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.gridwidth = 2;
				gbc.gridheight = 1;
				gbc.fill = GridBagConstraints.BOTH;
				gbc.weighty = 0.5;
				gbc.weightx = 0.5;
				// frame1.setSize(new
				// Dimension(panel.getWidth(),panel.getHeight()/2));
				// mainPanel.add(panel,gbc);

				gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.gridwidth = 2;
				gbc.gridheight = 1;
				gbc.fill = GridBagConstraints.BOTH;
				gbc.weighty = 0.5;
				gbc.weightx = 0.5;
				// frame2.setSize(new
				// Dimension(panel.getWidth(),panel.getHeight()/2));
				// mainPanel.add(panel2,gbc);

				// panel.remove(frame3);
				// panel.remove(frame4);

				JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
				splitPane.setTopComponent(panel);
				splitPane.setBottomComponent(panel2);
				splitPane.setRightComponent(new JPanel());
				splitPane.setLeftComponent(new JPanel());

				Dimension minimumSize = new Dimension(
						new Dimension(mainPanel.getWidth() / 2, mainPanel.getHeight() / 2));
				panel.setMinimumSize(minimumSize);
				panel2.setMinimumSize(minimumSize);
				splitPane.setDividerLocation(200);
				splitPane.setPreferredSize(new Dimension(mainPanel.getWidth(), mainPanel.getHeight()));
				splitPane.setOpaque(false);
				// Add the split pane to this panel.
				mainPanel.add(splitPane);

				frame1.revalidate();
				frame2.revalidate();

				panel.revalidate();
				mainPanel.revalidate();
			}

		});

	}

	private void PanelsThree() {

		mediaPlayerX = new MainPlayer().getM();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 0.5;
		gbc.weightx = 0.5;
		frame1.setSize(new Dimension(panel.getWidth(), panel.getHeight() / 2));
		panel.add(frame1, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 0.5;
		gbc.weightx = 0.5;
		frame2.setSize(new Dimension(panel.getWidth() / 2, panel.getHeight() / 2));
		panel.add(frame2, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 0.5;
		gbc.weightx = 0.5;
		frame3.setSize(new Dimension(panel.getWidth() / 2, panel.getHeight() / 2));
		panel.add(frame3, gbc);

		frame1.revalidate();
		frame2.revalidate();
		frame3.revalidate();

		panel.revalidate();

	}

	private void PanelsFour() {

		mediaPlayerX = new MainPlayer().getM();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 0.5;
		gbc.weightx = 0.5;
		frame1.setSize(new Dimension(panel.getWidth() / 2, panel.getHeight() / 2));
		panel.add(frame1, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 0.5;
		gbc.weightx = 0.5;
		frame2.setSize(new Dimension(panel.getWidth() / 2, panel.getHeight() / 2));
		panel.add(frame2, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 0.5;
		gbc.weightx = 0.5;
		frame3.setSize(new Dimension(panel.getWidth() / 2, panel.getHeight() / 2));
		panel.add(frame3, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 0.5;
		gbc.weightx = 0.5;
		frame4.setSize(new Dimension(panel.getWidth() / 2, panel.getHeight() / 2));
		panel.add(frame4, gbc);

		frame1.revalidate();
		frame2.revalidate();
		frame3.revalidate();
		frame4.revalidate();
		panel.revalidate();

	}

	public void setupSplits(String splitType) {

		Border empty = BorderFactory.createEmptyBorder();
		TitledBorder titled = BorderFactory.createTitledBorder(empty, "Screen 1");
		titled.setTitleFont(new Font("Century Gothic", Font.BOLD, 12));
		titled.setTitleColor(Color.WHITE);
		titled.setBorder(emptyBorder);

		if (isFirstSplit == false && isSecondSplit == false) {
			isFirstSplit = true;
			isScreenSplit = true;
			/*
			 * if(isScreenSplit==true){ if(splitType.equals("hor")){
			 * topSplitPlane.setOrientation(JSplitPane.HORIZONTAL_SPLIT); }else
			 * if(splitType.equals("ver")){
			 * topSplitPlane.setOrientation(JSplitPane.VERTICAL_SPLIT); }
			 * topSplitPlane.revalidate(); System.out.println("heuy stom fussim"
			 * );
			 * 
			 * }else if(isScreenSplit==false){
			 */

			panel.setBorder(titled);
			new JSplitPaneWithZeroSizeDivider().initializeSplits();
			topSplitPlane.setDividerSize(1);
			topSplitPlane.setRightComponent(panel2);
			Dimension minimumSize = new Dimension(new Dimension(200, 200));
			panel2.setMinimumSize(minimumSize);
			topSplitPlane.setDividerLocation(768);

			if (splitType.equals("hor")) {
				topSplitPlane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			} else if (splitType.equals("ver")) {
				topSplitPlane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			}

			new MainPlayer().player2();
			mediaPlayerX = new MainPlayer().getM();

		} else if (isFirstSplit == true && isSecondSplit == false) {
			isSecondSplit = true;
			isFirstSplit = false;

			new JSplitPaneWithZeroSizeDivider().initializeSplits();
			Dimension minimumSize = new Dimension(new Dimension(200, 200));
			bottomSplitPlane.setBorder(null);
			panel3.setMinimumSize(minimumSize);

			// panel3.setPreferredSize(new
			// Dimension(mainPanel.getWidth()/2,mainPanel.getHeight()/2));
			bottomSplitPlane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			bottomSplitPlane.setLeftComponent(panel3);
			topSplitPlane.setPreferredSize(new Dimension(mainSplitPlane.getWidth(), mainSplitPlane.getHeight() / 2));
			bottomSplitPlane.setPreferredSize(new Dimension(mainSplitPlane.getWidth(), mainSplitPlane.getHeight() / 2));

			mainSplitPlane.setDividerSize(1);
			bottomSplitPlane.setMinimumSize(minimumSize);
			mainSplitPlane.setDividerLocation(300);
			mainSplitPlane.setBottomComponent(bottomSplitPlane);
			mainSplitPlane.revalidate();
			if (splitType.equals("hor")) {
				// bottomSplitPlane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			} else if (splitType.equals("ver")) {
				// bottomSplitPlane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			}

			new MainPlayer().player3();
			mediaPlayerX = new MainPlayer().getM();

		} else if (isSecondSplit == true && isFirstSplit == false) {
			Dimension minimumSize = new Dimension(new Dimension(200, 200));
			bottomSplitPlane.setDividerSize(1);
			panel4.setMinimumSize(minimumSize);
			bottomSplitPlane.setDividerLocation(300);
			bottomSplitPlane.setRightComponent(panel4);
			new MainPlayer().player4();
			mediaPlayerX = new MainPlayer().getM();
		}
	}

	public static void fullscreen() {

		if (mediaPlayerX.isFullScreen()) {

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					closeFLoatingStage();
				}

			});
			mediaPlayerX.setFullScreen(false);
			mainFrame.add(applet.getContentPane(), BorderLayout.SOUTH);
			mainFrame.add(TopFrame, BorderLayout.NORTH);
			mainFrame.setCursor(Cursor.getDefaultCursor());
			mainFrame.revalidate();
			if (isMaximised) {

				cr.deregisterComponent(mainFrame);
				cr.deregisterComponent(panel);
				cr.deregisterComponent(frame1);
			} else {
				cr.registerComponent(mainFrame);
				cr.registerComponent(panel);
				cr.registerComponent(frame1);
			}

		} else if (!mediaPlayerX.isFullScreen()) {
			mediaPlayerX.setFullScreen(true);
			mainFrame.remove(TopFrame);
			mainFrame.remove(applet.getContentPane());
			mainFrame.revalidate();

			cr.deregisterComponent(mainFrame);
			cr.deregisterComponent(panel);
			cr.deregisterComponent(frame1);

			startTimer();

		}
	}

	public void clickTwo() {

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		panel.add(frame2, gbc);

		panel.revalidate();
		mainFrame.revalidate();
		// player(0);
		// player2();
	}

	public static void playNext() {
		mediaListPlayer1.playNext();
	}

	public static void playPrev() {
		mediaListPlayer1.playNext();
	}

	public void onPauseGraphics() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				Stage primaryStage = new Stage();

				try {

					Parent root;
					root = FXMLLoader.load(getClass().getResource("/res/onPause.fxml"));
					primaryStage.initStyle(StageStyle.TRANSPARENT);
					Scene scene = new Scene(root, 70, 70);

					scene.setFill(javafx.scene.paint.Color.TRANSPARENT);

					primaryStage.setScene(scene);

					primaryStage.setX(panel.getWidth() / 2 + panel.getLocationOnScreen().getX() - scene.getWidth() / 2);
					primaryStage
							.setY(panel.getHeight() / 2 + panel.getLocationOnScreen().getY() - scene.getHeight() / 2);
					primaryStage.setAlwaysOnTop(true);
					// primaryStage.centerOnScreen();
					primaryStage.show();
					Timeline timeline;
					int i = 0;
					timeline = new Timeline(new KeyFrame(Duration.millis(200), event -> {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								primaryStage.close();
							}
						});

					}));
					timeline.setCycleCount(1);
					timeline.play();

				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}

		});
	}

	public static void closeFLoatingStage() {
		// floatingStage.hide();
		f.hide();
	}

	public void setFControlLocation(int x, int y) {

		f.setLocation(x, y);
	}

	public static void initiateFloatingControlClass() {
		if (isFloatingControlsactivated == false) {
			f = new FControlDialog(mainFrame);
			f.setUndecorated(true);
			f.setSize(755, 31);
			f.setLocation((int) (frame1.getWidth() / 2 - f.getWidth() / 2), mainFrame.getHeight() - 100);
			f.setVisible(true);
			f.show();

			isFloatingControlsactivated = true;

			/*
			 * Platform.runLater(new Runnable() {
			 * 
			 * @Override public void run() {
			 * 
			 * 
			 * floatingStage = new Stage();
			 * 
			 * 
			 * try {
			 * 
			 * Parent root; root = FXMLLoader.load(getClass().getResource(
			 * "/res/floatingControls.fxml"));
			 * floatingStage.initStyle(StageStyle.TRANSPARENT); Scene scene =
			 * new Scene(root, 757, 46 );
			 * 
			 * scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
			 * 
			 * 
			 * floatingStage.setScene(scene);
			 * floatingStage.setX(panel.getWidth()/2+panel.getLocationOnScreen()
			 * .getX()-scene.getWidth()/2);
			 * floatingStage.setY(mainFrame.getHeight()-100);
			 * floatingStage.setAlwaysOnTop(true);
			 * 
			 * //primaryStage.centerOnScreen(); floatingStage.show();
			 * isFloatingControlsactivated= true; Timeline timeline; int i =0;
			 * timeline = new Timeline(new KeyFrame(Duration.millis(200), event
			 * -> { Platform.runLater(new Runnable() {
			 * 
			 * @Override public void run() { //floatingStage.setIconified(true);
			 * //floatingStage.close(); } });
			 * 
			 * })); timeline.setCycleCount(1); //timeline.play();
			 * 
			 * } catch (IOException e1) { e1.printStackTrace(); }
			 * 
			 * }
			 * 
			 * 
			 * });
			 */
		} else {
			// floatingStage.show();
			f.show();
		}
	}

	public void onPlayGraphics() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				Stage primaryStage = new Stage();

				try {

					Parent root;
					root = FXMLLoader.load(getClass().getResource("/res/onPause.fxml"));
					primaryStage.initStyle(StageStyle.TRANSPARENT);
					Scene scene = new Scene(root, 70, 70);

					scene.setFill(javafx.scene.paint.Color.TRANSPARENT);

					primaryStage.setScene(scene);

					primaryStage.setX(panel.getWidth() / 2 + panel.getLocationOnScreen().getX() - scene.getWidth() / 2);
					primaryStage
							.setY(panel.getHeight() / 2 + panel.getLocationOnScreen().getY() - scene.getHeight() / 2);
					primaryStage.setAlwaysOnTop(true);
					// primaryStage.centerOnScreen();
					primaryStage.show();
					Timeline timeline;
					int i = 0;
					timeline = new Timeline(new KeyFrame(Duration.millis(200), event -> {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								primaryStage.close();
							}
						});

					}));
					timeline.setCycleCount(1);
					timeline.play();

				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}

		});
	}

	final class VideoMouseMovementDetector extends MouseMovementDetector {

		private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

		VideoMouseMovementDetector(Component component, int timeout,
				EmbeddedMediaPlayerComponent mediaPlayerComponent) {
			super(component, timeout);
			this.mediaPlayerComponent = mediaPlayerComponent;
		}

		@Override
		protected void onMouseAtRest() {
			mediaPlayerComponent.setCursorEnabled(false);
			mediaPlayerX.setEnableMouseInputHandling(true);
			mediaPlayerX.setEnableKeyInputHandling(true);
		}

		@Override
		protected void onMouseMoved() {
			mediaPlayerComponent.setCursorEnabled(true);
			mediaPlayerX.setEnableMouseInputHandling(false);
			mediaPlayerX.setEnableKeyInputHandling(false);
		}

		@Override
		protected void onStopped() {
			mediaPlayerComponent.setCursorEnabled(false);
			mediaPlayerX.setEnableMouseInputHandling(true);
			mediaPlayerX.setEnableKeyInputHandling(true);
		}
	}

	public static void setEventStatus(Boolean eventStatus) {
		MediaShare.eventStatus = eventStatus;
	}

	public static Boolean getEventStatus() {
		return eventStatus;
	}

	public final class UpdateRunnable implements Runnable {

		private final EmbeddedMediaPlayer mediaPlayer;

		public UpdateRunnable(EmbeddedMediaPlayer mediaPlayer1) {
			this.mediaPlayer = mediaPlayer1;
		}

		public void run() {
			final long time = mediaPlayer.getTime();
			final int position = (int) (mediaPlayer.getPosition() * 1000.0f);

			// Updates to user interface components must be executed // Dispatch
			// Thread
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if (mediaPlayer.isPlaying()) {
						try {
							if (position > 0 && setupComplete == true) {
								System.out.println(position);
								outputStream1.writeObject(position);
								outputStream1.flush();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						updateTime(time);

						// updatePosition(position);
						// new ControlsClass().updatePosition(position);
					}
				}
			});
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		eventStatus = true;
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		eventStatus = false;

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		eventStatus = true;

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// System.exit(0);
		eventStatus = false;
		setupTrayIcon(mediaPathName);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		try {
			GlobalScreen.registerNativeHook();

		} catch (NativeHookException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		GlobalScreen.addNativeKeyListener(new MediaShare());
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent event) {
		if (eventStatus) {
			switch (event.getKeyCode()) {

			case NativeKeyEvent.VC_SHIFT_L:
				isShiftPressed = true;
				break;
			case NativeKeyEvent.VC_SHIFT_R:
				isShiftPressed = true;
				break;
			}

			if (event.getKeyCode() == NativeKeyEvent.VC_SPACE) {
				// new PlaylistClass().onPlayControls();
				// new ControlsClass().onPlayControls();
				if (mediaPlayerX.isPlaying()) {
					//
					mediaPlayerX.pause();
					onPauseGraphics();
					mediaPlayerX.setAdjustVideo(true);

				} else {
					mediaPlayerX.play();
					onPlayGraphics();
				}

			} else if (event.getKeyCode() == NativeKeyEvent.VC_LEFT) {
				mediaPlayerX.skip(-15000);

			} else if ((event.getKeyCode() == NativeKeyEvent.VC_RIGHT)) {
				mediaPlayerX.skip(15000);

			} else if ((event.getKeyCode() == NativeKeyEvent.VC_F)) {

				fullscreen();

			} else if ((event.getKeyCode() == NativeKeyEvent.VC_P)) {

				mediaListPlayer1.playPrevious();

			} else if ((event.getKeyCode() == NativeKeyEvent.VC_S)) {
				// mediaPlayer1.stop();
			} else if ((event.getKeyCode() == NativeKeyEvent.VC_N)) {

				mediaListPlayer1.playNext();

			} else if ((event.getKeyCode() == NativeKeyEvent.VC_L)) {

				clickL();

			} else if ((event.getKeyCode() == NativeKeyEvent.VC_4)) {

				PanelsFour();

			} else if ((event.getKeyCode() == NativeKeyEvent.VC_3)) {

				PanelsThree();

			} else if ((event.getKeyCode() == NativeKeyEvent.VC_2)) {

				PanelsTwo();

			} else if ((event.getKeyCode() == NativeKeyEvent.VC_1)) {

				PanelsOne();

			}

		}

	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent event) {
		switch (event.getKeyCode()) {

		case NativeKeyEvent.VC_SHIFT_L:
			isShiftPressed = false;
			break;
		case NativeKeyEvent.VC_SHIFT_R:
			isShiftPressed = false;
			break;
		}

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		// TODO Auto-generated method stub

	}

}
