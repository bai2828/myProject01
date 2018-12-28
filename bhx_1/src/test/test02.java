package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class test02 {
	 public static void main(String[] args) throws ParseException {
			String start = "201809";//开始月份
			String end = "202802";//结束月份
			String format = "yyyyMM";
			int a = monthBetween(start,end,format);
			System.out.println(a);
	    }
	 
	 /*
	  * 
	  * 
	  */
	 public static int monthBetween(String start,String end,String format) throws ParseException{
	        SimpleDateFormat sdf = new SimpleDateFormat(format);
	        Calendar bef = Calendar.getInstance();
	        Calendar aft = Calendar.getInstance();
	        bef.setTime(sdf.parse(start));
	        aft.setTime(sdf.parse(end));
	        int result = aft.get(Calendar.MONTH)-bef.get(Calendar.MONTH);
	        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
	        return Math.abs(month + result);
	 }
}
