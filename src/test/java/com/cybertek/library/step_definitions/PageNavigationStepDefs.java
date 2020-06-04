package com.cybertek.library.step_definitions;

import com.cybertek.library.pages.DashboardPage;
import com.cybertek.library.pages.UsersPage;
import com.cybertek.library.utilities.BrowserUtils;
import com.cybertek.library.utilities.Driver;
import com.cybertek.library.utilities.LibraryConstants;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PageNavigationStepDefs {

    DashboardPage dashboardPage = new DashboardPage();
    UsersPage usersPage = new UsersPage();

    @When("I click on {string} link")
    public void i_click_on_link(String link) {
        System.out.println("I click on " + link + " link");
        switch (link.toLowerCase()){
            case "dashboard":
                dashboardPage.dashboard.click();
                break;
            case "users":
                dashboardPage.users.click();
                break;
            case "books":
                dashboardPage.books.click();
                break;
        }
    }
    @Then("{string} page should be displayed")
    public void page_should_be_displayed(String page) {
        BrowserUtils.wait(2);
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().endsWith(page.toLowerCase()));

        switch (page.toLowerCase()){
            case "users":
                String actual = dashboardPage.pageHeader.getText();
                break;
            case "books":
                actual = dashboardPage.pageHeader.getText();
                break;
        }

    }
    @Then("show records default value should be {int}")
    public void show_records_default_value_should_be(Integer selected) {
        System.out.println("selected = " + selected);
        UsersPage usersPage = new UsersPage();
        String actual = usersPage.getShowRecords().getFirstSelectedOption().getText();
        Assert.assertEquals(selected+"",actual);
    }

    @Then("show records should have following options:")
    public void show_records_should_have_following_options(List<String> options) {
        System.out.println("options.size() = " + options.size());
        System.out.println(options);
        List<WebElement> webElements = usersPage.getShowRecords().getOptions();
        List<String> elementsText = BrowserUtils.getElementsText(webElements);
        Assert.assertEquals(options, elementsText);
    }
    @When("I select show {int} records")
    public void i_select_show_records(Integer option) {
        usersPage.getShowRecords().selectByVisibleText(option.toString());
        BrowserUtils.wait(2);
    }

    @Then("the users table must display {int} records")
    public void the_users_table_must_display_records(int expectedCount) {
        int actualCount = usersPage.allRows.size();
        Assert.assertEquals(expectedCount,actualCount);
    }

    @When("I go/navigate to {string} page")
    public void i_go_to_page(String page) {
        System.out.println("I click on " + page + " link");
        switch (page.toLowerCase()){
            case LibraryConstants.DASHBOARD:
                dashboardPage.dashboard.click();
                break;
            case LibraryConstants.USERS:
                dashboardPage.users.click();
                break;
            case LibraryConstants.BOOKS:
                dashboardPage.books.click();
                break;
        }
    }
}
