package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilies.ExtentManager;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class DummyClass extends BaseClass {

@Test
    public void dummyTest(){
//    ExtentManager.startTest("DummyTest test"); --This has been implemented in TestListener
        String title=getdriver().getTitle();
    System.out.println("Title is########"+title);

    ExtentManager.logStep("Verifying the title");
        assert title.equals("OrangeHRM"):"Test failed - Tittle is not matching";
        System.out.println("Test passed - Tittle is matching");
        ExtentManager.logSkip("This case is skipped");
        throw new SkipException("Skipping the test as part of testing");

    }

}
