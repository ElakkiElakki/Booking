Feature: SC06_ST - End to End Booking Flow

  @Login
  Scenario: TS_ST_006 - E2E booking flow from Excel
    Given User logs in to booking.com stays

    # ── Row 1 — Complete full booking flow 
    Given User reads stays E2E test data from Excel row 1
    And User clicks on the first stays hotel from results
    And User has selected a stays room and clicked reserve
    When User fills stays booking form from Excel data
    Then "Finish booking page displayed" should be verified on stays E2E

    # ── Row 2 — Empty mandatory booking form fields
    Given User reads stays E2E test data from Excel row 2
    And User clicks on the first stays hotel from results
    And User has selected a stays room and clicked reserve
    When User fills stays booking form from Excel data
    Then "Validation error shown" should be verified on stays E2E

    # ── Row 3 — Invalid email 
    Given User reads stays E2E test data from Excel row 3
    And User clicks on the first stays hotel from results
    And User has selected a stays room and clicked reserve
    When User fills stays booking form from Excel data
    Then "Validation error shown" should be verified on stays E2E

    # ── Row 1 — Assert payment page
    Given User reads stays E2E test data from Excel row 1
    And User clicks on the first stays hotel from results
    And User has selected a stays room and clicked reserve
    When User fills stays booking form from Excel data
    Then "Payment page displayed" should be verified on stays E2E