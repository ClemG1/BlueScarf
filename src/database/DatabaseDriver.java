package database;

import java.sql.*;

public class DatabaseDriver {
	
	//Attributes
	private String jdbcDriver;
	private String dbURL;
	private Connection connection;
	private Statement statement;
	
	/**
	  * @brief : class constructor
	  * @param : none
	  * @returns : none
	 **/
	public DatabaseDriver() {
		this.jdbcDriver = "com.mysql.jdbc.Driver";
		this.dbURL = "jdbc:mysql://localhost/bluescarf";
		this.connection = null;
		this.statement = null;
	}
	
	/**
	  * @brief : register the driver for the database
	  * @param : none
	  * @returns : none
	 **/
	private void registerDriver() {
		try {
			Class.forName(this.jdbcDriver);
		}
		
		/*********Exceptions handling*****************/
		catch (ExceptionInInitializerError eie) {
			System.out.println(eie.toString());
			eie.printStackTrace();
		}
		catch (LinkageError le) {
			System.out.println(le.toString());
			le.printStackTrace();
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.toString());
			cnfe.printStackTrace();
		}
		/***********End of Exceptions handling**********/
	}

	/**
	  * @brief : connect the database
	  * @param : none
	  * @returns : none
	 **/
	private void connect() {
		try {
			this.connection = DriverManager.getConnection(this.dbURL,"root","E\"vkG6if");
		}
		catch (SQLException sqle) {
			System.out.println(sqle.toString());
			sqle.printStackTrace();
		}
	}
	
	/**
	  * @brief : create a statement
	  * @param : none
	  * @returns : none
	 **/
	private void createStatement() {
		try {
			this.statement = this.connection.createStatement();
		}
		catch (SQLException sqle) {
			System.out.println(sqle.toString());
			sqle.printStackTrace();
		}
	}
	
	/**
	  * @brief : prepare to use the database
	  * @param : none
	  * @returns : none
	 **/
	private void init() {
		try {
			registerDriver();
			connect();
			createStatement();
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	  * @brief : create the database use by the application
	  * @param : none
	  * @returns : none
	 **/
	public void createDatabase() {
		try {
			init();
			
			//create user table
			String query = "CREATE TABLE user ( id INT AUTO_INCREMENT PRIMARY KEY NOT NULL, name VARCHAR(100) NOT NULL, login VARCHAR(25) NOT NULL, password VARCHAR(25) NOT NULL, email VARCHAR(250));";
			int result = this.statement.executeUpdate(query);
			if ( result == 0) {
				System.out.println("User table created.");
			}
			
			//create admin table
			query = "CREATE TABLE admin ( id INT AUTO_INCREMENT PRIMARY KEY NOT NULL, name VARCHAR(100) NOT NULL, login VARCHAR(25) NOT NULL, password VARCHAR(25) NOT NULL, email VARCHAR(250));";
			result = this.statement.executeUpdate(query);
			if ( result == 0) {
				System.out.println("Admin table created.");
			}
			//Add the root admin
			query = "INSERT INTO admin (name,login,password,email) VALUES ('root','root','Iamgroot','root@bluescarf.com');";
			result = this.statement.executeUpdate(query);
			if ( result == 1) {
				System.out.println("Root added.");
			}
			
			//create connectTo table
			query = "CREATE TABLE connectTo ( id INT PRIMARY KEY NOT NULL, ip VARCHAR(15));";
			result = this.statement.executeUpdate(query);
			if ( result == 0) {
				System.out.println("ConnectTo table created.");
			}
			//Init at NULL
			query = "INSERT INTO connectTo (id,ip) VALUES (0,NULL);";
			result = this.statement.executeUpdate(query);
			if ( result == 1) {
				System.out.println("ConnectTo initialized.");
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	  * @brief : check if the user is an admin by is login and password
	  * @param : a login and password
	  * @returns : true if the login and password match with an admin else false
	 **/
	public boolean isAdmin(String login, String password) {
		try {
			boolean isAdmin;
			init();
			String query = "SELECT id FROM admin WHERE login = '" + login + "' AND password = '" + password +"';";
			ResultSet result = this.statement.executeQuery(query);
			if (result.next() == false) {
				isAdmin = false;
			}
			else {
				isAdmin = true;
			}
			return isAdmin;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	  * @brief : check if the login and password match with a user
	  * @param : a login and password
	  * @returns : true if the login and password match with an user else false
	 **/
	public boolean isUser (String login, String password) {
		try {
			boolean isUser;
			init();
			String query = "SELECT id FROM user WHERE login = '" + login + "' AND password = '" + password +"';";
			ResultSet result = this.statement.executeQuery(query);
			if (result.next() == false) {
				isUser = false;
			}
			else {
				isUser = true;
			}
			return isUser;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	  * @brief : retrieve the id of a user using his name
	  * @param : a name
	  * @returns : the id
	 **/
	public int getIdByName (String name) {
		try {
			int id;
			init();
			String query = "SELECT id FROM user WHERE name = '" + name + "';";
			ResultSet result = this.statement.executeQuery(query);
			if (result.next() == false) {
				id = -1;
			}
			else {
				id = result.getInt(1);
			}
			return id;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return -1;
		}
	}

	/**
	  * @brief : give the ip address to connect to when you launch the app
	  * @param : none
	  * @returns : the ip address or NULL if no one is connected
	 **/
	public String getIpToConnect() {
		try {
			init();
			String ip = "";
			String query = "SELECT ip FROM connectTo;";
			ResultSet result = this.statement.executeQuery(query);
			if (result.next()) {
				ip = result.getString(1);
			}
			return ip;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "NULL";
		}
	}

}
