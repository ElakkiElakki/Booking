@Login
Feature: Traveller Details Validation

  Scenario: Validate traveller and contact details
    Given user is on booking homepage
    When user performs flight search validations
      | step | departure | destination | expected            |
      | 1    |           | Paris       | missing_departure   |
      | 2    | Bangalore |             | missing_destination |
      | 3    | Bangalore | Paris       | success             |
    And user selects travel date and travellers
    And user triggers flight search
    Then user views available flight results
    And user selects first flight and proceeds
    Then user lands on traveller details section
    When user completes adult validation flow
    And user enters valid child details
    And user validates email flow
    And user enters phone and proceeds
