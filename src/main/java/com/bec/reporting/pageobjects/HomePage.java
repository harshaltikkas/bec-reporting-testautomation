package com.bec.reporting.pageobjects;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {
	
	/*Universal Selector Elements*/
	@FindBy(xpath="//i[contains(text(),'chevron_right')]/ancestor::span[@class='float-right']")
	public WebElement openarrow;
	
	@FindBy(xpath="//i[contains(text(),'chevron_left')]/ancestor::span[@class='float-right']")
	public WebElement closearrow;
	
	@FindBy(xpath="//li[contains(text(),'Overview')]")
	public WebElement overviewtext;
	
	@FindBy(xpath="//div[@class='menu-title' and contains(text(),'School')]")
	public WebElement schoolTitleOnSliderMenu;
	
	@FindBy(xpath="//div[@class='menu-title' and contains(text(),'District Term')]")
	public WebElement districtNameOnSliderMenu;
	
	@FindBy(xpath="//div[@class='universal-test-search']//span//div//input[@placeholder='Search']")
	public WebElement searchOnSliderMenu;
	
	@FindBy(xpath="//span[@class='menu-name' and contains(text(),'Roster')]")
	public WebElement rostertab;
	
	@FindBy(xpath="//span[@class='menu-name' and contains(text(),'Test')]")
	public WebElement testtab;
	
	@FindBy(xpath="//span[@class='menu-name' and contains(text(),'Date')]")
	public WebElement datetab;

	@FindBy(xpath="//div[@class='menu-title' and contains(text(),'School')]/following-sibling::div/button")
	public WebElement schooldropdownbtn;
	
	@FindBy(xpath="//div[@class='menu-title' and contains(text(),'Class')]/following-sibling::div/button")
	public WebElement classdropdownbtn;
	
	@FindBy(xpath="//div[@class='menu-title' and contains(text(),'Student')]/following-sibling::div/button")
	public WebElement studentdropdownbtn;
	
	@FindBy(xpath="//div[@class='menu-title' and contains(text(),'District Term')]/following-sibling::div/button")
	public WebElement districttermdropdownbtn;
	
	@FindBy(xpath="//span[@class='menu-name' and contains(text(),'Roster')]/ancestor::div[@class='menu-item']/following-sibling::div//button[contains(text(),'Apply Filters')]")
	public WebElement rosterapplyfilterbtn;
	
	@FindBy(xpath="//span[@class='menu-name' and contains(text(),'Test')]/ancestor::div[@class='menu-item']/following-sibling::div//button[contains(text(),'Apply Filters')]")
	public WebElement testapplyfilterbtn;
	
	@FindBy(xpath="//span[@class='menu-name' and contains(text(),'Date')]/ancestor::div[@class='menu-item']/following-sibling::div//button[contains(text(),'Apply Filters')]")
	public WebElement dateapplyfilterbtn;

	@FindBy(xpath="//span[@class='menu-name' and contains(text(),'Roster')]/ancestor::div[@class='menu-item']/following-sibling::div//div[contains(text(),'Cancel')]")
	public WebElement rostercancelbtn;
	
	@FindBy(xpath="//span[@class='menu-name' and contains(text(),'Test')]/ancestor::div[@class='menu-item']/following-sibling::div//div[contains(text(),'Cancel')]")
	public WebElement testcancelbtn;
	
	@FindBy(xpath="//span[@class='menu-name' and contains(text(),'Date')]/ancestor::div[@class='menu-item']/following-sibling::div//div[contains(text(),'Cancel')]")
	public WebElement datecancelbtn;
	
	@FindBy(xpath="//label[@title='Select All Tests']")
	public WebElement allcheckbox;
	
	@FindBy(xpath="//div[@class='input-checkbox checkbox-lightBlue']")
	public List<WebElement> testnamecheckboxlist;
	
	@FindBy(xpath="//div[@class='menu-title' and contains(text(),'District Term')]/following-sibling::div//ul/li")
	public List<WebElement> districttermlist;
	
	@FindBy(xpath="//span[@class='scroll-right scroll-active float-right']/i")
	public WebElement rightarrowofpaginationontesttab;
	
	@FindBy(xpath="//div[@class='menu-title' and contains(text(),'School')]/following-sibling::div//div[@class='menu-dropdown-list-inr']/ul//li")
	public List<WebElement> schoollist;
	
	@FindBy(xpath="//div[@class='menu-title' and contains(text(),'Class')]/following-sibling::div//div[@class='menu-dropdown-list-inr']/ul//li")
	public List<WebElement> classlist;
	
	@FindBy(xpath="//div[@class='menu-title' and contains(text(),'Student')]/following-sibling::div//div[@class='menu-dropdown-list-inr']/ul//li")
	public List<WebElement> studentlist;

	/*Context Header Elements*/
	@FindBy(xpath="//div[@class='context-header-main']//span[contains(text(),'School:')]/following-sibling::span")
	public WebElement schoolnameoncontextheader;
	
	@FindBy(xpath="//div[@class='context-header-main']//span[contains(text(),'Class:')]/following-sibling::span")
	public WebElement classnameoncontextheader;

	@FindBy(xpath="//div[@class='context-header-main']//span[contains(text(),'Student:')]/following-sibling::span")
	public WebElement studentnameoncontextheader;
	
	@FindBy(xpath="//div[@class='context-header-main']//span[contains(text(),'Tests:')]/following-sibling::span")
	public WebElement nooftestoncontextheader;
	
	@FindBy(xpath="//div[@class='context-header-main']//span[contains(text(),'Dates:')]/following-sibling::span")
	public WebElement datesoncontextheader;
	
	/*Footer Elements*/
	@FindBy(xpath="//div[@class='footer-key-btn']/span/i")
	public WebElement footerkey;
	

}
