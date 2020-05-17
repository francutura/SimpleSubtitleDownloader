package xmlrpc;

import java.util.HashMap;
import java.util.List;

public interface OpenSubtitlesAPI {
	/**
	 * 
	 * returned token is set in private variable String token 
	 * data is array of currently logged in users
	 * status returns current status should be "200 OK" if everything is in order.
	 * can return null if something went wrong
	 * 
	 * @param username
	 * @param password
	 * @param language
	 * @param userAgent
	 * @return token, data, status, seconds
	 * 
	 */
	public HashMap<String,String> LogIn(String username, String password, String language, String userAgent);
	
	/**
	 * Function logs out user based on their TOKEN
	 * returns null or something other than status "200 OK" if something failed
	 * 
	 * @param token 
	 * @return status should return "200 OK"
	 */
	public HashMap<String, String> LogOut(String token);
	
	
	/**
	 * 
	 * method is designed to process multiple searches
	 * <p>
	 * search params: sublanguageid' => $sublanguageid, 'moviehash' => $moviehash, 'moviebytesize' => $moviesize, 
	 * imdbid => $imdbid, query => 'movie name', "season" => 'season number', 
	 * "episode" => 'episode number', 'tag' => tag
	 * 
	 * return data:
	 * [MatchedBy] [IDSubMovieFile] [MovieHash] [MovieByteSize] [MovieTimeMS] [IDSubtitleFile] [SubFileName] [SubActualCD] [SubSize]
	 * [SubHash] [SubLastTS] [IDSubtitle] [UserID] [SubLanguageID] [SubFormat] [SubSumCD] [SubAuthorComment] [SubAddDate] [SubBad]
	 * [SubRating] [SubDownloadsCnt] [MovieReleaseName] [MovieFPS] [IDMovie] [IDMovieImdb] [MovieName] [MovieNameEng] [MovieYear] 
	 * [ISO639] [LanguageName] [SubHearingImpaired] [UserRank] [SeriesSeason] [SeriesEpisode] [MovieKind] [SubEncoding]
	 * [SubDownloadLink] [ZipDownloadLink] [SubtitlesLink]
	 * 
	 * @param token Session ID
	 * @param arraySearch array of items to search
	 * @param limit set limit of results
	 * @return
	 */
	public HashMap<String, String> SearchSubtitles (String token, List<HashMap<String, String>> arraySearch, HashMap<String, String> limit);
	
	/**
	 * 
	 *  Returns BASE64 encoded gzipped IDSubtitleFile(s). 
	 *  You need to BASE64 decode and ungzip 'data' to get its contents. 
	 *  LIMIT is for maximum 20 IDSubtitleFiles, others will be ignored. 
	 * 
	 * @param token
	 * @param IDSubtitleFile
	 * @return status, data, seconds
	 */
	public HashMap<String, String> DownloadSubtitles (String token, List<String> IDSubtitleFile);
		
}
