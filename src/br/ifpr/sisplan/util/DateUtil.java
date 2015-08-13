package br.ifpr.sisplan.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
	public static String DefaultDateFormat = "dd/MM/yyyy";
	public static boolean validateDateFormat(String strDate) {
		if(strDate.isEmpty()) return false;
		Pattern p = Pattern.compile("^[0-3][0-9]/[0-1][0-9]/[0-9][0-9][0-9][0-9]$");
		Matcher m = p.matcher(strDate);
		if(!m.matches()) return false;
		
		String[] arrayDate = strDate.split("/");
		int day = Integer.parseInt(arrayDate[0]);
		int month = Integer.parseInt(arrayDate[1]);
		if(day > 31) return false;
		if(month > 12) return false;
		return true;
	}
	
	public static Date stringToDate(String strDate) {
		Date dt=null;
		try {
			dt = new SimpleDateFormat(DateUtil.DefaultDateFormat).parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dt;
	}
}