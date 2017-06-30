Feature: Check the page title
  I want to check the title of my page when i click on a link

  Scenario: Check page title for Our Passion page
    Given I on the the selenium practice website
    When I navigate to "Our Passion"
    Then I check page title is "Our Passion"
    And I close the browser

  Scenario: Check page title for Menu page
    Given I on the the selenium practice website
    When I navigate to "Menu"
    Then I check page title is "Menu"
    And I close the browser

  Scenario: Check page title for Welcome page
    Given I on the the selenium practice website
    When I navigate to "Welcome"
    Then I check page title is "Welcome"
    And I close the browser
