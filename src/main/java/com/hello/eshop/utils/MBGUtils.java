package com.hello.eshop.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * 逆向工程的方法
 * @author Hello
 *
 */
public class MBGUtils {
	
	public static void generator(){
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File("msg.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		try {
			Configuration config = cp.parseConfiguration(configFile);
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
}
