package graphic;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import localSystem.LocalFilesManager;
import javax.swing.*;
import javax.swing.event.MouseInputListener;


public class SendButton extends JButton implements MouseInputListener {

	public SendButton() {
		super();
	}
	

	public SendButton(String text) {
		super(text);
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton()==1) {
			System.out.println(InterfaceHM.getTextChatEditor());

		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
