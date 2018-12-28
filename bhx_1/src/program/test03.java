package program;
/**
 * 笔试：判断运行结果
 */

import java.util.ArrayList;
import java.util.List;

public class test03 {
	
	 public static void main(String[] args)  {

		 List<Integer> list = new ArrayList<Integer>();
		 list.add(1);
		 list.add(5);
		 list.add(3);
		 list.add(7);
		 list.add(9);
		 for(int i = 0 ; i < list.size(); ++i){
			 if(i%2 == 0){//0 2 
				 list.remove(i);
			 }
		 }
		 System.out.println(list.toString());
	 }
}
