package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPostRequest(String postUrl, String params)  {
		try {
			URL url = new URL(postUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestProperty("Accept-Charset", "utf-8");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8");
			writer.flush();
			writer.close();
			
			InputStream responseInStream = connection.getInputStream();
			InputStreamReader reder = new InputStreamReader(
					responseInStream, "UTF-8");
			BufferedReader breader = new BufferedReader(reder);
			 
			String content = null;
			StringBuffer sb = new StringBuffer();
			while ((content = breader.readLine()) != null) {
				sb.append(content);
			}
			breader.close();
			
			return sb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
    }
    
    /**
     * Https POST请求
     * @param url
     * @param jsonReq
     * @throws Exception
     */
    public static String post(String url, JSONObject jsonReq) throws Exception {
		
		HttpPost httpPost = new HttpPost(url);
		MultipartEntityBuilder multiPartBuilder = MultipartEntityBuilder
				.create();
		
		multiPartBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		
		multiPartBuilder.addTextBody("mdata", jsonReq.toString(),
				ContentType.APPLICATION_JSON);
		
		HttpEntity multiEntity = multiPartBuilder.build();
		
		httpPost.setEntity(multiEntity);
		
		SSLContextBuilder contextBuilder = new SSLContextBuilder();
		contextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				contextBuilder.build(), new AllowAllHostnameVerifier());
		
		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(sslsf).build();
		
		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		String content = EntityUtils.toString(httpEntity);
		
		httpClient.close();
		
		return content;
	}
    
    /**
	 * 发送https的get请求
	 * @param host	域名或者ip
	 * @param port	端口
	 * @param url	路径
	 * @return	调用接口返回的数据
	 * @throws Exception
	 */
	public static String sendGetHttpsNoCertify(String host, int port, String url)throws Exception {
		// 自定义的管理器
		X509TrustManager xtm = new Java2000TrustManager();
		TrustManager mytm[] = { xtm };
		// 得到上下文
		SSLContext ctx = SSLContext.getInstance("SSL");
		// 初始化
		ctx.init(null, mytm, null);
		// 获得工厂
		SSLSocketFactory factory = ctx.getSocketFactory();

		// 从工厂获得Socket连接
		Socket socket = factory.createSocket(host, port);

		// 剩下的就和普通的Socket操作一样了
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		out.write("GET " + url + " HTTP/1.0\n\n");
		out.flush();
		System.out.println("start   work!");
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = in.readLine()) != null) {
			sb.append(line + "\n");
		}
		out.close();
		in.close();
		return sb.toString();
	}
	
	/**
	 * 发送Http请求.
	 * @param httpUrl
	 * @param method	GET或者POST请求
	 * @param object	json数据
	 * @return
	 */
	public static String sendHttp(String httpUrl,String method, JSONObject object){
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(method);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(),"utf-8");
			System.out.println("【发送的地址:"+httpUrl+"】");
			System.out.println("【方式:"+method+"】");
			System.out.println("【发参数:"+object.toString()+"】");
			writer.write(object.toString());
			writer.flush();
			writer.close();
			
			InputStreamReader reder = new InputStreamReader(connection.getInputStream(), "utf-8");

			BufferedReader breader = new BufferedReader(reder);

			String content = null;
			StringBuffer sb = new StringBuffer();
			while ((content = breader.readLine()) != null) {
				sb.append(content);
			}
			breader.close();
			reder.close();
			System.out.println("调用http接口获取的数据:"+sb.toString());
			
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
/**
 * 
 * @ClassName:Java2000TrustManager
 * @Description:自定义的认证管理类
 * @author: HBC
 * @date: 2016年8月2日 下午3:35:45
 *
 */
class Java2000TrustManager implements X509TrustManager {
	Java2000TrustManager() {
		// 这里可以进行证书的初始化操作
	}

	// 检查客户端的可信任状态
	public void checkClientTrusted(X509Certificate chain[], String authType)
			throws CertificateException {
		System.out.println("检查客户端的可信任状态...");
	}

	// 检查服务器的可信任状态
	public void checkServerTrusted(X509Certificate chain[], String authType)
			throws CertificateException {
		System.out.println("检查服务器的可信任状态");
	}

	// 返回接受的发行商数组
	public X509Certificate[] getAcceptedIssuers() {
		System.out.println("获取接受的发行商数组...");
		return null;
	}
}