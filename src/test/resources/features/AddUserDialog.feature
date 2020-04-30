Feature: Add new users dialog

  Scenario: Placeholder tests
    Given I am on the login page
    And I login using following credentials:
      | email    | librarian21@library |
      | password | aZ849tSZ            |
    And I click on "Users" link
    When I click on Add User
    Then dialog fields must have matching placeholder
    | full name | Full Name |
    | email     | Email     |
    | password  | Password  |
    | address   | ""        |

  Scenario: Verify user information
    Given I am on the login page
    And I login using following credentials:
      | email    | librarian21@library |
      | password | aZ849tSZ            |
    And I click on "Users" link
      When I search for "486"
      Then table should contain this data
      | User ID   | 486                             |
      | Full Name | Asuncion Pfeffer                |
      | Email     | Oralia Daugherty234@library.com |


