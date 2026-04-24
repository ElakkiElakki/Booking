Feature: SC03_ST - Hotel Details and Room Selection
  As a registered user I want to verify hotel details
  and room selection on booking.com stays

  @Login
  Scenario: TS_ST_003_004 - Hotel Details and Room Selection
    Given User logs in to booking.com stays

    # TC3 Row 1 — Click hotel and open details 
    Given User is on booking.com stays homepage
    And User has searched for stays in "chennai" checkin "2026-08-25" checkout "2026-09-02"
    And User clicks on the first stays hotel from results
    When User performs stays hotel action "click hotel and open details"
    Then "Hotel page opens" should be verified on stays hotel page

    # TC3 Row 2 — Validate hotel details 
    When User performs stays hotel action "validate hotel details"
    Then "Details displayed correctly" should be verified on stays hotel page

    #TC3 Row 3 — Click reserve without room
    When User performs stays hotel action "click reserve without room"
    Then "Error message displayed" should be verified on stays hotel page

    # TC3 Row 4 — Validate images gallery 
    When User performs stays hotel action "validate images gallery"
    Then "Images displayed properly" should be verified on stays hotel page

    # TC4 Row 1 — Select room and proceed
    Given User is on booking.com stays homepage
    And User has searched for stays in "chennai" checkin "2026-08-25" checkout "2026-09-02"
    And User clicks on the first stays hotel from results
    When User performs stays room action "select room proceed"
    Then "Navigated to booking page" should be verified on stays room section

    # TC4 Row 2 — Click without room
    Given User is on booking.com stays homepage
    And User has searched for stays in "chennai" checkin "2026-08-25" checkout "2026-09-02"
    And User clicks on the first stays hotel from results
    When User performs stays room action "click without room"
    Then "Error displayed" should be verified on stays room section

    # ── TC4 Row 3 — Change room count ────────────────────────────────
    Given User is on booking.com stays homepage
    And User has searched for stays in "chennai" checkin "2026-08-25" checkout "2026-09-02"
    And User clicks on the first stays hotel from results
    When User performs stays room action "change room count"
    Then "Room count updated" should be verified on stays room section

    # ── TC4 Row 4 — Select different room ────────────────────────────
    Given User is on booking.com stays homepage
    And User has searched for stays in "chennai" checkin "2026-08-25" checkout "2026-09-02"
    And User clicks on the first stays hotel from results
    When User performs stays room action "select different room"
    Then "Price updates accordingly" should be verified on stays room section