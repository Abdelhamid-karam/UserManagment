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
Feature: Update username

  @tag1
  Scenario:  Update username as userID and requesterUserID are same person 
    Given I have a valid user managment authentication token
    And   I have user portalID 
    And I have userID for both user and requester   
    When I post UpdateUsername API
    Then response code should be 200 
    And make sure that change take action by calling get user api 
    
    
    
    
    
    
   