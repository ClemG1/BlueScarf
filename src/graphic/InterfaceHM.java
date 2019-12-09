package graphic;

import java.awt.*;
import javax.swing.*;

public class InterfaceHM {
	
	//Attributes
	JFrame mainWindow; //main window of the app
	JPanel relativePanel; //this is a panel that has the same size than the main window to make it responsive
	JPanel usersSection; //section where online users are displayed
	JPanel chatingSection; //section for chat with your friend ;)
	JPanel filesSection; //section where available files are displayed
	JMenuBar menuBar; //menu at the top of the interface

	/**
	  * @brief : class constructor
	  * @param : none
	  * @returns : none
	 **/
	public InterfaceHM () {
		//use to set the size of the sections in the relative panel
		float sizeBorderSection = 1; //users and files section
		float sizeMiddleSection = 3; //chating section
		
		//create a relative panel for responsive
		RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS,10);
		this.relativePanel = new JPanel(rl);
		this.relativePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		//create the main window
		this.mainWindow = new JFrame("BlueScarf");
		
		//create the menu bar
		this.menuBar = new JMenuBar();
		
		//set the gap between each component of the menu bar
		this.menuBar.setMargin(new Insets(10,20,5,20));
		
		//set the background color of the menu bar
		Color menuBarColor = new Color(19, 73, 107);
		this.menuBar.setBackground(menuBarColor);
		
		//create the different panel for each section
		this.usersSection = new JPanel(new GridLayout(2, 0));
		this.chatingSection = new JPanel(new GridLayout(2, 0));
		this.filesSection = new JPanel(new GridLayout(2, 0));
		
		//add the sections at the relative panel
		this.relativePanel.add(this.usersSection,sizeBorderSection);
		this.relativePanel.add(this.chatingSection,sizeMiddleSection);
		this.relativePanel.add(this.filesSection,sizeBorderSection);
	}
	
	private void addWidgets() {
		
		//create menu
		JMenu customizeMenu = new JMenu("Customize");
		JMenu helpMenu = new JMenu("Help");
		
		//create menu items
		JMenuItem profilMenuItem = new JMenuItem("profil");
		JMenuItem helpMenuItem = new JMenuItem("About BlueScarf");
		
		//add menus to the menu bar
		this.menuBar.add(customizeMenu);
		this.menuBar.add(helpMenu);
		
		//add menu items to the menu
		customizeMenu.add(profilMenuItem);
		helpMenu.add(helpMenuItem);
		
		//create labels
		JLabel usersLabel = new JLabel("Online Users : ", SwingConstants.LEFT);
		JLabel chatLabel = new JLabel("Chat Box : ", SwingConstants.LEFT);
		JLabel filesLabel = new JLabel("Your Files : ", SwingConstants.LEFT);
		
		//set the vertical alignment of the labels
		usersLabel.setVerticalAlignment(SwingConstants.TOP);
		chatLabel.setVerticalAlignment(SwingConstants.TOP);
		filesLabel.setVerticalAlignment(SwingConstants.TOP);
		
		//set the panels' size when you open the app
		this.usersSection.setPreferredSize(new Dimension(200, 800));
		this.chatingSection.setPreferredSize(new Dimension(400, 800));
		this.filesSection.setPreferredSize(new Dimension(200, 800));
		
		//set borders around panels
		this.usersSection.setBorder(BorderFactory.createLineBorder(Color.black));
		this.chatingSection.setBorder(BorderFactory.createLineBorder(Color.black));
		this.filesSection.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//add the labels to the panels
		this.usersSection.add(usersLabel);
		this.chatingSection.add(chatLabel);
		this.filesSection.add(filesLabel);
	}
	
	private void createAndShowGUI() {
		
		try {
			//Make sure we have nice window decorations.
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
	        JFrame.setDefaultLookAndFeelDecorated(true);
	        
	        //set up the  main window.
	        this.mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	        
	        //creates the components of the window
	        addWidgets();
	        
	        //add the menu bar to the main window
	        this.mainWindow.setJMenuBar(this.menuBar);
	        
	        //add the relative panel to the window
	        this.mainWindow.getContentPane().add(this.relativePanel);
	        this.mainWindow.pack();
	        
	        //Display the window.
	        this.mainWindow.setVisible(true);
		}
		
		/*********Exceptions handling*****************/
		catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.toString());
			cnfe.printStackTrace();
		}
		catch (InstantiationException ie) {
			System.out.println(ie.toString());
			ie.printStackTrace();
		}
		catch (IllegalAccessException iae) {
			System.out.println(iae.toString());
			iae.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException ulfe) {
			System.out.println(ulfe.toString());
			ulfe.printStackTrace();
		}
		catch (ClassCastException cce) {
			System.out.println(cce.toString());
			cce.printStackTrace();
		}
		/***********End of Exceptions handling**********/
	}
	
	public void start() {
		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
	
}
