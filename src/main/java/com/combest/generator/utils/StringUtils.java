package com.combest.generator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author lhb
* @date 创建时间：2020年10月31日 上午9:39:07
* @Description 
*/
public class StringUtils {
	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。
	 * 例如：HELLO_WORLD->HelloWorld
	 * 
	 * @param name 转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String lineToHump(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将首字母小写
			return name.substring(0, 1).toLowerCase() + name.substring(1);
		}
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 处理真正的驼峰片段
			if (result.length() == 0) {
				// 第一个驼峰片段，全部字母都小写
				result.append(camel.toLowerCase());
			} else {
				// 其他的驼峰片段，首字母大写
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

	/**
	 * 驼峰转下划线
	 * 
	 * @param name
	 * @return
	 */
	public static String humpToLine2(String name) {
		Pattern humpPattern = Pattern.compile("[A-Z]");
		Matcher matcher = humpPattern.matcher(name);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	public static String takeTableAlias(String tableName) {
		StringBuilder tableAlias = new StringBuilder();
		String[] strings = tableName.split("_");
		for (String str : strings) {
			tableAlias.append(str.subSequence(0, 1).toString());
		}
		
		return tableAlias.toString().toLowerCase();
	}
	
	public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }
	
	public static boolean isEmpty(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
