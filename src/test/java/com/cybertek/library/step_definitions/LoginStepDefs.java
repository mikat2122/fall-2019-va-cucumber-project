package com.cybertek.library.step_definitions;

import com.cybertek.library.pages.LoginPage;
import com.cybertek.library.utilities.ConfigurationReader;
import com.cybertek.library.utilities.Driver;
import com.cybertek.library.utilities.LibraryConstants;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;


public class LoginStepDefs {
    LoginPage loginPage = new LoginPage();
    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Going to the login page");
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
    }
    @When("I login as a librarian")
    public void i_login_as_a_librarian() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("logging in as a librarian");
        String email = ConfigurationReader.getProperty("librarian_email");
        String password = ConfigurationReader.getProperty("librarian_password");
        loginPage.login(email, password);
    }
    @Then("dashboard should be displayed")
    public void dashboard_should_be_displayed() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Verify dashboard page");
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
        wait.until(ExpectedConditions.urlContains("dashboard"));
        String actualUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("dashboard"));

    }
    @When("I login as a student")
    public void i_login_as_a_student() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Logging in as a student");
        String email = ConfigurationReader.getProperty("student_email");
        String password = ConfigurationReader.getProperty("student_password");
        loginPage.login(email, password);
    }
    @When("I login as admin")
    public void i_login_as_admin() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Logging in as a student");
        String email = ConfigurationReader.getProperty("email");
        String password = ConfigurationReader.getProperty("password");
        loginPage.login(email, password);
    }
    @Given("I login using following credentials:")
    public void i_login_using_following_credentials(Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        loginPage.login(email, password);
    }

    @And("I login to application as a {word}")
    public void i_login_to_application_as_a(String user) throws Exception {
        String email = null, password = null;
        switch (user.toLowerCase()){
            case LibraryConstants.LIBRARIAN:
                email = ConfigurationReader.getProperty("librarian_email");
                password = ConfigurationReader.getProperty("librarian_password");
                break;
            case LibraryConstants.STUDENT:
                email = ConfigurationReader.getProperty("student_email");
                password = ConfigurationReader.getProperty("student_password");
                break;
            default:
                throw new Exception("Wrong user type is provided: " + user);
        }
        loginPage.login(email, password);
    }
}
