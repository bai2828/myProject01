package util;


import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	public static boolean empty(String s) {
		return s == null || s.trim().length() == 0;
	}
	public static String genNumber() {
		long time = System.currentTimeMillis();
		return (time + "").substring(3);
	}	
	
	/**
	 * 把给定字符串首字母变成大写
	 * @param str
	 * @return
	 */
	public static String upperFirst(String str){
		if(empty(str)){
			return str;
		}
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}
	/**
	 * 把给定字符串首字母变成小写
	 * @param str
	 * @return
	 */
	public static String lowerFirst(String str){
		if(empty(str)){
			return str;
		}
		return str.substring(0,1).toLowerCase() + str.substring(1);
	}
	
	/**
	 * if null,get ""
	 * @param str
	 * @return
	 */
	public static String nullToString(String str){
		return str == null ? "":str;
	}
	
	/**
     * 判断是否含有特殊字符
     * @param str
     * @return true为包含，false为不包含
     */
    public  boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
    
	/**
	 * 去掉括号补充说明的部分
	 * 例如： 峰峰煤矿（有限）公司医院  --> 峰峰煤矿公司医院
	 * @param str
	 * @return
	 */
	public  String rmBracket(String str){
		int a = str.indexOf("（");
		int b = str.indexOf("）");
		int c = str.indexOf("(");
		int d = str.indexOf(")");		
		
		if(a>0 && b>0){
			str = str.substring(0, str.indexOf("（"))+str.substring(str.indexOf("）")+1);
		}				
		if(c>0 && d>0){
			str = str.substring(0, str.indexOf("("))+str.substring(str.indexOf(")")+1);
		}		
		if(a>0 && b<0){
			str = str.substring(0, str.indexOf("（"));
		}		
		if(c>0 && d<0){
			str = str.substring(0, str.indexOf("("));
		}		
		return str;
	}	

	/*
	 * 去掉某字符及以后的内容  
	 * 例如：北京妇幼保健院/北京妇女儿童医院 --> 北京妇幼保健院
	 */
	public  String rmSplit(String str,String s){
		if(!str.contains(s)) return str;		
		return str.substring(0, str.indexOf(s));
	}
	
	
	/*
	 * 判断两个字符串是否存在包含关系
	 */
	public boolean isContains(String a, String b) {
		boolean status = a.contains(b);
		if (status)
			return status;
		boolean state = b.contains(a);
		if (state)
			return state;
		return false;
	}
	
	
	//判断一个字符串是否含有数字
	public  boolean hasDigit(String content) {
		boolean flag = false;
		Pattern p = Pattern.compile(".*\\d+.*");
		Matcher m = p.matcher(content);
		if (m.matches())
			flag = true;
		return flag;
	}
	
	
	/*
	 * 字符串中的数字转汉字  例如：402 >> 四零二
	 */
	public String numToStr(String str){
		
		char[] a = str.toCharArray();
		String [] array = new String[]{"零","一","二","三","四","五","六","七","八","九"}; 
		
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<a.length;i++ ){		
			int index = Integer.parseInt(String.valueOf(a[i]));
			sb.append(array[index]);}
		
		return sb.toString();
	}

	
	/*
	 * 字符串中的数字转汉字  例如：四零二 >>402 
	 */
	public String strToNum(String str){
		char[] a = str.toCharArray();
		Map<String,String> map = new HashMap<String, String>();
		map.put("零", "0");
		map.put("〇", "0");
		map.put("一", "1");
		map.put("二", "2");
		map.put("三", "3");
		map.put("四", "4");
		map.put("五", "5");
		map.put("六", "6");
		map.put("七", "7");
		map.put("八", "8");
		map.put("九", "9");
		StringBuffer sb = new StringBuffer();
		for(char arr : a ){
			sb.append(arr+"");
		}	
		return sb.toString();
	}

	/*
	 * 将数字转换成汉字数字  402 >> 四百零二
	 */
    public String toChinese(String string) {
        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

        String result = "";

        int n = string.length();
        for (int i = 0; i < n; i++) {

            int num = string.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
        }
        return result;
    }
    
	/**
	 * 将数字字符串转化成double类型返回，null或者空字符串转成0.0
	 * @param amount
	 * @return
	 * @throws ParseException
	 */
	public static double transMoneyStr2Double(String amount) throws ParseException{
		
		return StringUtils.empty(amount) ? 0.0 : Double.parseDouble(amount);
	}
	
	/**
	 * 对给定的数字字符串进行求和并返回double类型的和
	 * @param amount
	 * @return
	 * @throws ParseException
	 */
	public static double getSum(String... moneys) throws ParseException{
		double sum = 0.0;
		if(moneys != null && moneys.length > 0){
			for(String money : moneys){
				sum += transMoneyStr2Double(money);
			}
		}
		
		return sum;
	}
	
	

	/**
	 * 检测是否是合法手机号
	 * @param s
	 * @return
	 */
	static final String CHINESE_PHONE_PATTERN = "^(13|14|15|18|17)\\d{9}$";
	
	public static boolean isValidChinesePhoneNumber(String s) {
		return Pattern.matches(CHINESE_PHONE_PATTERN, s);
	}
	
	/**
	 * 检测字符串b在字符串a中出现的次数
	 * @param s
	 * @return
	 */
	 public static int hit(String a, String b) {
	        if (a.length() < b.length()) {
	            return 0;
	        }
	        char[] a_t = a.toCharArray();
	        int count = 0;	 
	        for (int i = 0; i < a.length() - b.length(); i++) {
	            StringBuffer buffer = new StringBuffer();
	            for (int j = 0; j < b.length(); j++) {
	                buffer.append(a_t[i + j]);
	            }
	            if(buffer.toString().equals(b)){
	                count ++;
	            }
	        }	 
	        return count;
	    }
	 
		/**
		 * 将Unicode编码的文本进行解码;
		 * @param str Unicode编码文本
		 * @return 解码后的文本
		 */
		public static String unicodeToString(String str) {
		 
		    Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");    
		    Matcher matcher = pattern.matcher(str);
		    char ch;
		    while (matcher.find()) {
		        ch = (char) Integer.parseInt(matcher.group(2), 16);
		        str = str.replace(matcher.group(1), ch + "");    
		    }
		    return str;
		}
		
		/**
		 * 格式化字符串
		 * 
		 * 例：formateString("xxx{0}bbb",1) = xxx1bbb
		 * 
		 * @param str
		 * @param params
		 * @return
		 */
		public static String formateString(String str, String... params) {
			for (int i = 0; i < params.length; i++) {
				str = str.replace("{" + i + "}", params[i]);
			}
			return str;
		}
		
		/**
		 * @description   例  把字符串  abc,dhf   转化为    'abc','dhf'
		 * @param strs
		 * @return	tmp_ids_arr
		 */
		public static String str2MySqlStrIn(String strs) {
			String tmp_ids_arr = "";
			String[] idsArr = null;
			
			if(null != strs && !"".equals(strs)) {
				idsArr = strs.split(",");
				for(String id : idsArr) {
					tmp_ids_arr += "'" + id + "',";
				}
				tmp_ids_arr = tmp_ids_arr.substring(0, tmp_ids_arr.lastIndexOf(","));
			}
			return tmp_ids_arr;
		}
	
}
