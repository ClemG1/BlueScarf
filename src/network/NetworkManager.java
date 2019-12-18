package network;

import localSystem.LocalFilesManager;
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
	private Boolean clientWorking = true;
	
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
	public synchronized void run() {
		try {
			switch (this.mode) {
			case "-c":
				
				LocalFilesManager contact = new LocalFilesManager("contact.txt", "/home/cgehin/Documents/BlueScarf/configFiles/", "", '-', "r");
				contact.start();
				contact.join();
				String ipContact = contact.getDataFile();
				
				clientWorking = false;
				notifyAll();
				System.out.println("I justed notify");
				
				if(ipContact.contains("none")) {
					System.out.println("Client : I'm waiting");
					while(!clientWorking) {
						wait();
						clientWorking = true;
					}
					System.out.println("Client : I'm done waiting");
					LocalFilesManager newContact = new LocalFilesManager("contact.txt", "/home/cgehin/Documents/BlueScarf/configFiles/", "", '-', "r");
					newContact.start();
					newContact.join();
					String ipNewContact = newContact.getDataFile();
					this.socket = new Socket(ipNewContact,this.portServer);
					send();
					this.socket.close();
				} 
				else { //ipContact is a valid ip
					
					this.socket = new Socket(ipContact,this.portServer);
					send();
					this.socket.close();
				}
				
				break;
			case "-s":
				
				this.serverSocket = new ServerSocket(this.portServer);
				System.out.println("Server up");
				
				System.out.println("Server : I'm waiting");
				while (clientWorking) {
					System.out.println("I'm in the loop");
					wait();
					clientWorking = false;
				}
				System.out.println("Server : I'm done waiting");
				
				//treatment when new connection
				LocalFilesManager contactDelete = new LocalFilesManager("contact.txt", "/home/cgehin/Documents/BlueScarf/configFiles/", "", '-', "d");
				contactDelete.start();
				String ipToWrite = this.ipAddress.toString().substring(1);
				LocalFilesManager contactWrite = new LocalFilesManager("contact.txt", "/home/cgehin/Documents/BlueScarf/configFiles/", ipToWrite, '\0', "w");
				contactWrite.start();
				
				//wait for connection state
				while(true) {
					
					Socket socket = this.serverSocket.accept();
					
					clientWorking = true;
					notifyAll();
					
					System.out.println("Connection established");
					ServerThread serverThread = new ServerThread(socket);
					serverThread.start();
				}
				
				//break;
				
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
