package com.orangehrm.pages;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private ActionDriver actionDriver;

    private By adminTab = By.xpath("//span[text()='Admin']");
    private By userIdBttn = By.className("oxd-userdropdown-tab");
    private By logoutBttn = By.xpath("//a[text()='Logout']");
    private By orangeHrmLogo = By.xpath("//div[@class='oxd-brand-banner']//img");

    public HomePage(WebDriver driver) {
        //actionDriver = new ActionDriver(driver);
        this.actionDriver = BaseClass.getActionDriver();
    }

    //Method to verify if admin tab is visible
    public boolean isAdmintabVisible() {
        return actionDriver.isDisplayed(adminTab);
    }

    public boolean verifyOrangeHrmLogo() {
        return actionDriver.isDisplayed(orangeHrmLogo);
    }

    //Method to perform logout operation
    public void logout() {
        actionDriver.click(userIdBttn);
        actionDriver.click(logoutBttn);
    }
}
