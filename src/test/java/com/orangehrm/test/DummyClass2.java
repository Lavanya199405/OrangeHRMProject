package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilies.ExtentManager;
import org.testng.annotations.Test;

public class DummyClass2 extends BaseClass {

@Test
    public void dummyTest2(){
    //ExtentManager.startTest("DummyTest2 test");--This has been implemented in TestListener
        String title=getdriver().getTitle();
    ExtentManager.logStep("Verifying the title");
    System.out.println("Title is########"+title);
        assert title.equals("OrangeHRM"):"Test failed - Tittle is not matching";
        System.out.println("Test passed - Tittle is matching");
    ExtentManager.logStep("Validation successfull");

    }

}
