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
Feature:  Get user info 

  @tag1
  Scenario:  Get user info by code
    Given I have a valid user managment authentication token
    And   I have user "code" 
    When I call  Get user info api
    Then response code should be 200 
    And   response schema matches the json schema provided in "getUserInfo.json" file
    
    
     @tag2
  Scenario:   Get user info by Email
    Given I have a valid user managment authentication token
    And   I have user "Email" 
    When I call  Get user info api
    Then response code should be 200 
    And   response schema matches the json schema provided in "getUserInfo.json" file
    
     @tag3
  Scenario:   Get user info by Username
    Given I have a valid user managment authentication token
    And   I have user "Username" 
    When I call  Get user info api
    Then response code should be 200 
    And   response schema matches the json schema provided in "getUserInfo.json" file