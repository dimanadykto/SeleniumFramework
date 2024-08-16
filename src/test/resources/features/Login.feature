Feature: Login related scenarios

  @smoke @sprint1 @diane @luckycharm
  Scenario: Valid admin login
    When user enters valid username and password
    And user clicks on login button
    Then user is successfully logged in
