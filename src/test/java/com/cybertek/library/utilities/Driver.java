package com.cybertek.library.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class Driver {

    private Driver(){}

    private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();



    public static WebDriver getDriver(){
        // check if the driver has value, if not - assign a value
        if(driverPool.get() == null){
            // get the driver type from properties file
            String browser = ConfigurationReader.getProperty("browser");

            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driverPool.set(new ChromeDriver());
                    break;
                case "remote-chrome":
                    try{
                        ChromeOptions chromeOptions = new ChromeOptions();
                        URL url = new URL("http://54.165.116.76:4444/wd/hub");
                        driverPool.set(new RemoteWebDriver(url, chromeOptions));
                    } catch(MalformedURLException e){
                        e.printStackTrace();
                    }
                    break;
                case "remote-firefox":
                    try{
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        URL url = new URL("http://54.165.116.76:4444/wd/hub");
                        driverPool.set(new RemoteWebDriver(url, firefoxOptions));
                    } catch(MalformedURLException e){
                        e.printStackTrace();
                    }
                    break;
                case "chrome-headless":
                    WebDriverManager.chromedriver().setup();
                    driverPool.set(new ChromeDriver(new ChromeOptions().setHeadless(true)));
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver());
                    break;
                case "firefox-headless":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver(new FirefoxOptions().setHeadless(true)));
                    break;
                case "ie":
                    if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                        throw new WebDriverException("Your OS doesn't support Internet Explorer");
                    WebDriverManager.iedriver().setup();
                    driverPool.set(new InternetExplorerDriver());
                    break;
                case "edge":
                    if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                        throw new WebDriverException("Your OS doesn't support Edge");
                    WebDriverManager.edgedriver().setup();
                    driverPool.set(new EdgeDriver());
                    break;
                case "safari":
                    if (!System.getProperty("os.name").toLowerCase().contains("mac"))
                        throw new WebDriverException("Your OS doesn't support Safari");
                    WebDriverManager.getInstance(SafariDriver.class).setup();
                    driverPool.set(new SafariDriver());
                    break;
            }
        }
        return driverPool.get();
    }

    public static void closeDriver(){
        if (driverPool.get() != null) {
            // close the driver
            driverPool.get().quit();
            driverPool.remove();
        }
    }
}
