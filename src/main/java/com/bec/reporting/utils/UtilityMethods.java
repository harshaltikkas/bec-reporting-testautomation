package com.bec.reporting.utils;

//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;


public class UtilityMethods {
	public static void scrollToElement(WebDriver driver,WebElement element) {
		//This will scroll the page till the element is found		
		/*JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);*/
		 Actions builder = new Actions(driver);
         Action mouseOver = builder
                 .moveToElement(element)
                 .build();
         mouseOver.perform();
                 
	}
	
	public static void scrollPageUp(WebDriver driver) {
		 Actions builder = new Actions(driver);
         builder.sendKeys(Keys.PAGE_UP).build().perform();
  }

}
