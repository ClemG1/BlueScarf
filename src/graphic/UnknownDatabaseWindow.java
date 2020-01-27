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

public class UnknownDatabaseWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UnknownDatabaseWindow frame = new UnknownDatabaseWindow();
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
	public UnknownDatabaseWindow() {
		setBounds(100, 100, 404, 170);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane unknownDatabaseText = new JTextPane();
		unknownDatabaseText.setBackground(new Color(238,238,238));
		unknownDatabaseText.setText("The database is unreachable please try again.");
		unknownDatabaseText.setBounds(24, 46, 366, 26);
		contentPane.add(unknownDatabaseText);
		
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


