/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Jun 18, 2015	10:36:25 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xybase.ax.eai.archcomp.common.util.StringUtil;


/**
 * @note
 *
 */
public class StringRemover {

	protected List<String> unwantedString;

	protected List<String> unwantedStringPattern;

	public StringRemover() {
		this.unwantedString = new ArrayList<String>();
		this.unwantedStringPattern = new ArrayList<String>();
	}

	public void setUnwantedString(List<String> unwantedString) {
		this.unwantedString = unwantedString;
	}

	public void setUnwantedStringPattern(List<String> unwantedStringPattern) {
		this.unwantedStringPattern = unwantedStringPattern;
	}

	public String remove(String strings) {
		// Remove unwanted predefined string (if specified)
		if (unwantedString.size() > 0) {
			for (String unwanted : unwantedString) {
				while (strings.contains(unwanted)) {
					strings = strings.replace(unwanted,
							StringUtil.emptyChar);
				}
			}
		}

		// Remove unwanted string specified by regex pattern
		if (unwantedStringPattern.size() > 0) {
			for (String regexPattern : unwantedStringPattern) {
				Pattern pattern = Pattern.compile(regexPattern);
				Matcher matcher = pattern.matcher(strings);

				while (matcher.find()) {
					strings = strings.replace(matcher.group(),
							StringUtil.emptyChar);
					matcher = pattern.matcher(strings);
				}
			}
		}
		return strings;
	}

}
