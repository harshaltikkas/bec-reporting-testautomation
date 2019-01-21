package com.bec.reporting.steps;

import java.io.IOException;
import java.util.Properties;
import com.bec.reporting.utils.Driver;
import com.bec.reporting.utils.FileRead;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
	
	@Before
	public void openBrowser() throws InterruptedException, IOException {
		String browser;
		Properties p=FileRead.readProperties();
		if (System.getProperty("browser") == null) {
			browser = p.getProperty("default_browser_Name");
		} else {
			browser = System.getProperty("browser");
		}		
	//	log.info(" Launch Browser: " + browser+" on Environment:"+p.getProperty("seleniumEnvironment"));
		System.out.println(" Launch Browser: " + browser+" on Environment:"+p.getProperty("seleniumEnvironment"));
		Driver.webdriver = Driver.getCurrentDriver(p.getProperty("seleniumEnvironment"), browser);
	}

	@After
	public void after(Scenario scenario) {

		if (scenario.isFailed()) {
			Driver.embedScreenshot();
		}
	}

}
