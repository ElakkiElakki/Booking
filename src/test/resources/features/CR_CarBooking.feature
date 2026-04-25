@Login
Feature: Car Rental Booking Validation

# 🔹 SCENARIO 1
Scenario: Verify booking form submission using Excel data

Given user is on booking page
When user fills booking details from excel
Then booking form should be filled successfully