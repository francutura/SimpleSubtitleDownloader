package xmlrpc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class XmlRpcImpl implements OpenSubtitlesAPI{
	private static final String UserAgent = "TemporaryUserAgent";
	private static final String ApiLink = "https://api.opensubtitles.org/xml-rpc";
	private String token;
	private XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
    private XmlRpcClient client = new XmlRpcClient();


	
	public XmlRpcImpl() {
		
	    try {
			config.setServerURL(new URL(ApiLink));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	    
	    client.setConfig(config);
	    
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, String> LogIn(String username, String password, String language, String userAgent) {
		
		if (userAgent == null) {
			userAgent = UserAgent;
		}
		
		String[] params = {username, password, language, userAgent};
		HashMap<String, String> retVal = null;
		try {
			retVal = (HashMap<String, String>) client.execute("LogIn", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setToken(retVal.get("token"));
		return retVal;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, String> LogOut(String token) {
		
		String[] params = {token};
		HashMap<String, String> retVal = null;
		
		try {
			retVal = (HashMap<String, String>) client.execute("LogOut", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retVal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, String> SearchSubtitles(String token, List<HashMap<String, String>> arraySearch,
			HashMap<String, String> limit) {
		
		HashMap<String, String> retVal = null;
		List<Object> params = new ArrayList<>();
		params.add(token);
		params.add(arraySearch);
		params.add(limit);
		
		
		try {
			retVal = (HashMap<String, String>) client.execute("SearchSubtitles", params);
		} catch(Exception e) {e.printStackTrace();}
		
		
		return retVal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, String> DownloadSubtitles(String token, List<String> IDSubtitleFile) {
		List<Object> params = new ArrayList<>();
		params.add(token);
		params.add(IDSubtitleFile);
		
		HashMap<String, String> retVal = null;
		
		try {
			retVal = (HashMap<String, String>) client.execute("DownloadSubtitles", params);
		} catch (Exception e) {e.printStackTrace();}
		
		return retVal;
		
		
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
	
	public String getUserAgent() {
		return UserAgent;
	}

}
