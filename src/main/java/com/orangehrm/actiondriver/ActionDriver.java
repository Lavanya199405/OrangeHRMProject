package com.orangehrm.actiondriver;

import com.orangehrm.base.BaseClass;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ActionDriver {
    private WebDriver driver;
    private WebDriverWait wait;
    public int seconds;
    public static final Logger log = BaseClass.log;


    public ActionDriver(WebDriver driver) {
        this.driver = driver;
        int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        log.info("WebDriver Instance is created");
    }


    //Method to click an element
    public void click(By by){
        try{
            driver.findElement(by).click();
        }catch(Exception e){
            log.error("Element is not clickable:"+e.getMessage());
        }
    }

    //Method to enter text into a field --Avoid code duplication
    public void enterText(By by, String value){
        try {
            waitForElementTobeVisible(by);
//            driver.findElement(by).clear();
//            driver.findElement(by).sendKeys(value);
            WebElement element = driver.findElement(by);
            element.clear();
            element.sendKeys(value);
        }
        catch(Exception e){
            log.error("Unable to enter the value:"+e.getMessage());
        }

    }

    //Method to get value from the field
    public String getText(By by)
    {
        try{
            waitForElementTobeVisible(by);
            return driver.findElement(by).getText();
        }
        catch(Exception e)
        {
            log.error("Unable to get the text:"+e.getMessage());
            return "";
        }

    }

    // Method to compare text
    public boolean compareText(By by, String expectedText){
        try {
            waitForElementTobeVisible(by);
            String actualText = driver.findElement(by).getText();
            if(actualText.equalsIgnoreCase(expectedText))
            {
                System.out.println("text are matching:"+actualText+"equals"+expectedText);
                return true;
            }
            else{
                log.error("text are matching:"+actualText+"not equals"+expectedText);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Unable to compare text:"+e.getMessage());
            return false;
        }

    }

    // Method to check if element is dispalyed
    /*public boolean isDisplayed(By by){
        try {
            waitForElementTobeVisible(by);
            boolean isDisplayed = driver.findElement(by).isDisplayed();
            if(isDisplayed){
                System.out.println("Element is visible");
                return isDisplayed;
            }else{
                return isDisplayed;
            }
        } catch (Exception e) {
            System.out.println("Element is not displayed:"+e.getMessage());
            return false;
        }
    }*/

    //refactored code to check whether element is displayed
    public boolean isDisplayed(By by){
        try {
            waitForElementTobeVisible(by);
            return driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            System.out.println("Element is not displayed:"+e.getMessage());
            return false;
        }
    }
    //Scroll to an element
    public void scrollToElement(By by){
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement element = driver.findElement(by);
            js.executeScript("argument[0],scrollIntoView(true);", element);
        } catch (Exception e) {
            System.out.println("Unable to locate element:"+e.getMessage());
        }
    }

    //Wait for element to be clickable
    public void waitForElementTobeClickable(By by) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            System.out.println("element is not clickable:" + e.getMessage());

        }
    }

    //Wait for element to be visible

    public void waitForElementTobeVisible(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            System.out.println("Element is not visible:" + e.getMessage());
        }
    }
}

