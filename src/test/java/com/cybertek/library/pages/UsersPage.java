package com.cybertek.library.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class UsersPage extends PageBase {
    @FindBy(name = "tbl_users_length")
    public WebElement showRecords;

    @FindBy(xpath = "//table/tbody/tr")
    public List<WebElement> allRows;

    @FindBy(css = ".btn.btn-lg.btn-outline.btn-primary.btn-sm")
    public WebElement addUserButton;

    @FindBy(tagName = "input")
    public WebElement search;

    @FindBy(xpath = "//table/tbody/tr/td[2]")
    public List<WebElement> allUserIds;

    @FindBy(xpath = "//table/tbody/tr/td[3]")
    public List<WebElement> allNames;

    @FindBy(xpath = "//table/tbody/tr/td[4]")
    public List<WebElement> allEmails;

    @FindBy(tagName = "th")
    public List<WebElement> columnNames;

    @FindBy(id = "user_groups")
    public WebElement userGroupDropdown;

    @FindBy(css = "a.page-link:not([title])")
    public List<WebElement> pageList;

    public void clickAddUserButton(){
        addUserButton.click();
    }

    public Select getShowRecords(){
        return new Select(showRecords);
    }


}
