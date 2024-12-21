package HealthCareMgtProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class Login_Page_exceldata {
    WebDriver driver;
    @BeforeTest
    public void setup()
    {
        driver=new EdgeDriver();
        driver.get("https://katalon-demo-cura.herokuapp.com/profile.php#login");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test(priority = 2)
    public void test_Login_Positive()
    {
        WebElement txtusername = driver.findElement(By.id("txt-username"));
        txtusername.clear();
        txtusername.sendKeys("John Doe");
        WebElement txtpassword =driver.findElement(By.id("txt-password"));
        txtpassword.clear();
        txtpassword.sendKeys("ThisIsNotAPassword");
        WebElement btnsubmit =driver.findElement((By.id("btn-login")));
        btnsubmit.click();
        String url="https://katalon-demo-cura.herokuapp.com/#appointment";
        try{
            Assert.assertEquals(driver.getCurrentUrl(),url);
        }
        catch (Exception e)
        {
            System.out.println("Login Failed");
        }
    }

    /*@DataProvider(name="loginData")
    public String[][] testDataExcel() throws IOException {
        String testDataFile="HealthCareTestData.xlsx";
        ExcelReader excelReader=new ExcelReader(testDataFile);
        String[][] data=excelReader.getDataFromSheet(testDataFile,"LoginData");
        return data;
    }*/

    @DataProvider(name="dp")
    Object[][] loginData()
    {
        Object data[][]= {
                {"abc@gmail.com","test123"},
                {"John Doe"," "},
                {" ","ThisIsNotAPassword"},
                {"@@@@&&***","@@@@&&***"},
                {"",""},
                {" ", " "},
        };
        return data;
    }

    @Test(dataProvider="dp",priority = 1)
    public void test_Login_Negative1(String username,String password)
    {
        WebElement txtusername = driver.findElement(By.id("txt-username"));
        txtusername.clear();
        txtusername.sendKeys(username);
        WebElement txtpassword =driver.findElement(By.id("txt-password"));
        txtpassword.clear();
        txtpassword.sendKeys(password);
        WebElement btnsubmit =driver.findElement((By.id("btn-login")));
        btnsubmit.click();

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement lblmsg=driver.findElement(By.xpath("//p[@class='lead text-danger']"));
        wait.until(d -> lblmsg.isDisplayed());
        String msg= lblmsg.getText();

        String expectedmsg="Login failed! Please ensure the username and password are valid.";
        try
        {
            Assert.assertEquals(msg,expectedmsg);
        }
        catch (Exception e)
        {
            System.out.println("Message failed to appear");
        }

    }

    @Test(priority = 0)
    public void test_Login_Negative2()
    {
        WebElement btnsubmit =driver.findElement((By.id("btn-login")));
        btnsubmit.click();

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement lblmsg=driver.findElement(By.xpath("//p[@class='lead text-danger']"));
        wait.until(d -> lblmsg.isDisplayed());
        String msg= lblmsg.getText();

        String expectedmsg="Login failed! Please ensure the username and password are valid.";
        try
        {
            Assert.assertEquals(msg,expectedmsg);
        }
        catch (Exception e)
        {
            System.out.println("Message failed to appear");
        }

    }
    @AfterMethod
    public void teardown()
    {
        driver.quit();
        //https://github.com/drahem/cura-healthcare-sercive-/commit/4184927427321fb01c0f24212a1886fdc6ad525c#diff-fe3662cf79ef680835dd126899939624ce9b8a4bd9ff95a76726f359cdfa0351
    }

}
