Feature: Proof of concept that my feature works

  Scenario: My first test
    Given I on the the selenium practice website
    When I navigate to "Menu"
    Then I check page title is "Menu"
    And I close the browser
