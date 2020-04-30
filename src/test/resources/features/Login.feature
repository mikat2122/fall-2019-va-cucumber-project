@smoke @lib-100 @login @regression
Feature: Login
  As an user,
  I should be able to login to the application

  @librarian @staff
  Scenario: Login as a librarian
    Given I am on the login page
    When I login as a librarian
    Then dashboard should be displayed

  @student
  Scenario: Login as a student
    Given I am on the login page
    When I login as a student
    Then dashboard should be displayed
    # this is a comment

  @admin @staff
  Scenario: Login as an admin
    Given I am on the login page
    When I login as admin
    Then dashboard should be displayed

