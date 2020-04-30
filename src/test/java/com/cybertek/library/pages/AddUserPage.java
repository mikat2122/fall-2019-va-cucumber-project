package com.cybertek.library.pages;
import com.cybertek.library.utilities.BrowserUtils;
import com.cybertek.library.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AddUserPage extends PageBase {

    @FindBy(name = "full_name")
    public WebElement fullNameInput;

    @FindBy(name = "password")
    public WebElement passwordInput;

    @FindBy(name = "email")
    public WebElement emailInput;

    @FindBy(name = "start_date")
    public WebElement startDate;

    @FindBy(name = "end_date")
    public WebElement endDate;

    @FindBy(id = "address")
    public WebElement addressInput;

    @FindBy(id = "user_group_id")
    public WebElement UserGroupDropdown;

    @FindBy(id = "status")
    public WebElement activeDropdown;

    @FindBy(xpath = "//button[.='Close']")
    public WebElement closeButton;

    @FindBy(xpath = "//button[.='Save changes']")
    public WebElement saveChangesButton;

    @FindBy(xpath = "//tbody/tr/td[4]")
    public List<WebElement> emails;

    @FindBy(xpath = "//div[@class='toast-message'][.='The user has been created.']")
    public WebElement userHasBeenCreatedMessage;

    public void clickCloseButton(){
        closeButton.click();
    }
    public void clickSaveChangesButton(){
        saveChangesButton.click();
    }
    public String getExpectedEndDate(String datePattern, int addMonths){
        LocalDate futureDate = LocalDate.now().plusMonths(addMonths);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(datePattern);
        return dtf.format(futureDate);
    }
    public String getActualStartDate(){
        String actualStartDate = startDate.getAttribute("value");
        return actualStartDate;
    }
    public String getActualEndDate(){
        String actualEndDate = endDate.getAttribute("value");
        return actualEndDate;
    }
    public void fillOutNewUserInfo(String fullName, String password, String email){
        emailInput.sendKeys(email);
        fullNameInput.sendKeys(fullName);
        passwordInput.sendKeys(password);
    }
    public boolean checkIfTableContainsUserWithEmail(String email){
        Driver.getDriver().navigate().refresh();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 5);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//tbody/tr/td[4]")));
        List<String> allEmails = BrowserUtils.getElementsText(emails);
        for(String eachEmail : allEmails){
            if(eachEmail.equals(email)){
                return true;
            }
        }
        return false;
    }
}
