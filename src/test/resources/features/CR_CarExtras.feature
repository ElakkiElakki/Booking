@Login
Feature: Car Rental Extras Validation


# 🔹 SCENARIO 1
Scenario: Verify extras are displayed on extras page
Given user is on car rental extras page
Then user should see available extras


# 🔹 SCENARIO 2
Scenario: Verify selecting GPS updates price and summary
Given user is on car rental extras page
When user selects "GPS"
Then check whether it reflects over the price
When user clicks continue from extras page
Then user should be on protection page

# 🔹 SCENARIO 3
Scenario: Verify user can proceed without selecting extras
Given user is on car rental extras page
When user skips selecting extras
And user clicks continue from extras page
Then user should proceed without selecting extras