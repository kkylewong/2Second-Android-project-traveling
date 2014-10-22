package com.widgets;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCalculator {

//	private String nowdate, delay;
	public DateCalculator() {
		//String nowdate, String delay
//		this.nowdate=nowdate;
//		this.delay=delay;
	}


	/**
	* 将短时间格式字符串转换为时间 yyyy-MM-dd
	*
	* @param strDate
	* @return
	*/
	public static Date strToDate(String strDate) {
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	ParsePosition pos = new ParsePosition(0);
	Date strtodate = formatter.parse(strDate, pos);
	return strtodate;
	}
	
	
	/**
	* 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	*/
	public static String getNextFewDays(String nowdate, int delay) {
		try{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String mdate = "";
		Date d = strToDate(nowdate);
		long myTime = (d.getTime() / 1000) + delay * 24 * 60 * 60;
		d.setTime(myTime * 1000);
		mdate = format.format(d);
		return mdate;
		}catch(Exception e){
		return "";
		}
		}
}
