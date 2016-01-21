package com.example.bheemesh.myapplication.Utils;

import java.io.ByteArrayOutputStream;
import java.util.Vector;

/**
 * Various String utility functions.
 * 
 * Most of the functions herein are re-implementations of the ones in apache
 * commons StringUtils. The reason for re-implementing this is that the
 * functions are fairly simple and using my own implementation saves the
 * inclusion of a 200Kb jar file.
 * 
 * @author paul.siegmann
 * 
 */
public class StringUtil {

	/**
	 * Whether the String is not null, not zero-length and does not contain of
	 * only whitespace.
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isNotBlank(String text) {
		return !isBlank(text);
	}

	/**
	 * Whether the String is null, zero-length and does contain only whitespace.
	 */
	public static boolean isBlank(String text) {
		if (isEmpty(text)) {
			return true;
		}
		for (int i = 0; i < text.length(); i++) {
			if (!Character.isWhitespace(text.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Whether the given string is null or zero-length.
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isEmpty(String text) {
		return (text == null) || (text.length() == 0);
	}

	/**
	 * Whether the given source string ends with the given suffix, ignoring
	 * case.
	 * 
	 * @param source
	 * @param suffix
	 * @return
	 */
	public static boolean endsWithIgnoreCase(String source, String suffix) {
		if (isEmpty(suffix)) {
			return true;
		}
		if (isEmpty(source)) {
			return false;
		}
		if (suffix.length() > source.length()) {
			return false;
		}
		return source.substring(source.length() - suffix.length())
				.toLowerCase().endsWith(suffix.toLowerCase());
	}

	/**
	 * If the given text is null return "", the original text otherwise.
	 * 
	 * @param text
	 * @return
	 */
	public static String defaultIfNull(String text) {
		return defaultIfNull(text, "");
	}

	/**
	 * If the given text is null return "", the given defaultValue otherwise.
	 * 
	 * @param text
	 * @param defaultValue
	 * @return
	 */
	public static String defaultIfNull(String text, String defaultValue) {
		if (text == null) {
			return defaultValue;
		}
		return text;
	}

	/**
	 * Null-safe string comparator
	 * 
	 * @param text1
	 * @param text2
	 * @return
	 */
	public static boolean equals(String text1, String text2) {
		if (text1 == null) {
			return (text2 == null);
		}
		return text1.equals(text2);
	}

	/**
	 * Pretty toString printer.
	 * 
	 * @param keyValues
	 * @return
	 */
	public static String toString(Object... keyValues) {
		StringBuilder result = new StringBuilder();
		result.append('[');
		for (int i = 0; i < keyValues.length; i += 2) {
			if (i > 0) {
				result.append(", ");
			}
			result.append(keyValues[i]);
			result.append(": ");
			Object value = null;
			if ((i + 1) < keyValues.length) {
				value = keyValues[i + 1];
			}
			if (value == null) {
				result.append("<null>");
			} else {
				result.append('\'');
				result.append(value);
				result.append('\'');
			}
		}
		result.append(']');
		return result.toString();
	}

	public static int hashCode(String... values) {
		int result = 31;
		for (int i = 0; i < values.length; i++) {
			result ^= String.valueOf(values[i]).hashCode();
		}
		return result;
	}

	/**
	 * Gives the substring of the given text before the given separator.
	 * 
	 * If the text does not contain the given separator then the given text is
	 * returned.
	 * 
	 * @param text
	 * @param separator
	 * @return
	 */
	public static String substringBefore(String text, char separator) {
		if (isEmpty(text)) {
			return text;
		}
		int sepPos = text.indexOf(separator);
		if (sepPos < 0) {
			return text;
		}
		return text.substring(0, sepPos);
	}

	/**
	 * Gives the substring of the given text before the last occurrence of the
	 * given separator.
	 * 
	 * If the text does not contain the given separator then the given text is
	 * returned.
	 * 
	 * @param text
	 * @param separator
	 * @return
	 */
	public static String substringBeforeLast(String text, char separator) {
		if (isEmpty(text)) {
			return text;
		}
		int cPos = text.lastIndexOf(separator);
		if (cPos < 0) {
			return text;
		}
		return text.substring(0, cPos);
	}

	/**
	 * Gives the substring of the given text after the last occurrence of the
	 * given separator.
	 * 
	 * If the text does not contain the given separator then "" is returned.
	 * 
	 * @param text
	 * @param separator
	 * @return
	 */
	public static String substringAfterLast(String text, char separator) {
		if (isEmpty(text)) {
			return text;
		}
		int cPos = text.lastIndexOf(separator);
		if (cPos < 0) {
			return "";
		}
		return text.substring(cPos + 1);
	}

	/**
	 * Gives the substring of the given text after the given separator.
	 * If the text does not contain the given separator then "" is returned.
	 */
	public static String substringAfter(String text, char c) {
		if (isEmpty(text)) {
			return text;
		}
		int cPos = text.indexOf(c);
		if (cPos < 0) {
			return "";
		}
		return text.substring(cPos + 1);
	}
	public static final String[] split(String original, String separator) {
		Vector<String> nodes = new Vector<String>();

		int index = 0;
		while (index < original.length()) {
			int temp = original.indexOf(separator, index);
			if (temp == -1)
				break;
			nodes.addElement(original.substring(index, temp));
			index = temp + separator.length();
		}
		nodes.addElement(original.substring(index));

		// Create splitted string array
		String[] result = new String[nodes.size()];
		if (nodes.size() > 0) {
			for (int loop = 0; loop < nodes.size(); loop++) {
				result[loop] = nodes.elementAt(loop);
			}
		}
		return result;
	}

	public static byte[] getUnicodeFromAscii(int language, byte[] data,
											 int offset, int length) {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		char result[] = new char[500];
		char lang = (char) (language & 0xFFFF);
		// byte addition = 0;
		int index = 0;
		// switch(language){
		// //English
		// case LangConstants.EIndiLangEnglish: lang = 0x0000;
		// break;
		// //Hindi
		// case LangConstants.EIndiLangHindi: lang = 0x0900;
		// break;
		// //Marathi
		// case LangConstants.EIndiLangMarathi: lang = 0x0900;
		// break;
		// //Gujarati + 0x80
		// case LangConstants.EIndiLangGujarati: lang = 0x0A80;
		// break;
		// //Punjabi
		// case LangConstants.EIndiLangPunjabi: lang = 0x0A00;
		// break;
		// //Bengali
		// case LangConstants.EIndiLangBengali: lang = 0x0980;
		// break;
		// //Kannada
		// case LangConstants.EIndiLangKannada: lang = 0x0C80;
		// break;
		// //Telugu
		// case LangConstants.EIndiLangTelugu: lang = 0x0C00;
		// addition = 0x00;
		// break;
		// //Tamil
		// case LangConstants.EIndiLangTamil: lang = 0x0B80;
		// break;
		// //Malayalam
		// case LangConstants.EIndiLangMalayalam: lang = 0x0D00;
		// break;
		// }
		char ch = 0;
		byte temp = 0;
		for (int i = 0; i < length; i++) {
			ch = 0;
			temp = data[offset + i];
			// if(temp == -128){
			// byte b1,b2;
			// if(offset+i+1 < length && data[offset+i+1] != -128){
			// b1 = data[offset+i+1];
			// i++;
			// }else{
			// b1 = data[offset+i+2];
			// i+=2;
			// }
			// if(offset+i+1 < length && data[offset+i+1] != -128){
			// b2 = data[offset+i+1];
			// i++;
			// }else{
			// b2 = data[offset+i+2];
			// i+=2;
			// }
			// ch = (char)(((b1 & 0xFFFF)<<8) | b2);
			// continue;
			// }
			if (temp == -128) {
				ch = (char) (((data[offset + i + 1] & 0xFFFF) << 8) | (data[offset
						+ i + 2] & 0xFF));
				i += 2;
				result[index++] = ch;
			} else {
				if (((temp >> 7) & 0x01) == 1) {
					temp = (byte) (temp & 0x7F);
					ch = (char) (lang | temp);
				} else {
					ch = (char) (0x0000 | temp);
				}
				result[index++] = ch;
			}
			try {
				if (index == 500) {
					bout.write(new String(result, 0, index).getBytes("UTF-8"));
					index = 0;
				}
			} catch (Exception e) {
			}
		}
		byte[] tempData = null;
		try {
			bout.write(new String(result, 0, index).getBytes("UTF-8"));
			tempData = bout.toByteArray();
			bout.close();
		} catch (Exception e) {
		}
		return tempData;
	}

	public static String decodeUrl(String str) {
		str = str.replace('+', ' ');
		StringBuffer result = new StringBuffer();
		int temp = 0;
		while (temp < str.length()) {
			int temp1 = str.indexOf('%', temp);
			if (temp1 < 0) {
				result.append(str.substring(temp));
				break;
			}
			result.append(str.substring(temp, temp1));
			result.append((char) Integer.parseInt(
					str.substring(temp1 + 1, temp1 + 3), 16));
			temp = temp1 + 3;
		}
		if (result.length() <= 0)
			return str;
		return result.toString();
	}

	public static String replaceAdditionalColon(String url) {
		// Replacing additional colons after the proper domain name with port
		// otherwise we get exceptions in J2ME while opening Connection.
		try {
			int temp1 = 0;
			if (url.contains("http://")) {
				temp1 = url.indexOf("/", url.indexOf("http://") + 8);
			} else if (url.contains("https://")) {
				temp1 = url.indexOf("/", url.indexOf("https://") + 9);
			}
			int temp = 0;
			while (temp < url.length()) {
				temp = url.indexOf(':', temp);
				if (temp < 0) {
					break;
				}
				if (temp < temp1) {
					temp++;
					continue;
				}
				String str1 = url.substring(temp + 1);
				String str2 = url.substring(0, temp);
				url = str2 + "%3A" + str1;
				temp++;
			}

		} catch (Exception e) {
			// System.out.println("Exception in replaceAdditionalColon - "+e);
			return url;
		}
		return url;
	}
}
