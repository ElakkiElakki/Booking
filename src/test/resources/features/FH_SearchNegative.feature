@Login
Feature: Invalid Flight + Hotel Search

Scenario Outline: Validate invalid search inputs

  Given user is on booking homepage after login
  When user opens flight + hotel tab
  And user clears search fields
  And user enters departure "<departure>"
  And user enters destination "<destination>"
  And user clicks search button
  Then system should handle invalid search result

Examples:
  | departure | destination |
  |           | Chennai     |
  | Chennai   |             |
  | Chennai   | @@@@        |
  |           |             |