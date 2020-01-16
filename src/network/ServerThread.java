package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import localSystem.LocalFilesManager;

public class ServerThread extends Thread{
	
	//Attributes
	private Socket socket;
	
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
			LocalFilesManager onlineUser = new LocalFilesManager("onlineUser.txt", LocalFilesManager.getPath());
			while ((msg = bufferIn.readLine()) != null) { //until the client end the connection
				System.out.println(msg);
				String msgHeader = msg.subSequence(0, 3).toString();
				String msgData = msg.substring(3).toString();
				switch (msgHeader) {
					case "-c:" :
						System.out.println("Contact received.");
						contact.write(msgData, '-');
						String allOnlineUsers = contact.readAllFile();
						String onlineUsers[] = allOnlineUsers.split("-");
						for(int i = 0; i < onlineUsers.length; i++) {
							if(! onlineUsers[i].equals(NetworkManager.ipAddress.toString())) {
								System.out.println("ip to connect : (" + onlineUsers[i] + ")");
								Client client = new Client(InetAddress.getByName(onlineUsers[i].substring(1)),"-u:");
								client.start();
							}
						}
						break;
					case "-m:" :
						System.out.println(msgData);
						break;
					case "-u:" :
						System.out.println("update received.");
						contact.deleteFile();
						contact.write(msgData, '\0');
						
						//update online user from contact
						String contactEntries[] = msgData.split("-");
						for (int i = 0; i < contactEntries.length; i++) {
							String contactData[] = contactEntries[i].split(":");
							onlineUser.write(contactData[0], '-');
						}
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
