@Login
Feature: Car Rental Details Validation


# 🔹 SCENARIO 1
Scenario: Verify car selection opens details page in new tab
Given user is on car rental results page
When user selects "Kia Picanto" car from results
Then car details page should open in a new tab


# 🔹 SCENARIO 2
Scenario: Verify car details information
Given user is on car rental details page
Then car name should match selected car
And pickup location should match previously selected location
And drop location should match previously selected location
And car details should display price for one day


# 🔹 SCENARIO 3
Scenario: Verify continue action on car details page
Given user is on car rental details page
When user clicks on continue button
Then user should proceed further in booking flow