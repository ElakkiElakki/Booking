Feature: Seat selection simple flow

  @Login
  Scenario: Select seats for both segments and proceed to payment
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
      | Nethra    | Shee     | Male   |
      | Arjun     | Kumar    | Male   |
    And user enters contact details
      | email          | country     | phone      |
      | test@gmail.com | India (+91) | 9876543210 |
    And user selects food preference for all travellers
    And user expands price breakdown dropdown
    Then user lands on flexibility page
    When user selects flexible ticket option
    And user clicks next
    When user selects two flight seats
    And user proceeds to flight payment
    Then user should land on flight payment page