package graphic;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JCheckBox;

public class UserButton extends JButton{
	
	private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
    private Color currentBackgroundColor;

    private boolean pressed;


    public UserButton(String text) {
        super(text);
        super.setContentAreaFilled(false);
        this.pressed=false;
        this.currentBackgroundColor = this.getBackground();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
       		//System.out.println("Pressed " + pressed);
            if (pressed) {
                setCurrentBackgroundColor(getBackground());
                g.setColor(getBackground());
                pressed=false;
            } else {
                setCurrentBackgroundColor(getPressedBackgroundColor());
                g.setColor(getPressedBackgroundColor());
                pressed=true;

            }

        } else if (getModel().isRollover()) {
            if (pressed) {
                g.setColor(getCurrentBackgroundColor());
            } else {
                g.setColor(hoverBackgroundColor);
            }
        } else {
            //System.out.println("Dernier else " + this.pressed);
            if (pressed) {
                g.setColor(getCurrentBackgroundColor());
            } else {
                g.setColor(getBackground());
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
