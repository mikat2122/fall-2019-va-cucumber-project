package com.cybertek.library.step_definitions;

import com.cybertek.library.pojos.User;
import com.cybertek.library.utilities.AuthenticationUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;

public class LibraryUsersStepDefs {

    String token;
    List<User> userList = new ArrayList<>();
    List<User> groupIdList = new ArrayList<>();

    @Given("I get information for each user using get_user_by_id endpoint")
    public void i_get_information_for_each_user_using_get_user_by_id_endpoint() {
        token = AuthenticationUtility.getLibrarianToken();
        List<String> idList = given().
                header("x-library-token", token).
                log().all().
                when().get("/get_all_users").prettyPeek().
                jsonPath().getList("id");

        // remove the admin user
        idList.remove(0);

        // for each id, call the get user by endpoint

        for (int i = 0; i < 10; i++) {
            User userPojo = given().
                    header("x-library-token", token).
                    pathParam("id", idList.get(i)).
                    log().all().
                    when().
                    get("/get_user_by_id/{id}").peek().as(User.class);

            userList.add(userPojo);
        }
    }

    @When("I get the available groups using the get_user_groups endpoint")
    public void i_get_the_available_groups_using_the_get_user_groups_endpoint() {
        groupIdList = given().
                header("x-library-token", token).
                log().all().
                when().
                get("/get_user_groups").peek().
                jsonPath().getList("id");
    }

    @Then("groups of non admin users should match the groups from get_user_groups")
    public void groups_of_non_admin_users_should_match_the_groups_from_get_user_groups() {
        // verify that each pojo object in the user list contains one of the ids from the groupList
        for (User user : userList) {
            assertThat(user.getUserGroupId(), in((List) groupIdList));
        }
    }
}
