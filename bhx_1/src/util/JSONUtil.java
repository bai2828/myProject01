package util;

import java.io.IOException;

import net.sf.json.JSONException;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * 实体类和JSON对象之间相互转化（依赖包jackson-all-1.7.6.jar、jsoup-1.5.2.jar）
 * 
 * @author <q>
 *
 */
public class JSONUtil {
	/**
	 * 将json转化为实体POJO
	 * 
	 * @param jsonStr
	 * @param obj
	 * @return
	 */
	public static <T> Object JSONToObj(String jsonStr, Class<T> obj) {
		T t = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			t = objectMapper.readValue(jsonStr, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 将实体POJO转化为JSON
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 * @throws JSONException
	 * @throws IOException
	 */
	public static <T> String objectToJson(T obj) {
		ObjectMapper mapper = new ObjectMapper();
		// Convert object to JSON string
		String jsonStr = "";
		try {
			jsonStr = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			jsonStr = "";
		}
		return jsonStr;
	}

	
	
	
}