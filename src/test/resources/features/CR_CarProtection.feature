@Login
Feature: Car Rental Protection Validation

# 🔹 SCENARIO 1 
Scenario: Verify booking with full protection
Given user is on car rental protection page
When user selects protection "Full Protection"
And user clicks go to book with full protection
Then user should proceed to booking page


# 🔹 SCENARIO 2 
Scenario: Verify booking without full protection
Given user is on car rental protection page
When user clicks go to book without full protection
Then user should proceed to booking page