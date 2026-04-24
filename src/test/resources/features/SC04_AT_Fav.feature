@Login
Feature: Attractions Favs Validation

  Scenario: AT08 Validate selecting attraction into My Favourites
    Given user starts fav test case "ATF04" on attractions page
    When user searches fav attraction destination "Amsterdam"
    And user selects the heart icon from attraction search result page
    And user opens my fav from profile menu
    Then selected attraction should be displayed in my fav page

  Scenario: ATF09 Validate deselecting attraction from My Favourites
    Given user starts fav test case "ATF05" on attractions page
    When user searches fav attraction destination "Singapore"
    And user selects the heart icon from attraction search result page
    And user opens my fav from profile menu
    Then selected attraction should be displayed in my fav page
    When user goes back to attraction search results page
    And user deselects the same heart icon from attraction search result page
    And user opens my fav from profile menu
    Then selected attraction should not be displayed in my fav page