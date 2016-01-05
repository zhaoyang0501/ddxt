package com.pzy.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	public static String getEncodeStr(String str){
		if(StringUtils.isBlank(str))
			return "***";
		int b=str.length()/3;
		int c=str.length()*2/3;
		return StringUtils.substring(str, 0, b)+"******"+StringUtils.substring(str, c, str.length());
	}
}
