@Login
Feature: Attraction gallery Validation
Scenario: AT07 Validate user can open attraction gallery page
  Given user starts gallery test case "ATG07" on attractions page
  When user searches attraction destination "Amsterdam"
  And user clicks first see availability button from search results
  Then attraction gallery should be visible
