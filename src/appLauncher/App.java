package appLauncher;
import localSystem.User;

public class App {

	public static void main(String[] args) {
		
		User admin = new User("Donald","Johnson",1); //Personal informations needs to be retrieve from the database
		admin.start();

	}

}
