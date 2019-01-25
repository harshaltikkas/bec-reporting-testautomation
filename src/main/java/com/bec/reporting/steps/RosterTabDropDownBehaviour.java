package com.bec.reporting.steps;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;
import com.bec.reporting.pageobjects.HomePage;
import com.bec.reporting.utils.CBTConfiguration;
import com.bec.reporting.utils.Driver;
import com.bec.reporting.utils.IWait;
import com.bec.reporting.utils.UtilityMethods;
import com.google.common.base.Verify;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RosterTabDropDownBehaviour {
	 /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(RosterTabDropDownBehaviour.class);
	HomePage homePage = PageFactory.initElements(Driver.webdriver, HomePage.class);
	public static String selectedSchoolText,selectedclassText,selectedStudentText;

	/**Scenario 2*/
	@When("^User click on Roster tab within the Universal Selector Tab$")
	public void user_click_on_Roster_tab_within_the_Universal_Selector_Tab() throws Throwable {
		log.info("executing Scenario 2");
		try {
			homePage.rostertab.click();
			IWait.explicit_wait(Driver.webdriver, homePage.schoolTitleOnSliderMenu);
			Verify.verify(homePage.schoolTitleOnSliderMenu.isDisplayed());
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}

	@Then("^User should be able to select school,class and student from dropdown$")
	public void user_should_be_able_to_select_school_class_and_student_from_dropdown() throws Throwable {
		try {
			int schoolcount=0,selectedschool=0,classcount=0,selectedclass=0,studentcount=0,selectedstudent=0;
			
			/**selecting school from dropdown*/
			homePage.schooldropdownbtn.click();
			Thread.sleep(500);
			schoolcount=homePage.schoollist.size();
			selectedschool=(int) (Math.random()*schoolcount);
/*			UtilityMethods.scrollPageDown(Driver.webdriver,selectedschool);
			Thread.sleep(500);
			homePage.schoollist.get(selectedschool).click();	*/
			homePage.schoollist.get(1).click();		//for testing	
			selectedSchoolText=homePage.schooldropdownbtn.getText();
			log.info(selectedSchoolText);
			/**selecting class from dropdownd*/
			homePage.classdropdownbtn.click();
			Thread.sleep(500);
			classcount=homePage.classlist.size();
			selectedclass=(int) (Math.random()*classcount);
/*			UtilityMethods.scrollPageDown(Driver.webdriver,selectedclass);
			Thread.sleep(500);
			homePage.classlist.get(selectedclass).click();				*/
			homePage.classlist.get(1).click();	//for testing			
			selectedclassText=homePage.classdropdownbtn.getText();
			log.info(selectedclassText);
			
			/**selecting student from dropdown*/
			homePage.studentdropdownbtn.click();
			Thread.sleep(500);
			studentcount=homePage.studentlist.size();
			selectedstudent=(int) (Math.random()*studentcount);
			UtilityMethods.scrollPageDown(Driver.webdriver,selectedstudent);
			Thread.sleep(500);
			homePage.studentlist.get(selectedstudent).click();
			selectedStudentText=homePage.studentdropdownbtn.getText();
			log.info(selectedStudentText);
			UtilityMethods.scrollPageUp(Driver.webdriver);
			Thread.sleep(500);
		}
		catch(Exception e) {
			UtilityMethods.processException(e);
		}
	}

	@Then("^User closes the Roster Tab and comes back to Roster Tab, school, class and student dropdown are filled with previously selected values$")
	public void user_closes_the_Roster_Tab_and_comes_back_to_Roster_Tab_school_class_and_student_dropdown_are_filled_with_previously_selected_values() throws Throwable {
		try {
			homePage.closearrow.click();
			Thread.sleep(500);
			homePage.rostertab.click();
			IWait.explicit_wait(Driver.webdriver, homePage.schoolTitleOnSliderMenu);
			Assert.assertTrue(homePage.schooldropdownbtn.getText().equals(selectedSchoolText));
			Assert.assertTrue(homePage.classdropdownbtn.getText().equals(selectedclassText));
			Assert.assertTrue(homePage.studentdropdownbtn.getText().equals(selectedStudentText));
			CBTConfiguration.score = "pass";
		} catch (Exception e) {
			UtilityMethods.processException(e);
		}
	}
}
