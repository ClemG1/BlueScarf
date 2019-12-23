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
	private void prepare() {
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
	
	public boolean checkAdmin(String login, String password) {
		try {
			boolean isAdmin;
			prepare();
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
}
