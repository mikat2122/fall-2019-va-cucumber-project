Feature: Books Table


  Scenario: Verify search result
    Given I am on the login page
    And I login to application as a librarian
    When I navigate to "Books" page
    And I search for "The Goldfinch"
    Then search table should contain results matching The Goldfinch


  Scenario: Verify search result
    Given I am on the login page
    And I login to application as a librarian
    When I navigate to "Books" page
    When I edit book The kite runner
    Then I verify book information
      | name            | author          | year |
      | The kite runner | Khaled Hosseini | 2003 |
