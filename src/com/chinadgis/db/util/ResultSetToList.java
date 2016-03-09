package com.chinadgis.db.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSetToList {
	/**
	 * 使用说明，类属性名和字段名称要相同（注：不包括空格，不区分大小写。目前setter方法要求一个形参，个人若需要请修改，支持多个参数）
	 * @param rs
	 * @param clazz
	 * @return List type
	 */
	public static List MakeRsToList(ResultSet rs, Class<?> cls) {
		List list = new ArrayList();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int clos = rsmd.getColumnCount();
			Object obj =  cls.newInstance();
			Field[] fileds1 = cls.getDeclaredFields();
			Map<String, String> map = new HashMap<String, String>();
			String colName = "";
			String proName = "";
			for (int i = 1; i <= clos; i++) {
				colName = rsmd.getColumnName(i).replace("_", "").toUpperCase(); // 删除下划线并转换成大写
				for (int j = 0; j < fileds1.length; j++) {
					proName = fileds1[j].getName();
					if (colName.equals(proName.toUpperCase())) {
						map.put(rsmd.getColumnName(i),
								Character.toUpperCase(proName.charAt(0))
										+ proName.substring(1));
						break;
					}
				}
			}
			String temp = "";
			while (rs.next()) {
				obj = cls.newInstance();
				for (int i = 1; i <= clos; i++) {
					temp = rsmd.getColumnName(i);					
					Method method = obj.getClass().getMethod("set" + map.get(temp),Class.forName(rsmd.getColumnClassName(i)));
					method.invoke(obj, rs.getObject(i));
				}
				list.add(obj);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
