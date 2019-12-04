package localSystem;

public class User extends Thread{
	
	//Attributes
	private String firstName; //first name of the user
	private String lastName; //last name of the user
	
	/**
	  * @brief : class constructor
	  * @param : none
	  * @returns : none
	 **/
	public User (String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		start();
	}

	public void run () {
		LocalFilesManager profilFile = new LocalFilesManager(".profil",LocalFilesManager.getPath(),this.firstName + " " + this.lastName,'-',"w");
	}
	
}
