package com.cybertek.library.step_definitions;

import com.cybertek.library.pages.BooksPage;
import com.cybertek.library.utilities.AuthenticationUtility;
import com.cybertek.library.utilities.BrowserUtils;
import com.cybertek.library.utilities.DBUtils;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookCategoryStepDefs {

    @Then("book categories must match api and db")
    public void book_categories_must_match_api_and_db() {
        // get categories information from db
        String sqlQuery = "SELECT name FROM book_categories";
        // this method returns all values from a single column
        List<Object> dbNamesObj = DBUtils.getColumnData(sqlQuery, "name");
        List<String> dbNames = new ArrayList<>();
        for(Object o : dbNamesObj){
            dbNames.add(o.toString());
        }

        // get categories information from api
        String token = AuthenticationUtility.getLibrarianToken();
        Response response = given().
                header("x-library-token", token).
                log().all().
                when().get("/get_book_categories");
        response.then().statusCode(200);
        List<String> apiNames = response.jsonPath().getList("name");
        // get categories information from ui
        BooksPage booksPage = new BooksPage();
        List<WebElement> namesEL = booksPage.mainCategoryList().getOptions();
        List<String> uiNames = BrowserUtils.getElementsText(namesEL);
        uiNames.remove(0);

        // compare 3 lists
        assertThat(uiNames, allOf(equalTo(apiNames), equalTo(dbNames)));
    }
}
