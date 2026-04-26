
Feature: Car Rental Search Validation

Scenario Outline: Validate car rental search with pickup and drop details

Given user starts test case "<testCaseId>" on car rental page
When user enters "<pickupType>" pickup as "<pickupValue>"
And user enters "<dropType>" drop as "<dropValue>"
And user clicks car rental search button
Then car rental result should be "<expectedResult>"

Examples:
| testCaseId | pickupType | pickupValue | dropType | dropValue        | expectedResult |
| CR01       | empty      |             | empty    |                  | validation     |
| CR02       | valid      | Dubai International Airport (DXB), Dubai, United Arab Emirates       | invalid  | @@@###           | validation     |
| CR03       | valid      | Dubai International Airport (DXB), Dubai, United Arab Emirates       | valid    | Dubai Downtown   | success        |