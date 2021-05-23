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
Feature: Add user

  @tag1
  Scenario: Add user without username and email linked to portal
    Given I have a valid user managment authentication token
    And   I have user data "without_email_createdBy_onPortal"
    When  I call add user api
    Then  response code should be 200
    And   response schema matches the json schema provided in "AddUser.json" file
    
  @tag2
  Scenario: Add user with username and email linked to nagwa 
    Given I have a valid user managment authentication token
    And   I have user data "with_email_notCreatedBy_onNagwa"
    When  I call add user api
    Then  response code should be 200
     And   response schema matches the json schema provided in "AddUser.json" file
     
  @tag3
  Scenario: Add user with username and email linked to portal 
    Given I have a valid user managment authentication token
    And   I have user data "with_email_createdBy_onPortal"
    When  I call add user api
    Then  response code should be 200
     And   response schema matches the json schema provided in "AddUser.json" file
    
    
   