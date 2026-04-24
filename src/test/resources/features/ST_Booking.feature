Feature: SC05_ST - Booking Form

  @Login
  Scenario: TS_ST_005 - Booking Form All Test Cases
    Given User logs in to booking.com stays
    Given User is on booking.com stays homepage
    And User has searched for stays in "chennai" checkin "2026-08-25" checkout "2026-09-02"
    And User clicks on the first stays hotel from results
    And User has selected a stays room and clicked reserve
    When User fills stays booking form with details:
      | firstName       | lastName | email                    | phone      |
      | Vickyvigneshini | S        | vickygroup4cap@gmail.com | 9876543210 |
    Then "Finish booking page displayed" should be verified on stays booking page
    Given User is on booking.com stays homepage
    And User has searched for stays in "chennai" checkin "2026-08-25" checkout "2026-09-02"
    And User clicks on the first stays hotel from results
    And User has selected a stays room and clicked reserve
    When User fills stays booking form with details:
      | firstName | lastName | email | phone |
      |           |          |       |       |
    Then "Validation error shown" should be verified on stays booking page
    # ── TC5 Row 3 — Invalid email — DEFECT IDENTIFIED ────────────────────
    Given User is on booking.com stays homepage
    And User has searched for stays in "chennai" checkin "2026-08-25" checkout "2026-09-02"
    And User clicks on the first stays hotel from results
    And User has selected a stays room and clicked reserve
    When User fills stays booking form with details:
      | firstName | lastName | email | phone      |
      | Vicky     | S        | abc@  | 9876543210 |
    Then "Email validation error shown" should be verified on stays booking page
    Then "Email validation error shown" should be verified on stays booking page
    Given User is on booking.com stays homepage
    And User has searched for stays in "chennai" checkin "2026-08-25" checkout "2026-09-02"
    And User clicks on the first stays hotel from results
    And User has selected a stays room and clicked reserve
    When User fills stays booking form with details:
      | firstName | lastName | email                    | phone |
      | Vicky     | S        | vickygroup4cap@gmail.com |       |
    Then "Finish booking page displayed" should be verified on stays booking page
