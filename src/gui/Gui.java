package gui;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.SpringLayout;

import ssd.*;
import xmlrpc.XmlRpcImpl;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class Gui {

	protected JFrame frmSimpleSubtitleDownloader;
	private JTextField textField;
	ArrayList<Subtitle> listSubtitles = new ArrayList<>();
	private XmlRpcImpl client = new XmlRpcImpl();
	private String[] languages = { "English", "Croatian" };

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * @wbp.parser.constructor
	 * @param token
	 */
	public Gui(String token) {
		client.setToken(token);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmSimpleSubtitleDownloader = new JFrame();
		frmSimpleSubtitleDownloader.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frmSimpleSubtitleDownloader.setTitle("Simple Subtitle Downloader");
		frmSimpleSubtitleDownloader.setResizable(false);
		frmSimpleSubtitleDownloader.setBackground(Color.DARK_GRAY);
		frmSimpleSubtitleDownloader.getContentPane().setBackground(Color.BLACK);
		SpringLayout springLayout = new SpringLayout();
		frmSimpleSubtitleDownloader.getContentPane().setLayout(springLayout);

		JLabel lblFilmTitle = new JLabel("Film Title: ");
		springLayout.putConstraint(SpringLayout.WEST, lblFilmTitle, 35, SpringLayout.WEST,
				frmSimpleSubtitleDownloader.getContentPane());
		lblFilmTitle.setForeground(Color.WHITE);
		lblFilmTitle.setBackground(new Color(255, 255, 255));
		lblFilmTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frmSimpleSubtitleDownloader.getContentPane().add(lblFilmTitle);

		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, lblFilmTitle, 1, SpringLayout.NORTH, textField);
		springLayout.putConstraint(SpringLayout.NORTH, textField, 107, SpringLayout.NORTH,
				frmSimpleSubtitleDownloader.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textField, 126, SpringLayout.WEST,
				frmSimpleSubtitleDownloader.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField, -148, SpringLayout.EAST,
				frmSimpleSubtitleDownloader.getContentPane());
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmSimpleSubtitleDownloader.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnSearch = new JButton("Search");
		springLayout.putConstraint(SpringLayout.WEST, btnSearch, 35, SpringLayout.WEST,
				frmSimpleSubtitleDownloader.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnSearch, -26, SpringLayout.SOUTH,
				frmSimpleSubtitleDownloader.getContentPane());
		btnSearch.setFont(new Font("Consolas", Font.PLAIN, 14));
		frmSimpleSubtitleDownloader.getContentPane().add(btnSearch);

		JButton btnLogOut = new JButton("LOG OUT");
		springLayout.putConstraint(SpringLayout.EAST, btnSearch, -420, SpringLayout.WEST, btnLogOut);
		springLayout.putConstraint(SpringLayout.WEST, btnLogOut, 555, SpringLayout.WEST,
				frmSimpleSubtitleDownloader.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnLogOut, -26, SpringLayout.SOUTH,
				frmSimpleSubtitleDownloader.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnLogOut, -39, SpringLayout.EAST,
				frmSimpleSubtitleDownloader.getContentPane());
		btnLogOut.setFont(new Font("Consolas", Font.PLAIN, 14));
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HashMap<String, String> retVal;
				retVal = client.LogOut(client.getToken());
				if (retVal.get("status").equals("200 OK")) {
					System.exit(0);
				}

				else {
					JOptionPane.showMessageDialog(null, "Log Out Failed!", "Log Out failed", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		frmSimpleSubtitleDownloader.getContentPane().add(btnLogOut);

		JLabel lblLanguage = new JLabel("Language: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblLanguage, 23, SpringLayout.SOUTH, lblFilmTitle);
		springLayout.putConstraint(SpringLayout.WEST, lblLanguage, 0, SpringLayout.WEST, lblFilmTitle);
		lblLanguage.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLanguage.setForeground(Color.WHITE);
		frmSimpleSubtitleDownloader.getContentPane().add(lblLanguage);

		JComboBox<String> languageBox = new JComboBox<>(languages);
		springLayout.putConstraint(SpringLayout.NORTH, languageBox, 21, SpringLayout.SOUTH, textField);
		springLayout.putConstraint(SpringLayout.WEST, languageBox, 0, SpringLayout.EAST, lblLanguage);
		springLayout.putConstraint(SpringLayout.EAST, languageBox, 91, SpringLayout.EAST, lblLanguage);
		frmSimpleSubtitleDownloader.getContentPane().add(languageBox);

		frmSimpleSubtitleDownloader.setBounds(100, 100, 698, 404);
		frmSimpleSubtitleDownloader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SearchWorker search = new SearchWorker(client, textField.getText(), languageBox.getSelectedItem().toString());
				search.execute();

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							DownloadWindow frame = new DownloadWindow(client, (ArrayList<Subtitle>) search.get());
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});

	}
}
