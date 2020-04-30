package com.cybertek.library.step_definitions;

import com.cybertek.library.pages.AddUserPage;
import com.cybertek.library.pages.LoginPage;
import com.cybertek.library.pages.UsersPage;
import com.cybertek.library.utilities.ConfigurationReader;
import com.cybertek.library.utilities.DateTimeFormatUtils;
import com.cybertek.library.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.Map;
import java.util.Random;


public class AddUsersStepDefs {
    LoginPage loginPage = new LoginPage();
    UsersPage usersPage = new UsersPage();
    AddUserPage addUserPage = new AddUserPage();

    Random random = new Random();
    int index = random.nextInt(1000);
    String email = "new_user_" + index + "@test.com";

    @Given("I access Users page as a librarian")
    public void i_access_Users_page_as_a_librarian() {
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
        String username = ConfigurationReader.getProperty("email");
        String password = ConfigurationReader.getProperty("password");
        loginPage.login(username, password);
        usersPage.users.click();
    }
    @Given("I click on Add User")
    public void i_click_on_Add_User() {
        usersPage.clickAddUserButton();
    }
    @Then("start date should be today's date")
    public void start_date_should_be_today_s_date() {
        String actualStartDate = addUserPage.getActualStartDate();
        String expectedStartDate = DateTimeFormatUtils.getCurrentDate("yyyy-MM-dd");
        Assert.assertEquals(expectedStartDate, actualStartDate);
    }
    @Then("end date should be one month from today")
    public void end_date_should_be_one_month_from_today() {
        String actualEndDate = addUserPage.getActualEndDate();
        String expectedEndDate = addUserPage.getExpectedEndDate("yyyy-MM-dd", 1);
        Assert.assertEquals(expectedEndDate, actualEndDate);

    }
    @Given("I enter new user information with random email")
    public void i_enter_new_user_information_with_random_email() {
        addUserPage.fillOutNewUserInfo("Test Name", "test_password", email);
    }
    @When("I click the Close link")
    public void i_click_the_Close_link() {
        addUserPage.clickCloseButton();
    }
    @Then("the users table should not contain user with that email")
    public void the_users_table_should_not_contain_user_with_that_email() {
        boolean found = addUserPage.checkIfTableContainsUserWithEmail(email);
        Assert.assertFalse(found);
    }
    @When("I save new user information with random email")
    public void i_save_new_user_information_with_random_email() {
        addUserPage.fillOutNewUserInfo("Test Name", "test_password", email);
        addUserPage.clickSaveChangesButton();
    }
    @When("the users table must contain the correct user information")
    public void the_users_table_must_contain_the_correct_user_information() {
        boolean found = addUserPage.checkIfTableContainsUserWithEmail(email);
        Assert.assertTrue(found);
    }
    @Then("dialog fields must have matching placeholder")
    public void dialog_fields_must_have_matching_placeholder(Map<String, String> fields) {
        for(String key: fields.keySet()){
            System.out.println("key = " + key);
            System.out.println("value = " + fields.get(key));
        }
        String expectedFullName = fields.get("full name");
        String actualFullName = addUserPage.fullNameInput.getAttribute("placeholder");
        Assert.assertEquals(expectedFullName, actualFullName);

        String expectedEmail = fields.get("email");
        String actualEmail = addUserPage.emailInput.getAttribute("placeholder");
        Assert.assertEquals(expectedEmail, actualEmail);

        String expectedPassword = fields.get("password");
        String actualPassword = addUserPage.passwordInput.getAttribute("placeholder");
        Assert.assertEquals(expectedPassword, actualPassword);

        String actualAddress = addUserPage.addressInput.getAttribute("placeholder");
        Assert.assertEquals("Address placeholder must be empty","",actualAddress);
    }

}
