package com.chinadgis.db.util;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.chinadgis.util.EncryptUtils;

public class UtilDbForOracle {
	private static Properties p;
	public static Connection getConnection(){
		try{
			
			if(p==null)
			{	p = new Properties();			
				p.load(new FileReader("assets/config/dbconf.properties"));
			}			
			String password = p.getProperty("jdbc.password");
			String url = p.getProperty("jdbc.url");
			String username = p.getProperty("jdbc.username");
			String driverClassName = p.getProperty("jdbc.driverClassName");
			Class.forName(driverClassName).newInstance();
			String encrypted = p.getProperty("password.encrypted");
			password = Boolean.valueOf(encrypted)? EncryptUtils.encryptToDES(password):password;
			Connection conn = DriverManager.getConnection(url, username, password);
//			System.out.println(conn + "------return conn");
			return conn;
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}
}