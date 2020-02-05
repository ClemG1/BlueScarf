package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import database.DatabaseDriver;
import graphic.MainWindow;
import graphic.NewMessageWindow;
import localSystem.LocalFilesManager;
import localSystem.User;

public class ServerThread extends Thread{
	
	//Attributes
	private Socket socket;
	public static String newUserData;
	public static String newMessageFrom;
	
	/**
	  * class constructor
	  * @param : socket to communicate with
	 **/
	public ServerThread (Socket socket) {
		this.socket = socket;
	}
	
	/**
	  * run function of the thread
	 **/
	public void run() {
		try {
			
			//connection established state
			BufferedReader bufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msg = "";
			LocalFilesManager contact = new LocalFilesManager("contact.txt", LocalFilesManager.getPath());
			LocalFilesManager onlineUsersFile = new LocalFilesManager("onlineUsers.txt", LocalFilesManager.getPath());
			while ((msg = bufferIn.readLine()) != null) { //until the client end the connection
				String msgHeader = msg.subSequence(0, 3).toString();
				String msgData = msg.substring(3).toString();
				switch (msgHeader) {
					case "-c:" :
						
						//update the new user information
						newUserData = msgData;
						
						//Retrieve the information on all current online user 
						String allOnlineUsers = contact.readAllFile();
						String onlineUsers[] = allOnlineUsers.split("-");
						
						//send to the newcomer all the other user
						String newContactData[] = msgData.split(":"); //index 0 = name, index 1 = ip address
						Client responseClient = new Client(InetAddress.getByName(newContactData[1].substring(1)),"-r:");
						responseClient.start();
						responseClient.join();
						
						contact.write(msgData, "-"); //update our contact file
						
						//inform the others about the newcomer
						for(int i = 0; i < onlineUsers.length; i++) {
							String detailsUser[] = onlineUsers[i].split(":"); //index 0 = name, index 1 = ip address
							if(! detailsUser[1].equals(NetworkManager.localIpAddress.toString())) {
								Client client = new Client(InetAddress.getByName(detailsUser[1].substring(1)),"-u:");
								client.start();
							}
						}
						
						//update online user from contact
						onlineUsersFile.overwrite("","");
						String contactEntriesOnConnection[] = contact.readAllFile().split("-");
						for (int i = 0; i < contactEntriesOnConnection.length; i++) {
							String contactData[] = contactEntriesOnConnection[i].split(":");
							onlineUsersFile.write(contactData[0], "-");
						}
						break;
					case "-m:" : //format : -m:Name:/10.7.30				
						String userData[] = msgData.split(":"); //index 0 = name, index 1 = ip
						
						String userName = userData[0];
						userName = userName.trim();
						String userNameParts[] = userName.split(" ");
						
						DatabaseDriver database = new DatabaseDriver();
						String history = database.retrieveHistory(User.localUserName, userName);
						
						LocalFilesManager convFile = new LocalFilesManager(userNameParts[0] + userNameParts[1] + ".txt", LocalFilesManager.getPath()+"conv/");
						convFile.overwrite(history, "");
						
						Client.speakWith = userName;
						
						Client convClient = new Client(InetAddress.getByName(userData[1].substring(1)), "-s:");
						convClient.start();
						
						break;
					case "-u:" :
						contact.write(msgData, "");
						
						//update online user from contact
						onlineUsersFile.overwrite("","");
						String contactEntriesOnUpdate[] = contact.readAllFile().split("-");
						for (int i = 0; i < contactEntriesOnUpdate.length; i++) {
							String contactData[] = contactEntriesOnUpdate[i].trim().split(":");
							onlineUsersFile.write(contactData[0], "-");
						}
						break;
					case "-d:" :
						contact.deleteInFile(msgData);
						
						//update online user from contact
						onlineUsersFile.overwrite("","");
						String contactEntriesOnDeconnection[] = contact.readAllFile().split("-");
						for (int i = 0; i < contactEntriesOnDeconnection.length; i++) {
							String contactData[] = contactEntriesOnDeconnection[i].trim().split(":");
							onlineUsersFile.write(contactData[0], "-");
						}
						break;
					case "-s:" : //format : -s:Name:Message
						
						//write the message in the matching conv file
						String messageDataParts[] = msgData.split(":"); //0 = name of the person who send the message, 1 = the message
						messageDataParts[0] = messageDataParts[0].trim();
						newMessageFrom = messageDataParts[0];
						String convUserParts[] = messageDataParts[0].split(" ");
						String convUser = convUserParts[0].concat(convUserParts[1]);
						LocalFilesManager messageFile = new LocalFilesManager(convUser + ".txt", LocalFilesManager.getPath() + "conv/");
						messageFile.write("recv:" + messageDataParts[1], "-");
						if(newMessageFrom.contains(MainWindow.currentUserInChatWith)) {
							MainWindow.displayMessage(messageDataParts[0]);
						}
						else {
							NewMessageWindow.start();
						}
						break;
					default :
						System.out.println("Incorrect message header.");
				}
			}
			
			//connection closed state
			bufferIn.close();
			this.socket.close();
			
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
}
