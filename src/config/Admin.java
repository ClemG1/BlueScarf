package config;

import database.DatabaseDriver;
import java.util.Scanner;

public class Admin extends Thread{
	
	//Attributes
	boolean isAdmin = false;

	/**
	  * @brief : class constructor
	  * @param : none
	  * @returns : none
	 **/
	public Admin() {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please enter your login : ");
			String login = scanner.next();
			System.out.println("Please enter your password : ");
			String password = scanner.next();
			DatabaseDriver database = new DatabaseDriver();
			if(database.isAdmin(login, password)) {
				System.out.println("Authentication successed");
				isAdmin = true;
			}
			else {
				System.out.println("Authentication failed");
			}
			scanner.close();
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
	}
	
	/**
	  * @brief : run function of the thread
	  * @param : none
	  * @returns: none
	 **/
	public void run() {
		DatabaseDriver database = new DatabaseDriver();
		System.out.println(database.getIpToConnect());
	}
	
}
