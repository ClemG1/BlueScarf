package appLauncher;
import graphic.AuthentificationWindow;
import graphic.InterfaceHM;
import localSystem.User;

public class App {

	public static InterfaceHM window;

	public static void main(String[] args) {		
		AuthentificationWindow authentificationWindow = new AuthentificationWindow();
		authentificationWindow.start();
	}
	

}
