package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SendRequestUtil {

	public static String getDataFromURL(String strURL, Map<String, Object> param) throws Exception {
		URL url = new URL(strURL);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);

		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
		final StringBuilder sb; 
		if (param != null) {
			sb = new StringBuilder(param.size() << 4);// 4次方
			final Set<String> keys = param.keySet();
			for (final String key : keys) {
				final Object value = param.get(key);
				sb.append(key); // 不能包含特殊字符
				sb.append('=');
				sb.append(value);
				sb.append('&');
			}
			// 将最后的 '&' 去掉
			sb.deleteCharAt(sb.length() - 1);
		}else{
			sb = new StringBuilder(100 << 4);
		}
		writer.write(sb.toString());
		writer.flush();
		writer.close();

		InputStreamReader reder = new InputStreamReader(conn.getInputStream(), "utf-8");

		BufferedReader breader = new BufferedReader(reder);

		String content = null;
		String result = null;
		while ((content = breader.readLine()) != null) {
			result += content;
		}
		
		return result.substring(4);

	}
	
	public static void main(String[] args) {
		try {
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("custName", "wangbing");
			param.put("custApply", "50000");
			System.out.println(getDataFromURL("http://localhost:8080/Bank/DoLoan",param));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
