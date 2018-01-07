package cn.sitedev.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
	public static String getNumberStr(String content) {
		if (content != null && content != "") {
			content = content.trim();
			Pattern pattern = Pattern.compile("\\d+");
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				return matcher.group(0);
			}
		}
		return "";
	}

	public static int getNumber(String content) {
		return Integer.parseInt(getNumberStr(content));
	}
}
