package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilies.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseClass {

    private HomePage homepage;
    private LoginPage loginpage;

    @BeforeMethod
    public void setupPages(){
        loginpage = new LoginPage(getdriver());
        homepage = new HomePage(getdriver());
    }

    @Test
    public void verifyvalidLoginTest(){
        //ExtentManager.startTest("Valid Login Test"); --This has been implemented in TestListener
        ExtentManager.logStep("Navigating to login page by entering username and password");
        loginpage.login("admin", "admin123");
        ExtentManager.logStep("Verifying admin tab is visible or not");
        Assert.assertTrue(homepage.isAdmintabVisible(),"Admin tab should be visible after succesful login");
        ExtentManager.logStep("Validation successfull");
        homepage.logout();
        ExtentManager.logStep("Logged out successfully!");
        staticWait(2);
    }

    @Test
    public void verifyInvalidLogin(){
        //ExtentManager.startTest("Invalid Login Test"); --This has been implemented in TestListener
        ExtentManager.logStep("Navigating to login page by entering username and password");
        loginpage.login("admin", "admin");
        String expectederrorMssg = "Invalid credentials";
        Assert.assertTrue(loginpage.verifyErrorMssg(expectederrorMssg), "test failed: invalid error message");
        ExtentManager.logStep("Validation successfull");
    }
}
