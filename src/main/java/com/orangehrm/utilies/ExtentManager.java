package com.orangehrm.utilies;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExtentManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static Map<Long, WebDriver> driverMap= new HashMap<>();

    //Initialise the extent report
    public static synchronized ExtentReports getreporter(){
        if (extent==null) {
            String reportpath = System.getProperty("user.dir") + "/src/test/resources/extentreports/extentreport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportpath);
            spark.config().setReportName("Automation test report");
            spark.config().setDocumentTitle("OrangeHRM report");
            spark.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(spark);
            //Adding system information
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("Java version", System.getProperty("java.version"));
            extent.setSystemInfo("User Name", System.getProperty("user.name"));

        }
        return extent;
    }

    //Start the test
    public static synchronized ExtentTest startTest(String testname){
        ExtentTest extentTest =getreporter().createTest(testname);
        test.set(extentTest);
        return extentTest;

    }

    //End the test
    public static synchronized void endTest(){
        getreporter().flush();
    }

    //Get current thread's test
    public static synchronized ExtentTest getTest(){
        return test.get();
    }

    //Get the name of the current test
    public static String getTestName(){
        ExtentTest currentTest = getTest();
        if(currentTest!=null){
            return currentTest.getModel().getName();
        }
        else {
            return "No test is currently active for this thread";
        }
    }

    //log a step
    public static void logStep(String logMessage){
        getTest().info(logMessage);
    }

    //log step validation with screenshot
    public static void logstepsWithScreenshot(WebDriver driver, String logMessage, String screenshotmessage){
        getTest().pass(logMessage);
        //Screenshot method
        attachScreenshot(driver, screenshotmessage);

    }

    //log a failure
    public static void logFailure(WebDriver driver, String logMessage, String screenshotmessage){
        String colorMessage = String.format("<span style='color:red;'>%s</span>", logMessage);
        getTest().fail(colorMessage);
        //Screenshot method
        attachScreenshot(driver, screenshotmessage);
    }

    //log a skip
    public static void logSkip(String logMessage){
        String colorMessage = String.format("<span style='color:orange;'>%s</span>", logMessage);
        getTest().skip(colorMessage);
    }

    //Take Screenshot with date and time in the file
    public synchronized static String takeScreenshot(WebDriver driver, String screenshotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot)driver;
        File src= ts.getScreenshotAs(OutputType.FILE);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        //saving the scrrenshot to file
        String destPath = System.getProperty("user.dir")+"/src/test/resources/screenshots/"+screenshotName+"_"+timeStamp+".png";
        File finalPath = new File(destPath);
        try {
            FileUtils.copyFile(src, finalPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Convert screenshot to Base-64 for embedding the report
        String base64Format = convertToBase64(src);
        return base64Format;



    }

    //Convert screenshot to Base-64
    public static String convertToBase64(File screenShotFile) throws IOException {
        String base64format = "";
        //Read the file content into byte array
        byte[] fileContent = FileUtils.readFileToByteArray(screenShotFile);
         return base64format = Base64.getEncoder().encodeToString(fileContent);

    }

    //Attach screenshot to report using Base64
    public synchronized static void attachScreenshot(WebDriver driver, String message){
        try {
            String screenShotBase64 = takeScreenshot(driver, getTestName());
            getTest().info(message, com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(screenShotBase64).build());
        } catch (IOException e) {
            getTest().fail("Failed to attach the screenshot:"+message);
            e.printStackTrace();
        }

    }


    //Register WebDriver for current thread
    public static void registerDriver(WebDriver driver){
        driverMap.put(Thread.currentThread().getId(), driver);
    }
}
