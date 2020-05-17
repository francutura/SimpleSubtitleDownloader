package ssd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import xmlrpc.XmlRpcImpl;

/**
 * Searches for subtitles and returns a List of subtitles
 * 
 * @author Fran
 *
 */

public class SearchWorker extends SwingWorker<List<Subtitle>, String> {

	private String[] args;
	private XmlRpcImpl client;
	private HashMap<String, String> limit = new HashMap<>();
	private ArrayList<Subtitle> listSubs = new ArrayList<>();
	private static final String LIMIT = "10";

	/**
	 * args[0] is query args[1] is language
	 * 
	 * @param client
	 * @param args
	 */
	public SearchWorker(XmlRpcImpl client, String... args) {
		this.client = client;
		this.args = args;
	}

	@Override
	protected List<Subtitle> doInBackground() throws Exception {

		try {
			HashMap<String, String> temp = new HashMap<>();
			initLimit(LIMIT);

			try {
				if (args[0] != null) {
					temp.put("query", args[0]);
				}

				if (args[1] != null) {
					if (args[1].equals("English")) {
						temp.put("sublanguageid", "eng");
					}
					if (args[1].equals("Croatian")) {
						temp.put("sublanguageid", "hrv");
					}
				}
			} catch (Exception e) {
			}

			ArrayList<HashMap<String, String>> searchParams = new ArrayList<>();
			searchParams.add(temp);

			HashMap<String, ?> retVal = client.SearchSubtitles(client.getToken(), searchParams, limit);
			Object[] ret = (Object[]) retVal.get("data");

			for (Object r : ret) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> data = (HashMap<String, String>) r;
				listSubs.add(new Subtitle(data.get("SubFileName"), data.get("IDSubtitleFile")));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Search Failed!", "Search failed", JOptionPane.ERROR_MESSAGE);
		}
		return listSubs;
	}

	private void initLimit(String s) {
		limit.put("limit", s);
	}

}
