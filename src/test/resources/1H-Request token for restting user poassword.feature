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
Feature: request token for restting user poassword

  @tag1
  Scenario:  request token for restting user poassword by user ID 
    Given I have a valid user managment authentication token
    And   I have user ID
    When I post request token for restting user poassword by user "ID"
    Then response code should be 200 
    
    
    @tag2
   Scenario:  request token for restting user poassword by username 
     Given I have a valid user managment authentication token
     And   I have username
     When I post request token for restting user poassword by user "username"
     Then response code should be 200 
    
    
   