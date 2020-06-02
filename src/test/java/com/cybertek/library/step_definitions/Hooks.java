package com.cybertek.library.step_definitions;

import com.cybertek.library.utilities.ConfigurationReader;
import com.cybertek.library.utilities.DBUtils;
import com.cybertek.library.utilities.Driver;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.concurrent.TimeUnit;

public class Hooks {

    @Before
    public void setUpScenario() {
        System.out.println("set up browser");
        Driver.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Driver.getDriver().manage().window().maximize();
    }

    @Before(value = "@db", order = 1)
    public void connect(){
        System.out.println("connecting to db");
        String url = "jdbc:mysql://"+ ConfigurationReader.getProperty("qa2_db_host")+
                ConfigurationReader.getProperty("qa2_db_name");
        String username = ConfigurationReader.getProperty("qa2_db_username");
        String password = ConfigurationReader.getProperty("qa2_db_password");

        System.out.println(url);
        DBUtils.createConnection(url, username, password);
    }

    @After(value = "@db", order = 1)
    public void closeConnection(){
        DBUtils.destroy();
    }

    @After
    public void tearDownScenario(Scenario scenario) {
        System.out.println("scenario.getSourceTagNames() = " + scenario.getSourceTagNames());
        System.out.println("scenario.getName() = " + scenario.getName());

        if(scenario.isFailed()){
            // take screenshot using selenium
            final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            // attach it to report
            scenario.embed(screenshot, "image/png", scenario.getName());
        }

        Driver.closeDriver();
    }

    @BeforeStep
    public void setUpStep() {
        // System.out.println("prints before every step");
    }

    @AfterStep
    public void tearDownStep() {
        // System.out.println("prints after every step");
    }

}
