package fiit.nlp.Synpar;

import java.io.*;
import java.net.*;

public class Request {

	public static String Post(String url, String formData) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		
		StringBuilder postData = new StringBuilder();
	
		postData.append(formData);
		
		byte[] postDataBytes = postData.toString().getBytes("UTF-8");
		
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.getOutputStream().write(postDataBytes);
		
		Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		
		StringBuilder sb = new StringBuilder();
		for (int c; (c = in.read()) >= 0;)
            sb.append((char)c);
		
		System.out.println(sb.toString());
		
		return sb.toString();
	}
	
}
