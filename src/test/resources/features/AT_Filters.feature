@Login
Feature: Attractions Filters Validation

  Scenario: Validate category filter after search
    Given user starts filter test case "AT04" and opens attractions page
    When user enters destination "Amsterdam"
    And user clicks attractions search button for filters
    And user selects category filter "Tours"
    Then attractions results should be filtered by category "Tours"

  Scenario: Validate multiple filters after search
    Given user starts filter test case "AT05" and opens attractions page
    When user enters destination "Amsterdam"
    And user clicks attractions search button for filters
    And user selects category filter "Nature & outdoor"
    And user selects review score filter "4 and up"
    And user selects time of day filter "Afternoon"
    Then attractions results should match selected filters

  Scenario: Validate lowest price sorting after search
    Given user starts filter test case "AT06" and opens attractions page
    When user enters destination "Amsterdam"
    And user clicks attractions search button for filters
    And user clicks lowest price sort option
    Then attractions results should be sorted by lowest price