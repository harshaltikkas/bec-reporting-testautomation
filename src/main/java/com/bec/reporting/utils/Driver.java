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
import org.junit.Assert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import com.bec.reporting.tests.TestRunner;

//import lombok.extern.slf4j.Slf4j;
//@Slf4j
public class Driver {
	/** This method close the all running instance of browser  */
	private static class BrowserCleanup implements Runnable {
		public void run() {
			System.out.println("Cleaning up the browser");
			try { 
				Driver.webdriver.quit();
			} catch (NullPointerException e) {
				System.out.println("Browser already shut down.");
			}
		}
	}
	
	public static WebDriver webdriver = null;
	public static DesiredCapabilities caps;
	public static boolean crossbrwr=false;
	
	public synchronized static WebDriver getCurrentDriver(String seleniumEnv, String browserName) throws FileNotFoundException, IOException {
		if (webdriver == null) {
			webdriver = createWebdriver(seleniumEnv, browserName);
			webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		return webdriver;
	}
	
	/** This method create the web driver with the property specifies */	
	public static WebDriver createWebdriver(String seleniumEnv, String browserName) throws FileNotFoundException, IOException {
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
	
				CBTConfiguration.sessionId=((RemoteWebDriver) webdriver).getSessionId().toString();
				crossbrwr=true;
			}
						
			try {
				return new RemoteWebDriver(seleniumHub, caps);
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

	private static WebDriver selectLocalBrowser(String browserName) {
		String os = System.getProperty("os.name");
		System.out.println(os);

		switch (browserName) {
		case "firefox":

			if (!os.equalsIgnoreCase("Mac OS X")) {
				File pathBinary = new File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
				System.setProperty("webdriver.gecko.driver", "D:\\geckodriver\\geckodriver.exe");
				// browser = new FirefoxDriver();
				FirefoxBinary firefoxBinary = new FirefoxBinary(pathBinary);
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				webdriver = new FirefoxDriver(firefoxBinary, firefoxProfile);
			} else {
				webdriver = new FirefoxDriver();
				((JavascriptExecutor) webdriver).executeScript("alert('Test')");
				webdriver.switchTo().alert().accept();
			}
			break;
		case "chrome":

			if (!os.equalsIgnoreCase("Mac OS X")) {
				System.setProperty("webdriver.chrome.driver",
						"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
			}
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("--disable-extensions");
			options.addArguments("start-maximized");
			webdriver = new ChromeDriver(options);
			if (os.equalsIgnoreCase("Mac OS X")) {
				((JavascriptExecutor) webdriver).executeScript("alert('Test')");
				webdriver.switchTo().alert().accept();
			}
			break;
		case "ie":

			DesiredCapabilities IEcaps = DesiredCapabilities.internetExplorer();
			IEcaps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			System.setProperty("webdriver.ie.driver", "C://Program Files//Internet Explorer//IEDriverServer.exe");
			webdriver = new InternetExplorerDriver(IEcaps);
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

}
