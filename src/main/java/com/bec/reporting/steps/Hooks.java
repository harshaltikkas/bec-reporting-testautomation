package com.bec.reporting.steps;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.bec.reporting.utils.Driver;
import com.bec.reporting.utils.FileRead;
import com.bec.reporting.utils.Reporter;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
	/******** Log Attribute ********/
	private static Logger log = Logger.getLogger(Hooks.class);
	public static String reportBrowser;
	
	@Before
	public void openBrowser() throws InterruptedException, IOException {
		try {
			log.info(
					"***********************************************************************************************************");
			String browser;
			Properties p = FileRead.readProperties();
			if (System.getProperty("browser") == null) {
				browser = p.getProperty("default_browser_Name");
			} else {
				browser = System.getProperty("browser");
			}
			log.info(" Launch Browser: " + "\"" + browser + "\"" + " on Environment:"
					+ p.getProperty("seleniumEnvironment"));
			Driver.webdriver = Driver.getCurrentDriver(p.getProperty("seleniumEnvironment"), browser);
			reportBrowser = browser;
			Reporter.assignAuthor("BenchMark Universal - Automation Tool");
		} catch (Exception e) {
			log.error("initConfig Error", e);
		}
	}

	@After
	public void after(Scenario scenario) throws FileNotFoundException, IOException {
		if (Driver.crossbrwr) {
			Driver.crossBrowserSetting();
		}
		if (scenario.isFailed()) {
			Driver.embedScreenshot();
		}
	}

}
