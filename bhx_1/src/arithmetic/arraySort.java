package arithmetic;

import java.util.Arrays;

public class arraySort {

	public static void main(String[] args) {
		int[] arr = {4,6,3,8,9,3,1,0};
		arr = xunZeSort(arr);
		System.out.println(Arrays.toString(arr));
	}
	
	/**
	 * 冒泡排序
	 * @param arr
	 * @return
	 */
	public static int[] maoPaoSort(int[] arr){
		for(int i=0;i<arr.length-1;i++){
			for(int j =0;j<arr.length-i-1;j++){
				if(arr[j]>arr[j+1]){
					int t = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = t;
				}
			}
		}
		return arr;
	}
	
	/**
	 * 选择排序
	 * @param arr
	 * @return
	 */
	public static int[] xunZeSort(int[] arr){
		for(int i=0;i<arr.length-1;i++){
			for(int j=i+1;j<arr.length;j++){
				if(arr[i]>arr[j]){
					int t = arr[i];
					arr[i] = arr[j];
					arr[j] = t;
				}
			}
		}
		return arr;
	}
}
