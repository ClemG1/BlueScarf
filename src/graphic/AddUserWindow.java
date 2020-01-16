package graphic;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class AddUserWindow extends JFrame{
	
	private JPanel contentPanel; 

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddUserWindow frame = new AddUserWindow();
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
	public AddUserWindow() {
		
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
		JTextArea nameArea = new JTextArea(1, 15);
		contentConstraints.gridwidth = GridBagConstraints.REMAINDER;
		contentGridBag.setConstraints(nameArea, contentConstraints);
		contentPanel.add(nameArea);
		
		JLabel login = new JLabel("Login");
		contentConstraints.gridwidth = 1; //reset to default
		contentGridBag.setConstraints(name, contentConstraints);
		contentPanel.add(login);
		JTextArea loginArea = new JTextArea(1, 15);
		contentConstraints.gridwidth = GridBagConstraints.REMAINDER;
		contentGridBag.setConstraints(loginArea, contentConstraints);
		contentPanel.add(loginArea);
		
		JLabel password = new JLabel("Password");
		contentConstraints.gridwidth = 1; //reset to default
		contentGridBag.setConstraints(password, contentConstraints);
		contentPanel.add(password);
		JTextArea passwordArea = new JTextArea(1, 15);
		contentConstraints.gridwidth = GridBagConstraints.REMAINDER;
		contentGridBag.setConstraints(passwordArea, contentConstraints);
		contentPanel.add(passwordArea);
		
		JLabel email = new JLabel("Email");
		contentConstraints.gridwidth = 1; //reset to default
		contentGridBag.setConstraints(email, contentConstraints);
		contentPanel.add(email);
		JTextArea emailArea = new JTextArea(1, 15);
		contentConstraints.gridwidth = GridBagConstraints.REMAINDER;
		contentGridBag.setConstraints(emailArea, contentConstraints);
		contentPanel.add(emailArea);
	}
	
}
