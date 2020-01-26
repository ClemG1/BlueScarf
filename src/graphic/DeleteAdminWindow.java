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

public class DeleteAdminWindow extends JFrame{
	
	private JPanel contentPanel; 

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteAdminWindow frame = new DeleteAdminWindow();
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
	public DeleteAdminWindow() {
		
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
		JLabel name = new JLabel("Name");
		contentGridBag.setConstraints(name, contentConstraints);
		contentPanel.add(name);
		final JTextField nameArea = new JTextField();
		nameArea.setPreferredSize(new Dimension (200,24));
		contentConstraints.gridwidth = GridBagConstraints.REMAINDER;
		contentGridBag.setConstraints(nameArea, contentConstraints);
		contentPanel.add(nameArea);
		
		final JLabel login = new JLabel("Login");
		contentConstraints.gridwidth = 1; //reset to default
		contentGridBag.setConstraints(login, contentConstraints);
		contentPanel.add(login);
		final JTextField loginArea = new JTextField();
		loginArea.setPreferredSize(new Dimension (200,24));
		contentConstraints.gridwidth = GridBagConstraints.REMAINDER;
		contentGridBag.setConstraints(loginArea, contentConstraints);
		contentPanel.add(loginArea);
		
		JLabel password = new JLabel("Password");
		contentConstraints.gridwidth = 1; //reset to default
		contentGridBag.setConstraints(password, contentConstraints);
		contentPanel.add(password);
		final JTextField passwordArea = new JTextField();
		loginArea.setPreferredSize(new Dimension (200,24));
		contentConstraints.gridwidth = GridBagConstraints.REMAINDER;
		contentGridBag.setConstraints(passwordArea, contentConstraints);
		contentPanel.add(passwordArea);
		
		JButton createButton = new JButton("Delete");
		contentGridBag.setConstraints(createButton, contentConstraints);
		contentPanel.add(createButton);
		
		createButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String name =  nameArea.getText();
				String login = loginArea.getText();
				String password = passwordArea.getText();
				if( name.equals("") || login.equals("") || password.equals("")) {
					FieldMissingWindow.start();
				}
				else {
					DatabaseDriver database = new DatabaseDriver();
					database.deleteAdmin(name, login, password);
					dispose();
				}
			}
		});
	}
	
}
