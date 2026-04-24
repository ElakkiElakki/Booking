@Login
Feature: Flight + Hotel Search Flow

Scenario: Validate search using excel
  Given user is on booking homepage after login
  When user opens flight + hotel tab
  When user enters departure location from excel row 1
  And user enters destination location from excel row 1
  And user clicks search button
  Then user should see hotel results