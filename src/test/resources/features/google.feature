Feature: smoke test#1, verify searched results

  Scenario: user can see searched keyword in title on an opened result page

    Given an open browser with google.com
    When a keyword automation is entered in input field
    Then open the first link on search results page
    Then title contains automation searched word

  Scenario: user can search expected domain on search result pages

    Given an open browser with google.com
    When a keyword automation is entered in input field
    Then verify that there is testautomationday.com domain on 5 search results pages