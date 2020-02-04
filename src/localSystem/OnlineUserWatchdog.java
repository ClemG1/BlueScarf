package localSystem;

import graphic.MainWindow;

public class OnlineUserWatchdog extends FileWatchdog {

	public OnlineUserWatchdog (String fileName) {
		super(fileName);
		setDelay(1000);
	}
	
	@Override
	protected void doOnChange() {
		MainWindow.addOnlineUsers();
	}
	
}
