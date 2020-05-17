package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import xmlrpc.XmlRpcImpl;

import java.awt.Color;
import java.awt.Desktop;

import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class LogInBox extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6872296950664649135L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField;
	private XmlRpcImpl client = new XmlRpcImpl();

	private static final String REGISTER_URI = "https://www.opensubtitles.org/newuser";
	
	/**
	 * Create the frame.
	 */
	public LogInBox() {
		setBackground(Color.BLACK);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 275, 335);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblLogIn = new JLabel("LOG IN");
		lblLogIn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLogIn.setForeground(Color.WHITE);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblLogIn, 36, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblLogIn, 96, SpringLayout.WEST, contentPane);
		contentPane.add(lblLogIn);
		
		JLabel lblUsername = new JLabel("USERNAME: ");
		lblUsername.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblUsername.setForeground(Color.WHITE);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("PASSWORD: ");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblUsername, -17, SpringLayout.NORTH, lblPassword);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblPassword, 152, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblPassword, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblUsername, 0, SpringLayout.WEST, lblPassword);
		lblPassword.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblPassword.setForeground(Color.WHITE);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, passwordField, -2, SpringLayout.NORTH, lblPassword);
		sl_contentPane.putConstraint(SpringLayout.WEST, passwordField, 0, SpringLayout.WEST, lblLogIn);
		sl_contentPane.putConstraint(SpringLayout.EAST, passwordField, 136, SpringLayout.EAST, lblPassword);
		contentPane.add(passwordField);
		
		textField = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField, -2, SpringLayout.NORTH, lblUsername);
		sl_contentPane.putConstraint(SpringLayout.WEST, textField, 0, SpringLayout.WEST, lblLogIn);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField, -26, SpringLayout.EAST, contentPane);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel retLogIn = new JLabel("");
		retLogIn.setFont(new Font("Consolas", Font.PLAIN, 18));
		retLogIn.setForeground(Color.RED);
		sl_contentPane.putConstraint(SpringLayout.WEST, retLogIn, 0, SpringLayout.WEST, lblLogIn);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, retLogIn, -27, SpringLayout.SOUTH, contentPane);
		contentPane.add(retLogIn);
		
		JButton btnLogIn = new JButton("LOG IN");
		btnLogIn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				retLogIn.setText(null);
				HashMap<String, String> retVal = null;
				retVal = client.LogIn(textField.getText(), passwordField.getText(), "en", client.getUserAgent());
				if(retVal != null) {
					String token = retVal.get("token");
					if(retVal.get("status").equals("200 OK")) {
						retLogIn.setText("Log In Succes!");
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									Gui window = new Gui(token);
									window.frmSimpleSubtitleDownloader.setVisible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						setVisible(false);
					}
					else if (retVal.get("status").equals("401 Unauthorized")) {
						retLogIn.setText("Wrong password");
					}
					else {
						JOptionPane.showMessageDialog(null, "Log In failed!", "Log In failed", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					System.err.println("Log In returned null");
				}
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnLogIn, 28, SpringLayout.SOUTH, lblPassword);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnLogIn, 25, SpringLayout.WEST, contentPane);
		contentPane.add(btnLogIn);
		
		JButton btnRegister = new JButton("REGISTER");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			        try {
			            desktop.browse(new URI(REGISTER_URI));
			        } catch (Exception exc) {
			            exc.printStackTrace();
			        }
			    }
			}
		});
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnRegister, 0, SpringLayout.SOUTH, btnLogIn);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnRegister, 0, SpringLayout.EAST, passwordField);
		contentPane.add(btnRegister);
	}
	
}
