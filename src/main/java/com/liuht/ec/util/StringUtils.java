package com.liuht.ec.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

/**
* @package com.liuht.ec.base.util
* <p>Title: StringUtils.java</p>
* <p>Description: String工具类</p>
* <p>Copyright: Copyright (c) 2016</p>
*
* @author 刘惠涛
* @date 2016年3月22日 上午11:05:58
* @version 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class StringUtils {
	public StringUtils() {
	}

	public static boolean isEmpty(Object str) {
		return str == null || "".equals(str);
	}

	public static boolean hasLength(CharSequence str) {
		return str != null && str.length() > 0;
	}

	public static boolean hasLength(String str) {
		return hasLength(((CharSequence) (str)));
	}

	public static boolean hasText(CharSequence str) {
		if (!hasLength(str))
			return false;
		int strLen = str.length();
		for (int i = 0; i < strLen; i++)
			if (!Character.isWhitespace(str.charAt(i)))
				return true;

		return false;
	}

	public static boolean hasText(String str) {
		return hasText(((CharSequence) (str)));
	}

	public static boolean containsWhitespace(CharSequence str) {
		if (!hasLength(str))
			return false;
		int strLen = str.length();
		for (int i = 0; i < strLen; i++)
			if (Character.isWhitespace(str.charAt(i)))
				return true;

		return false;
	}

	public static boolean containsWhitespace(String str) {
		return containsWhitespace(((CharSequence) (str)));
	}

	public static String trimWhitespace(String str) {
		if (!hasLength(str))
			return str;
		StringBuilder sb;
		for (sb = new StringBuilder(str); sb.length() > 0 && Character.isWhitespace(sb.charAt(0)); sb.deleteCharAt(0))
			;
		for (; sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1)); sb.deleteCharAt(sb.length() - 1))
			;
		return sb.toString();
	}

	public static String trimAllWhitespace(String str) {
		if (!hasLength(str))
			return str;
		int len = str.length();
		StringBuilder sb = new StringBuilder(str.length());
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if (!Character.isWhitespace(c))
				sb.append(c);
		}

		return sb.toString();
	}

	public static String trimLeadingWhitespace(String str) {
		if (!hasLength(str))
			return str;
		StringBuilder sb;
		for (sb = new StringBuilder(str); sb.length() > 0 && Character.isWhitespace(sb.charAt(0)); sb.deleteCharAt(0))
			;
		return sb.toString();
	}

	public static String trimTrailingWhitespace(String str) {
		if (!hasLength(str))
			return str;
		StringBuilder sb;
		for (sb = new StringBuilder(str); sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1)); sb
				.deleteCharAt(sb.length() - 1))
			;
		return sb.toString();
	}

	public static String trimLeadingCharacter(String str, char leadingCharacter) {
		if (!hasLength(str))
			return str;
		StringBuilder sb;
		for (sb = new StringBuilder(str); sb.length() > 0 && sb.charAt(0) == leadingCharacter; sb.deleteCharAt(0))
			;
		return sb.toString();
	}

	public static String trimTrailingCharacter(String str, char trailingCharacter) {
		if (!hasLength(str))
			return str;
		StringBuilder sb;
		for (sb = new StringBuilder(str); sb.length() > 0 && sb.charAt(sb.length() - 1) == trailingCharacter; sb
				.deleteCharAt(sb.length() - 1))
			;
		return sb.toString();
	}

	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null)
			return false;
		if (str.startsWith(prefix))
			return true;
		if (str.length() < prefix.length()) {
			return false;
		} else {
			String lcStr = str.substring(0, prefix.length()).toLowerCase();
			String lcPrefix = prefix.toLowerCase();
			return lcStr.equals(lcPrefix);
		}
	}

	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if (str == null || suffix == null)
			return false;
		if (str.endsWith(suffix))
			return true;
		if (str.length() < suffix.length()) {
			return false;
		} else {
			String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
			String lcSuffix = suffix.toLowerCase();
			return lcStr.equals(lcSuffix);
		}
	}

	public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
		for (int j = 0; j < substring.length(); j++) {
			int i = index + j;
			if (i >= str.length() || str.charAt(i) != substring.charAt(j))
				return false;
		}

		return true;
	}

	public static int countOccurrencesOf(String str, String sub) {
		if (str == null || sub == null || str.length() == 0 || sub.length() == 0)
			return 0;
		int count = 0;
		int idx;
		for (int pos = 0; (idx = str.indexOf(sub, pos)) != -1; pos = idx + sub.length())
			count++;

		return count;
	}

	public static String replace(String inString, String oldPattern, String newPattern) {
		if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null)
			return inString;
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		int index = inString.indexOf(oldPattern);
		int patLen = oldPattern.length();
		for (; index >= 0; index = inString.indexOf(oldPattern, pos)) {
			sb.append(inString.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
		}

		sb.append(inString.substring(pos));
		return sb.toString();
	}

	public static String delete(String inString, String pattern) {
		return replace(inString, pattern, "");
	}

	public static String deleteAny(String inString, String charsToDelete) {
		if (!hasLength(inString) || !hasLength(charsToDelete))
			return inString;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < inString.length(); i++) {
			char c = inString.charAt(i);
			if (charsToDelete.indexOf(c) == -1)
				sb.append(c);
		}

		return sb.toString();
	}

	public static String quote(String str) {
		return str == null ? null : (new StringBuilder()).append("'").append(str).append("'").toString();
	}

	public static Object quoteIfString(Object obj) {
		return (obj instanceof String) ? quote((String) obj) : obj;
	}

	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, '.');
	}

	public static String unqualify(String qualifiedName, char separator) {
		return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
	}

	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}

	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}

	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0)
			return str;
		StringBuilder sb = new StringBuilder(str.length());
		if (capitalize)
			sb.append(Character.toUpperCase(str.charAt(0)));
		else
			sb.append(Character.toLowerCase(str.charAt(0)));
		sb.append(str.substring(1));
		return sb.toString();
	}

	public static String getFilename(String path) {
		if (path == null) {
			return null;
		} else {
			int separatorIndex = path.lastIndexOf("/");
			return separatorIndex == -1 ? path : path.substring(separatorIndex + 1);
		}
	}

	public static String getFilenameExtension(String path) {
		if (path == null)
			return null;
		int extIndex = path.lastIndexOf('.');
		if (extIndex == -1)
			return null;
		int folderIndex = path.lastIndexOf("/");
		if (folderIndex > extIndex)
			return null;
		else
			return path.substring(extIndex + 1);
	}

	public static String stripFilenameExtension(String path) {
		if (path == null)
			return null;
		int extIndex = path.lastIndexOf('.');
		if (extIndex == -1)
			return path;
		int folderIndex = path.lastIndexOf("/");
		if (folderIndex > extIndex)
			return path;
		else
			return path.substring(0, extIndex);
	}

	public static String applyRelativePath(String path, String relativePath) {
		int separatorIndex = path.lastIndexOf("/");
		if (separatorIndex != -1) {
			String newPath = path.substring(0, separatorIndex);
			if (!relativePath.startsWith("/"))
				newPath = (new StringBuilder()).append(newPath).append("/").toString();
			return (new StringBuilder()).append(newPath).append(relativePath).toString();
		} else {
			return relativePath;
		}
	}

	public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens,
			boolean ignoreEmptyTokens) {
		if (str == null)
			return null;
		StringTokenizer st = new StringTokenizer(str, delimiters);
		List tokens = new ArrayList();
		do {
			if (!st.hasMoreTokens())
				break;
			String token = st.nextToken();
			if (trimTokens)
				token = token.trim();
			if (!ignoreEmptyTokens || token.length() > 0)
				tokens.add(token);
		} while (true);
		return toStringArray(tokens);
	}
	
	public static String[] toStringArray(Collection collection) {
		if (collection == null)
			return null;
		else
			return (String[]) collection.toArray(new String[collection.size()]);
	}

	public static String[] toStringArray(Enumeration enumeration) {
		if (enumeration == null) {
			return null;
		} else {
			List list = Collections.list(enumeration);
			return (String[]) list.toArray(new String[list.size()]);
		}
	}
	
	

}
