package graphic;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import localSystem.LocalFilesManager;

public class MainWindow extends JFrame{
	
	private JPanel contentPanel;
	private static JPanel onlineUsersPanel;
	private static int currentIndex; //use to update the online user list
	private static JPanel chatPanel;
	private JPanel filesPanel;

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
			
			//create a panel for chat
			chatPanel = new JPanel();
			chatPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
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
			int numberOfUser = usersTab.length - 1;
			
			if(startIndex < numberOfUser) { //update only if the start index match with at least one user
				//clear old list that might be display
				onlineUsersPanel.removeAll();
				onlineUsersPanel.revalidate();
				
				//define the layout for onlineUsersPanel
				GridBagLayout onlineUsersGridBag = new GridBagLayout();
				onlineUsersPanel.setLayout(onlineUsersGridBag);
				GridBagConstraints onlineUsersConstraints = new GridBagConstraints();
				onlineUsersConstraints.fill = GridBagConstraints.BOTH;
				onlineUsersConstraints.gridwidth = GridBagConstraints.REMAINDER;
				
				//add 20 users or less to the panel
				int index = startIndex;
				currentIndex = startIndex;
				while ((index  <  numberOfUser) && (index < (startIndex+20))) { //the display is limit a 20 users
					final JButton userButton = new JButton(usersTab[index]);
					onlineUsersGridBag.setConstraints(userButton, onlineUsersConstraints);
					onlineUsersPanel.add(userButton);
					
					
					userButton.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							String userParts[] = userButton.getText().split(" ");
							String userName = "";
							for( int i = 0; i < userParts.length; i++) {
								userName += userParts[i];
							}
							displayMessage(userName);
						}
					});
					
					index++;
				}
				
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
				
			
				//event listener for the scroll down button
				scrollDownButton.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						MainWindow.addOnlineUsers(currentIndex + 20);
					}
				});
				
				//event listener for the scroll up button
				scrollUpButton.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if(currentIndex - 20 >= 0) {
							MainWindow.addOnlineUsers(currentIndex - 20);
						}
					}
				});
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
			chatPanel.revalidate();
			chatPanel.repaint();
			
			//gets all the message with the user given in parameter
			final String chatWith = userName;
			final LocalFilesManager convFile = new LocalFilesManager("conv/" + userName + ".txt",LocalFilesManager.getPath());
			String messages = convFile.readAllFile();
			String messagesTab[] = messages.split("-");
			int numberOfMessages = messagesTab.length;
			
			//define the layout for the chat panel
			GridBagLayout chatGridBag = new GridBagLayout();
			chatPanel.setLayout(chatGridBag);
			GridBagConstraints chatConstraints = new GridBagConstraints();
			chatConstraints.fill = GridBagConstraints.BOTH;
			
			//display the messages
			int index = 0;
			while (index  < (numberOfMessages-1)) {
				
				//split the header and the data
				String messageDriver[] = new String[2];
				messageDriver[0] = messagesTab[index].substring(0, 5); //header
				messageDriver[1] = messagesTab[index].substring(5); //data
				
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
			
			//event listener for the send button
			sendButton.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					String toSend = draftArea.getText();
					convFile.write("send:" + toSend, '-');
					displayMessage(chatWith);
				}
			});
			
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
}


