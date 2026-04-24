@Login
Feature: Flight Search Validation

  Scenario: Validate wrong inputs then correct flow in same session
    Given user is on booking homepage
    When user performs flight search validations
      | step | departure | destination | expected            |
      | 1    |           | Paris       | missing_departure   |
      | 2    | Bangalore |             | missing_destination |
      | 3    | Bangalore | Paris       | success             |
