Feature: Verfiy Login feature

  @login @successful
  Scenario: verify login using valid credentials
    Given User is on Login Page
    When User enter username as "rajshri@gmail.com"
    And User enter password as "January@123"
    And User click on login button
    Then User is on dashboard Page

  @login @unsuccessful
  Scenario: verify login using invalid credentials
    Given User is on Login Page
    When User enter username as "rajshri@gmail.com"
    And User enter password as "January@1234"
    And User click on login button
    Then User verify authentication failure popup is displayed
    Then User verify user is on Login Page