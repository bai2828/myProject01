package arithmetic;
/**   
 * @Title:连续数字中，找出缺失的数    
 * @Description:   
 * @author: bhx  
 * @date: 2018年8月28日      
 */
public class findLoseKey {

	public static void main(String[] args) {
		int[] a = {1, 2, 5, 4, 6, 9, 7, 8, 10};
		System.out.println(findloseNum(a));
	}

    /**
     * 算法实现
     * @param a
     * @return
     */
	public static int findloseNum(int[] a){
        int b[] = null;
        b = new int[a.length+1]; //缺失几个数，就多开辟几个长度。
        for (int i = 0; i < a.length; i++) {
            b[ a[i]-1 ] = 1;
        }
        for(int i = 0; i < b.length; i++) {
            if (b[i] == 0) {
                return (i+1);
            }
        }
		return -1;
	}
}
