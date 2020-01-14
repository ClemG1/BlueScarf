package graphic;

import localSystem.LocalFilesManager;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

//import com.sun.glass.events.MouseEvent;

public class InterfaceHM extends JFrame{
	
	//Attributes
	public static JFrame mainWindow; //main window of the app
	public static JPanel relativePanel; //this is a panel that has the same size than the main window to make it responsive
	public static JPanel usersSection; //section where online users are displayed
	public static JPanel chatingSection; //section for chat with your friend ;)
	public static JPanel filesSection; //section where available files are displayed
	public static JMenuBar menuBar; //menu at the top of the interface
	static JTextArea chatEditor;
	public static String userWith; //user with whom you're in conversation
	/**
	  * @brief : class constructor
	  * @param : none
	  * @returns : none
	 **/
	public InterfaceHM () {
		
		//create the main window
		mainWindow = new JFrame("BlueScarf");
		
		//create a relative panel for responsive
		RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS,10);
		relativePanel = new JPanel(rl);
		relativePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		//create the menu bar
		menuBar = new JMenuBar();
		
		//set the gap between each component of the menu bar
		menuBar.setMargin(new Insets(10,20,5,20));
		
		//set the background color of the menu bar
		Color menuBarColor = new Color(19, 73, 107);
		menuBar.setBackground(menuBarColor);
		
		//create the different panel for each section
		//this.chatingSection = new JPanel();
		filesSection = new JPanel();
		
	}
	
	/**
	  * @brief : retrieve all the online user and add them to the user section panel
	  * @param : none
	  * @return : none
	  * @note : use onlineUsers.txt file
	 **/
	private void displayOnlineUsers() {
		try {
			LocalFilesManager filesManager = new LocalFilesManager("onlineUsers.txt",LocalFilesManager.getPath());
			String users = filesManager.readAllFile();
			String usersTab[] = users.split("-");
			int numberOfUser = usersTab.length - 1;
			usersSection = new JPanel(new GridLayout(numberOfUser,0,5,5));
			int k = 0;
			while (k  < numberOfUser) {
				/*UserButton user = new UserButton(usersTab[k]) ;
				user.setBackground(Color.blue);
				user.setHoverBackgroundColor(Color.cyan);
				user.setPressedBackgroundColor(Color.darkGray);
				this.usersSection.add(user);*/
				
				final JButton userButton = new JButton(usersTab[k]);
				usersSection.add(userButton);
				
				userButton.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						InterfaceHM.chatingSection.removeAll();
						InterfaceHM.chatingSection.validate();
						String userParts[] = userButton.getText().split(" ");
						String userName = "";
						for( int i = 0; i < userParts.length; i++) {
							userName += userParts[i];
						}
						displayMessage(userName);
						chatingSection.validate();	
						chatingSection.repaint();
					}
				});
				
				k++;
				
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	  * @brief : retrieve all the message with a user
	  * @param : none
	  * @return : none
	  * @note : use username.txt file, also add the text box to send message and the send button
	 **/
	private void displayMessage(String UserConvFile) {
		try {
			LocalFilesManager filesManager = new LocalFilesManager("conv/" + UserConvFile + ".txt",LocalFilesManager.getPath());
			String messages = filesManager.readAllFile();
			String messagesTab[] = messages.split("-");
			int numberOfMessages = messagesTab.length;
			//System.out.println("length message "+messagesTab.length+"numberOfMessages: "+numberOfMessages+" " +messagesTab[numberOfMessages]);
			JPanel grid =  new JPanel();
			grid.setLayout(new GridLayout(numberOfMessages+1,2,5,5));
			chatingSection.add(grid);
			int k = 0;
			while (k  < numberOfMessages) {
				//System.out.println("numero k : "+k+" "+ messagesTab[k]+"  "+messagesTab[k].substring(0, 5));
				String messageDriver[] = new String[2];
				messageDriver[0] = messagesTab[k].substring(0, 5);
				messageDriver[1] = messagesTab[k].substring(5);
				if(messageDriver[0].equals("send:") ) { //decide if it's display left or right
					//JSplitPane messageSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
					JTextArea message = new JTextArea(messageDriver[1]);
					message.setLineWrap(true);
					message.setOpaque(true);
					message.setBackground(new Color(36,223,225));
					/*messageSplit.add(message, JSplitPane.RIGHT);
					messageSplit.add(new JPanel(),JSplitPane.LEFT); //empty panel for display*/
					grid.add(new JPanel());
					grid.add(message);
				}
				else {
					//JSplitPane messageSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
					JTextArea message = new JTextArea(messageDriver[1]) ;
					message.setLineWrap(true);
					message.setBackground(new Color(210,165,52));;
					/*messageSplit.add(message, JSplitPane.LEFT);
					messageSplit.add(new JPanel(),JSplitPane.RIGHT);*/
					grid.add(message);
					grid.add(new JPanel());
				}
				k++;
			}
			
			//create JTextArea
			chatEditor = new JTextArea(1,50);
			
			//create SendButton
			JButton sendButton = new JButton("Send");
			
			
			//add to the sending message panel
			grid.add(chatEditor);
			grid.add(sendButton);
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	  * @brief : add the components of the different sections
	  * @param : none
	  * @returns : none
	 **/
	private void addWidgets() {
		
		/*************** Menu Section ***************/
		//create menu
		JMenu customizeMenu = new JMenu("Customize");
		JMenu helpMenu = new JMenu("Help");
		
		//set menu color
		Color menuColor = new Color(19, 73, 107);
		customizeMenu.setOpaque(true);
		helpMenu.setOpaque(true);
		customizeMenu.setBackground(menuColor);
		helpMenu.setBackground(menuColor);
		customizeMenu.setBorderPainted(true);
		helpMenu.setBorderPainted(true);
		
		//create menu items
		JMenuItem profilMenuItem = new JMenuItem("profil");
		JMenuItem helpMenuItem = new JMenuItem("About BlueScarf");
		
		//set menu items color
		Color menuItemsColor = new Color(19, 73, 107);
		profilMenuItem.setOpaque(true);
		helpMenuItem.setOpaque(true);
		profilMenuItem.setBackground(menuItemsColor);
		helpMenuItem.setBackground(menuItemsColor);
		profilMenuItem.setBorderPainted(true);
		helpMenuItem.setBorderPainted(true);
		
		//add menus to the menu bar
		menuBar.add(customizeMenu);
		menuBar.add(helpMenu);
		
		//add menu items to the menu
		customizeMenu.add(profilMenuItem);
		helpMenu.add(helpMenuItem);
		/*****************End Of Menu Section*******************/
		
		/****************Online user Management******************/
		//retrieve the online user and add them to the panel
		displayOnlineUsers();
		
		//set the panel's size when you open the app
		usersSection.setPreferredSize(new Dimension(200, 800));
		
		/*************End OF Online user Management**************/
		
		/*******************Chat box Management******************/
		chatingSection = new JPanel();
		chatingSection.setLayout(new GridLayout(0,1));
		
		//set borders
		chatingSection.setBorder(BorderFactory.createLineBorder(Color.black));
		
		/***************End of chat box management***************/
		
		/*******************To Modify********************************/
		//create labels
		JLabel filesLabel = new JLabel("Your Files : ", SwingConstants.LEFT);

		//set the vertical alignment of the labels
		filesLabel.setVerticalAlignment(SwingConstants.TOP);

		
		//add the labels and Editor Pane to the panels
		filesSection.add(filesLabel);
		
		//set the panels' size when you open the app
		chatingSection.setPreferredSize(new Dimension(400, 800));
		filesSection.setPreferredSize(new Dimension(200, 800));
		
		//set borders around panels
		filesSection.setBorder(BorderFactory.createLineBorder(Color.black));
		/**************************End To Modify*********************************/
		
		/************************Relative Panel Management**************/
		//use to set the size of the sections in the relative panel
		float sizeBorderSection = 1; //users and files section
		float sizeMiddleSection = 3; //chating section
		
		//add the sections at the relative panel
		relativePanel.add(usersSection,sizeBorderSection);
		relativePanel.add(chatingSection,sizeMiddleSection);
		relativePanel.add(filesSection,sizeBorderSection);
		/********************End of Relative Panel Management**************/
	}
	
	/**
	  * @brief : return text written in ChatEditor
	 **/
	public static String getTextChatEditor() {
		return chatEditor.getText();
	}
	
	/**
	  * @brief : create and display the window
	  * @param : none
	  * @returns : none
	 **/
	private void createAndShowGUI() {
		
		try {
			//Make sure we have nice window decorations.
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
	        
	        //set up the  main window.
	        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	        
	        //creates the components of the window
	        addWidgets();
	        
	        //add the menu bar to the main window
	        mainWindow.setJMenuBar(menuBar);
	        
	        //add the relative panel to the window
	        mainWindow.getContentPane().add(relativePanel);
	        mainWindow.pack();
	        
	        //Display the window.
	        mainWindow.setVisible(true);
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
	
	/**
	  * @brief : start the interface
	  * @param : none
	  * @returns : none
	 **/
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
