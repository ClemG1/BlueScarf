package localSystem;

import graphic.MainWindow;

public class OnlineUserWatchdog extends FileWatchdog {

	public OnlineUserWatchdog (String fileName) {
		super(fileName);
	}
	
	protected void doOnChange() {
		System.out.println("File has been changed");
		MainWindow.addOnlineUsers(MainWindow.currentIndex);
	}
	
}
