Feature: Check out form filling and submission

  Scenario: Submit Tea feedback
    Given I on the the selenium practice website
    When I navigate to "Let's Talk Tea"
    And Fill the Feedback form
      | Fields  | Value                        |
      | Name    | Some other name              |
      | Email   | email@email.com              |
      | Subject | Some other subject           |
      | Message | This is another long message |
    Then I verify if form is submitted
    # And I close the browser
