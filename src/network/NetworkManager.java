package network;

import java.io.IOException;
import java.net.*;

public class NetworkManager extends Thread{
	
	//Attributes
	private String mode;
	private DatagramSocket socket;
	
	/**
	  * @brief : class constructor
	  * @param : mode (client or server)
	  * @returns : none
	  * @note : UDP protocol used
	 **/
	public NetworkManager(String mode) {
		this.mode = mode;
		try {
			this.socket = new DatagramSocket();
		}
		catch (SocketException se) {
			System.out.println(se.toString());
			se.printStackTrace();
		}
	}
	
	/**
	  * @brief : run function of the thread
	  * @param : none
	  * @returns: none
	 **/
	public void run() {
		switch (this.mode) {
		case "-c":
			//do something right here, please... :(
			String messageOut = "hello world";
			try {
				DatagramPacket outPacket= new DatagramPacket(messageOut.getBytes(), messageOut.length(),InetAddress.getLocalHost(), 1234);
				this.socket.send(outPacket);
			}
			
			/*********Exceptions handling*****************/
			catch (UnknownHostException uhe) {
				System.out.println(uhe.toString());
				uhe.printStackTrace();
			}
			catch (IOException ioe) {
				System.out.println(ioe.toString());
				ioe.printStackTrace();
			}
			/***********End of Exceptions handling**********/
			
			break;
		case "-s":
			
			/****************Main loop of the server*****************/
			byte[] buffer = new byte[256];
			DatagramPacket inPacket = new DatagramPacket(buffer,buffer.length);
			try {
				while(true) {
					this.socket.receive(inPacket);
					InetAddress clientAddress = inPacket.getAddress();
					int clientPort = inPacket.getPort();
					String messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
					//do something right here
					System.out.println(messageIn); //test
				}
			}
			
			/****************End of the Main loop of the server*****************/
			
			/*********Exceptions handling*****************/
			catch (SocketTimeoutException  ste) {
				System.out.println(ste.toString());
				ste.printStackTrace();
			}
			catch (PortUnreachableException   pue) {
				System.out.println(pue.toString());
				pue.printStackTrace();
			}
			catch (IOException ioe) {
				System.out.println(ioe.toString());
				ioe.printStackTrace();
			}
			/***********End of Exceptions handling**********/
			
			break;
		default:
			System.out.println("Invalid Network mode.");	
		}
	}

}
