package graphic;

import java.awt.*;
import java.awt.event.MouseEvent;
import appLauncher.App;

import javax.swing.JButton;
import javax.swing.event.MouseInputListener;

public class UserButton extends JButton implements MouseInputListener {
	private String user;
	private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
    private Color BackgroundColor;

    public UserButton(String text) {
        super(text);
        this.user=getUserName(text);
        //super.setContentAreaFilled(false);
        this.setOpaque(false);
        this.BackgroundColor = this.getBackground();
        addMouseListener(this);
    }


    public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }
    
    public Color getCurrentBackgroundColor() {
        return BackgroundColor;
    }

    public void setCurrentBackgroundColor(Color currentBackgroundColor) {
        this.BackgroundColor = currentBackgroundColor;
    }
    
	/**
	  * @brief : when given the text in the UserButton return the fileName associated with this user 
	  * @param : text in UserButton
	  * @return : fileName
	 **/
    public String getUserName(String user) {
    	String userna="";
    	String userNaes[]=user.split(" ");
    	int namesNumber = userNaes.length;
    	int i=0;
    	while (i<namesNumber) {
    		userna =userna+ userNaes[i];
    		i=i+1;
    	}
    	return userna;
    }
    
    @Override
	public void mouseClicked(MouseEvent e) {
    		this.setBackground(BackgroundColor);
    		App.window.UpdateChatEditor(this.user);
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		//this.setBackground(hoverBackgroundColor);
	}


	@Override
	public void mouseExited(MouseEvent e) {
    	//if (e.getClickCount()%2 == 0) {
    	//	this.setBackground(pressedBackgroundColor);
    	//}else {
    	//	this.setBackground(BackgroundColor);
    	//}
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
