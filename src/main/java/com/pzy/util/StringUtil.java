package com.pzy.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	public static String getEncodeStr(String str){
		if(StringUtils.isBlank(str))
			return "***";
		if(str.length()<5)
			return str;
		else
			return StringUtils.substring(str,0, 2)+"******"+StringUtils.substring(str,str.length()-2, str.length());
	}
	
	public static String getEncodeStr4(String str){
		if(StringUtils.isBlank(str))
			return "***";
		if(str.length()<5)
			return str;
		else
			return "******"+StringUtils.substring(str,str.length()-4, str.length());
	}
}
