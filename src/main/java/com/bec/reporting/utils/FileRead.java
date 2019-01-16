package com.bec.reporting.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class FileRead {

	public static FileInputStream readFile(String file) throws FileNotFoundException {
		return new FileInputStream(new File(file));
	}
	
	public static Properties readProperties() throws FileNotFoundException, IOException {
		
		Properties prop = new Properties();
		prop.load(readFile("src/test/resources/configuration.properties"));
		return prop;
	}
	
}
