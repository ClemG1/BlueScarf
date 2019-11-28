package localSystem;

public class User extends Thread{
	
	//Attributes
	private String firstName; //first name of the user
	private String lastName; //last name of the user
	private LocalFiles filesManager; //use to manage the local files
	
	/*
	 * @brief : class constructor
	 * @param : none
	 * @returns : none
	 */
	public User (String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		LocalFiles testFiles = new LocalFiles("test.txt","/home/cgehin/Documents/BlueScarf/");
		this.filesManager = testFiles;
		start();
	}

	public void run () {
		//System.out.println(this.firstName + " " + this.lastName + " is connected.");
		this.filesManager.test(this.firstName + " " + this.lastName + " is connected.", '-');
	}
	
}
