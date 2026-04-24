Feature: SC01_ST - Homepage Search
  As a registered user I want to search for stays

  @Login
  Scenario: TS_ST_001 - Homepage Search All Test Cases
    Given User logs in to booking.com stays

    # ── TC1 Row 1 — Valid search ──────────────────────────────────────
    Given User is on booking.com stays homepage
    When User performs search with destination "Bangalore" date "none" adults 2 children 1
    Then "Results page is displayed" should be verified on homepage

    # ── TC1 Row 2 — Invalid destination ──────────────────────────────
    Given User is on booking.com stays homepage
    When User performs search with destination "asdfgh123" date "none" adults 0 children 0
    Then "Results page is displayed with auto corrected location" should be verified on homepage

    # ── TC1 Row 3 — Empty destination ────────────────────────────────
    Given User is on booking.com stays homepage
    When User performs search with destination "" date "none" adults 0 children 0
    Then "Validation error is displayed" should be verified on homepage

    # ── TC1 Row 4 — Past date ─────────────────────────────────────────
    Given User is on booking.com stays homepage
    When User performs search with destination "South Korea" date "2026-04-16" adults 0 children 0
    Then "Past date is not selectable" should be verified on homepage