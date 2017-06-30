Feature: Test
  I want to use this template for my feature file

  Scenario Outline: Title of your scenario outline
    Given I on the the selenium practice website
    When I navigate to <Link>
    Then I check page title is <Title>
    And I close the browser

    Examples: 
      | Link        | Title       |
      | Our Passion | Our Passion |
      | Menu        | Menu        |
      | Welcome     | Welcome     |
