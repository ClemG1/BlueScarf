package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.*;

public class NetworkManager extends Thread{
	
	//Attributes
	private String mode;
	private ServerSocket serverSocket;
	private Socket socket;
	private int portServer = 6666;
	private int portClient = 6969;
	private InetAddress ipAddress;
	
	/**
	  * @brief : class constructor
	  * @param : mode (client or server)
	  * @returns : none
	  * @note : TCP protocol used
	 **/
	public NetworkManager(String mode) {
		try {
			this.mode = mode;
			
			//find the ip address of the computer
			Enumeration<NetworkInterface> interfaceList = NetworkInterface.getNetworkInterfaces(); //list the interface of the computer
			Boolean found = false; //true when you find a valid ip address
			while((! found) && interfaceList.hasMoreElements()) {
				NetworkInterface networkInterface = (NetworkInterface) interfaceList.nextElement();
			    Enumeration<InetAddress> addressList = networkInterface.getInetAddresses(); //address list of the interface
			    while ((! found) && addressList.hasMoreElements()) {
			    	InetAddress address = (InetAddress) addressList.nextElement();
			        String addressPart[] = address.getHostAddress().split("\\.");
			        if ((addressPart.length == 4) && (!addressPart[0].equals("127")) && (!addressPart[0].equals("192"))) { //a suitable address has been found
			        	found = true;
			        	this.ipAddress = address;
			        }
			    }
			}
			System.out.println("my ip is : " + this.ipAddress);
			
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
	}
	
	/*
	 * @brief : send a message written by the user by the socket
	 * @param : none
	 * @return : none
	 * @note : print the message which has been send
	 */
	private void send() {
		try {
			BufferedWriter bufferOut = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please enter your message : ");
			String msg = scanner.next();
			while (msg.compareToIgnoreCase("exit") != 0) {
				bufferOut.write(msg);
				bufferOut.newLine();
				bufferOut.flush();
				System.out.println("Send : " + msg);
				System.out.println("Please enter your message : ");
				msg = scanner.next();
			}
			scanner.close();
			bufferOut.close();
		} 
		catch (IOException ioe) {
			System.out.println("Client : " + ioe);
		}
	}
	
	/**
	  * @brief : run function of the thread
	  * @param : none
	  * @returns: none
	 **/
	public void run() {
		try {
			switch (this.mode) {
			case "-c":
				
				this.socket = new Socket("10.1.5.17",this.portServer);
				send();
				this.socket.close();
				
				break;
			case "-s":
				
				this.serverSocket = new ServerSocket(this.portServer);
				System.out.println("Server up");
				
				//wait for connection state
				Socket socket = this.serverSocket.accept();
				System.out.println("Connection established");
				
				//connection established state
				BufferedReader bufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String msg = "";
				while ((msg = bufferIn.readLine()) != null) { //until the client end the connection
					System.out.println(msg);
				}
				
				//connection closed state
				bufferIn.close();
				socket.close();
				System.out.println("Connection closed");
				
				break;
				
			default:
				System.out.println("Invalid Network mode.");	
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

}
