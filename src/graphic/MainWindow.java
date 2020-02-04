package graphic;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.crypto.Data;

import database.DatabaseDriver;
import localSystem.*;
import network.Client;
import network.NetworkManager;

public class MainWindow extends JFrame {
	
	private JPanel contentPanel;
	private static JPanel onlineUsersPanel;
	public static int currentIndex; //use to update the online user list
	private static JPanel chatPanel;
	private JPanel filesPanel;
	public static String currentUserInChatWith = User.localUserName;

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public MainWindow() {
		try {
			//Frame configuration
			setTitle("BlueScarf");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 1000, 650);
			
			//action performed when the user close the main window
			addWindowListener(new WindowAdapter() {
				public void windowClosing (WindowEvent e) {
					try {
						
						//management of the contact on deconnection
						DatabaseDriver database = new DatabaseDriver();
						LocalFilesManager contact = new LocalFilesManager("contact.txt", LocalFilesManager.getPath());
						LocalFilesManager onlineUsersFile = new LocalFilesManager("onlineUsers.txt", LocalFilesManager.getPath());
						
						String allOnlineUsers = onlineUsersFile.readAllFile();
						String onlineUsers[] = allOnlineUsers.split("-");
						String allContacts = contact.readAllFile();
						String contacts[] = allContacts.split("-");
						
						//ip to connect management
						if(onlineUsers.length == 1) { //I'm the last one on the network
							database.setIpToConnectToNULL();
						}
						else { //I need to check if I'm not the one to be contacted
							String ipToConnect = database.getIpToConnect();
							if (ipToConnect.equals(NetworkManager.localIpAddress.toString().substring(1))) {
								int userIndex = 0;
								boolean found = false;
								while(userIndex < contacts.length && ! found) { //stop when we find an other user than us
									String detailsUserToConnect[] = contacts[userIndex].trim().split(":"); //index 0 = name, index 1 = ip address
									if(detailsUserToConnect[1].equals(NetworkManager.localIpAddress.toString())) {
										userIndex++;
									}
									else {
										found = true;
										database.updateIpToConnect(detailsUserToConnect[1].substring(1)); 
									}
								}
							}
						}
						
						onlineUsersFile.overwrite("", ""); //reset the file
						
						for(int i = 0; i < contacts.length; i++) {
							String detailsUser[] = contacts[i].trim().split(":"); //index 0 = name, index 1 = ip address
							if(detailsUser.length == 2 && ! detailsUser[1].equals(NetworkManager.localIpAddress.toString())) {
								Client client = new Client(InetAddress.getByName(detailsUser[1].substring(1)),"-d:");
								client.start();
								client.join();
							}
						}
						
						contact.overwrite("", "");
						
						//management of conv on deconnection
						LocalFilesManager convDirectory = new LocalFilesManager("conv/", LocalFilesManager.getPath());
						String[] convFiles = convDirectory.findFilesInDirectory();
						for(int i = 0; i < convFiles.length; i++ ) {
							LocalFilesManager convFile = new LocalFilesManager(convFiles[i], LocalFilesManager.getPath() + "conv/");
							System.out.println("File : " + convFiles[i].trim());
							String fileNameParts[] = convFiles[i].trim().split("\\."); //index 0 = user name, index 1 = "txt"
							String userName = fileNameParts[0];
							
							//looking to separate the first name and surname
							String firstName = "";
							String surname = "";
							for(int j = 1; j < userName.length(); j++) {
								char currentLetter = userName.charAt(j);
								if(Character.isUpperCase(currentLetter)) {
									firstName = userName.substring(0,j);
									surname = userName.substring(j);
									break;
								}
							}
							String databaseName = firstName + " " + surname;
							
							String newHistory = convFile.readAllFile();
							database.updateHistory(User.localUserName, databaseName, newHistory);
							convFile.deleteFile();
						}
					}
					catch (Exception ex) {
						System.out.println(ex.toString());
						ex.printStackTrace();
					}
				}
			});
			
			//create the menu bar and add the components
			JMenuBar menuBar = new JMenuBar();
			JMenu adminMenu = new JMenu("Admin");
			JMenu userMenu = new JMenu("User");
			JMenuItem addUserItem = new JMenuItem(new AbstractAction("Add User") {
				public void actionPerformed(ActionEvent e) {
					if(User.isAdmin) {
						AddUserWindow.start();
					}
					else {
						NotAdminErrorWindow.start();
					}
				}
			});
			JMenuItem addAdminItem = new JMenuItem(new AbstractAction("Add Admin") {
				public void actionPerformed(ActionEvent e) {
					if(User.isAdmin) {
						AddAdminWindow.start();
					}
					else {
						NotAdminErrorWindow.start();
					}
				}
			});
			JMenuItem deleteUserItem = new JMenuItem(new AbstractAction("Delete User") {
				public void actionPerformed(ActionEvent e) {
					if(User.isAdmin) {
						DeleteUserWindow.start();
					}
					else {
						NotAdminErrorWindow.start();
					}
				}
			});
			JMenuItem deleteAdminItem = new JMenuItem(new AbstractAction("Delete Admin") {
				public void actionPerformed(ActionEvent e) {
					if(User.isAdmin) {
						DeleteAdminWindow.start();
					}
					else {
						NotAdminErrorWindow.start();
					}
				}
			});
			JMenuItem changeLoginAdmin = new JMenuItem(new AbstractAction("Change Login") {
				public void actionPerformed(ActionEvent e) {
					if(User.isAdmin) {
						ChangeLoginAdminWindow.start();
					}
					else {
						NotAdminErrorWindow.start();
					}
				}
			});
			adminMenu.add(addUserItem);
			adminMenu.add(addAdminItem);
			adminMenu.add(deleteUserItem);
			adminMenu.add(deleteAdminItem);
			adminMenu.add(changeLoginAdmin);
			JMenuItem changeLogin = new JMenuItem(new AbstractAction("Change Login") {
				public void actionPerformed(ActionEvent e) {
					if(!User.isAdmin) {
						ChangeLoginWindow.start();
					}
					else {
						NotUserErrorWindow.start();
					}
				}
			});
			userMenu.add(changeLogin);
			menuBar.add(adminMenu);
			menuBar.add(userMenu);
			setJMenuBar(menuBar);
			
			//create a content panel for the frame
			contentPanel = new JPanel();
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			GridBagLayout contentPanelGridBag = new GridBagLayout();
			contentPanel.setLayout(contentPanelGridBag);
			GridBagConstraints contentPanelConstraints = new GridBagConstraints();
			contentPanelConstraints.fill = GridBagConstraints.BOTH;
			setContentPane(contentPanel);
			
			//create a panel for the display of online users
			onlineUsersPanel = new JPanel();
			onlineUsersPanel.setBorder(new EmptyBorder(10,10,10,10));
			addOnlineUsers(0); //start at the first user by default	
			contentPanelGridBag.setConstraints(onlineUsersPanel, contentPanelConstraints);
			contentPanel.add(onlineUsersPanel);
			OnlineUserWatchdog onlineUserWatchdog = new OnlineUserWatchdog(LocalFilesManager.getPath() + "onlineUsers.txt");
			onlineUserWatchdog.start();
			
			//create a panel for chat and create a empty area by default
			chatPanel = new JPanel();
			chatPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
			addEmptyChatPanel();
			contentPanelGridBag.setConstraints(chatPanel, contentPanelConstraints);
			contentPanel.add(chatPanel);
			
			//create a panel for the files
			filesPanel = new JPanel();
			filesPanel.setBorder(new EmptyBorder(10,10,10,10));
			contentPanelGridBag.setConstraints(filesPanel, contentPanelConstraints);
			contentPanel.add(filesPanel);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Add the online user to the online user panel
	 */
	public static void addOnlineUsers(int startIndex) {
		try {
			
			//discover all the user online
			LocalFilesManager filesManager = new LocalFilesManager("onlineUsers.txt",LocalFilesManager.getPath());
			String users = filesManager.readAllFile();
			String usersTab[] = users.split("-");
			int numberOfUser = usersTab.length;
			
			if(startIndex < numberOfUser) { //update only if the start index match with at least one user
				//clear old list that might be display
				onlineUsersPanel.removeAll();

				//define the layout for onlineUsersPanel
				GridBagLayout onlineUsersGridBag = new GridBagLayout();
				onlineUsersPanel.setLayout(onlineUsersGridBag);
				GridBagConstraints onlineUsersConstraints = new GridBagConstraints();
				onlineUsersConstraints.fill = GridBagConstraints.BOTH;
				onlineUsersConstraints.gridwidth = GridBagConstraints.REMAINDER;
				
				//add 20 users or less to the panel
				int index = startIndex;
				currentIndex = startIndex;
				/*while ((index  <  numberOfUser) && (index < (startIndex+20))) { //the display is limit a 20 users
					final JButton userButton = new JButton(usersTab[index]);
					onlineUsersGridBag.setConstraints(userButton, onlineUsersConstraints);
					onlineUsersPanel.add(userButton);
					
					
					userButton.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							try {
								String test = userButton.getText().toString();
								System.out.println(test);
								LocalFilesManager convFile = new LocalFilesManager(test + ".txt", LocalFilesManager.getPath()+"conv/");
							}
							catch (Exception ex) {
								System.out.println(ex.toString());
								ex.printStackTrace();
							}
						}
					});
					
					index++;
				}*/
				final JList<String> userList = new JList<String>(usersTab);
				userList.setBorder(new EmptyBorder(10, 10, 10, 10));
				userList.setBackground(new Color(238,238,238));
				onlineUsersGridBag.setConstraints(userList, onlineUsersConstraints);
				
				userList.addListSelectionListener(new ListSelectionListener() {
					
					@Override
					public void valueChanged(ListSelectionEvent lse) {
						try {
							if(userList.getValueIsAdjusting()) { //use to prevent the double execution
								
								String userName = userList.getSelectedValue();
								userName = userName.trim();
								currentUserInChatWith = userName;
								String userNameParts[] = userName.split(" ");
								
								DatabaseDriver database = new DatabaseDriver();


								
								LocalFilesManager contactFile = new LocalFilesManager("contact.txt", LocalFilesManager.getPath());
								String contacts = contactFile.readAllFile();
								String contactLog[] = contacts.split("-");
								for(int i = 0; i < contactLog.length; i++) {
									String contactData[] = contactLog[i].split(":"); //index 0 = name, index 1 = ip
									if(contactData[0].contains(userName)) {
										Client.speakWith = userName;
										Client convClient = new Client(InetAddress.getByName(contactData[1].substring(1)), "-m:");
										convClient.start();
										System.out.println("Client started");
									}
								}
								
								displayMessage(userName);
							}
						}
						catch (Exception e) {
							System.out.println(e.toString());
							e.printStackTrace();
						}
					}
				});
				
				onlineUsersPanel.add(userList);
				
				/*
				//add scroll button at the bottom
				onlineUsersConstraints.gridwidth = 1; //reset to default
				onlineUsersConstraints.weightx = 1.0; //same size for both button
				JButton scrollDownButton = new JButton("v");
				onlineUsersGridBag.setConstraints(scrollDownButton, onlineUsersConstraints);
				onlineUsersPanel.add(scrollDownButton);
				onlineUsersConstraints.gridwidth = GridBagConstraints.REMAINDER;
				JButton scrollUpButton = new JButton("^");
				onlineUsersGridBag.setConstraints(scrollUpButton, onlineUsersConstraints);
				onlineUsersPanel.add(scrollUpButton);
				onlineUsersPanel.revalidate();
				onlineUsersPanel.repaint();
			
				//event listener for the scroll down button
				scrollDownButton.addMouseListener(new MouseAdapter() {User.localUserName
					public void mouseClicked(MouseEvent e) {
						MainWindow.addOnlineUsers(currentIndex + 20);
					}
				
				});
				
				//event listener for the scroll up bécraséutton
				scrollUpButton.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if(currentIndex - 20 >= 0) {
							MainWindow.addOnlineUsers(currentIndex - 20);
						}
					}
				});*/
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Display a chat with an other user
	 * @param UserConvFile
	 */
	public static void displayMessage(String userName) {
		try {
			
			//clear the panel
			chatPanel.removeAll();
			
			//gets all the message with the user given in parameter
			final String chatWith = userName;
			userName = userName.trim();
			String userNameParts[] = userName.split(" ");
			String convFileName = userNameParts[0].concat(userNameParts[1]);
			final LocalFilesManager convFile = new LocalFilesManager(convFileName + ".txt",LocalFilesManager.getPath() + "conv/");
			
			
			//check if the history has been retrieve
			DatabaseDriver database =  new DatabaseDriver();
			
			String history = database.retrieveHistory(User.localUserName, userName);
			if(!database.historyIsRetrieve(User.localUserName, userName)) {
				String newMessages = convFile.readAllFile();
				convFile.overwrite(history, "");
				convFile.write(newMessages, "");
				database.setHistoryRetrieveField(User.localUserName, userName, 1);
				database.setHistoryUpToDateField(User.localUserName, userName, 0);
			}

			String messages = convFile.readAllFile();
			messages = messages.trim();
			String messagesTab[] = messages.split("-");
			int numberOfMessages = messagesTab.length;
			
			//define the layout for the chat panel
			GridBagLayout chatGridBag = new GridBagLayout();
			chatPanel.setLayout(chatGridBag);
			GridBagConstraints chatConstraints = new GridBagConstraints();
			chatConstraints.fill = GridBagConstraints.BOTH;
			
			//display the messages
			int index = 0;
			while (index  < numberOfMessages) {
				
				//split the header and the data
				String messageDriver[] = new String[2];
				messageDriver[0] = messagesTab[index].trim().substring(0, 5); //header
				messageDriver[1] = messagesTab[index].trim().substring(5); //data
				
				if(messageDriver[0].equals("send:") ) { //decide if it's display left or right based on the header
					JTextArea message = new JTextArea("You : " + messageDriver[1]);
					message.setLineWrap(true);
					message.setBackground(new Color(36,223,225));
					chatConstraints.gridwidth = GridBagConstraints.REMAINDER;
					chatConstraints.anchor = GridBagConstraints.LINE_END;
					chatGridBag.setConstraints(message, chatConstraints);
					chatPanel.add(message);
				}
				else {
					JTextArea message = new JTextArea(userName + " : " + messageDriver[1]) ;
					message.setLineWrap(true);
					message.setBackground(new Color(210,165,52));
					chatConstraints.gridwidth = GridBagConstraints.REMAINDER;
					chatConstraints.anchor = GridBagConstraints.LINE_START;
					chatGridBag.setConstraints(message, chatConstraints);
					chatPanel.add(message);
				}
				index++;
			}
			
			//create JTextArea
			final JTextArea draftArea = new JTextArea(2,50);
			chatConstraints.gridwidth = GridBagConstraints.RELATIVE;
			chatConstraints.anchor = GridBagConstraints.CENTER; //reset to default
			chatGridBag.setConstraints(draftArea, chatConstraints);
			chatPanel.add(draftArea);
			
			//create SendButton
			JButton sendButton = new JButton("Send");
			chatConstraints.gridwidth = GridBagConstraints.REMAINDER;
			chatGridBag.setConstraints(sendButton, chatConstraints);
			chatPanel.add(sendButton);
			
			//event listener for the send button						System.out.println("partie 5 ça passe");
			sendButton.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					String toSend = draftArea.getText();
					Client.sendMessage(toSend);
					convFile.write("send:" + toSend, "-");
					displayMessage(chatWith);
				}
			});
			
			chatPanel.revalidate();
			chatPanel.repaint();
			
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

	private void addEmptyChatPanel() {
		
		JPanel emptyChatPanel = new JPanel();
		emptyChatPanel.setBorder(new EmptyBorder(10,10,10,10));
		emptyChatPanel.setBackground(Color.WHITE);
		emptyChatPanel.setPreferredSize(new Dimension(600,525));
		chatPanel.add(emptyChatPanel);
		
	}
	
}
