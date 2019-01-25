package com.bec.reporting.steps;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import com.bec.reporting.pageobjects.HomePage;
import com.bec.reporting.utils.CBTConfiguration;
import com.bec.reporting.utils.Driver;
import com.bec.reporting.utils.FileRead;
import com.bec.reporting.utils.IWait;
import com.bec.reporting.utils.UtilityMethods;
import com.google.common.base.Verify;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class FlyInMenuBehaviourSteps {
	 /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(FlyInMenuBehaviourSteps.class);
	public static Properties prop;
	public static String toolTipText="";
	HomePage homePage = PageFactory.initElements(Driver.webdriver, HomePage.class);
	@Given("^User is on sso portal's home page$")
	public void user_is_on_sso_portal_s_home_page() throws Throwable {
		log.info("User is on sso portal's home page");
		try {
			prop = FileRead.readProperties();
			Driver.launch_browser(prop.getProperty("portalUrl"));
			IWait.explicit_wait(Driver.webdriver, homePage.overviewtext);
			Assert.assertTrue("User is on SSO portal's home screen", homePage.overviewtext.isDisplayed());
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}
	/**Scenario 1*/
	@When("^User Click on open arrows of \"([^\"]*)\" tab within the Universal Selector Tab$")
	public void user_Click_on_open_arrows_of_tab_within_the_Universal_Selector_Tab(String tabName) throws Throwable {
		log.info("executing Scenario 1");
		try {
			Verify.verify(homePage.openarrow.isDisplayed());
			Driver.webdriver.findElement(By.xpath("//span[@class='menu-name' and contains(text(),'" + tabName + "')]"))
					.click();
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}

	@Then("^User should be able to open the slider for the \"([^\"]*)\" Tab and able to see the close arrows$")
	public void user_should_be_able_to_open_the_slider_for_the_Tab_with_and_able_to_see_the_close_arrows(String tabName) throws Throwable {
		try {
			if (tabName.equals("Roster")) {
				IWait.explicit_wait(Driver.webdriver, homePage.schoolTitleOnSliderMenu);
				Verify.verify(homePage.schoolTitleOnSliderMenu.isDisplayed());
			} else if (tabName.equals("Test")) {
				IWait.explicit_wait(Driver.webdriver, homePage.searchOnSliderMenu);
				Verify.verify(homePage.searchOnSliderMenu.isDisplayed());
			} else {
				IWait.explicit_wait(Driver.webdriver, homePage.districtNameOnSliderMenu);
				Verify.verify(homePage.districtNameOnSliderMenu.isDisplayed());
			}
			Assert.assertTrue(homePage.closearrow.isDisplayed());
			CBTConfiguration.score = "pass";
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}

	@When("^User Click on close arrows for \"([^\"]*)\" tab within the Universal Selector Tab$")
	public void user_Click_on_close_arrows_for_tab_within_the_Universal_Selector_Tab(String tabName) throws Throwable {

		try {
			IWait.implicit_wait(2);
			homePage.closearrow.click();
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}

	@Then("^User should be able to close the slider for the \"([^\"]*)\" Tab and able to see the open arrows$")
	public void user_should_be_able_to_close_the_slider_for_the_Tab_and_able_to_see_the_open_arrows(String tabName) throws Throwable {
		try {
			IWait.implicit_wait(2);
			Assert.assertTrue(homePage.openarrow.isDisplayed());
			CBTConfiguration.score = "pass";
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}

	/**Scenario 2*/
	@When("^User Click on Roster tab within the Universal Selector Tab$")
	public void user_Click_on_Roster_tab_within_the_Universal_Selector_Tab() throws Throwable {
		log.info("executing Scenario 2");
		try {
			homePage.rostertab.click();
			IWait.explicit_wait(Driver.webdriver, homePage.schoolTitleOnSliderMenu);
			Verify.verify(homePage.schoolTitleOnSliderMenu.isDisplayed());
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}

	@Then("^User should be able to select School \"([^\"]*)\" and Class \"([^\"]*)\" and Student \"([^\"]*)\" drop downlist and click on apply filter button$")
	public void user_should_be_able_to_select_School_and_Class_and_Student_drop_downlist_and_click_on_apply_filter_button(String school, String classNm, String student) throws Throwable {
		try {
			// selecting school from dropdown
			homePage.schooldropdownbtn.click();
			WebElement selectSchool = Driver.webdriver.findElement(By.xpath(
					"//div[@class='menu-title' and contains(text(),'School')]/following-sibling::div//div[@class='menu-dropdown-list-inr']/ul//li[contains(text(),'"
							+ school + "')]"));
			IWait.explicit_wait(Driver.webdriver, selectSchool);
			selectSchool.click();
			String schoolName = homePage.schooldropdownbtn.getText();
			Thread.sleep(500);
			// selecting class from dropdown
			homePage.classdropdownbtn.click();
			WebElement selectClass = Driver.webdriver.findElement(By.xpath(
					"//div[@class='menu-title' and contains(text(),'Class')]/following-sibling::div//div[@class='menu-dropdown-list-inr']/ul//li[contains(text(),'"
							+ classNm + "')]"));
			IWait.explicit_wait(Driver.webdriver, selectClass);
			selectClass.click();
			String className = homePage.classdropdownbtn.getText();
			Thread.sleep(500);
			// selecting student from dropdown
			homePage.studentdropdownbtn.click();
			WebElement selectStudent = Driver.webdriver.findElement(By.xpath(
					"//div[@class='menu-title' and contains(text(),'Student')]/following-sibling::div//div[@class='menu-dropdown-list-inr']/ul//li[contains(text(),'"
							+ student + "')]"));
			IWait.explicit_wait(Driver.webdriver, selectStudent);
			selectStudent.click();
			String studentName = homePage.studentdropdownbtn.getText();
			Thread.sleep(500);
			homePage.rosterapplyfilterbtn.click();
			Thread.sleep(500);
			/*
			 * verifying class and school and student on context menu by comparing dropdown
			 * text and context menu values
			 */
			Assert.assertEquals(schoolName, homePage.schoolnameoncontextheader.getText().trim());
			Assert.assertEquals(className, homePage.classnameoncontextheader.getText().trim());
			if (!(studentName.equals("All"))) {
				Assert.assertTrue(homePage.studentnameoncontextheader.getText().trim().contains(studentName));
			}
			CBTConfiguration.score = "pass";
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}

	@Then("^User should be able to click on cancel button to close the Roster Tab\\.$")
	public void user_should_be_able_to_click_on_cancel_button_to_close_the_Roster_Tab() throws Throwable {
		try {
			homePage.rostertab.click();
			IWait.explicit_wait(Driver.webdriver, homePage.schoolTitleOnSliderMenu);
			Verify.verify(homePage.schoolTitleOnSliderMenu.isDisplayed());
			Thread.sleep(500);
			homePage.rostercancelbtn.click();
			Thread.sleep(500);
			Assert.assertEquals(false, homePage.schoolTitleOnSliderMenu.isDisplayed());
			CBTConfiguration.score = "pass";
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}

	/**Scenario 3*/
	@When("^User Click on Test tab within the Universal Selector Tab$")
	public void user_Click_on_Test_tab_within_the_Universal_Selector_Tab() throws Throwable {
		log.info("executing Scenario 3");
		try {
			homePage.testtab.click();
			IWait.explicit_wait(Driver.webdriver, homePage.searchOnSliderMenu);
			Verify.verify(homePage.searchOnSliderMenu.isDisplayed());
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}

	@Then("^User should be able to select \"([^\"]*)\" Test and click on apply filter button$")
	public void user_should_be_able_to_select_Test_and_click_on_apply_filter_button(String testType) throws Throwable {
		try {
			int count = 0, selectcheckbox = 0;
			count = homePage.testnamecheckboxlist.size();
			selectcheckbox = (int) (Math.random() * count);
			homePage.allcheckbox.click();
			Thread.sleep(500);
			switch (testType) {
			case "single":
				homePage.testnamecheckboxlist.get(selectcheckbox).click();
				Thread.sleep(500);
				homePage.testapplyfilterbtn.click();
				Thread.sleep(500);
				UtilityMethods.scrollPageUp(Driver.webdriver);
				Thread.sleep(500);
				Assert.assertEquals(true, homePage.nooftestoncontextheader.getText().equals("All (1)"));
				break;
			case "multiple":
				for (int i = 0; i < count; i = i + 2) {
					homePage.testnamecheckboxlist.get(i).click();
					Thread.sleep(500);
				}
				homePage.testapplyfilterbtn.click();
				Thread.sleep(500);
				UtilityMethods.scrollPageUp(Driver.webdriver);
				Thread.sleep(500);
				Assert.assertEquals(true, homePage.nooftestoncontextheader.getText().equals("All (5)"));
				break;
			default:
				do {
					/*
					 * logic: fetch total count of each page , iterate each checkbox till all
					 * checkboxes on all pages and verify whether all checkbox is checked or not
					 */
					count = homePage.testnamecheckboxlist.size();

				} while (homePage.rightarrowofpaginationontesttab.isEnabled());
				break;
			}
			CBTConfiguration.score = "pass";
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}

	@Then("^User should be able to click on cancel button to close the Test Tab\\.$")
	public void user_should_be_able_to_click_on_cancel_button_to_close_the_Test_Tab() throws Throwable {
		try {
			homePage.testtab.click();
			IWait.explicit_wait(Driver.webdriver, homePage.searchOnSliderMenu);
			Verify.verify(homePage.searchOnSliderMenu.isDisplayed());
			homePage.testcancelbtn.click();
			Thread.sleep(500);
			UtilityMethods.scrollPageUp(Driver.webdriver);
			Assert.assertEquals(false, homePage.searchOnSliderMenu.isDisplayed());
			CBTConfiguration.score = "pass";
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}
	
	/**Scenario 4*/
	@When("^User Click on Date tab within the Universal Selector Tab$")
	public void user_Click_on_Date_tab_within_the_Universal_Selector_Tab() throws Throwable {
		log.info("executing Scenario 4");
		try {
			homePage.datetab.click();
			IWait.explicit_wait(Driver.webdriver, homePage.districtNameOnSliderMenu);
			Verify.verify(homePage.districtNameOnSliderMenu.isDisplayed());
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}

	@Then("^User should be able to select district term and click on apply filter button$")
	public void user_should_be_able_to_select_district_term_and_click_on_apply_filter_button() throws Throwable {
		try {
			// selecting Random District Term from the list
			homePage.districttermdropdownbtn.click();
			int count = 0, selectdistrictterm = 0;
			count = homePage.districttermlist.size();
			selectdistrictterm = (int) (Math.random() * count);
			homePage.districttermlist.get(selectdistrictterm).click();
			Thread.sleep(500);
			String districtterm = homePage.districttermdropdownbtn.getText();
			Thread.sleep(500);
			homePage.dateapplyfilterbtn.click();
			Thread.sleep(500);
			/*
			 * Verifying District Term on context menu by comparing dropdown text and
			 * context menu values
			 */
			Assert.assertEquals(districtterm, homePage.datesoncontextheader.getText().trim());
			CBTConfiguration.score = "pass";
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}

	@Then("^User should be able to click on cancel button to close the Date Tab\\.$")
	public void user_should_be_able_to_click_on_cancel_button_to_close_the_Date_Tab() throws Throwable {
		try {
			homePage.datetab.click();
			IWait.explicit_wait(Driver.webdriver, homePage.districtNameOnSliderMenu);
			Verify.verify(homePage.districtNameOnSliderMenu.isDisplayed());
			homePage.datecancelbtn.click();
			Thread.sleep(500);
			Assert.assertEquals(false, homePage.districtNameOnSliderMenu.isDisplayed());
			CBTConfiguration.score = "pass";
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}

	/**Scenario 5*/	
	@When("^User mousehover on \"([^\"]*)\" tab within the Universal Selector Tab$")
	public void user_mousehover_on_tab_within_the_Universal_Selector_Tab(String tabName) throws Throwable {
		log.info("executing Scenario 5");
		try {
			WebElement el = Driver.webdriver.findElement(By.xpath("//span[@class='menu-name' and contains(text(),'"
					+ tabName + "')]/ancestor::div[@class='menu-item']"));
			Actions builder = new Actions(Driver.webdriver);
			Action mouseOver = builder.moveToElement(el).build();
			mouseOver.perform();
			toolTipText = el.getAttribute("title");
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}

	@Then("^\"([^\"]*)\" tab show tool tip \"([^\"]*)\"$")
	public void tab_show_tool_tip(String tabName, String toolTip) throws Throwable {
		try {
			// Verifying for tooltiptext
			Assert.assertTrue(toolTipText.equals(toolTip));
			log.info(tabName+" shows tooltip "+toolTip);
			CBTConfiguration.score = "pass";
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}

}
