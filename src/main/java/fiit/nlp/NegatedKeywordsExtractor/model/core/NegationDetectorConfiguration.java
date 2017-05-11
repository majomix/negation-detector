package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class NegationDetectorConfiguration {
	public static String getProperty(String name) {
		String file = System.getProperty("user.home") + File.separator + "NegationDetector.ini";
		Properties properties = new Properties();
		try(InputStream input = new FileInputStream(file)) {
			properties.load(input);
			return properties.getProperty(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
