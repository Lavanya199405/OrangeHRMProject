package com.orangehrm.pages;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private ActionDriver actionDriver;
    //Define locators using By class
    private By userNameField = By.name("username");
    private By passwordField = By.cssSelector("input[type='password']");
    private By loginbotton = By.xpath("//button[text()=' Login ']");
    private By errorMessage = By.xpath("//p[text()='Invalid credentials']");
    // private By OrangeHrmLogo = By.

    public LoginPage(WebDriver driver) {
//        actionDriver = new ActionDriver(driver);
        this.actionDriver = BaseClass.getActionDriver();
    }

    // Method to perform login
    public void login(String username, String Password) {
        actionDriver.enterText(userNameField, username);
        actionDriver.enterText(passwordField, Password);
        actionDriver.click(loginbotton);

    }

    //Method to check if error message is displayed
    public boolean errorMessageDisplayed() {
        return actionDriver.isDisplayed(errorMessage);
    }

    //Method to get text from errorMessage
    public String getErrorMessageText() {
        return actionDriver.getText(errorMessage);
    }

    //Verify error message is correct
    public boolean verifyErrorMssg(String expectedErrorMsg) {
        return actionDriver.compareText(errorMessage, expectedErrorMsg);
    }

}
