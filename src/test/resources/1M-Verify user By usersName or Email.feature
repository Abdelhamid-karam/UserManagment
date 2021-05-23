#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
 @tag
Feature: Verify user By usersName or Email

  @tag1
  Scenario:  Verify user By usersName
    Given I have a valid user managment authentication token
    And   I have user "UsersName" to be verified 
    And I have encrypted usersName or Email  
    When I post Verify user API
    Then response code should be 200 
    
    @tag2
  Scenario:  Verify user By Email
    Given I have a valid user managment authentication token
    And   I have user "Email" to be verified
    And I have encrypted usersName or Email    
    When I post Verify user API
    Then response code should be 200 
    
    
    
    
   