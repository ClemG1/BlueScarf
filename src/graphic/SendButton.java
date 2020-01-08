package graphic;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import localSystem.LocalFilesManager;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

import appLauncher.App;


public class SendButton extends JButton implements MouseInputListener {

	public SendButton() {
		super();
		
        addMouseListener(this);
	}
	

	public SendButton(String text) {
		super(text);
        addMouseListener(this);
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		try {
		System.out.println(InterfaceHM.getTextChatEditor());
		String toAppend ="send: "+ InterfaceHM.getTextChatEditor();
		LocalFilesManager filesManager = new LocalFilesManager("conv/" + InterfaceHM.userWith + ".txt",LocalFilesManager.getPath());
		filesManager.write(toAppend, '-');
		App.window.UpdateChatEditor(InterfaceHM.userWith);
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("entered");
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("exited");
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("pressed");
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("released");
	}


	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("dragged");
	}


	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("moved");
	}
}
