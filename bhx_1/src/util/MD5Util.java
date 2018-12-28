package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	
	public static String getMD5Str(String source) {
		try {
			return process(source).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String process(String source) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		// md5 encrypt
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(source.getBytes("UTF-8"));
		byte[] domain = md5.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		// converting domain to String
		for (int i = 0; i < domain.length; i++) {
			if (Integer.toHexString(0xFF & domain[i]).length() == 1) {
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & domain[i])); 
			} else
				md5StrBuff.append(Integer.toHexString(0xFF & domain[i]));
		}	

		return md5StrBuff.toString();	
	}
	
	
	/**
	 * 
	 * @Title: toMD5
	 * @author xubiao
	 * @Description: 将输入的字符串转为大写格式的MD5值 
	 * @date 2017年2月21日 上午10:03:18
	 * @param @param value
	 * @return String  返回值为转为大写的MD5值
	 * @throws
	 */
	public static String toMD5(String value){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			 md.update(value.getBytes());
		        //通过执行诸如填充之类的最终操作完成哈希计算。
		        byte b[] = md.digest();
		        //生成具体的md5密码到buf数组
		        int i;
		        StringBuffer buf = new StringBuffer("");
		        for (int offset = 0; offset < b.length; offset++) {
		          i = b[offset];
		          if (i < 0)
		            i += 256;
		          if (i < 16)
		            buf.append("0");
		          buf.append(Integer.toHexString(i));
		        }
		        return buf.toString().toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void main(String[] args) {
		String ss="00032017-01-08 10:47:142CB177AA8E4B88B8369BAE6CE8A2CCF2";
		String md5 = MD5Util.toMD5(ss);
		System.out.println(md5);
	}
}
