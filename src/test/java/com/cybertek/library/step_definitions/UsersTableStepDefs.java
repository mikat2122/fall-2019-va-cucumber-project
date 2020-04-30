package com.cybertek.library.step_definitions;

import com.cybertek.library.pages.UsersPage;
import com.cybertek.library.utilities.BrowserUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class UsersTableStepDefs {
    UsersPage usersPage = new UsersPage();

    @When("I search for {string}")
    public void i_search_for(String searchString) {
        usersPage.search.sendKeys(searchString);
        BrowserUtils.wait(1);
    }

    @Then("table should contain rows with {string}")
    public void table_should_contain_rows_with(String expectedSearchString) {
        int size = usersPage.allUserIds.size();
        for(int i=0; i<size; i++){
            String id = usersPage.allUserIds.get(i).getText().toLowerCase();
            String name = usersPage.allNames.get(i).getText().toLowerCase();
            String email = usersPage.allEmails.get(i).getText().toLowerCase();
            System.out.println("ROW: " + (i+1));
            System.out.println(id + "\t" + name + "\t" + email);
            boolean found = id.contains(expectedSearchString) || name.contains(expectedSearchString)
                    || email.contains(expectedSearchString);

            Assert.assertTrue(found);
        }
    }

    @Then("table should have following column names:")
    public void table_should_have_following_column_names(List<String> expectedColumnNames) {
        List<String> actualColumnNames = BrowserUtils.getElementsText(usersPage.columnNames);
        assertEquals(expectedColumnNames, actualColumnNames);
    }
    @Then("table should contain this data")
    public void table_should_contain_this_data(Map<String, String> user) {
        String name = user.get("Full Name");
        String id = user.get("User ID");
        String email = user.get("Email");

        // get all rowa, verify that at least one of the rows contains all of the user information
        List<WebElement> allRows = usersPage.allRows;
        List<String> allRowsTxt = BrowserUtils.getElementsText(allRows);
        boolean found = false;
        for(String row : allRowsTxt){
            System.out.println("row = " + row);
            found = row.contains(id) && row.contains(name) && row.contains(email);
            if(!found){
                continue;
            }
            Assert.assertTrue(found);
        }
    }
    @Then("Each User id should be unique")
    public void each_User_id_should_be_unique() {
        usersPage.getShowRecords().selectByVisibleText("100");
        BrowserUtils.wait(2);
        List<String> list = BrowserUtils.getElementsText(usersPage.allUserIds);
        Set<String> id_check = new HashSet<>(list);
        assertEquals(list.size(),id_check.size());
    }
}
