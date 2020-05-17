package gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import ssd.DownloadWorker;
import ssd.Subtitle;
import xmlrpc.XmlRpcImpl;

import java.awt.Color;
import javax.swing.JList;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class DownloadWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1046769506506088661L;
	private JPanel contentPane;
	private JList<Subtitle> list;
	private DefaultListModel<Subtitle> dflmodel = new DefaultListModel<>();
	@SuppressWarnings("unused")
	private XmlRpcImpl client;

	/**
	 * Create the frame.
	 */
	public DownloadWindow(XmlRpcImpl client, ArrayList<Subtitle> subList) {

		//sets look and feel
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);

		this.client = client;

		for (Subtitle sub : subList) {
			dflmodel.addElement(sub);
		}

		list = new JList<Subtitle>(dflmodel);
		list.setFont(new Font("Tahoma", Font.PLAIN, 18));

		setBackground(Color.BLACK);
		setBounds(100, 100, 478, 591);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		contentPane.add(list, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnDownload = new JButton("Download");

		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDownload.setEnabled(false);

				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Select where to save subtitles");

				if (chooser.showSaveDialog(contentPane) == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					String path = file.toString();

					if (!path.endsWith(".srt")) {
						path = path + ".srt";
					}

					DownloadWorker download = new DownloadWorker(client, path, list.getSelectedValue().getID(),
							btnDownload);
					download.execute();
				} else {
					btnDownload.setEnabled(true);
				}
			}
		});
		btnDownload.setFont(new Font("Consolas", Font.PLAIN, 14));
		panel.add(btnDownload);
	}

}
