@Login
Feature: Flight Selection Page

Background:
Given user is on booking homepage after login
When user opens flight + hotel tab
And user enters departure location from excel row 1
And user enters destination location from excel row 1
And user clicks search button
Then user should see hotel results
And user is on hotel listing page
And user selects first hotel
Then user should see room details page
When user selects room and clicks continue
Then flight selection page should be displayed

Scenario: Validate flight filters and complete booking flow

When user clicks cheapest tab
And user clicks fastest tab
And user applies luggage filter
And user enables direct routes only
And user selects one stop flights
And user resets filters
And user selects best tab
And user selects flight and clicks continue
Then traveller details page should be displayed
