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
		this.dbURL = "jdbc:mysql://localhost";
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
	
	
	public void test() {
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
}
