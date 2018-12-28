package util;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BigDecimalUtil {

	public static final BigDecimal ZERO_OF_BIGDECIMAL = new BigDecimal(0).setScale(2);
	public static final BigDecimal ONE_OF_BIGDECIMAL = new BigDecimal(1).setScale(2);
	
	// 1.5 转换成 2
	public static final int ROUND_HALF_UP = 4;
	// 向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向下舍入, 例如1.55 保留一位小数结果为1.5 
	public static final int ROUND_HALF_DOWN = 5;
	// 向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，如果保留位数是奇数，使用ROUND_HALF_UP ，如果是偶数，使用ROUND_HALF_DOWN 
	public static final int ROUND_HALF_EVEN = 6;
	
	
	private static final Pattern AMOUNT_PATTERN = Pattern.compile("^(0|[1-9]\\d{0,11})\\.(\\d\\d)$"); // 不考虑分隔符的正确性
	private static final char[] RMB_NUMS = "零壹贰叁肆伍陆柒捌玖".toCharArray();
	private static final String[] UNITS = { "元", "角", "分", "整" };
	private static final String[] U1 = { "", "拾", "佰", "仟" };
	private static final String[] U2 = { "", "万", "亿" };
	
	
	/**
	 * @author bhx
	 * @time 2017年3月21日15:36:04
	 * description:两个bigDecimal是否相等，其中数值比较全部才用默认的数值
	 * return 
	 */
	public static boolean equal(BigDecimal decimal1,BigDecimal decimal2){
		boolean b = false;
		if(decimal1.setScale(4, ROUND_HALF_UP).equals(decimal2.setScale(4, BigDecimal.ROUND_HALF_UP))){
			b = true;
		}
		return b;
	}
	
	/**
	 * 获取最小值,如果不传参数返回0.0，只传1个参数返回自己，传多个，返回值最小的那个
	 * @param nums
	 * @return
	 */
	public static BigDecimal getMin(BigDecimal... nums){
		if(nums == null){
			return ZERO_OF_BIGDECIMAL;
		}else if(nums.length == 1){
			return nums[0];
		}else{
			BigDecimal min = nums[0];
			
			for(BigDecimal num : nums){
				if(num.compareTo(min) == -1){
					min = num;
				}
			}
			
			return min;
		}
	}
	/**
	 * 获取最大值,如果不传参数返回0.0，只传1个参数返回自己，传多个，返回值最大的那个
	 * @param nums
	 * @return
	 */
	public static BigDecimal getMax(BigDecimal... nums){
		if(nums == null || nums.length == 0){
			return ZERO_OF_BIGDECIMAL;
		}else if(nums.length == 1){
			return nums[0];
		}else{
			BigDecimal max = nums[0];
			
			for(BigDecimal num : nums){
				if(num.compareTo(max) == 1){
					max = num;
				}
			}
			
			return max;
		}
	}
	
	/**
	 * @author bhx
	 * @time 2017年3月21日15:36:04
	 * description:两个bigDecimal是否相等，其中数值比较全部才用默认的数值
	 * @param digit:精度的位数
	 * @param hex:进制方式，BigDecimalUtil.ROUND_HALF_UP为1.5变2，BigDecimalUtil.ROUND_HALF_DOWN为1.5变1.
	 * return 
	 */
	public static boolean equal(BigDecimal decimal1,BigDecimal decimal2,int digit,int hex){
		boolean b = false;
		if(decimal1.setScale(digit, BigDecimal.ROUND_HALF_UP).equals(decimal2.setScale(digit, hex))){
			b = true;
		}
		return b;
	}
	
	
	/**
	 * @Time 2017年4月20日09:57:05
	 * @author bhx
	 * 	BigDeimal的加法，为默认精度2位,四舍五入 支持连加
	 * @param bigDecimals
	 * @return
	 */
	public static BigDecimal add(BigDecimal... bigDecimals){
		BigDecimal sum = ZERO_OF_BIGDECIMAL;
		
		for (BigDecimal bd : bigDecimals){
			if(bd != null){
				sum = sum.add(bd);
			}
		}
		return sum.setScale(2,ROUND_HALF_UP);
	}
	
	/**
	 * @Time 2017年4月20日09:57:05
	 * @author bhx
	 * 	BigDeimal的加法，为默认四舍五入 支持连加
	 * @param bigDecimals
	 * @param i 精度的长度
	 * @return
	 */
	public static BigDecimal add(int i,BigDecimal... bigDecimals){
		if(bigDecimals==null || bigDecimals.length == 0){
			throw new RuntimeException("没有入参！请传入2个以上参数");
		}else if(bigDecimals.length == 1){
			return bigDecimals[0];
		}else{
			BigDecimal sum = bigDecimals[0];
			if(sum == null){
				throw new RuntimeException("传入的第一个数为null。");
			}
			for (int k=1;k<bigDecimals.length;k++){
				if(bigDecimals[k]==null){
					throw new RuntimeException("传入的第“"+(k+1)+"”数为null。");
				}
				sum = sum.add(bigDecimals[k]);
			}
			
			return sum.setScale(i,ROUND_HALF_UP);
		}
	}
	
	/**
	 * @Time 2017年4月20日09:57:05
	 * @author bhx
	 * 	BigDeimal的加法 支持连加
	 * @param bigDecimals
	 * @param i 精度的长度
	 * @param j 进制
	 * 		可以取 ROUND_HALF_UP 四舍五入，ROUND_HALF_DOWN 五舍六入
	 * @return
	 */
	public static BigDecimal add(int i,int j,BigDecimal... bigDecimals){
		if(bigDecimals==null || bigDecimals.length == 0){
			throw new RuntimeException("没有入参！请传入2个以上参数");
		}else if(bigDecimals.length == 1){
			return bigDecimals[0];
		}else{
			BigDecimal sum = bigDecimals[0];
			if(sum == null){
				throw new RuntimeException("传入的第一个数为null。");
			}
			for (int k=1;k<bigDecimals.length;k++){
				if(bigDecimals[k]==null){
					throw new RuntimeException("传入的第“"+(k+1)+"”数为null。");
				}
				sum = sum.add(bigDecimals[k]);
			}
			
			return sum.setScale(i,j);
		}
	}
	
	/**
	 * @Time 2017年4月20日09:57:05
	 * @author bhx
	 * 	BigDeimal的减法，为默认精度2位,四舍五入
	 *  前面减后面 支持连减
	 * @param bigDecimals
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal... bigDecimals){
		if(bigDecimals==null || bigDecimals.length == 0){
			throw new RuntimeException("没有入参！请传入2个以上参数");
		}else if(bigDecimals.length == 1){
			return bigDecimals[0];
		}else{
			BigDecimal sum = bigDecimals[0];
			
			for (int i=1;i<bigDecimals.length;i++){
				sum = sum.subtract(bigDecimals[i]);
			}
			return sum.setScale(2,ROUND_HALF_UP);
		}
	}
	
	/**
	 * @Time 2017年4月20日09:57:05
	 * @author bhx
	 * 	BigDeimal的减法，为默认四舍五入
	 *  前面减后面 支持连减
	 * @param bigDecimals
	 * @param i 精度的长度
	 * @return
	 */
	public static BigDecimal subtract(int i,BigDecimal... bigDecimals){
		if(bigDecimals==null || bigDecimals.length == 0){
			throw new RuntimeException("没有入参！请传入2个以上参数");
		}else if(bigDecimals.length == 1){
			return bigDecimals[0];
		}else{
			BigDecimal sum = bigDecimals[0];
			if(sum == null){
				throw new RuntimeException("传入的第一个数为null。");
			}
			for (int k=1;k<bigDecimals.length;k++){
				if(bigDecimals[k]==null){
					throw new RuntimeException("传入的第“"+(k+1)+"”数为null。");
				}
				sum = sum.subtract(bigDecimals[k]);
			}
			
			return sum.setScale(i,ROUND_HALF_UP);
		}
	}
	
	/**
	 * @Time 2017年4月20日09:57:05
	 * @author bhx
	 * 	BigDeimal的减法
	 *  前面减后面 支持连减
	 * @param bigDecimals
	 * @param i 精度的长度
	 * @param j 进制
	 * 		可以取 ROUND_HALF_UP 四舍五入，ROUND_HALF_DOWN 五舍六入
	 * @return
	 */
	public static BigDecimal subtract(int i,int j,BigDecimal... bigDecimals){
		if(bigDecimals==null || bigDecimals.length == 0){
			throw new RuntimeException("没有入参！请传入2个以上参数");
		}else if(bigDecimals.length == 1){
			return bigDecimals[0];
		}else{
			BigDecimal sum = bigDecimals[0];
			if(sum == null){
				throw new RuntimeException("传入的第一个数为null。");
			}
			for (int k=1;k<bigDecimals.length;k++){
				if(bigDecimals[k]==null){
					throw new RuntimeException("传入的第“"+(k+1)+"”数为null。");
				}
				sum = sum.subtract(bigDecimals[k]);
			}
			
			return sum.setScale(i,j);
		}
	}

	/**
	 * @Time 2017年4月20日09:57:05
	 * @author bhx
	 * 	BigDeimal的乘法，为默认精度2位,四舍五入 支持连乘 
	 * @param bigDecimals
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal... bigDecimals){
		
		if(bigDecimals==null || bigDecimals.length == 0){
			throw new RuntimeException("没有入参！请传入2个以上参数");
		}
		
		BigDecimal sum = ONE_OF_BIGDECIMAL;
		
		for (BigDecimal bd : bigDecimals){
			sum = sum.multiply(bd);
		}
		return sum.setScale(2,ROUND_HALF_UP);
	}
	
	/**
	 * @Time 2017年4月20日09:57:05
	 * @author bhx
	 * 	BigDeimal的乘法，为默认四舍五入 支持连乘
	 * @param bigDecimals
	 * @param i 精度的长度
	 * @return
	 */
	public static BigDecimal multiply(int i,BigDecimal... bigDecimals){
		if(bigDecimals==null || bigDecimals.length == 0){
			throw new RuntimeException("没有入参！请传入2个以上参数");
		}else if(bigDecimals.length == 1){
			return bigDecimals[0];
		}else{
			BigDecimal sum = bigDecimals[0];
			if(sum == null){
				throw new RuntimeException("传入的第一个数为null。");
			}
			for (int k=1;k<bigDecimals.length;k++){
				if(bigDecimals[k]==null){
					throw new RuntimeException("传入的第“"+(k+1)+"”数为null。");
				}
				sum = sum.multiply(bigDecimals[k]);
			}
			
			return sum.setScale(i,ROUND_HALF_UP);
		}
	}
	/**
	 * @Time 2017年4月20日09:57:05
	 * @author bhx
	 * 	BigDeimal的乘法 支持连乘
	 * @param bigDecimals
	 * @param i 精度的长度
	 * @param j 进制
	 * 		可以取 ROUND_HALF_UP 四舍五入，ROUND_HALF_DOWN 五舍六入
	 * @return
	 */
	public static BigDecimal multiply(int i,int j,BigDecimal... bigDecimals){
		if(bigDecimals==null || bigDecimals.length == 0){
			throw new RuntimeException("没有入参！请传入2个以上参数");
		}else if(bigDecimals.length == 1){
			return bigDecimals[0];
		}else{
			BigDecimal sum = bigDecimals[0];
			if(sum == null){
				throw new RuntimeException("传入的第一个数为null。");
			}
			for (int k=1;k<bigDecimals.length;k++){
				if(bigDecimals[k]==null){
					throw new RuntimeException("传入的第“"+(k+1)+"”数为null。");
				}
				sum = sum.multiply(bigDecimals[k]);
			}
			
			return sum.setScale(i,j);
		}
	}
	
	/**
	 * @Time 2017年4月20日09:57:05
	 * @author bhx
	 * 	BigDeimal的除法，为默认精度2位,四舍五入
	 *  前面除后面 支持连除,如果入参是null或者长度是0，抛出异常，如果入参只有1位，返回本身，如果入参长度是2个以上，按顺序除，例如divide(6,3,2) = 6/3/2 = 1
	 * @param bigDecimals
	 * @return
	 */
	public static BigDecimal divide(BigDecimal... bigDecimals){
		if(bigDecimals==null || bigDecimals.length == 0){
			throw new RuntimeException("没有入参！请传入2个以上参数");
		}else if(bigDecimals.length == 1){
			return bigDecimals[0];
		}else{
			BigDecimal sum = bigDecimals[0];
			if(sum == null){
				throw new RuntimeException("传入的被除数为null。");
			}
			for (int i=1;i<bigDecimals.length;i++){
				if(bigDecimals[i]==null){
					throw new RuntimeException("传入的第“"+i+"”除数为null。");
				}
				sum = sum.divide(bigDecimals[i]);
			}
			
			return sum.setScale(2,ROUND_HALF_UP);
		}
	}
	
	/**
	 * @Time 2017年4月20日09:57:05
	 * @author bhx
	 * 	BigDeimal的除法，为默认四舍五入
	 *  前面除后面 支持连除
	 * @param bigDecimals
	 * @param i 精度的长度
	 * @return
	 */
	public static BigDecimal divide(int i,BigDecimal... bigDecimals){
		if(bigDecimals==null || bigDecimals.length == 0){
			throw new RuntimeException("没有入参！请传入2个以上参数");
		}else if(bigDecimals.length == 1){
			return bigDecimals[0];
		}else{
			BigDecimal sum = bigDecimals[0];
			if(sum == null){
				throw new RuntimeException("传入的被除数为null。");
			}
			for (int k=1;k<bigDecimals.length;k++){
				if(bigDecimals[k]==null){
					throw new RuntimeException("传入的第“"+k+"”除数为null。");
				}
				sum = sum.divide(bigDecimals[k]);
			}
			
			return sum.setScale(i,ROUND_HALF_UP);
		}
	}
	
	/**
	 * @Time 2017年4月20日09:57:05
	 * @author bhx
	 * 	BigDeimal的除法
	 *  前面除后面 支持连除
	 * @param bigDecimals
	 * @param i 精度的长度
	 * @param j 进制
	 * 		可以取 ROUND_HALF_UP 四舍五入，ROUND_HALF_DOWN 五舍六入
	 * @return
	 */
	public static BigDecimal divide(int i,int j,BigDecimal... bigDecimals){
		if(bigDecimals==null || bigDecimals.length == 0){
			throw new RuntimeException("没有入参！请传入2个以上参数");
		}else if(bigDecimals.length == 1){
			return bigDecimals[0];
		}else{
			BigDecimal sum = bigDecimals[0];
			if(sum == null){
				throw new RuntimeException("传入的被除数为null。");
			}
			for (int k=1;k<bigDecimals.length;k++){
				if(bigDecimals[k]==null){
					throw new RuntimeException("传入的第“"+k+"”除数为null。");
				}
				sum = sum.divide(bigDecimals[k]);
			}
			
			return sum.setScale(i,ROUND_HALF_UP);
		}
	}
	
	/**
	 * big是null或者值等于0的时候返回true
	 * @param big
	 * @return
	 */
	public static boolean isNullOrEmpty(BigDecimal big){
		if(big == null){
			return true;
		}
		if(big.compareTo(ZERO_OF_BIGDECIMAL)==0){
			return true;
		}
		return false;
	}
	
	

    /** 需要将数字格式化为1,000,000.00的形式（也可不格式化）
     * 将金额（整数部分等于或少于12位，小数部分2位）转换为中文大写形式.  
     * @param amount 金额数字  
     * @return       中文大写  
     * @throws IllegalArgumentException  
     */  
    public static String convert(String amount) throws IllegalArgumentException {   
    	
       
    	// 如果有带逗号的分隔符则去掉分隔符。否则返回原值   
    	amount = amount.contains(",")?amount.replace(",", ""):amount;   
    	 //判断是否输入小数点，如果没有输入小数点，则进行格式化
    	amount = amount.contains(".")?amount:(amount+".00");
    	//如果是小数点以后为1位，则在末尾添加个0，凑齐两位小数
    	String temp = amount.substring(amount.indexOf(".")+1, amount.length());
    	amount = temp.length() == 1?amount+"0":amount;
  
        // 验证金额正确性   
        if (amount.equals("0.00")) {   
            throw new IllegalArgumentException("金额不能为零.");   
        }   
        Matcher matcher = AMOUNT_PATTERN.matcher(amount);   
        if (! matcher.find()) {   
            throw new IllegalArgumentException("输入金额有误.");   
        }   
  
        String integer  = matcher.group(1); // 整数部分   
        String fraction = matcher.group(2); // 小数部分   
  
        String result = "";   
        if (! integer.equals("0")) {   
            result += integer2rmb(integer) + UNITS[0]; // 整数部分   
        }   
        if (fraction.equals("00")) {   
            result += UNITS[3]; // 添加[整]   
        } else if (fraction.startsWith("0") && integer.equals("0")) {   
            result += fraction2rmb(fraction).substring(1); // 去掉分前面的[零]   
        } else {   
            result += fraction2rmb(fraction); // 小数部分   
        }   
  
        return result;   
    }   
  
    // 将金额小数部分转换为中文大写   
    private static String fraction2rmb(String fraction) {   
        char jiao = fraction.charAt(0); // 角   
        char fen  = fraction.charAt(1); // 分   
        return (RMB_NUMS[jiao - '0'] + (jiao > '0' ? UNITS[1] : ""))   
                + (fen > '0' ? RMB_NUMS[fen - '0'] + UNITS[2] : "");   
    }   
  
    // 将金额整数部分转换为中文大写   
    private static String integer2rmb(String integer) {   
        StringBuilder buffer = new StringBuilder();   
        // 从个位数开始转换   
        int i, j;   
        for (i = integer.length() - 1, j = 0; i >= 0; i--, j++) {   
            char n = integer.charAt(i);   
            if (n == '0') {   
                // 当n是0且n的右边一位不是0时，插入[零]   
                if (i < integer.length() - 1 && integer.charAt(i + 1) != '0') {   
                    buffer.append(RMB_NUMS[0]);   
                }   
                // 插入[万]或者[亿]   
                if (j % 4 == 0) {   
                    if (i > 0 && integer.charAt(i - 1) != '0'  
                            || i > 1 && integer.charAt(i - 2) != '0'  
                            || i > 2 && integer.charAt(i - 3) != '0') {   
                        buffer.append(U2[j / 4]);   
                    }   
                }   
            } else {   
                if (j % 4 == 0) {   
                    buffer.append(U2[j / 4]);     // 插入[万]或者[亿]   
                }   
                buffer.append(U1[j % 4]);         // 插入[拾]、[佰]或[仟]   
                buffer.append(RMB_NUMS[n - '0']); // 插入数字   
            }   
        }   
        return buffer.reverse().toString();   
    }   
  
	/**
	 * 将数据格式化为 ###,###.00
	 * 
	 * @param num_bigdecimal
	 * @return
	 */
	public static String formatNumber(BigDecimal num_bigdecimal) {
		String num = null;
		DecimalFormat formate = new DecimalFormat("###,###.00");
		num = formate.format(num_bigdecimal);

		return num;
	}
	
	
	public static void main(String[] args){
//		System.out.println((int)'<');
//		System.out.println((int)'=');
//		System.out.println((int)'>');
//		System.out.println((int)'!');
		System.out.println(subtract(new BigDecimal("10"),new BigDecimal("30")));
	}
}
