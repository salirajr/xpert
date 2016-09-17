package craps.ingrate.ruleengine.misc;

import com.concept.ruleengine.common.util.StringUtil;

public class Util {
	public static void main(String[] args) {
		
		String numeric = "A13";
//		numeric = "-1212";
		numeric = "13.3";
		System.out.println(StringUtil.toInt(numeric));
	}
}
