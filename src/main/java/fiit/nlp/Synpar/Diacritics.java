package fiit.nlp.Synpar;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import fiit.nlp.Synpar.Request;

public class Diacritics {

	public static String Reconstruct(String input) throws IOException {
		String htmlResponse = Request.Post("http://diakritik.korpus.sk/", createPostData(input));
		
		Document doc = Jsoup.parse(htmlResponse);
		
		Element reconstructed = doc.select(".recinside").first();
		
		return reconstructed.text();
	}
	
	private static String createPostData(String text) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append("text=");
		sb.append(URLEncoder.encode(text, "UTF-8"));
		sb.append("&method=4gram");
		return sb.toString();
	}
	
}
