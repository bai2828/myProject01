package arithmetic;

/**
 *  回文数校验
 *  判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 */
public class subject003 {

    public static void main(String[] args) {
        System.out.println(isHuiWenShu(31213));
    }

    /**
     * 方法实现
     * @param target
     * @return
     */
    public static boolean isHuiWenShu(int target) {
        StringBuilder sb = new StringBuilder(String.valueOf(target));
        int a = Integer.parseInt(sb.reverse().toString());
        if ( a == target ){
            return  true;
        }
        return false;
    }
}
