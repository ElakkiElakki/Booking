@Login
Feature: Flight Search Validation

  Scenario Outline: Validate flight search inputs
    Given user is on booking homepage
    When user enters departure "<departure>" and destination "<destination>"
    And user clicks search
    Then user should see "<expected>"

    Examples:
      | departure | destination | expected            |
      |           | Paris       | missing_departure   |
      | Bangalore |             | missing_destination |
      | Bangalore | Paris       | success             |
