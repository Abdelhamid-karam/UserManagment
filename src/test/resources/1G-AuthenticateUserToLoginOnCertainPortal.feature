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
Feature: Authenticate portal user to login

  @tag1
  Scenario:  Authenticate user to login on nagwa portal
    Given I have a valid user managment authentication token
    And   I have user email and password
    And I have "nagwa" portal ID  
    When I post Authenticate portal user to login
    Then response code should be 200 
     And   response schema matches the json schema provided in "Authenticate portal user to login.json" file
    
    
    @tag2
  Scenario:  Authenticate user to login on new portal
     Given I have a valid user managment authentication token
     And   I have user email and password
     And I have "new portal" portal ID  
     When I post Authenticate portal user to login
    Then response code should be 200 
     And   response schema matches the json schema provided in "Authenticate portal user to login.json" file
    
    
   