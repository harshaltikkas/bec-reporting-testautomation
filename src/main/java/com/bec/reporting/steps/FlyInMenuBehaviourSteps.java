package com.bec.reporting.steps;


import java.util.Iterator;
import java.util.Properties;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import com.bec.reporting.pageobjects.HomePage;
import com.bec.reporting.utils.Driver;
import com.bec.reporting.utils.FileRead;
import com.bec.reporting.utils.IWait;
import com.google.common.base.Verify;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;

//@Slf4j
public class FlyInMenuBehaviourSteps {
	public static Properties prop;
	HomePage homePage = PageFactory.initElements(Driver.webdriver, HomePage.class);
	@Given("^User is on sso portal's home page$")
	public void user_is_on_sso_portal_s_home_page() throws Throwable {
		//log.info("User is on sso portal's home page");
		try {
			prop=FileRead.readProperties();
			//HomePage homePage = PageFactory.initElements(Driver.webdriver, HomePage.class);
			Driver.launch_browser(prop.getProperty("portalUrl"));
			IWait.explicit_wait(Driver.webdriver, homePage.studentIcon);
			Assert.assertTrue("User is on SSO portal's home screen", homePage.studentIcon.isDisplayed());
		} catch (Exception e) {
	//		log.error(e.getMessage());
			System.out.println(e.getMessage());
		}
	}
/**Scenario 1*/
	@When("^User Click on open arrows of \"([^\"]*)\" tab within the Universal Selector Tab$")
	public void user_Click_on_open_arrows_of_tab_within_the_Universal_Selector_Tab(String tabName) throws Throwable {
		try {
			Verify.verify(homePage.openarrow.isDisplayed());
			Driver.webdriver.findElement(By.xpath("//span[@class='menu-name' and contains(text(),'"+tabName+"')]")).click();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Then("^User should be able to open the slider for the \"([^\"]*)\" Tab and able to see the close arrows$")
	public void user_should_be_able_to_open_the_slider_for_the_Tab_with_and_able_to_see_the_close_arrows(String tabName) throws Throwable {

		try {
			if(tabName.equals("Roster")) {
				IWait.explicit_wait(Driver.webdriver, homePage.schoolTitleOnSliderMenu);
				Verify.verify(homePage.schoolTitleOnSliderMenu.isDisplayed());
			}
			else if(tabName.equals("Test")) {
				IWait.explicit_wait(Driver.webdriver, homePage.searchOnSliderMenu);
				Verify.verify(homePage.searchOnSliderMenu.isDisplayed());
			}
			else {
				IWait.explicit_wait(Driver.webdriver, homePage.districtNameOnSliderMenu);
				Verify.verify(homePage.districtNameOnSliderMenu.isDisplayed());
			}
			Assert.assertTrue(homePage.closearrow.isDisplayed());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			Assert.assertFalse(homePage.closearrow.isDisplayed());
		}
	}

	@When("^User Click on close arrows for \"([^\"]*)\" tab within the Universal Selector Tab$")
	public void user_Click_on_close_arrows_for_tab_within_the_Universal_Selector_Tab(String tabName) throws Throwable {

		try {
			IWait.implicit_wait(2);
			homePage.closearrow.click();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Then("^User should be able to close the slider for the \"([^\"]*)\" Tab and able to see the open arrows$")
	public void user_should_be_able_to_close_the_slider_for_the_Tab_and_able_to_see_the_open_arrows(String tabName) throws Throwable {
		try {
			IWait.implicit_wait(2);
			Assert.assertTrue(homePage.openarrow.isDisplayed());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			Assert.assertFalse(homePage.openarrow.isDisplayed());
		}
	}

	/*Scenario 2**/
	@When("^User Click on Roster tab within the Universal Selector Tab$")
	public void user_Click_on_Roster_tab_within_the_Universal_Selector_Tab() throws Throwable {
		try {
			homePage.rostertab.click();
			IWait.explicit_wait(Driver.webdriver, homePage.schoolTitleOnSliderMenu);
			Verify.verify(homePage.schoolTitleOnSliderMenu.isDisplayed());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

	@Then("^User should be able to select School \"([^\"]*)\" and Class \"([^\"]*)\" and Student \"([^\"]*)\" drop downlist and click on apply filter button$")
	public void user_should_be_able_to_select_School_and_Class_and_Student_drop_downlist_and_click_on_apply_filter_button(String school, String classNm, String student) throws Throwable {
		try {
			//select school
			homePage.schooldropdownbtn.click();
			WebElement selectSchool=Driver.webdriver.findElement(By.xpath("//div[@class='menu-title' and contains(text(),'School')]/following-sibling::div//div[@class='menu-dropdown-list-inr']/ul//li[contains(text(),'"+school+"')]"));
			IWait.explicit_wait(Driver.webdriver, selectSchool);
			selectSchool.click();
			String schoolName=homePage.schooldropdownbtn.getText();
			Thread.sleep(1000);
			//select class
			homePage.classdropdownbtn.click();
			WebElement selectClass=Driver.webdriver.findElement(By.xpath("//div[@class='menu-title' and contains(text(),'Class')]/following-sibling::div//div[@class='menu-dropdown-list-inr']/ul//li[contains(text(),'"+classNm+"')]"));
			IWait.explicit_wait(Driver.webdriver, selectClass);
			selectClass.click();
			String className=homePage.classdropdownbtn.getText();
			Thread.sleep(1000);
			//select student
			homePage.studentdropdownbtn.click();
			WebElement selectStudent=Driver.webdriver.findElement(By.xpath("//div[@class='menu-title' and contains(text(),'Student')]/following-sibling::div//div[@class='menu-dropdown-list-inr']/ul//li[contains(text(),'"+student+"')]"));
			IWait.explicit_wait(Driver.webdriver, selectStudent);
			selectStudent.click();
			String studentName=homePage.studentdropdownbtn.getText();
			Thread.sleep(1000);
			homePage.rosterapplyfilterbtn.click();
			Thread.sleep(1000);
			//verify class and school and student on context menu by comparing dropdown text and context menu values todo
			Assert.assertEquals(schoolName, homePage.schoolnameoncontextheader.getText().trim());
			Assert.assertEquals(className, homePage.classnameoncontextheader.getText().trim());
			if(!(studentName.equals("All"))) {
				Assert.assertTrue(homePage.studentnameoncontextheader.getText().trim().contains(studentName));
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

	@Then("^User should be able to click on cancel button to close the Roster Tab\\.$")
	public void user_should_be_able_to_click_on_cancel_button_to_close_the_Roster_Tab() throws Throwable {
		try {
			homePage.rostertab.click();
			IWait.explicit_wait(Driver.webdriver, homePage.schoolTitleOnSliderMenu);
			Verify.verify(homePage.schoolTitleOnSliderMenu.isDisplayed());
			Thread.sleep(1000);
			homePage.rostercancelbtn.click();
			Thread.sleep(1000);
			Assert.assertEquals(false, homePage.schoolTitleOnSliderMenu.isDisplayed());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

	@When("^User Click on Test tab within the Universal Selector Tab$")
	public void user_Click_on_Test_tab_within_the_Universal_Selector_Tab() throws Throwable {
		try {
			homePage.testtab.click();
			IWait.explicit_wait(Driver.webdriver, homePage.schoolTitleOnSliderMenu);
			Verify.verify(homePage.searchOnSliderMenu.isDisplayed());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

	@Then("^User should be able to select \"([^\"]*)\" Test and click on apply filter button$")
	public void user_should_be_able_to_select_Test_and_click_on_apply_filter_button(String testType) throws Throwable {
		try {
			int count=0,selectcheckbox=0;
			homePage.allcheckbox.click();
			Thread.sleep(1000);
			switch(testType) {
			case "single":
				count=homePage.testnamecheckboxlist.size();
				selectcheckbox=(int) (Math.random()*count);
				homePage.testnamecheckboxlist.get(selectcheckbox).click();				
				Thread.sleep(1000);
				homePage.testapplyfilterbtn.click();
				Thread.sleep(1000);
				Assert.assertEquals("All (1)", homePage.nooftestoncontextheader.getText().equals("All (1)"));
				break;
			case "multiple":
				break;
			default:
				do {
					
				}
				while(homePage.rightarrowofpaginationontesttab.isEnabled());
				break;
			}
			
			//click on apply filter button
			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

	@Then("^User should be able to click on cancel button to close the Test Tab\\.$")
	public void user_should_be_able_to_click_on_cancel_button_to_close_the_Test_Tab() throws Throwable {
		try {
			homePage.testtab.click();
			IWait.explicit_wait(Driver.webdriver, homePage.searchOnSliderMenu);
			Verify.verify(homePage.searchOnSliderMenu.isDisplayed());
			Thread.sleep(1000);
			homePage.testcancelbtn.click();
			Thread.sleep(1000);
			Assert.assertEquals(false, homePage.searchOnSliderMenu.isDisplayed());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

}
