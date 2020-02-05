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
	  * class constructor
	  * @param : none
	  * @return : none
	 **/
	public DatabaseDriver() {
		try {
			LocalFilesManager databaseFile = new LocalFilesManager("databaseConf.txt",LocalFilesManager.getPath());
			String databaseData[] = databaseFile.readAllFile().split("\\|"); //o = database, 1 = username, 2 = password
			
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
			databaseFile.overwrite("none", "|");
		}
	}
	
	/**
	 * check if the database has already been created on the network
	 * @return a boolean
	 */
	public boolean alreadyCreated() {
		try {
			boolean exist = true;
			String query = "SHOW TABLES LIKE 'user'";
			ResultSet result = this.statement.executeQuery(query);
			if(!result.next()) {
				exist = false;
			}
			return exist;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	  * create the database use by the application
	  * @param : none
	  * @return : none
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
			query = "INSERT INTO admin (name,login,password,email) VALUES ('Bruce Wayne','root','Iamgroot','root@bluescarf.com');";
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
			query = "CREATE TABLE history (name1 VARCHAR(25) NOT NULL, name2 VARCHAR(25) NOT NULL, messages TEXT, retrieve TINYINT(1), uptodate TINYINT(1), PRIMARY KEY (name1,name2));";
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
	  * check if the user is an admin by is login and password
	  * @param : a login and password
	  * @return : true if the login and password match with an admin else false
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
	  * check if the login and password match with a user
	  * @param : a login and password
	  * @return : true if the login and password match with an user else false
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
	  * retrieve the name of a user using his login and password
	  * @param : a login and password
	  * @return : the name
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
	  * retrieve the name of a admin using his login and password
	  * @param : a login and password
	  * @return : the name
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
	  * retrieve the id of a user using his name
	  * @param : a name
	  * @return : the id
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
	  * retrieve the id of a admin using his name
	  * @param : a name
	  * @return : the id
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
	  * give the ip address to connect to when you launch the app
	  * @param : none
	  * @return : the ip address or NULL if no one is connected
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
	
	/**
	 * update the ip to contact when a newcomer try to connect at the network
	 * @param ip address (String)
	 */
	public void updateIpToConnect(String ip) {
		try {
			String query = "UPDATE connectTo SET ip = '" + ip + "' WHERE id = 0;";
			int result = this.statement.executeUpdate(query);
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * reset the ip to contact to null when the last user quit the application
	 */
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
	
	/**
	 * create a new user with the matching informations 
	 * @param name
	 * @param login
	 * @param password
	 * @param email
	 */
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
	
	/**
	 * create a new admin with the matching informations 
	 * @param name
	 * @param login
	 * @param password
	 * @param email
	 */
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
	
	/**
	 * create a new history between two users. The first name match with the local file system
	 * @param name1
	 * @param name2
	 */
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
	
	/**
	 * delete an user account
	 * @param name
	 * @param login
	 * @param password
	 */
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
	
	/**
	 * delete an admin account
	 * @param name
	 * @param login
	 * @param password
	 */
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
	
	/**
	 * get the id of an user using his login 'n password
	 * @param login
	 * @param password
	 * @return
	 */
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
	
	/**
	 * get the id of an admin using his login 'n password
	 * @param login
	 * @param password
	 * @return
	 */
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
	
	/**
	 * check if the login is free to use
	 * @param login
	 * @return a boolean
	 */
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
	
	/**
	 * check if the admin login is free to use
	 * @param login
	 * @return boolean
	 */
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
	
	/**
	 * update the user login
	 * @param login
	 * @param id
	 */
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
	
	/**
	 * update the admin login
	 * @param login
	 * @param id
	 */
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
	
	/**
	 * retrieve the history between two users
	 * @param name1
	 * @param name2
	 * @return the history (String)
	 */
	public String retrieveHistory(String name1, String name2) {
		try {
			String history = "recv:Welcome on BlueScarf, you can start a new chat with this user. Enjoy !|";
			String query = "SELECT messages FROM history WHERE name1 = '" + name1 +"' AND name2 = '" + name2 + "';";
			ResultSet result = this.statement.executeQuery(query);
			if (result.next()) {
				history = result.getString(1);
			}
			else {
				query = "INSERT INTO history (name1,name2,messages,retrieve,uptodate) VALUES ('" + name1 + "','" + name2 + "','" + history + "',0,1);";
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
	
	/**
	 * check if the history has already been loaded
	 * @param name1
	 * @param name2
	 * @return a boolean
	 */
	public boolean historyIsRetrieve(String name1, String name2) {
		try {
			boolean isRetrieve = false;
			int intResult;
			String query = "SELECT retrieve FROM history WHERE name1 = '" + name1 +"' AND name2 = '" + name2 + "';";
			ResultSet result = this.statement.executeQuery(query);
			if (result.next()) {
				intResult = result.getInt(1);
				if(intResult == 1) {
					isRetrieve = true;
				}
			}
			return isRetrieve;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * check if the history is up to date
	 * @param name1
	 * @param name2
	 * @return a boolean
	 */
	public boolean historyIsUpToDate(String name1, String name2) {
		try {
			boolean upToDate = false;
			int intResult;
			String query = "SELECT uptodate FROM history WHERE name1 = '" + name1 +"' AND name2 = '" + name2 + "';";
			ResultSet result = this.statement.executeQuery(query);
			if (result.next()) {
				intResult = result.getInt(1);
				if(intResult == 1) {
					upToDate = true;
				}
			}
			return upToDate;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * set the history retrieve field
	 * @param name1
	 * @param name2
	 * @param isRetrieve 0 = false, 1 = one
	 */
	public void setHistoryRetrieveField (String name1, String name2, int isRetrieve) {
		try {
			String query = "UPDATE history SET retrieve = " + isRetrieve + " WHERE name1 = '" + name1 +"' AND name2 = '" + name2 + "';";
			int result = this.statement.executeUpdate(query);
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * set the history uptodate field
	 * @param name1
	 * @param name2
	 * @param isUpToDate
	 */
	public void setHistoryUpToDateField (String name1, String name2, int isUpToDate) {
		try {
			String query = "UPDATE history SET uptodate = " + isUpToDate + " WHERE name1 = '" + name1 +"' AND name2 = '" + name2 + "';";
			int result = this.statement.executeUpdate(query);
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * update the messages in the history
	 * @param name1
	 * @param name2
	 * @param the messages
	 */
	public void updateHistory(String name1, String name2, String text) {
		try {
			String query = "UPDATE history SET messages = '" + text + "' WHERE name1 = '" + name1 + "' AND name2 = '" + name2  + "';";
			int result = this.statement.executeUpdate(query);
			setHistoryUpToDateField(name1, name2, 1);
			setHistoryRetrieveField(name1, name2, 0);
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
}
