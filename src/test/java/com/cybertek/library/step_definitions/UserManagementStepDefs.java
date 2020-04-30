package com.cybertek.library.step_definitions;

import com.cybertek.library.pages.UsersPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UserManagementStepDefs {
    UsersPage usersPage = new UsersPage();

    @Then("User group default value should be {string}")
    public void user_group_default_value_should_be(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("I select User group {string}")
    public void i_select_User_group(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("Groups columns in user table should only contain {string}")
    public void groups_columns_in_user_table_should_only_contain(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
