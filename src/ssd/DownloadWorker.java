package ssd;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.apache.ws.commons.util.Base64;

import xmlrpc.XmlRpcImpl;

public class DownloadWorker extends SwingWorker<Integer, Integer> {

	private String path;
	private XmlRpcImpl client;
	private String ID;
	private JButton btnDownload;

	/**
	 * Downloads selected subtitles to given path
	 * 
	 * @param client      XMLRPC client
	 * @param path        path where to download the subtitle to
	 * @param ID          subtitle id
	 * @param btnDownload refrence to button component
	 * 
	 */
	public DownloadWorker(XmlRpcImpl client, String path, String ID, JButton btnDownload) {
		this.path = path;
		this.client = client;
		this.ID = ID;
		this.btnDownload = btnDownload;
	}

	@Override
	protected Integer doInBackground() throws Exception {

		try {
			ArrayList<String> temp = new ArrayList<>();
			temp.add(ID);

			HashMap<String, ?> retVal = client.DownloadSubtitles(client.getToken(), temp);
			Object[] ret = (Object[]) retVal.get("data");

			for (Object r : ret) {

				@SuppressWarnings("unchecked")
				HashMap<String, String> fin = (HashMap<String, String>) r;
				byte[] decoded = Base64.decode(fin.get("data"));
				InputStream stream = new ByteArrayInputStream(decoded);
				GZIPInputStream gis = new GZIPInputStream(stream);
				FileOutputStream fos = new FileOutputStream(path);

				byte[] buffer = new byte[1024];
				int len;
				while ((len = gis.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				gis.close();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Download failed!", "Download failed", JOptionPane.ERROR_MESSAGE);
		}

		return 1;
	}

	protected void done() {
		btnDownload.setEnabled(true);
	}
}
