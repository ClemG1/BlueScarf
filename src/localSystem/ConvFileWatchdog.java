package localSystem;

import graphic.MainWindow;
import network.Client;

public class ConvFileWatchdog extends FileWatchdog {
	
	public ConvFileWatchdog (String fileName) {
		super(fileName);
		setDelay(1000);
	}
	
	protected void doOnChange() {
		Client.sendMessage();
		MainWindow.displayMessage(MainWindow.currentUserInChatWith);
	}
	
}
