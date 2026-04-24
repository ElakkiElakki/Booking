@Login
Feature: Car Rental Booking Validation

# 🔹 SCENARIO 1
Scenario: Verify booking form submission using Excel data

Given user is on booking page
When user fills booking details from excel
And user selects Google Pay and continues
Then booking should proceed successfully