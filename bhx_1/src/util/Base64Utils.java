package util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class Base64Utils {
	// 字符串编码
	private static final String UTF_8 = "UTF-8";

	/**
	 * 加密字符串
	 * 
	 * @param inputData
	 * @return
	 */
	public static String decodeData(String inputData) {
		try {
			if (null == inputData) {
				return null;
			}
			return new String(Base64.decodeBase64(inputData.getBytes(UTF_8)), UTF_8);
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

	/**
	 * 解密加密后的字符串
	 * 
	 * @param inputData
	 * @return
	 */
	public static String encodeData(String inputData) {
		try {
			if (null == inputData) {
				return null;
			}
			return new String(Base64.encodeBase64(inputData.getBytes(UTF_8)), UTF_8);
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(encodeData("admin:admin"));
		String enStr = encodeData("admin:admin");
		System.out.println(decodeData(enStr));
		
	}
}
