package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import org.testng.annotations.Test;

public class DummyClass2 extends BaseClass {

@Test
    public void dummyTest2(){
        String title=getdriver().getTitle();
    System.out.println("Title is########"+title);
        assert title.equals("OrangeHRM"):"Test failed - Tittle is not matching";
        System.out.println("Test passed - Tittle is matching");

    }

}
