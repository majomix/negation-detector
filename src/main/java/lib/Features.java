package lib;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Features {

	private static Map<String, String> definitions = new HashMap<String, String>();
	private static Map<String, Map<Integer, String>> mapping = new HashMap<String, Map<Integer, String>>();

	static {
		definitions.put("S", "sub");
		definitions.put("A", "adj");
		definitions.put("P", "pron");
		definitions.put("N", "num");
		definitions.put("V", "verb");
		definitions.put("G", "par");
		definitions.put("D", "adv");
		definitions.put("E", "prep");
		definitions.put("O", "kon");
		definitions.put("T", "part");
		definitions.put("J", "int");
		definitions.put("R", "ref");
		definitions.put("Y", "kmor");
		definitions.put("W", "abr");
		definitions.put("Z", "punkt");
		definitions.put("Q", "undefined");
		definitions.put("hash", "non-word");
		definitions.put("perc", "quote");
		
		Map<Integer, String> subject_positions = new HashMap<Integer, String>();
		//subject_positions.put(0, "pos");
		subject_positions.put(1, "par");
		subject_positions.put(2, "gen");
		subject_positions.put(3, "num");
		subject_positions.put(4, "case");
		mapping.put("sub", subject_positions);
		
		Map<Integer, String> adjective_positions = new HashMap<Integer, String>();
		//adjective_positions.put(0, "pos");
		adjective_positions.put(1, "par");
		adjective_positions.put(2, "gen");
		adjective_positions.put(3, "num");
		adjective_positions.put(4, "case");
		adjective_positions.put(5, "degree");
		mapping.put("adj", adjective_positions);
		
		Map<Integer, String> pronoms_positions = new HashMap<Integer, String>();
		//pronoms_positions.put(0, "pos");
		pronoms_positions.put(1, "par");
		pronoms_positions.put(2, "gen");
		pronoms_positions.put(3, "num");
		pronoms_positions.put(4, "case");
		pronoms_positions.put(5, "agl");
		mapping.put("pron", pronoms_positions);
		
		Map<Integer, String> nums_positions = new HashMap<Integer, String>();
		//nums_positions.put(0, "pos");
		nums_positions.put(1, "par");
		nums_positions.put(2, "gen");
		nums_positions.put(3, "num");
		nums_positions.put(4, "case");
		mapping.put("num", nums_positions);
		
		Map<Integer, String> verbs_positions = new HashMap<Integer, String>();
		//verbs_positions.put(0, "pos");
		verbs_positions.put(1, "vform");
		verbs_positions.put(2, "aspect");
		verbs_positions.put(3, "num");
		verbs_positions.put(4, "per");
		verbs_positions.put(5, "gen");
		verbs_positions.put(6, "neg");
		mapping.put("verb",	verbs_positions);
		
		Map<Integer, String> par_positions = new HashMap<Integer, String>();
		//par_positions.put(0, "pos");
		par_positions.put(1, "kind");
		par_positions.put(2, "gen");
		par_positions.put(3, "num");
		par_positions.put(4, "case");
		par_positions.put(5, "degree");
		mapping.put("par", par_positions);
		
		Map<Integer, String> adv_positions = new HashMap<Integer, String>();
		//adv_positions.put(0, "pos");
		adv_positions.put(1, "degree");
		mapping.put("adv", adv_positions);
		
		Map<Integer, String> prep_positions = new HashMap<Integer, String>();
		//prep_positions.put(0, "pos");
		prep_positions.put(1, "form");
		prep_positions.put(2, "case");
		mapping.put("prep", prep_positions);
		
		Map<Integer, String> kon_positions = new HashMap<Integer, String>();
		//prep_positions.put(0, "pos");
		kon_positions.put(1, "cond");
		mapping.put("kon", kon_positions);
		
		Map<Integer, String> part_positions = new HashMap<Integer, String>();
		//part_positions.put(0, "pos");
		part_positions.put(1, "cond");
		mapping.put("part", part_positions);
		
		Map<Integer, String> int_positions = new HashMap<Integer, String>();
		//int_positions.put(0, "pos");
		mapping.put("int", int_positions);
		
		Map<Integer, String> ref_positions = new HashMap<Integer, String>();
		//ref_positions.put(0, "pos");
		mapping.put("ref", ref_positions);
		
		Map<Integer, String> kmor_positions = new HashMap<Integer, String>();
		//kmor_positions.put(0, "pos");
		mapping.put("kmor", kmor_positions);
		
		Map<Integer, String> abr_positions = new HashMap<Integer, String>();
		//abr_positions.put(0, "pos");
		mapping.put("abr", abr_positions);
		
		Map<Integer, String> punkt_positions = new HashMap<Integer, String>();
		//punkt_positions.put(0, "pos");
		mapping.put("punkt", punkt_positions);
		
		Map<Integer, String> undefined_positions = new HashMap<Integer, String>();
		//undefined_positions.put(0, "pos");
		mapping.put("ref", undefined_positions);
	}
	
	public static String Create (String m_tag, Boolean predCandidate) {
		String pos = definitions.get(Character.toString(m_tag.charAt(0)));
		String res = "pos=" + m_tag.charAt(0);
		String neg = "";
		String flag = "";
		String[] splitted = m_tag.split(":");
		
		if (splitted.length > 1) {
			m_tag = splitted[0];
			
			if (splitted[1].indexOf('r') >= 0) {
				flag += "|namedentity=true";
			}
			if (splitted[1].indexOf('q') >= 0) {
				flag += "|error=true";
			}
		}
		
		if (m_tag.indexOf('+') >= 0) {
			neg += "|neg=a";
			m_tag = StringUtils.stripEnd(m_tag, "+");
		}
		if (m_tag.indexOf('-') >= 0) {
			neg += "|neg=n";
			m_tag = StringUtils.stripEnd(m_tag, "-");
		}
		
		StringUtils.stripEnd(m_tag, "-");
		
		Map<Integer, String> positions = mapping.get(pos);

		for (int i = 1; i < m_tag.length(); i++) {
			res += "|" + positions.get(i) + "=" + m_tag.charAt(i);
		}
		
		if (predCandidate && res.contains("vform=L") && res.contains("per=c")) {
			res += "|pred_candidate=true";
		}
		
		return res + neg + flag;
	}
}
