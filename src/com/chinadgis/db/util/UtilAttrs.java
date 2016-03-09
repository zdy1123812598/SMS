package com.chinadgis.db.util;

import java.io.FileReader;
import java.util.Properties;

public class UtilAttrs {
	private static Properties attrs;

	public static Properties getProperties() {
		try {
			if (attrs == null) {
				attrs = new Properties();
				attrs.load(new FileReader("assets/config/attrsconf.properties"));
			}
			return attrs;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

}
