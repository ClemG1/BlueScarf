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
}
