package com.my.zx.utils;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zzz on 14-12-11.
 */
public class StringUtil {

	/*
	 * private StringUtil() { }
	 */

	/**
	 * check string
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNull(String string) {
		return (null == string || string.length() == 0 || string.equals("") || string.trim().length() == 0);
	}

	public static boolean isEmptyString(String str) {
		return !(str != null && str.trim().length() > 0);
	}

	/**
	 * 判断是否为整型字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		// +表示1个或多个（如"3"或"225"），*表示0个或多个（[0-9]*）（如""或"1"或"22"），?表示0个或1个([0-9]?)(如""或"7")
		boolean isNum = str.matches("[0-9]+");
		return isNum;
	}

	/**
	 * 半角转换为全角 说明：解决TextView中文字排版参差不齐的问题 将textview中的字符全角化。即将所有的数字、字母及标点全部转为全角字符，使它们与汉字同占两个字节 by:liubing
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 去除特殊字符或将所有中文标号替换为英文标号 说明：解决TextView中文字排版参差不齐的问题 by:liubing
	 * 
	 * @param str
	 * @return
	 */
	public static String StringFilter(String str) {
		str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!").replaceAll("：", ":").replace("，", ",").replace("。", ".").replace("……", "......");// 替换中文标号
		String regEx = "[『』]"; // 清除掉特殊字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * bytes 转成Hex
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] hexChars = new char[bytes.length * 2];
		int v;
		for (int j = 0; j < bytes.length; j++) {
			v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	/**
	 * 将字符串转成
	 * 
	 * @param text
	 * @return
	 */
	public static String sha1hash(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(text.getBytes("UTF-8"), 0, text.length());
			byte[] sha1hash = md.digest();
			return bytesToHex(sha1hash);
		} catch (Exception e) {
			Log.e(StringUtil.class.getName(), "", e);
			return null;
		}
	}

	public static String loadRawResourceString(Resources res, int resourceId) throws IOException {
		InputStream is = res.openRawResource(resourceId);
		return loadString(is);
	}

	public static String loadAssetString(Resources res, String filename) throws IOException {
		InputStream is = res.getAssets().open(filename);
		return loadString(is);
	}

	public static String loadString(InputStream is) throws IOException {
		StringBuilder builder = new StringBuilder();
		InputStreamReader reader = new InputStreamReader(is);
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			builder.append(buf, 0, numRead);
		}
		return builder.toString();
	}

	/**
	 * 将两个String数组组合一起 并去除相同的元素
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static String[] union(String[] a, String[] b) {
		LinkedList<String> retval = new LinkedList<String>();
		for (int i = 0; i < a.length; i++) {
			retval.add(a[i]);
		}
		for (int i = 0; i < b.length; i++) {
			if (!retval.contains(b[i])) {
				retval.add(b[i]);
			}
		}
		String[] retarray = new String[retval.size()];
		retval.toArray(retarray);
		return retarray;

	}

	/**
	 * 找出 两个String数组相同的部分
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static String[] intersection(String[] a, String[] b) {
		List<String> blist = Arrays.asList(b);
		LinkedList<String> retval = new LinkedList<String>();
		for (int i = 0; i < a.length; i++) {
			if (blist.contains(a[i])) {
				retval.add(a[i]);
			}
		}
		String[] retarray = new String[retval.size()];
		retval.toArray(retarray);
		return retarray;
	}

	/**
	 * 字符串转换成十六进制字符串
	 */
	public static String str2HexStr(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString();
	}

	/**
	 * 十六进制转换字符串
	 */
	public static String hexStr2Str(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}

	/**
	 * bytes转换成十六进制字符串
	 */
	public static String byte2HexStr(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			// if (n<b.length-1) hs=hs+":";
		}
		return hs.toUpperCase();
	}

	private static byte uniteBytes(String src0, String src1) {
		byte b0 = Byte.decode("0x" + src0).byteValue();
		b0 = (byte) (b0 << 4);
		byte b1 = Byte.decode("0x" + src1).byteValue();
		byte ret = (byte) (b0 | b1);
		return ret;
	}

	/**
	 * 十六进制字符串转换成bytes
	 */
	public static byte[] hexStr2Bytes(String src) {
		int m = 0, n = 0;
		int l = src.length() / 2;
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
		}
		return ret;
	}

	/**
	 * String的字符串转换成unicode的String
	 */
	public static String stringToUnicode(String strText) throws Exception {
		char c;
		String strRet = "";
		int intAsc;
		String strHex;
		for (int i = 0; i < strText.length(); i++) {
			c = strText.charAt(i);
			intAsc = (int) c;
			strHex = Integer.toHexString(intAsc);
			if (intAsc > 128) {
				strRet += "\\u" + strHex;
			} else {
				// 低位在前面补00
				strRet += "\\u00" + strHex;
			}
		}
		return strRet;
	}

	/**
	 * unicode的String转换成String的字符串
	 */
	public static String unicodeToString(String hex) {
		int t = hex.length() / 6;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < t; i++) {
			String s = hex.substring(i * 6, (i + 1) * 6);
			// 高位需要补上00再转
			String s1 = s.substring(2, 4) + "00";
			// 低位直接转
			String s2 = s.substring(4);
			// 将16进制的string转为int
			int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
			// 将int转换为字符
			char[] chars = Character.toChars(n);
			str.append(new String(chars));
		}
		return str.toString();
	}

	/**
	 * gbk的String转换成utf-8的字符串
	 */
	public static String gbk2utf8(String gbk) {
		String utf8 = "";
		try {
			utf8 = new String(gbk.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return utf8;
	}

	// 不能全位相同的数字和字母
	public static boolean equalStr(String numOrStr) {
		boolean flag = true;
		char str = numOrStr.charAt(0);
		for (int i = 0; i < numOrStr.length(); i++) {
			if (str != numOrStr.charAt(i)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	// 判断是否连续的字母和数字
	public static boolean isOrderNumeric(String numOrStr) {
		boolean flag = true;// 如果全是连续数字返回true
		boolean isNumeric = true;// 如果全是数字返回true
		/*
		 * for (int i = 0; i < numOrStr.length(); i++) { if (!Character.isDigit(numOrStr.charAt(i))) {
		 * isNumeric = false; break; } }
		 */

		char[] chars = numOrStr.toCharArray();
		int[] asciiArray = new int[chars.length];

		for (int i = 0; i < chars.length; i++) {
			asciiArray[i] = (int) (chars[i]);
		}
		if (true) {// 如果全是数字则执行是否连续数字判断
			for (int i = 0; i < numOrStr.length(); i++) {
				if (i > 0) {// 判断如123456 ,abcdef
					int num = asciiArray[i];
					int num_ = asciiArray[i - 1] + 1;
					if (num != num_) {
						flag = false;
						break;
					}
				}
			}
		} else {
			flag = false;
		}
		return flag;
	}

	// 判断是否连续的字母和数字--递减（如：987654、876543）连续数字返回true
	public static boolean isOrderNumeric_(String numOrStr) {
		boolean flag = true;// 如果全是连续数字返回true
		boolean isNumeric = true;// 如果全是数字返回true
		/*
		 * for (int i = 0; i < numOrStr.length(); i++) { if (!Character.isDigit(numOrStr.charAt(i))) {
		 * isNumeric = false; break; } }
		 */
		char[] chars = numOrStr.toCharArray();
		int[] asciiArray = new int[chars.length];

		for (int i = 0; i < chars.length; i++) {
			asciiArray[i] = (int) (chars[i]);
		}
		if (isNumeric) {// 如果全是数字则执行是否连续数字判断
			for (int i = 0; i < asciiArray.length; i++) {
				if (i > 0) {// 判断如654321

					int num = asciiArray[i];
					int num_ = asciiArray[i - 1] - 1;
					if (num != num_) {
						flag = false;
						break;
					}
				}
			}
		} else {
			flag = false;
		}
		return flag;
	}

	// 判断密码是否为password弱密码
	public static boolean isSimplePsw(String str) {
		Pattern pattern = Pattern.compile("password");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	public static String cityName(String cityName) {
		if (TextUtils.isEmpty(cityName)) {
			return "";
		} else {
			return cityName.replace("市", "");
		}

	}
}
