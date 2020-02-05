package graphic;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class LoginNotFreeWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginNotFreeWindow frame = new LoginNotFreeWindow();
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
	public LoginNotFreeWindow() {
		setBounds(100, 100, 404, 170);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnLoginFailedPlease = new JTextPane();
		txtpnLoginFailedPlease.setBackground(new Color(238,238,238));
		txtpnLoginFailedPlease.setText("This login is already taken.");
		txtpnLoginFailedPlease.setBounds(24, 46, 366, 26);
		contentPane.add(txtpnLoginFailedPlease);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(140, 84, 114, 25);
		contentPane.add(btnOk);
		
		btnOk.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
	}
}
