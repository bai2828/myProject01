package arithmetic;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。
 *
 * 示例 1:
 *
 * 输入: "III"
 * 输出: 3
 * 示例 2:
 *
 * 输入: "IV"
 * 输出: 4
 * 示例 3:
 *
 * 输入: "IX"
 * 输出: 9
 * 示例 4:
 *
 * 输入: "LVIII"
 * 输出: 58
 * 解释: L = 50, V= 5, III = 3.
 */
public class subject004 {

    /**
     * 代码实现
     * @param s
     * @return
     */
    public int romanToInt(String s) {
        Map<Character,Integer> map=new HashMap<>();
        map.put('I',1);
        map.put('V',5);
        map.put('X',10);
        map.put('L',50);
        map.put('C',100);
        map.put('D',500);
        map.put('M',1000);
        StringBuffer rev=new StringBuffer();
        rev.append(s);
        char[] num=rev.reverse().toString().toCharArray();
        int res= map.get(num[0]);
        for(int i=1;i<num.length;i++){
            if((int)map.get(num[i-1])>(int)map.get(num[i])){
                res=res-map.get(num[i]);
            }else
                res+=map.get(num[i]);
        }
        return res;
    }
}
