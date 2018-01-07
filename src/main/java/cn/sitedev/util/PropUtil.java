package cn.sitedev.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropUtil {
	private static final String PROP_FILE_NAME = "application.properties";

	public static String getProp(String key) throws IOException {

		InputStream in = ClassLoader.getSystemResourceAsStream(PROP_FILE_NAME);
		return getPropValue(in, key);

	}

	private static String getPropValue(InputStream inputStream, String key)
			throws IOException {
		Properties p = new Properties();

		p.load(inputStream);

		String value = p.getProperty(key);
		value = getValAfterRegex(value, p);
		value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
		return value;

	}

	private static String getValAfterRegex(String value, Properties p) {
		String regForAll = "\\w*\\$\\{\\w+?\\}[\\w\\\\+.]*";
		String regForReplace = "\\$\\{\\w+?\\}";
		Pattern pattern = Pattern.compile(regForReplace);
		Matcher matcher = pattern.matcher(value);
		// 如果匹配到
		if (matcher.find()) {
			String strFindBefore = matcher.group();
			String strFindAfter = strFindBefore.substring(2,
					strFindBefore.length() - 1);
			String valueForKey = p.getProperty(strFindAfter);
			value = value.replace(strFindBefore, valueForKey);
		}
		return value;
	}

}
