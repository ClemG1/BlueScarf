package graphic;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import network.ServerThread;

public class NewMessageWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewMessageWindow frame = new NewMessageWindow();
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
	public NewMessageWindow() {
		setBounds(100, 100, 450, 175);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnNewMessage = new JTextPane();
		txtpnNewMessage.setBackground(new Color(238,238,238));
		txtpnNewMessage.setText("You have received a new message from " + ServerThread.newMessageFrom + ".");
		txtpnNewMessage.setBounds(24, 46, 450, 26);
		contentPane.add(txtpnNewMessage);
		
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
