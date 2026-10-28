package com.orangehrm.base;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilies.LoggerManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.util.TimeUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class BaseClass {

    protected static Properties prop;
//    protected static WebDriver driver;
//    private static ActionDriver actionDriver;
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
    public static final Logger log= LoggerManager.getLogger(BaseClass.class);

    @BeforeSuite
    public void loadConfig() throws IOException {
        // load the config file
        prop = new Properties();
        FileInputStream file = new FileInputStream("src/main/resources/config.properties");
        prop.load(file);
    }

    @BeforeMethod
    public synchronized void setup() throws IOException {
        log.info("setting up WebDriver for:" + this.getClass().getSimpleName());
        launchBrowser();
        configureBrowser();
        staticWait(2);
//        //intialize actionDriver only once
//        if(actionDriver == null){
//            actionDriver = new ActionDriver(driver);
//            log.info("ActionDriver instance is created"+Thread.currentThread().getId());
//        }
        //initialize actionDriver for the current thread
        actionDriver.set(new ActionDriver(getdriver()));
        log.info("ActionDriver initialized for thread: " + Thread.currentThread().getId());

    }

    // Intiliatise the webDriver browser defined in the config properties file
    private synchronized void launchBrowser() throws IOException {
        String browser = prop.getProperty("browser");
        if (browser.equalsIgnoreCase("chrome")) {
            driver.set(new ChromeDriver());
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver.set(new FirefoxDriver());
        } else if (browser.equalsIgnoreCase("edge")) {
            driver.set(new EdgeDriver());
        } else {
            throw new IllegalArgumentException("Browser Not supported:" + browser);
        }
    }

    /* configure browser settings such as implicit wait, maximise the browser and enter the url
     */
    private void configureBrowser() {
        //Implicit wait
        int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
        getdriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        //Maximize the browser
        getdriver().manage().window().maximize();
        try {
            //Navigate to URL
            getdriver().get(prop.getProperty("url"));
        } catch (Exception e) {
            log.error("Failed to navigate to the URL:" + e.getMessage());

        }
    }

    //getter method for prop
    public static Properties getProp() {
        return prop;
    }

    //Driver getter method
//    public WebDriver getdriver() {
//        return driver;
//    }

//    //Driver setter method
//    public void setdriver(WebDriver driver) {
//        this.driver = driver;
//    }

    //Getter method for webdriver
    public static WebDriver getdriver(){
        if (driver.get() == null){
            log.error("WebDriver is not initialised");
            throw new IllegalStateException("WebDriver is not initialized");
        }
        return driver.get();
    }

    //Getter method for actiondriver
    public static ActionDriver getActionDriver(){
        if (actionDriver.get() == null){
            log.error("actionDriver is not initialised");
            throw new IllegalStateException("actionDriver is not initialized");
        }
        return actionDriver.get();
    }


    @AfterMethod
    public void teardown() {
        if (getdriver() != null) {
            try {
                getdriver().quit();
            } catch (Exception e) {
                log.error("Unable to quit the driver");
            }
            log.info("WebDriver instsnce is closed");
//            driver = null;
//            actionDriver = null;
            driver.remove();
            actionDriver.remove();
        }
    }

    public void staticWait(int seconds) {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
    }


}

