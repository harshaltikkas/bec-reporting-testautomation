package com.bec.reporting.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import com.bec.reporting.steps.Hooks;
import com.bec.reporting.tests.TestRunner;

public class Driver {
	 /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(Driver.class);
	static String resourceFolder="resources/files/software/";
	public static RemoteWebDriver webdriver = null;
	public static DesiredCapabilities caps;
	public static boolean crossbrwr=false;
	
	/** This method close the all running instance of browser  */
	private static class BrowserCleanup implements Runnable {
		public void run() {
			System.out.println("Cleaning up the browser");
			try { 
				Driver.webdriver.quit();
				//Runtime.getRuntime().exec("C:\\AutoIt scripts\\closeallbrowsers.exe"); 
				//OR
				Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
			} catch (NullPointerException e) {
				log.error("Browser already shut down.");
			}
			catch(Exception e) {
				log.error(e.getMessage()+"\n Problem Occurs to close browsers");
			}
		}
	}
	
	public synchronized static RemoteWebDriver getCurrentDriver(String seleniumEnv, String browserName) throws FileNotFoundException, IOException {
		if (webdriver == null) {
			webdriver = createWebdriver(seleniumEnv, browserName);
			webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		return webdriver;
	}
	
	/** This method create the web driver with the property specifies */	
	public static RemoteWebDriver createWebdriver(String seleniumEnv, String browserName) throws FileNotFoundException, IOException {
		Properties p =FileRead.readProperties();
		URL seleniumHub = null;
		caps = new DesiredCapabilities();
		if (seleniumEnv.equals("local")) {
			Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
			return selectLocalBrowser(browserName);
		} else {

			if (seleniumEnv.equals("browserstack")) {
				seleniumHub= new URL("https://" + p.getProperty("BROWSERSTACK_USERNAME") + ":"
						+ p.getProperty("BROWSERSTACK_ACCESS_KEY") + "@hub-cloud.browserstack.com/wd/hub");
				
				String plateForm= p.getProperty(browserName + ".platform");
				caps.setCapability("browser", p.getProperty(browserName + ".browserName"));
				caps.setCapability("browser_version", p.getProperty(browserName + ".version"));
				caps.setCapability("os", plateForm.substring(0, plateForm.indexOf(" ")));
				caps.setCapability("os_version",plateForm.substring(plateForm.indexOf(" ")+1));
				caps.setCapability("resolution", p.getProperty(browserName + ".screenResolution"));
				webdriver =  new RemoteWebDriver(seleniumHub, caps);
			}
			else if (seleniumEnv.equals("crossbrowser")) {
				CBTConfiguration cbt = new CBTConfiguration(p.getProperty("CROSSBROWSER_USERNAME"),p.getProperty("CROSSBROWSER_AUTH_KEY"));
				seleniumHub=cbt.getHubUrl();
				caps.setCapability("name", p.getProperty("name"));
				caps.setCapability("browserName", p.getProperty(browserName + ".browserName"));
				caps.setCapability("version", p.getProperty(browserName + ".version")); 
				caps.setCapability("platform", p.getProperty(browserName + ".platform"));
				caps.setCapability("screenResolution", p.getProperty(browserName + ".screenResolution"));
				caps.setCapability("record_video", p.getProperty("record_video"));
				caps.setCapability("record_network", p.getProperty("record_network"));
				caps.setCapability("build", p.getProperty("build"));
	
				webdriver =  new RemoteWebDriver(seleniumHub, caps);
				CBTConfiguration.sessionId=webdriver.getSessionId().toString();
				crossbrwr=true;
			}
						
			try {
				return webdriver;
			} catch (WebDriverException e) {
				Driver.writeToReport("WebDriverException: " + e.getMessage());
				Assert.fail(e.getMessage());
			}
			catch (Exception e) {
				Driver.writeToReport(e.getMessage());
			} 
			finally {
				Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
			}
		}
		return null;
	}

	private static RemoteWebDriver selectLocalBrowser(String browserName) {
		String os = System.getProperty("os.name");
		System.out.println(os);

		switch (browserName) {
		case "firefox":

			if (os.contains("Windows")) {
				File pathBinary = new File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
				System.setProperty("webdriver.gecko.driver", "D:\\geckodriver\\geckodriver.exe");
				FirefoxBinary firefoxBinary = new FirefoxBinary(pathBinary);
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				webdriver = new FirefoxDriver(firefoxBinary, firefoxProfile);
				
			} else {
				System.setProperty("webdriver.gecko.driver", resourceFolder+os+"/geckodriver");
				webdriver = new FirefoxDriver();
			}
			break;
		case "chrome":

			if (os.contains("Windows")) {
				System.setProperty("webdriver.chrome.driver",
						"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
			}
			else {
				 System.setProperty("webdriver.chrome.driver", resourceFolder+os+"/chromedriver");
			}
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("--disable-extensions");
			options.addArguments("start-maximized");
			webdriver = new ChromeDriver(options);
			break;
		case "ie":

			DesiredCapabilities IEcaps = DesiredCapabilities.internetExplorer();
			IEcaps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			System.setProperty("webdriver.ie.driver", "C://Program Files//Internet Explorer//IEDriverServer.exe");
			webdriver = new InternetExplorerDriver(IEcaps);
/*			 System.setProperty("webdriver.ie.driver", "C://Program Files//Internet Explorer//IEDriverServer.exe");
			 webdriver = new InternetExplorerDriver();*/
			break;
		case "opera":
			webdriver=new OperaDriver();
			break;
		case "safari":
			webdriver = new SafariDriver();
			break;
		default:
			throw new WebDriverException("No browser specified");
		}
		return webdriver;
	}

	public static boolean launch_browser(String url) {
		try {
			webdriver.get(url);
			String os = System.getProperty("os.name");
			if (os.equalsIgnoreCase("Mac OS X")) {
				webdriver.manage().window().setPosition(new Point(0, 0));
				webdriver.manage().window().setSize(new Dimension(1440, 900));
				webdriver.switchTo().window(webdriver.getWindowHandle());
			} else {
				webdriver.manage().window().maximize();
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void close_browser() {
		webdriver.close();
	}

	public static void quit_browser() {
		webdriver.quit();
	}

	public static String takeScreenshot(String filename) throws IOException {

		try {
			File file = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);
			String filePath = ("./screenshot/" + filename + ".jpg");
			FileUtils.copyFile(file, new File(filePath));
			return filePath;
		} catch (WebDriverException somePlatformsDontSupportScreenshots) {
			System.err.println(somePlatformsDontSupportScreenshots.getMessage());
			return null;
		}
	}

	public static void embedScreenshot() {
		try {
			byte[] screenshot = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.BYTES);
			TestRunner.scenario.embed(screenshot, "image/png");
		} catch (WebDriverException somePlatformsDontSupportScreenshots) {
			System.err.println(somePlatformsDontSupportScreenshots.getMessage());
		}
	}

	public static void writeToReport(String string) {
		TestRunner.scenario.write(string);
	}

	public static void crossBrowserSetting() throws FileNotFoundException, IOException {
		Properties p =FileRead.readProperties();
		String username = p.getProperty("CROSSBROWSER_USERNAME");
		String authkey = p.getProperty("CROSSBROWSER_AUTH_KEY");
		CBTConfiguration cbt = new CBTConfiguration(username, authkey);
		cbt.takeSnapshot();
		cbt.setScore(CBTConfiguration.score);
		//Extent-Report
		Reporter.loadXMLConfig(new File(FileRead.getReportConfigPath()));
		Reporter.setSystemInfo("Project Name", 	 p.getProperty("name"));
		Reporter.setSystemInfo("Project Build",  p.getProperty("build"));
		Reporter.setSystemInfo("Plateform", 	 p.getProperty(Hooks.reportBrowser + ".platform"));
		Reporter.setSystemInfo("Browser", 	     p.getProperty(Hooks.reportBrowser + ".browserName") + " " + p.getProperty(Hooks.reportBrowser + ".version"));
		Reporter.setSystemInfo("Screen-Resolution", p.getProperty(Hooks.reportBrowser + ".screenResolution"));

	}

}
