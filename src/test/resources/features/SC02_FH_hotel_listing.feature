@Login
Feature: Hotel Listing Page

Background:
  Given user is on booking homepage after login
  When user opens flight + hotel tab
  And user enters departure location from excel row 1
  And user enters destination location from excel row 1
  And user clicks search button
  Then user should see hotel results

Scenario: User applies filters and selects hotel
  Given user is on hotel listing page
  When user toggles map view
  And user applies 5 star filter
  And user sorts hotels by lowest price
  And user selects first hotel
  Then user should see room details page