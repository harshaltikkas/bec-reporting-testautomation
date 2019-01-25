@Feature 
Feature: Roster Tab Dropdown Behaviour

#Data incomplete, we need role of teacher for single and multiple class allocation.
#  @SmokeTests
#  Scenario Outline: Single or Multiple Selection of class Dropdown
#    Given "<usertype>" is on portal's home page
#    When within the Universal Selector Tab "<usertype>" with "<singleormultipleclass>" selection of class click on roster tab 
#    Then by default "All" student should be selected and "<usertype>" with "<singleormultipleclass>" should be able to select Student drop downlist
#    And verify class list and school list and student lastname starts in alpha order 

#  Examples: 
#      | 	usertype  				|	singleormultipleclass	|
#      | Student  				  |												|																														
#      | Teacher  				  |		single							|
#      | Teacher  		 			|		multiple						|
#      | School Admin 	 		|												|
#      | District Admin 		|												|

  Scenario: Verify if the user clicks on a selected option from the dropdownlist, close the list and displays the selection in the closed dropdown menu.
    Given User is on sso portal's home page
    When User click on Roster tab within the Universal Selector Tab
    Then User should be able to select school,class and student from dropdown 
    And User closes the Roster Tab and comes back to Roster Tab, school, class and student dropdown are filled with previously selected values

