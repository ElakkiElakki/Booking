@Login
Feature: Attractions Search Validation

  Scenario Outline: Validate attractions search with destination
    Given user starts test case "<testCaseId>" on attractions page
    When user enters "<destinationType>" destination as "<destinationValue>"
    And user clicks attractions search button
    Then attractions result should be "<expectedResult>"

    Examples:
      | testCaseId | destinationType | destinationValue | expectedResult |
      | AT01       | valid           | Brasov           | success        |
      | AT02       | empty           |                  | validation     |
      | AT03       | invalid         | @#$%             | validation     |