package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
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
        loginpage.login("admin", "admin123");
        Assert.assertTrue(homepage.isAdmintabVisible(),"Admin tab should be visible after succesful login");
        homepage.logout();
        staticWait(2);
    }

    @Test
    public void verifyInvalidLogin(){
        loginpage.login("admin", "admin");
        String expectederrorMssg = "Invalid credentials";
        Assert.assertTrue(loginpage.verifyErrorMssg(expectederrorMssg), "test failed: invalid error message");
    }
}
