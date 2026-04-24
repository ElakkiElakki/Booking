Feature: SC02_ST - Search Results Filters

  @Login
  Scenario Outline: TS_ST_002 - Apply filters
    Given User logs in to booking.com stays
    And User is on booking.com stays homepage
    And User has searched for stays in "Switzerland"
    When User applies stays "<filterType>" filter
    Then "<expectedResult>" should be verified after stays filter

    Examples:
      | filterType   | expectedResult                        |
      | none         | List of hotels is displayed           |
      | Free WiFi    | Filtered results displayed            |
      | Very good 8+ | Filtered results displayed            |
      | extreme      | No properties found after all filters |