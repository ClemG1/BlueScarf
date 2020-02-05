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

public class FieldMissingWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FieldMissingWindow frame = new FieldMissingWindow();
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
	public FieldMissingWindow() {
		setBounds(100, 100, 450, 175);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnLoginFailedPlease = new JTextPane();
		txtpnLoginFailedPlease.setBackground(new Color(238,238,238));
		txtpnLoginFailedPlease.setText("You must complete the fields name, login and password please.");
		txtpnLoginFailedPlease.setBounds(24, 46, 450, 26);
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

