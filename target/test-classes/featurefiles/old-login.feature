Feature: Login Validation
  I want to use this feature file to validation positive and negative scenarios of Login functionality.

  @smoke
  Scenario: Verfiy Login using valid credentials
    Given User is on a login page
    When User enter username as "mkanani"
    And User enter password as "mkanani123"
    And User click on login button
    Then User should be on dashboard page

  @e2e
  Scenario Outline: Verfiy Login using valid credentials using examples
    Given User is on a login page
    When User enter username as "<username>"
    And User enter password as "<password>"
    And User click on login button
    Then User should be on dashboard page

    Examples: 
      | username | password   |
      | techno   | techno123  |
      | credits  | credits234 |

  @smoke @regression
  Scenario: Verfiy new Login using valid credentials
    Given User is on a login page
    When User enter username as "akshay"
    And User enter password as "akshay123"
    And User click on login button
    Then User should be on dashboard page

  @e2e
  Scenario Outline: Verfiy Login using valid credentials using examples
    Given User is on a login page
    When User enter signup data from row number "<RowNumber>" and sheet "<sheetName>"
    And User click on login button
    Then User should be on dashboard page

    Examples: 
      | RowNumber | sheetName |
      |         1 | data      |
      |         2 | data      |
