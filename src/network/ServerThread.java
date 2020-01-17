package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import localSystem.LocalFilesManager;

public class ServerThread extends Thread{
	
	//Attributes
	private Socket socket;
	public static String newUserData;
	
	/**
	  * @brief : class constructor
	  * @param : socket to communicate with
	  * @returns : none
	 **/
	public ServerThread (Socket socket) {
		this.socket = socket;
	}
	
	/**
	  * @brief : run function of the thread
	  * @param : none
	  * @returns: none
	 **/
	public void run() {
		try {
			
			//connection established state
			BufferedReader bufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msg = "";
			LocalFilesManager contact = new LocalFilesManager("contact.txt", LocalFilesManager.getPath());
			LocalFilesManager onlineUsersFile = new LocalFilesManager("onlineUsers.txt", LocalFilesManager.getPath());
			while ((msg = bufferIn.readLine()) != null) { //until the client end the connection
				System.out.println(msg);
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
						
						contact.write(msgData, '-'); //update our contact file
						
						//inform the others about the newcomer
						for(int i = 0; i < onlineUsers.length; i++) {
							String detailsUser[] = onlineUsers[i].split(":"); //index 0 = name, index 1 = ip address
							if(! detailsUser[1].equals(NetworkManager.localIpAddress.toString())) {
								Client client = new Client(InetAddress.getByName(detailsUser[1].substring(1)),"-u:");
								client.start();
							}
						}
						
						//update online user from contact
						onlineUsersFile.overwrite("\0",'\0');
						String contactEntriesOnConnection[] = contact.readAllFile().split("-");
						for (int i = 0; i < contactEntriesOnConnection.length; i++) {
							String contactData[] = contactEntriesOnConnection[i].split(":");
							onlineUsersFile.write(contactData[0], '-');
						}
						break;
					case "-m:" :
						System.out.println(msgData);
						break;
					case "-u:" :
						System.out.println("update received : " + msgData);
						contact.write(msgData, '\0');
						
						//update online user from contact
						onlineUsersFile.overwrite("\0",'\0');
						String contactEntriesOnUpdate[] = contact.readAllFile().split("-");
						for (int i = 0; i < contactEntriesOnUpdate.length; i++) {
							String contactData[] = contactEntriesOnUpdate[i].split(":");
							onlineUsersFile.write(contactData[0], '-');
						}
						break;
					case "-d:" :
						System.out.println("deconection received : " + msgData);
						contact.deleteInFile(msgData);
						break;
					default :
						System.out.println("Incorrect message header.");
				}
			}
			
			//connection closed state
			bufferIn.close();
			this.socket.close();
			System.out.println("Connection closed");
			
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
}
