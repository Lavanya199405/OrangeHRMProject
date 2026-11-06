package com.orangehrm.listeners.testlistener;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilies.ExtentManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {


    //Trigerrs when test starts
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        //Start logging in extent report
        ExtentManager.startTest(testName);
    }

    //Triggered when test succeeds
    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.logstepsWithScreenshot(BaseClass.getdriver(),"Test passed successfully", "Test End: Test passed");

    }

    @Override
    public void onTestSkipped(ITestResult result) {
//        String testName = getTestName();
        ExtentManager.logSkip("Test skipped :"+getTestName(result));
    }

    //Triggerred when test failed
    @Override
    public void onTestFailure(ITestResult result) {

        String testFailureMessage = result.getThrowable().getMessage();
        ExtentManager.logStep(testFailureMessage);
        ExtentManager.logFailure(BaseClass.getdriver(),"Test failed", "test end"+getTestName(result)+"test passed");
    }

    @Override
    public void onFinish(ITestContext context) {

        ExtentManager.endTest();
    }

    @Override
    public void onStart(ITestContext context) {

        ExtentManager.getreporter();
    }

    public String getTestName(ITestResult result){
        return result.getMethod().getMethodName();

    }
}
