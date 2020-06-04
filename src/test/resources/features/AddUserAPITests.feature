Feature: Add user end point test

  # this test case specifically is used for testing the add user service
  @add_user @api
  Scenario: add student using add user service
    Given new student is added using the add_user endpoint
    And I am on the login page
    When I login as the new user created using add_user endpoint
    Then "Books" page should be displayed


  # this test case we test books table
  # for this test we want use api to generate new user information
  Scenario: books table
    Given new student is added using the add_user endpoint
    And I am on the login page
    When I login as the new user created using add_user endpoint
    And I navigate to "Books" page
    When I edit book The kite runner
    Then I verify book information
      | name            | author          | year |
      | The kite runner | Khaled Hosseini | 2003 |

   @db @api
    Scenario: verify book information using get_book_by_id endpoint
      Given I am on the login page
      When I login as a librarian
      And I navigate to "Books" page
      When I open book The kite runner
      Then book information must match the api for The kite runner