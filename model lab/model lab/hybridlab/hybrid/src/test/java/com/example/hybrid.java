package com.example;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class hybrid {
 WebDriver driver;
    Logger logger = Logger.getLogger(getClass());
    ExtentReports reports;
    ExtentTest test;
   @BeforeTest
    public void before() throws Exception{
        ExtentSparkReporter exsprk = new ExtentSparkReporter("C:\\Users\\DELL\\Desktop\\hybridlab\\report1.html");
        reports = new ExtentReports();
        reports.attachReporter(exsprk);
    }

    @Test(priority = 1)
    public void test1() throws Exception
    {
        test = reports.createTest("Test 1", "Add to Cart");
        PropertyConfigurator.configure("C:\\Users\\DELL\\Desktop\\hybridlab\\hybrid\\src\\test\\java\\com\\example\\log4j.properties");

        FileInputStream fs = new FileInputStream("C:\\Users\\hp\\Desktop\\s.testing\\demo\\que 9\\hybridlab\\data.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fs);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rnum = sheet.getLastRowNum();
        XSSFRow row = sheet.getRow(1);
        
        String fn = row.getCell(0).getStringCellValue();
        row = sheet.getRow(2);
        String ln = row.getCell(0).getStringCellValue();
        row = sheet.getRow(4);
        String city = row.getCell(0).getStringCellValue();
        row = sheet.getRow(7);
        String gmail = row.getCell(0).getStringCellValue();
        



        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.mayoclinic.org/");
        logger.info("opening url");
        Thread.sleep(3000);
        logger.warn("Time wait");
        driver.findElement(By.xpath("//*[@id='header__content-inner-container']/div[1]/ul/li[5]/div[1]/div/button/span/span[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"button-d87139392b\"]/span/span/span[1]/span")).click();
        logger.info("give now clicked");
        Thread.sleep(3000);
        logger.warn("Time wait");
        driver.findElement(By.xpath("//*[@id=\"amounts\"]/label[3]")).click();
        logger.info("$250 selected");
        
        Thread.sleep(3000);
        logger.warn("Time wait");
        WebElement drop = driver.findElement(By.xpath("//*[@id=\"designation\"]"));
        Select sobj = new Select(drop);
        sobj.selectByIndex(7);
        logger.info("Medical education selected");
        driver.findElement(By.xpath("//*[@id=\"adfWrapper\"]/fieldset[2]/div[1]/label")).click();
        logger.info("checkbox selected");
        Thread.sleep(3000);
        logger.warn("Time wait");
        
        WebElement drop1 = driver.findElement(By.xpath("//*[@id=\"personalTitle\"]"));
        Select sobj1 = new Select(drop1);
        sobj1.selectByIndex(1);
        logger.info("Title selected");
        
        driver.findElement(By.xpath("//*[@id=\"personalFirstName\"]")).sendKeys(fn);//firstname
        logger.info("firstname entered");
        driver.findElement(By.xpath("//*[@id=\"personalLastName\"]")).sendKeys(ln);//lastname
        logger.info("lastname entered");
        
        Thread.sleep(1000);
        logger.warn("Time wait");
        WebElement drop2 = driver.findElement(By.xpath("//*[@id=\"personalCountry\"]"));
        Select sobj2 = new Select(drop2);
        sobj2.selectByVisibleText("India");//country
        logger.info("India selected");
        
        Thread.sleep(1000);
        logger.warn("Time wait");
        WebElement drop3 = driver.findElement(By.xpath("//*[@id=\"personalState\"]"));
        Select sobj3 = new Select(drop3);
        sobj3.selectByVisibleText("NA");//state
        logger.info("state selected");
        
        driver.findElement(By.xpath("//*[@id=\"personalAddress\"]")).sendKeys("22");;//address
        driver.findElement(By.xpath("//*[@id=\"personalCity\"]")).sendKeys(city);//city
        driver.findElement(By.xpath("//*[@id=\"personalZip\"]")).sendKeys("514321");//postalcode
        driver.findElement(By.xpath("//*[@id=\"personalPhone\"]")).sendKeys("99554433");//phone number
        driver.findElement(By.xpath("//*[@id=\"personalEmail\"]")).sendKeys(gmail);//gmail
        logger.info("address filled");
        Thread.sleep(2000);
        logger.warn("Time wait");
        driver.findElement(By.xpath("//*[@id=\"adfSubmit\"]")).click();
        Thread.sleep(20000);
        WebElement f = driver.findElement(By.xpath("//*[@id='bbcheckout-iframe']"));
        driver.switchTo().frame(f);
        String tit = driver.findElement(By.xpath("//*[@id=\"header\"]/h1")).getText();
        
        Assert.assertTrue(tit.contains("Complete payment"));
        logger.info("Assertion done and program terminated");

    }

        @AfterMethod
    public void afterTest(ITestResult result) throws Exception{
        if(result.getStatus()==ITestResult.FAILURE)
        {
            test.log(Status.FAIL, "TestCase Failed: "+result.getName());
            test.log(Status.FAIL, "Testcase Failed Reason: "+result.getThrowable());
            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            String path = "C:\\Users\\DELL\\Desktop\\hybridlab\\"+result.getName()+"png";
            FileUtils.copyFile(screenshot,new File(path));
            test.addScreenCaptureFromPath(path);

        }
        else if (result.getStatus()==ITestResult.SUCCESS)
        { test.log(Status.PASS, "Test CAse Passed: "+result.getName());
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String path = "C:\\Users\\DELL\\Desktop\\hybridlab\\"+result.getName()+"png";
        FileUtils.copyFile(screenshot,new File(path));
        test.addScreenCaptureFromPath(path);
        }
      else if (result.getStatus()==ITestResult.SKIP)
        { test.log(Status.SKIP, "Test CAse Skipped: "+result.getName());
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String path = "D:\\Software Testing\\Day 11\\sec1logandreport\\"+result.getName()+"png";
        FileUtils.copyFile(screenshot,new File(path));
        test.addScreenCaptureFromPath(path);
        }
        reports.flush();
        driver.quit();
    }
}
