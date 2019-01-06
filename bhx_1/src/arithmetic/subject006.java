package arithmetic;

public class subject006 {

    public static void main(String[] args) {
        /*
         * 1.快速找出一个数组中的最大数、第二大数。
         *
         * 思路：如果当前元素大于最大数 max，则让第二大数等于原来的最大数 max， 再把当前元素的值赋给
         * max。如果当前的元素大于等于第二大数secondMax的值 而小于最大数max的值，则要把当前元素的值赋给 secondMax。
         */
        int[] arr = { 12, 49, 23, 32, 148, 48, };

        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        int secondMax = arr[0];
        for (int j = 0; j < arr.length; j++) {
            if (arr[j] > secondMax && arr[j] < max) {
                secondMax = arr[j];
            }
        }
        System.out.println("最大值:" + max + ",第二大数" + secondMax);
    }
}
