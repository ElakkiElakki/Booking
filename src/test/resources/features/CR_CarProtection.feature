@Login
Feature: Car Rental Protection Validation

Scenario: Verify booking with full protection
Given user is on car rental protection page
When user clicks go to book with full protection
Then user should proceed to booking page

Scenario: Verify booking without full protection
Given user is on car rental protection page
When user clicks go to book without full protection
Then user should proceed to booking page