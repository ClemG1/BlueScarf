package graphic;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import database.DatabaseDriver;
import localSystem.LocalFilesManager;

public class InstallerWindow extends JFrame{
	
	private JPanel contentPanel; 

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InstallerWindow frame = new InstallerWindow();
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
	public InstallerWindow() {
		
		//create and configure the frame
		setTitle("BlueScarf");
		setBounds(100, 100, 450, 400);
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
		contentConstraints.gridwidth = GridBagConstraints.REMAINDER;
		
		//add the welcome text
		JTextPane welcomeMsg = new JTextPane();
		welcomeMsg.setBackground(new Color(238,238,238));
		welcomeMsg.setPreferredSize(new Dimension (400,48));
		welcomeMsg.setText("Welcome on BlueScarf. You need to indicate a mysql database for the application and a valid user on it.");
		contentGridBag.setConstraints(welcomeMsg, contentConstraints);
		contentPanel.add(welcomeMsg);
		
		//add the component to fill for create a user
		JLabel link = new JLabel("Database");
		contentConstraints.gridwidth = 1; //reset to default
		contentGridBag.setConstraints(link, contentConstraints);
		contentPanel.add(link);
		final JTextField linkArea = new JTextField("//host/datbaseName");
		linkArea.setPreferredSize(new Dimension (200,24));
		contentConstraints.gridwidth = GridBagConstraints.REMAINDER;
		contentGridBag.setConstraints(linkArea, contentConstraints);
		contentPanel.add(linkArea);
		
		final JLabel user = new JLabel("Username");
		contentConstraints.gridwidth = 1; //reset to default
		contentGridBag.setConstraints(user, contentConstraints);
		contentPanel.add(user);
		final JTextField userArea = new JTextField();
		userArea.setPreferredSize(new Dimension (200,24));
		contentConstraints.gridwidth = GridBagConstraints.REMAINDER;
		contentGridBag.setConstraints(userArea, contentConstraints);
		contentPanel.add(userArea);
		
		JLabel password = new JLabel("Password");
		contentConstraints.gridwidth = 1; //reset to default
		contentGridBag.setConstraints(password, contentConstraints);
		contentPanel.add(password);
		final JTextField passwordArea = new JTextField();
		passwordArea.setPreferredSize(new Dimension (200,24));
		contentConstraints.gridwidth = GridBagConstraints.REMAINDER;
		contentGridBag.setConstraints(passwordArea, contentConstraints);
		contentPanel.add(passwordArea);
		
		JButton installButton = new JButton("Install");
		contentGridBag.setConstraints(installButton, contentConstraints);
		contentPanel.add(installButton);
		
		installButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String link =  linkArea.getText();
				String user = userArea.getText();
				String password = passwordArea.getText();
				if( link.equals("") || user.equals("") || password.equals("")) {
					FieldMissingWindow.start();
				}
				else {
					LocalFilesManager databaseFile = new LocalFilesManager("databaseConf.txt", LocalFilesManager.getPath());
					if(databaseFile.readAllFile().contains("none")) {
						databaseFile.overwrite(link, "|");
						databaseFile.write(user, "|");
						databaseFile.write(password, "|");
						DatabaseDriver database = new DatabaseDriver();
						database.createDatabase();
						dispose();
					}
					else {
						AlreadyInstalledWindow.start();
						dispose();
					}
				}
			}
		});
	}
	
}
