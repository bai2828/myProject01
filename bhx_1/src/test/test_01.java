package test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class test_01 {
	
	public static void main(String[] args) throws ParseException {
		String start = "201808"; // 起始日期
		String end = "201812";// 结束日期
		int loanMonth = monthBetween(String2Date(end, "yyyyMM"),
				String2Date(start, "yyyyMM"));
		System.out.println(loanMonth);
	}
	
    public static int monthBetween(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        if(c1.getTimeInMillis() < c2.getTimeInMillis()) return 0;
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 d1 = 2015-8-16 d2 = 2011-9-30
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if(month1 < month2 || month1 == month2 && day1 < day2) yearInterval --;
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2 ;
        if(day1 < day2) monthInterval --;
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval;
    }

    public static Date String2Date(String date, String format)
	        throws ParseException {
		date = date.replace("st", "").replace("nd", "").replace("rd", "")
		        .replace("th", "").replace("augu", "august");
		Date d = null;
		java.text.SimpleDateFormat f = null;
		if (format != null && format.indexOf("MMM") >= 0) {
			Locale locale = Locale.US;
			f = new java.text.SimpleDateFormat(format, locale);

		}
		else {
			f = new java.text.SimpleDateFormat(format);
		}

		d = f.parse(date);

		return d;
	}

}
