@smoke @lib-132 @navigation
Feature: Page navigation links

  Background:
    Given I am on the login page
    And I login as librarian

  # login as librarian
  # click on the users link
  # verify page Users
  Scenario: Go to users page
    When I click on "Users" link
    Then "Users" page should be displayed



  # login as librarian
  # click on the books link
  # verify page Books
  Scenario: Go to books page
    When I click on "Books" link
    Then "Books" page should be displayed


  # login as librarian
  # click on the dashboard link
  # verify page Dashboard
  @db
  Scenario: Go to books page
    When I click on "Dashboard" link
    Then "Dashboard" page should be displayed