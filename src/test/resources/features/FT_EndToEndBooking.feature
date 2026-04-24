@Login
Feature: Flight Booking End-to-End Flow (Partial Data Driven)

  Scenario: User completes booking with dynamic locations
    Given user launches booking homepage for flights
    When user opens flights section
    And user chooses one way trip option
    And user enters flight locations
      | from1     | from2   | to    |
      | Bangalore | Chennai | Paris |
    And user selects travel date and travellers
    And user triggers flight search
    Then user views available flight results
    And user selects first flight and proceeds
    Then user lands on traveller details section
    When user enters traveller details
      | firstName | lastName | gender |
      | Nethra    | shivani  | Female |
      | Shiva     | Kumar    | Male   |
    And user enters contact details
      | email                  | country     | phone      |
      | 2022peccb157@gail.com | India (+91) | 9876543210 |
    And user selects ticket type and proceeds
    And user skips seat selection
