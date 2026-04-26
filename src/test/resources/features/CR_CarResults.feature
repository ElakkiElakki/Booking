

@Login
Feature: Car Rental Results Validation

# 🔹 SCENARIO 1
Scenario: Verify car results are displayed
Given user is on car rental results page
Then user should see list of available cars


# 🔹 SCENARIO 2
Scenario: Verify filters are applied correctly
Given user is on car rental results page
When user applies filters like location, car category and price per day
Then filtered results should be displayed


# 🔹 SCENARIO 3
Scenario: Verify clearing filters restores default results
Given user is on car rental results page
When user applies filters like location, car category and price per day
And user clears all filters
Then default results should be displayed again