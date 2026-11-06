package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilies.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseClass {

    private LoginPage loginpage;
    private HomePage homepage;

    @BeforeMethod
    public void setpages() {
        loginpage = new LoginPage(getdriver());
        homepage = new HomePage(getdriver());
    }

    @Test
    public void verifyOrangeHrmLogo() {
        //ExtentManager.startTest("Home page verify logo test"); --This has been implemented in TestListener
        ExtentManager.logStep("Navigating to login page by entering username and password");
        loginpage.login("admin", "admin123");
        ExtentManager.logStep("Verifying logo is visible or not");
        Assert.assertTrue(homepage.verifyOrangeHrmLogo(), "Orange hrm logo is not visible");
        ExtentManager.logStep("Validation successfull");
        ExtentManager.logStep("Logged out Successfully!");
    }

}
