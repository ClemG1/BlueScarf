package appLauncher;
import graphic.InterfaceHM;
import localSystem.User;

public class App {

	public static void main(String[] args) {
		
		User newUser = new User("Donald","Johnson",1); //Personal informations needs to be retrieve from the database
		newUser.start();
	}

}
