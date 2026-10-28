package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
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
        loginpage.login("admin", "admin123");
        Assert.assertTrue(homepage.verifyOrangeHrmLogo(), "Orange hrm logo is not visible");
    }

}
