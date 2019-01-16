@Feature 
Feature: Fly In Menu Behaviour

#  @SmokeTests
#  Scenario Outline: Arrows to Open and Close the Tab
#    Given User is on sso portal's home page
#    When User Click on open arrows of "<universaltablist>" tab within the Universal Selector Tab
#    Then User should be able to open the slider for the "<universaltablist>" Tab and able to see the close arrows
#    When User Click on close arrows for "<universaltablist>" tab within the Universal Selector Tab
#    Then User should be able to close the slider for the "<universaltablist>" Tab and able to see the open arrows
   
#   Examples: 
#      | universaltablist 	|																															
#      | Roster   				  |
#      | Test 				 			|
#      | Date 	 						|												 
    
#  Scenario Outline: Roster Tab shows the dropdown list of Schools and Classes and Students to apply filters.
#    Given User is on sso portal's home page
#    When User Click on Roster tab within the Universal Selector Tab
#    Then User should be able to select School "<schoolname>" and Class "<classname>" and Student "<studentname>" drop downlist and click on apply filter button
#    And User should be able to click on cancel button to close the Roster Tab.

#    Examples: 
#      | schoolname				  	|	 		classname			  	|			studentname				|																															
#      | Marian Cross School	|		Handwriting 103			|				All							|
#      | Bedford School				|	Physical Education 3	|	Phy 123 Trena Newton	|										 
 

  Scenario Outline: Test Tab shows the list of all tests and allows an individual test and or multiple tests
    Given User is on sso portal's home page
    When User Click on Test tab within the Universal Selector Tab
    Then User should be able to select "<testtype>" Test and click on apply filter button
    And User should be able to click on cancel button to close the Test Tab.

    Examples: 
      | 	 testtype		|	 																																
      | 	  single		|	
      |		 multiple		|		
      |		  all				|										 


#  Scenario: Date Tab allows to select District Term
    
#  Scenario: Colour change and showing tool tip when tab hovered over and is selected or clicked.
    
#  Scenario: Display the data for the selections made in the fly in menu as well as updating the context header.
