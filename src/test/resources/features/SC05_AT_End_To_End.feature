
Feature: Attraction Gallery Image Validation

  Scenario: AT08 Validate user can click attraction gallery image and select tickets
    Given user starts gallery image test case "ATG08" on attractions page
    When user searches gallery image destination "Amsterdam"
    And user opens first attraction details page for gallery image
    And user clicks gallery image in attraction details page
    Then attraction gallery popup should be displayed
    When user clicks select tickets button
    And user clicks final select button
    And user clicks plus svg button and clicks next button
    And user fills all required details and clicks payment details button