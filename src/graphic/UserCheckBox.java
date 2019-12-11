package graphic;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JCheckBox;

public class UserCheckBox extends JCheckBox{
	
	private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
    private Color currentBackgroundColor;

    private boolean pressed;


    public UserCheckBox(String text, boolean selected) {
        super(text,selected);
        super.setContentAreaFilled(false);
        this.pressed=false;
        this.currentBackgroundColor = this.getBackground();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
       		System.out.println("Pressed " + pressed);
        	if (pressed==false) {
        		setCurrentBackgroundColor(getPressedBackgroundColor());
        		g.setColor(getPressedBackgroundColor());
        		pressed=true;
 
        	} else {
        		setCurrentBackgroundColor(getBackground());
        		g.setColor(getBackground());
        		pressed=false;
        	}
            
        } else if (getModel().isRollover()) {
        	if (pressed==false) {
                g.setColor(hoverBackgroundColor);
        	} else {
        		g.setColor(getCurrentBackgroundColor());
        	}
        } else {
        	if (pressed==false) 
        	{
        		g.setColor(getBackground());
        	} else {
        		g.setColor(getCurrentBackgroundColor());
        	}
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {
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
        return currentBackgroundColor;
    }

    public void setCurrentBackgroundColor(Color currentBackgroundColor) {
        this.currentBackgroundColor = currentBackgroundColor;
    }
}
