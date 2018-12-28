package util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

  
public class DateUtils {
	/**
	 * 将当前时间转换为int类型返回;
	 * @param pattern
	 * @return
	 */
	public static int getIntNowDate(String pattern) {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String nowDateString = format.format(date);
		if(nowDateString != null) {
			return Integer.parseInt(nowDateString);
		}
		return -1;
	}
	
	
	/**
	 * 当前时间转换成特定形式Date
	 * @param datePattern
	 * @return
	 */
	public static String getNowDateStrByPattern(String datePattern) {
		   Date currentTime = new Date();
		   SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
		   String dateString = formatter.format(currentTime);
		   return dateString;
	}
	
	/**
	 * 获取当前年
	 * @return
	 */
	public static Integer getCurrentYear(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}
	
	/**
	 * 获取当前月//TODO 要+1
	 * @return
	 */
	public static Integer getCurrentMonth(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH);
	}
	
	/**
	 * 获取当前天，当月的当天
	 * @return
	 */
	public static Integer getCurrentDay(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 获取当前小时,24时制
	 * @return
	 */
	public static Integer getCurrentHour(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 得到下一个日期
	 * @param date
	 * @param step
	 * @return
	 */
	public static Date getNextDate(Date date,int step){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, step);
		return c.getTime();
	}
	
	/**
	 * 根据日期和格式得到Date形式日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date getDateByPattern(Date date,String pattern){
	    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
	    String dateString = formatter.format(date);
	    Date d=null;
		try {
			d = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	/**
	 * 根据日期和格式得到String形式日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getStringByPattern(Date date,String pattern){
		Date currentTime = date;
	    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
	    String dateString = formatter.format(currentTime);
	    return dateString;
	}
	/**
	 * 根据给定的字符串日期，和形式，转化成Date
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date getDateByGiven(String date,String pattern){
		 SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		 Date d = null;
		 try {
			 d = formatter.parse(date);
		 } catch (ParseException e) {
			 e.printStackTrace();
		 }
		 return d;
	}
	/**
	 * 计算两个日期的时间间隔
	 * @param startday
	 * @param endday
	 * @return
	 */
	public static int getIntervalDays(Date startday, Date endday) {
		if (startday.after(endday)) {
			Date cal = startday;
			startday = endday;
			endday = cal;
		}
		long sl = startday.getTime();
		long el = endday.getTime();
		long ei = el - sl;
		return (int) (ei / (1000 * 60 * 60 * 24));
	}
	
	/**
	 * 
	 * @Title: getStrIntervalDays
	 * @author bhx
	 * @Description: 计算两个String类型的  时间间隔
	 * @date 2017年4月20日 上午10:57:35
	 * @param @param startDay  开始时间
	 * @param @param endDay   结束时间
	 * @param @param pattern   时间类型
	 * @param @return
	 * @return int
	 * @throws
	 */
	public static int getStrIntervalDays(String startDay, String endDay,String pattern) {
		SimpleDateFormat sft=new SimpleDateFormat(pattern);
		Date endday=null;
		Date startday=null;
		try {
		    startday=sft.parse(startDay);
			endday = sft.parse(endDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (startday.after(endday)) {
			Date cal = startday;
			startday = endday;
			endday = cal;
		}
		long sl = startday.getTime();
		long el = endday.getTime();
		long ei = el - sl;
		return (int) (ei / (1000 * 60 * 60 * 24));
	}
	
	/**
	 * 获得两个日期之前相差的月份 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getMonth(Date start, Date end) {
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

        if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }

	
	/**
	 * 获得几个月后的日期
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date getNextDateByMonth(Date date,int month){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, month);
		return c.getTime();
	}
	
	
	/**
	 * 获得几个月后的日期
	 * @param date
	 * @param month
	 * @param format
	 * @return String
	 */
	public static String getStringDateByMonth(Date date,int month,String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, month);
		return formatter.format(c.getTime());
	}
	
	/**
     * 根据给出的日期获取本年的第一天的日期
     * @param date
     * @return
     */
    public static Date getBeginOfYear(Date date){
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.set(Calendar.MONTH, 0);
    	c.set(Calendar.DATE, 31);
    	return c.getTime();
    }
    /**
     * 根据给出的日期获取本年的最后一天的日期
     * @param date
     * @return
     */
    public static Date getEndOfYear(Date date){
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.set(Calendar.MONTH, 11);
    	c.set(Calendar.DATE, 31);
    	return c.getTime();
    }
    /**
     * 获取上个月的第一天日期
     * @param date
     * @return
     */
    public static Date getLastDateBegin(Date date){
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.add(Calendar.MONTH, -1);
    	c.set(Calendar.DATE, 1);
    	return c.getTime();
    }
    /**
     * 获取上个月的最后一天日期
     * @param date
     * @return
     */
    public static Date getLastDateEnd(Date date){
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.set(Calendar.DATE, 1);
    	c.add(Calendar.DATE, -1);
    	return c.getTime();
    }
    
	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		if (strDate == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
    /**
	 * 判断是否润年
	 * 
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate) {

		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 产生周序列,即得到当前时间所在的年度是第几周
	 * 
	 * @return
	 */
	public static String getSeqWeek() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1)
			week = "0" + week;
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year + week;
	}
	

	/**
	 * 两个时间之间的天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}
	
	 /**
     * 在日期上增加天数
     * @param date 日期
     * @param n 要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }
    
	/**
	 * 得到几天前的时间
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}
	
	/**
	 * 得到几天后的时间
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}
	
	/**
	 * 得到几分钟前或后的时间
	 *
	 * @param d
	 * @param minute
	 * @return
	 */
	public static Date getDateAroundSomeMinute(Date d, int minute) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.add(Calendar.MINUTE, minute);// n分钟前或后
		return now.getTime();
	}
	
    /**
   	 * 获取一年前的时间（按天）
   	 * @author fanwenwei
   	 * 2016-12-08
   	 */
   	public static String getYearB() {
   		Date today=new Date();
   		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
   		Calendar calendar = Calendar.getInstance();
   		calendar.setTime(today);
   		calendar.add(Calendar.YEAR, -1);
   		Date date = calendar.getTime();
   		String TodayS = format.format(date); 
   		return TodayS;
   	}
   	
       /**
   	 * 获取一年前的时间（按月）
   	 * @author fanwenwei
   	 * 2016-12-08
   	 */
   	public static String getYearA() {
   		Date today=new Date();
   		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");  
   		Calendar calendar = Calendar.getInstance();
   		calendar.setTime(today);
   		calendar.add(Calendar.MONTH, -12);
   		Date date = calendar.getTime();
   		String TodayS = format.format(date); 
   		return TodayS;
   	}
   	/**
	 * 判断二个时间是否在同一个周
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	 /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }
    /**
    * 获取两个日期相差的月数
    * @param d1  较大的日期
    * @param d2  较小的日期
    * @return 如果d1>d2返回 月数差 否则返回0
    */
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
    

	/**
	 * 校验时间格式是否正确  2034/01/24
	 */
	public static boolean valiDateTime(String str){
		Pattern pattern = Pattern.compile("(\\d{4})/(\\d{1,2})/(\\d{1,2})");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public static void main(String[] args){
//		System.out.println(getNowDateStrByPattern("yyyy-MM-dd"));
//		System.out.println(getNowDate("yyyy-MM-dd"));
//		System.out.println(getDateByPattern(new Date(),"yyyy-MM-dd").equals(getNowDate("yyyy-MM-dd")));
//		Date d = getDateByGiven("2016-05-17", "yyyy-MM-dd");
//		System.out.println(d);
//		System.out.println(getNextDate(new Date(), -2));
//		System.out.println(getNextDateByMonth(d,-1));
//		System.out.println(getMonth(d,new Date()));
//		
//		System.out.println(getNextDateByMonth(new Date(), 1));
		
//		System.out.println(getCurrentMonth());
//		System.out.println(getNowDateStrByPattern("MM"));
//		System.out.println(getCurrentYear().toString());
//		
//		String year = DateUtil.getCurrentYear().toString();
//		String month = DateUtil.getNowDateStrByPattern("MM");
//		String day = "26";
//		String payDate = year + "/" + month + "/" + day;	//还款日期
//		System.out.println(payDate);
		
		
//		Calendar c = Calendar.getInstance();
//		Date date = new Date();
//    	c.setTime(date);
//		System.out.println(c.get(Calendar.YEAR));
//		System.out.println(c.get(Calendar.MONTH)+1);
//		System.out.println(c.get(Calendar.DATE));
//
//		System.out.println(date.getYear() + 1900);
		
	}
	
}