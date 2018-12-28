package arithmetic;

public class subject_002 {

	/**
	 * 递归实现 输入n输出n！
	 */
	
	public static void main(String[] args) {
		
		System.out.println(factorial(6));
	}
	
	//算法实现
	public static int factorial(int n){
		if(n==0)
			return 1;
		else
			return n*factorial(n-1);
	} 
}
