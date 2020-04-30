package com.cybertek.library.pages;

import com.cybertek.library.utilities.Driver;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage extends PageBase{
    public DashboardPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

}
