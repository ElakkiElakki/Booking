@Login
Feature: Hotel Details Page

Background:
  Given user is on booking homepage after login
  When user opens flight + hotel tab
  And user enters departure location from excel row 1
  And user enters destination location from excel row 1
  And user clicks search button
  Then user should see hotel results
  And user is on hotel listing page
  And user selects first hotel

Scenario: Validate hotel details page and complete booking flow
  Then user should see room details page

  When user clicks view map and closes it

  And user clicks reviews and returns to top

  And user clicks facilities and returns to top

  And user selects room and clicks continue

  Then flight selection page should be displayed