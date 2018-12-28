package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 日志类
 * @author rencw5
 *
 */
public class LogUtil {
	//定义时间格式
	public static String fmtDay="yyyy-MM-dd HH";
	public static String fmtSS="yyyy-MM-dd HH:mm:ss";
	private static Lock lock = new ReentrantLock();
	/** 打印字符串到日志
	 * @param s
	 */
	public static void log(String s) {
		lock.lock();
		File logFile = getLogFile();
		// true就是在文件里面追加内容
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter(logFile, true);
			pw = new PrintWriter(fw);
			// 日志里面添加打印时间
			pw.write("\r\n"+getStrDate(fmtSS)+"\r\n");
			pw.write(s+"\r\n");
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != pw) {
					pw.close();pw=null;
				}
				if (null != fw) {
					fw.close();fw=null;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logFile=null;
		lock.unlock();
	}

	/**
	 * 把Exception信息打印到日志文件
	 * author rencw5
	 * @param e
	 */
	public static void logException(Exception e) {
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			// 日志文件
			File logFile = getLogFile();
			// true就是在文件里面追加内容
			fw = new FileWriter(logFile, true);
			pw = new PrintWriter(fw);
			// 日志里面添加打印时间
			pw.write("\r\n"+getStrDate(fmtSS)+"\r\n");
			pw.flush();
			// 把异常信息打印到日志文件
			e.printStackTrace(pw);
			// 立即写入
			pw.flush();
			logFile=null;
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (null != pw) {
					pw.close();pw=null;
				}
				if (null != fw) {
					fw.close();fw=null;
				}
				
			} catch (IOException e2) {
				
				e.printStackTrace();
			}
		}
	}
    /**
     * 生成或者获取日志文件
     * @return
     */
	private static File getLogFile() {
		File logFile = null;
		String path = LogUtil.class.getResource("/").getPath().replaceFirst("/", "");
//		String path=LogUtil.class.getResource("/").getPath();
		try {
			path = URLDecoder.decode(path, "utf-8") + "hklog";
			System.out.println("日志路径path:"+path);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		File directory = new File(path);
		//路径
		 if (!directory.exists()) {
			 directory.mkdir();
			  }				
		//文件
		String logName =path+File.separator+getStrDate(fmtDay)+".txt";
		try {
			logFile = new File(logName);
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return logFile;
	}
	/**
     * 获取String类型的当前时间
     * @return
     */
	public static String getStrDate(String dateFormatter){
		SimpleDateFormat df = new SimpleDateFormat(dateFormatter);
		String nowDate=df.format(new Date());
		df=null;
		return nowDate;
	}
}
