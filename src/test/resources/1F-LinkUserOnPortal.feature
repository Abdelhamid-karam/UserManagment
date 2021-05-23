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
Feature:  Link user on portal

  @tag1
  Scenario:  Link user on nagwa portal
    Given I have a valid user managment authentication token
    And   I have user code to be linked on "nagwa"
    And I have portal ID and URL  
    When I post link user account to portal
    Then response code should be 200 
    
    
    @tag2
  Scenario:  Link user on new portal
    Given I have a valid user managment authentication token
    And   I have user code to be linked on "portal"
    And I have portal ID and URL  
    When I post link user account to portal
    Then response code should be 200 
    
    
   