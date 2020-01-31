package database;

import java.sql.*;

import graphic.UnknownDatabaseWindow;
import localSystem.LocalFilesManager;

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
		try {
			LocalFilesManager databaseFile = new LocalFilesManager("databaseConf.txt",LocalFilesManager.getPath());
			String databaseData[] = databaseFile.readAllFile().split("\\*"); //o = database, 1 = username, 2 = password
			
			if(databaseData[0].contains("none")) {
				UnknownDatabaseWindow.start();
			}
			else {
				this.jdbcDriver = "com.mysql.jdbc.Driver";
				this.dbURL = "jdbc:mysql:" + databaseData[0];
				this.connection = null;
				this.statement = null;
	
				//register driver
				Class.forName(this.jdbcDriver);
				
				//connect
				this.connection = DriverManager.getConnection(this.dbURL,databaseData[1],databaseData[2]);
				
				//create statement
				this.statement = this.connection.createStatement();
			}
		}
		catch (Exception e) {
			UnknownDatabaseWindow.start();
			LocalFilesManager databaseFile = new LocalFilesManager("databaseConf.txt",LocalFilesManager.getPath());
			databaseFile.overwrite("none", '-');
		}
	}
	
	/**
	  * @brief : create the database use by the application
	  * @param : none
	  * @returns : none
	 **/
	public void createDatabase() {
		try {			
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
			
			//create history table
			query = "CREATE TABLE history (name1 VARCHAR(25), name2 VARCHAR(25), messages TEXT, PRIMARY KEY (name1,name2));";
			result = this.statement.executeUpdate(query);
			if ( result == 0) {
				System.out.println("history table created.");
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
	  * @brief : retrieve the name of a user using his login and password
	  * @param : a login and password
	  * @returns : the name
	 **/
	public String getNameByLoginPassword(String login, String password) {
		try {
			String name;
			String query = "SELECT name FROM user WHERE login = '" + login + "' AND password = '" + password +"';";
			ResultSet result = this.statement.executeQuery(query);
			if (result.next() == false) {
				name = null;
			}
			else {
				name = result.getString(1);
			}
			return name;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	  * @brief : retrieve the name of a admin using his login and password
	  * @param : a login and password
	  * @returns : the name
	 **/
	public String getAdminNameByLoginPassword(String login, String password) {
		try {
			String name;
			String query = "SELECT name FROM admin WHERE login = '" + login + "' AND password = '" + password +"';";
			ResultSet result = this.statement.executeQuery(query);
			if (result.next() == false) {
				name = null;
			}
			else {
				name = result.getString(1);
			}
			return name;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return null;
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
	  * @brief : retrieve the id of a admin using his name
	  * @param : a name
	  * @returns : the id
	 **/
	public int getAdminIdByName (String name) {
		try {
			int id;
			String query = "SELECT id FROM admin WHERE name = '" + name + "';";
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
			String ip = null;
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
			return null;
		}
	}
	
	public void updateIpToConnect(String ip) {
		try {
			String query = "UPDATE connectTo SET ip = '" + ip + "' WHERE id = 0;";
			int result = this.statement.executeUpdate(query);
			if ( result == 1) {
				System.out.println("Ip updated.");
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public void setIpToConnectToNULL() {
		try {
			String query = "UPDATE connectTo SET ip = NULL WHERE id = 0;";
			int result = this.statement.executeUpdate(query);
			if ( result == 1) {
				System.out.println("Ip reset to null.");
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public void createUser(String name, String login, String password, String email) {
		try {
			if (email == null) {
				String query = "INSERT INTO user (name,login,password,email) VALUES ('" + name + "','" + login + "','" + password + "',NULL);";
				int result = this.statement.executeUpdate(query);
			}
			else {
				String query = "INSERT INTO user (name,login,password,email) VALUES ('" + name + "','" + login + "','" + password + "','" + email + "');";
				int result = this.statement.executeUpdate(query);
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public void createAdmin(String name, String login, String password, String email) {
		try {
			if (email == null) {
				String query = "INSERT INTO admin (name,login,password,email) VALUES ('" + name + "','" + login + "','" + password + "',NULL);";
				int result = this.statement.executeUpdate(query);
			}
			else {
				String query = "INSERT INTO admin (name,login,password,email) VALUES ('" + name + "','" + login + "','" + password + "','" + email + "');";
				int result = this.statement.executeUpdate(query);
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public void createHistory(String name1, String name2) {
		try {
			String query = "INSERT INTO history (name1,name2,messages) VALUES ('" + name1 + "','" + name2 +"','recv:Welcome on BlueScraf let's talk!');";
			int result  = this.statement.executeUpdate(query);
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public void deleteUser(String name, String login, String password) {
		try {
			String query = "DELETE FROM user WHERE name = '" + name + "' AND login = '" + login + "' AND password = '" + password + "';";
			int result = this.statement.executeUpdate(query);
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public void deleteAdmin(String name, String login, String password) {
		try {
			String query = "DELETE FROM admin WHERE name = '" + name + "' AND login = '" + login + "' AND password = '" + password + "';";
			int result = this.statement.executeUpdate(query);
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public int getIdByLoginPassword(String login, String password) {
		try {
			int id;
			String query = "SELECT id FROM user WHERE login = '" + login + "' AND password = '" + password + "';";
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
	
	public int getAdminIdByLoginPassword(String login, String password) {
		try {
			int id;
			String query = "SELECT id FROM admin WHERE login = '" + login + "' AND password = '" + password + "';";
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
	
	public boolean loginIsFree(String login) {
		try {
			boolean isFree = false;
			String query = "Select id FROM user WHERE login = '" + login + "';";
			ResultSet result = this.statement.executeQuery(query);
			if (result.next() == false) {
				isFree = true;
			}
			return isFree;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean adminLoginIsFree(String login) {
		try {
			boolean isFree = false;
			String query = "Select id FROM admin WHERE login = '" + login + "';";
			ResultSet result = this.statement.executeQuery(query);
			if (result.next() == false) {
				isFree = true;
			}
			return isFree;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	public void updateLogin(String login, int id) {
		try {
			String query = "UPDATE user SET login = '" + login + "' WHERE id = " + id + ";";
			int result = this.statement.executeUpdate(query);
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public void updateAdminLogin(String login, int id) {
		try {
			String query = "UPDATE admin SET login = '" + login + "' WHERE id = " + id + ";";
			int result = this.statement.executeUpdate(query);
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public String retrieveHistory(String name1, String name2) {
		try {
			String history = "recv:Welcome on BlueScarf, you can start a new chat with this user. Enjoy !";
			String query = "SELECT messages FROM history WHERE name1 = '" + name1 +"' AND name2 = '" + name2 + "';";
			ResultSet result = this.statement.executeQuery(query);
			if (result.next()) {
				history = result.getString(1);
			}
			else {
				query = "INSERT INTO history (name1,name2,messages) VALUES ('" + name1 + "','" + name2 + "','" + history + "');";
				int resultInsert = this.statement.executeUpdate(query);
			}
			return history;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return null;
		}
	}

}
