 @Login
Feature: Flight Filters and Sorting

  Scenario: Validate sorting, details, fare options and filters
    Given user launches booking homepage for flights
    When user opens flights section
    And user chooses one way trip option
    And user enters flight locations
      | from1     | from2   | to    |
      | Bangalore | Chennai | Paris |
    And user selects travel date and travellers
    And user triggers flight search
    Then user views available flight results
    And User validates cheapest sorting
    And User validates flight details
    And User validates fare options
    And User validates airline filter
    And User validates time filter
