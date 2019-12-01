package localSystem;

public class User extends Thread{
	
	//Attributes
	private String firstName; //first name of the user
	private String lastName; //last name of the user
	
	/*
	 * @brief : class constructor
	 * @param : none
	 * @returns : none
	 */
	public User (String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		start();
	}

	public void run () {
		//System.out.println(this.firstName + " " + this.lastName + " is connected.");
		LocalFilesManager testFile1 = new LocalFilesManager("test.txt","/home/gehin/Documents/insa/projects/BlueScarf/","test1",'-',"w");
	}
	
}
