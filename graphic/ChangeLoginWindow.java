package graphic;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import database.DatabaseDriver;

public class ChangeLoginWindow extends JFrame{
	
	private JPanel contentPanel; 

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangeLoginWindow frame = new ChangeLoginWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public ChangeLoginWindow() {
		
		//create and configure the frame
		setTitle("BlueScarf");
		setBounds(100, 100, 450, 300);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPanel);
		
		//configure the layout of the frame
		GridBagLayout contentGridBag = new GridBagLayout();
		contentPanel.setLayout(contentGridBag);
		GridBagConstraints contentConstraints = new GridBagConstraints();
		contentConstraints.fill = GridBagConstraints.BOTH;
		contentConstraints.insets = new Insets(10,10,10,10);
		
		//add the component to fill for create a user
		JLabel currentLogin = new JLabel("Current Login");
		contentGridBag.setConstraints(currentLogin, contentConstraints);
		contentPanel.add(currentLogin);
		final JTextField currentLoginArea = new JTextField();
		currentLoginArea.setPreferredSize(new Dimension (200,24));
		contentConstraints.gridwidth = GridBagConstraints.REMAINDER;
		contentGridBag.setConstraints(currentLoginArea, contentConstraints);
		contentPanel.add(currentLoginArea);
		
		final JLabel newLogin = new JLabel("New Login");
		contentConstraints.gridwidth = 1; //reset to default
		contentGridBag.setConstraints(newLogin, contentConstraints);
		contentPanel.add(newLogin);
		final JTextField newLoginArea = new JTextField();
		newLoginArea.setPreferredSize(new Dimension (200,24));
		contentConstraints.gridwidth = GridBagConstraints.REMAINDER;
		contentGridBag.setConstraints(newLoginArea, contentConstraints);
		contentPanel.add(newLoginArea);
		
		JLabel password = new JLabel("Password");
		contentConstraints.gridwidth = 1; //reset to default
		contentGridBag.setConstraints(password, contentConstraints);
		contentPanel.add(password);
		final JTextField passwordArea = new JTextField();
		newLoginArea.setPreferredSize(new Dimension (200,24));
		contentConstraints.gridwidth = GridBagConstraints.REMAINDER;
		contentGridBag.setConstraints(passwordArea, contentConstraints);
		contentPanel.add(passwordArea);
		
		JButton changeButton = new JButton("Change");
		contentGridBag.setConstraints(changeButton, contentConstraints);
		contentPanel.add(changeButton);
		
		changeButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String currentLogin =  currentLoginArea.getText();
				String newLogin = newLoginArea.getText();
				String password = passwordArea.getText();
				DatabaseDriver database = new DatabaseDriver();
				if( currentLogin.equals("") || newLogin.equals("") || password.equals("")) {
					FieldMissingWindow.start();
				}
				else {
					if(database.loginIsFree(newLogin)) {
						int id = database.getIdByLoginPassword(currentLogin, password);
						if(id == -1) {
							UnknownUserWindow.start();
						}
						else {
							database.updateLogin(newLogin, id);
							dispose();
						}
					}
					else {
						LoginNotFreeWindow.start();
					}
				}
			}
		});
	}
	
}

