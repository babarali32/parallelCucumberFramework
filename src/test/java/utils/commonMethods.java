package utils;

import Steps.PageInitializer;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class commonMethods extends PageInitializer {
    // parallel exectuo methods
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }
// till parallel execution methods
    public static WebDriver driver;
    public static void openBrowser() {
        configReader.readProperties(constants.PATH);
        String browser = configReader.getPropertyValue("browser");
        if (browser.equals("chrome")) {
            //System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("Firefox")) {//System.setProperty("webdriver.gecko.driver", "src/Driver/geckodriver.exe");
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            throw new RuntimeException("Invalid browser name");
        }
        driver.manage().window().maximize();
       // driver.manage().timeouts().implicitlyWait(20,TimeUnit.Seconds);
       // driver.manage().timeouts().implicitlyWait(constants.IMPLICIT_WAIT,TimeUnit.Seconds);
        driver.get(configReader.getPropertyValue("url"));
        pageObjectsInitializer();

    }
    // parallell method close code
    public static void closeBrowser() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }

//    // parallemetho close code
//    public static void closeBrowser() {
//        if (driver != null) {
//            driver.quit();
//        }
//
//    }
    public static void sendText(WebElement element,String sendToText){
        element.clear();
        element.sendKeys(sendToText);
    }
    public static WebDriverWait getwait(){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(constants.EXPLICIT_WAIT));

        return wait;
    }
    public static void waitForClickability(WebElement element){
        getwait().until(ExpectedConditions.elementToBeClickable(element));
    }
    public static void click(WebElement element){
        waitForClickability(element);
        element.click();
    }
    public static JavascriptExecutor getJavSript(){
        JavascriptExecutor js=(JavascriptExecutor) driver;
        return js;
    }
    public static void jsClick(WebElement element){
        getJavSript().executeScript("arguments[0].click()",element);
    }
    public static void selectDropDown(WebElement element,String text){
        Select select=new Select(element);
        select.selectByVisibleText(text);
        //select.selectByValue("Married");
        //select.selectByIndex(3);  -- inplace of String text make, int text
    }
    public static String takeScreenshot(String fileName) {
        if (driver == null) {
            System.out.println("Driver is null. Cannot take screenshot.");
            return null;
        }

        TakesScreenshot ts = (TakesScreenshot) driver;
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);
        String destinationFilePath = constants.SCREEN_SHOT_FILE_PATH + fileName + "_" + getTimeStamp("yyyy-MM-dd-HH-mm-ss") + ".png";

        try {
            FileUtils.copyFile(sourceFile, new File(destinationFilePath));
            System.out.println("Screenshot saved to: " + destinationFilePath);
            return destinationFilePath;
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static String getTimeStamp(String pattern) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}

//
//    //screenshot method
//    public static byte[] takeScreenshot(String fileName){
//        TakesScreenshot ts = (TakesScreenshot) driver;
//        byte[] picBytes = ts.getScreenshotAs(OutputType.BYTES);
//        File sourceFile = ts.getScreenshotAs(OutputType.FILE);
//
//        try {
//            FileUtils.copyFile(sourceFile, new File(constants.SCREEN_SHOT_FILE_PATH + fileName + " " +
//                    getTimeStamp("yyyy-MM-dd HH:mm:ss")+".png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return picBytes;
//    }
//
//    public static String getTimeStamp(String pattern){
//        Date date = new Date();
//        //yyyy-mm-dd-hh-mm-ss-ms
//        //to format the date according to our choice we have to use this function
//        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//        return sdf.format(date);
//    }
//}