package localSystem;

import network.Client;

public class ConvFileWatchdog extends FileWatchdog {
	
	public ConvFileWatchdog (String fileName) {
		super(fileName);
		setDelay(1000);
	}
	
	protected void doOnChange() {
		Client.newMessage = true;
	}
	
}
