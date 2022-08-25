package cn.alicem.generator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 李鸿斌
 * @version 创建时间：2020年9月12日 上午11:00:47
 * @Description 用于将DBeaver工具导出的Insert以及Update语句格式化成mybatis的SQL语句
 */
public class SqlFormatUtil {

	
	/**
	 * 工具类不允许有公有方法
	 */
	private SqlFormatUtil() {}
	
	/**
	 * 格式化Update语句
	 * @param sql
	 * @return
	 */
	public static String formatUpdateById(String sql) {
		/*
		String sql = "UPDATE jwmes.dbo.tbl_integration_logs\r\n" + 
				"SET tp_system_name='', create_time=''\r\n" + 
				"WHERE id=0;";
		*/
		if (sql == null || "".equals(sql)) {
			throw new RuntimeException("转换错误,传入SQL为空");
		}
		
		if (!sql.toUpperCase().contains("UPDATE")) {
			throw new RuntimeException("转换错误,非update语句");
		}
		
		System.out.println("OldSQL:\n"+sql);
		
		sql = sql.replace("\r\n", " "); //去换行
		StringBuilder sbSql = new StringBuilder(sql);
		//截取字段  tp_system_name='', create_time=''
		String fieldStr = sbSql.substring(sbSql.indexOf("SET")+3, sbSql.indexOf("WHERE"));
		fieldStr = fieldStr.replace(" ", ""); //去空格
		
		//拿到前半段sql UPDATE jwmes.dbo.tbl_integration_logs SET
		StringBuilder sb = new StringBuilder(sbSql.substring(0, sbSql.indexOf("SET")+3));
		sb.append(" ");
		
		//将字段分离  tp_system_name=''   create_time=''
		String[] fileds = fieldStr.split(",");
		
		for (int i = 0; i < fileds.length; i++) {
			//二次分离    tp_system_name    ''
			String[] split = fileds[i].split("=");
			if(i == fileds.length - 1) {//最后一个
				sb.append(split[0] + " = "+"#{"+lineToHump(split[0])+"}");
			}else {
				sb.append(split[0] + " = "+"#{"+lineToHump(split[0])+"}"+", ");
			}
		}
		
		//拿到WHERE条件
		String substring = sbSql.substring(sbSql.indexOf("WHERE") + 5, sbSql.length()).trim();
		String[] split = substring.split("=");
		
		//拼上where 主键
		sb.append(" WHERE ");
		sb.append(split[0]+" = "+"#{"+lineToHump(split[0])+"}");
			
		System.out.println("FormatSQL:\n"+sb.toString());			
		return sb.toString();
	}
	
	/**
	 * 格式化Insert语句
	 * @param sql
	 * @return
	 */
	public static String formatInsert(String sql) {
		/*
		String sql = "INSERT INTO jwmes.dbo.tbl_integration_logs" + 
				"(tp_system_name, create_time) " + 
				"VALUES('', '');";
		*/
		if (sql == null || "".equals(sql)) {
			throw new RuntimeException("转换错误,传入SQL为空");
		}
		
		if (!sql.toUpperCase().contains("INSERT")) {
			throw new RuntimeException("转换错误,非insert语句");
		}
		
		System.out.println("OldSQL:\n"+sql);	

		sql = sql.replace("\r\n", " "); //去换行
		//拿到所有字段  tp_system_name, create_time
		String fieldStr = sql.substring(sql.indexOf('(')+1, sql.indexOf(')'));
		
		//去掉空格  tp_system_name,create_time
		fieldStr = fieldStr.replace(" ", ""); //去空格
				
		//将字段分离成单个 
		String[] fields = fieldStr.split(",");
		
		/* 连表查询配置别名
		 * StringBuilder stringBuilder = new StringBuilder(); for (String field :
		 * fields) { stringBuilder.append("ef."); stringBuilder.append(field + " AS " +
		 * field + ",\n"); } System.out.println("-------------");
		 * System.out.println(stringBuilder.toString());
		 * System.out.println("----------------");
		 */
		
		//拿到前半段sql INSERT INTO jwmes.dbo.tbl_integration_logs(tp_system_name, create_time)VALUES
		String before = sql.substring(0,sql.lastIndexOf('('));
		
		StringBuilder sb = new StringBuilder(before);
		sb.append("(");
		
		//拼接字段
		for (int i = 0; i < fields.length; i++) {
			if(i == fields.length - 1) {
				//最後一個
				sb.append("#{"+lineToHump(fields[i])+"}");
			}else {
				sb.append("#{"+lineToHump(fields[i])+"}"+", ");
			}									
		}
		sb.append(")");
		System.out.println("FormatSQL:\n"+sb.toString());		
		
		return sb.toString();
	}
	
	
	
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
	
	public static void main(String[] args) {
		
		String sql = "INSERT INTO jwdevice.dbo.tbl_EquipmentFiles\r\n" + 
				"(EqFi_organizationCode, EqFi_registrarEgName, EqFi_registrar, EqFi_registDate, EqFi_purpose, EqFi_type, EqFi_deviceName, EqFi_deviceCode, EqFi_specificationType, EqFi_departmentNum, EqFi_department, EqFi_workshop, EqFi_productionLine, TuOn_productionLineCode, EqFi_locationOfDeposit, EqFi_dateOfProduction, EqFi_uupplier, EqFi_amountOfMoney, EqFi_actionName, EqFi_actionCode, EqFi_deviceStatus, EqFi_verificationNumber, EqFi_verificationDate, EqFi_installDate, EqFi_acceptanceDate, EqFi_personLiableEgName, EqFi_personLiable, EqFi_technicalAgreement, EqFi_pointInspectionStandard, EqFi_packingList, EqFi_instructionsFiles, EqFi_exitCertificate, EqFi_inspectionRecord, EqFi_openingAcceptanceList, EqFi_electricalDrawings, EqFi_mainAccessoriesList, EqFi_deviceSopFiles, EqFi_deviceVerificationFiles, EqFi_contractNumber, EqFi_procuremenContract, EqFi_remarks, EqFi_remindDay, EqFi_nextCheckTime, EqFi_checkCycle, EqFi_checkType, EqFi_checkTime, createTime, trace, auditorUserName, auditorEgName, currentNodeNum, status, currentNodeName, whether_import, runningStatus, EqFi_nature, EqFi_assetCoding, EqFi_workingHours, EqFi_personTel, theoreticalProcessingCycle)\r\n" + 
				"VALUES('', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 0, '', 0, '', '', getdate(), '', '', '', 0, '', '', 0, '', '', '', 0, '', 0);\r\n" + 
				"";

//		AbstractEndpoint<S, U>
		formatInsert(sql);
		
		

		
		
	}
}
