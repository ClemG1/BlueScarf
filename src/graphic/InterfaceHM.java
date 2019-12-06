package graphic;

import java.awt.*;
import javax.swing.*;

public class InterfaceHM {
	
	//Attributes
	JFrame mainWindow;
	JPanel usersSection;
	JPanel chatingSection;
	JPanel filesSection;

	/**
	  * @brief : class constructor
	  * @param : none
	  * @returns : none
	 **/
	public InterfaceHM () {
		this.mainWindow = new JFrame("BlueScarf");
		this.usersSection = new JPanel(new GridLayout(2, 0));
		this.chatingSection = new JPanel(new GridLayout(2, 0));
		this.filesSection = new JPanel(new GridLayout(2, 0));
	}
	
	private void addWidgets() {
		
		//create labels
		JLabel usersLabel = new JLabel("Online Users : ", SwingConstants.LEFT);
		JLabel chatLabel = new JLabel("Chat Box : ", SwingConstants.LEFT);
		JLabel filesLabel = new JLabel("Your Files : ", SwingConstants.LEFT);
		
		//set the vertical alignment of the labels
		usersLabel.setVerticalAlignment(SwingConstants.TOP);
		chatLabel.setVerticalAlignment(SwingConstants.TOP);
		filesLabel.setVerticalAlignment(SwingConstants.TOP);
		
		//set the panels' size
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
	        
	        //this.mainWindow.setSize(new Dimension(1000, 800));  
	        
	        //creates the components of the window
	        addWidgets();
	        
	        //add our section to the window
	        this.mainWindow.getContentPane().add(this.usersSection, BorderLayout.WEST);
	        this.mainWindow.getContentPane().add(this.chatingSection, BorderLayout.CENTER);
	        this.mainWindow.getContentPane().add(this.filesSection, BorderLayout.EAST);
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
