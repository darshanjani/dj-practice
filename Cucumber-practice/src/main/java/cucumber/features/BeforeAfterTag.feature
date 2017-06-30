Feature: Test cucumber before/after and tag annotation
  I want to check the title of my page when i click on a link

	@web
  Scenario: Check page title for Our Passion page
    Given I on the the selenium practice website
    When I navigate to "Our Passion"
    Then I check page title is "Our Passion"
    And I close the browser
