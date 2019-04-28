package ufy.mmcs.brs.InputFormTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class FormTest {
    protected WebDriver driver;

  /*  @BeforeClass // Runs this method before the first test method in the current class is invoked
    public void setUp() {
        // Create a new instance of the Firefox driver
        System.setProperty("webdriver.gecko.driver","C:\\Users\\Workstation\\Desktop\\Drivers\\geckodriver.exe");

        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }*/
    @Parameters("browser")
    @BeforeClass// @BeforeTest
    protected void  /* WebDriver*/ getDriver(@Optional("chrome") String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "D:\\MyWork\\Drivers\\chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "D:\\MyWork\\Drivers\\geckodriver.exe");
            driver = new FirefoxDriver();
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test // Marking this method as part of the test
    public void with_input_into_form() {
        driver.get("http://testgrade.sfedu.ru/");
        driver.findElement(By.id("grade")).click();

        driver.findElement(By.id("login")).sendKeys("ELLA");
        driver.findElement(By.id("password")).sendKeys("11111");
        driver.findElement(By.id("signin_b")).click();

        String user_name = driver.findElement(By.id("username")).getText();

        Assert.assertEquals(user_name, "Элла Кораблина");
        driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[3]/a[2]")).click();   // fa fa-sign-out fa-bg fa-fw
    }

    @Test // Marking this method as part of the test
    public void without_login_pswd_into_form() {
        driver.get("http://testgrade.sfedu.ru/");
        driver.findElement(By.id("grade")).click();

       // driver.findElement(By.id("login")).sendKeys("ELLA");
     //   driver.findElement(By.id("password")).sendKeys("11111");
        driver.findElement(By.id("signin_b")).click();

      //  String user_name = driver.findElement(By.className("EventItem error")).getText();
        String user_name = driver.findElement(By.xpath("/html/body/div[3]/div")).getText();
        Assert.assertEquals(user_name, "Неверный логин и/или пароль!");
    }
    @Test // Marking this method as part of the test
    public void without_pswd_into_form() {

        driver.get("http://testgrade.sfedu.ru/");
        driver.findElement(By.id("grade")).click();

        driver.findElement(By.id("login")).sendKeys("ELLA");
     //   driver.findElement(By.id("password")).sendKeys("11111");
        driver.findElement(By.id("signin_b")).click();

    //    String user_name = driver.findElement(By.className("EventItem error")).getText();
        String user_name = driver.findElement(By.xpath("/html/body/div[3]/div")).getText();
        Assert.assertEquals(user_name, "Неверный логин и/или пароль!");
    }

    @Test // Marking this method as part of the test
    public void without_login_into_form() {

        driver.get("http://testgrade.sfedu.ru/");
        driver.findElement(By.id("grade")).click();

       // driver.findElement(By.id("login")).sendKeys("ELLA");
        driver.findElement(By.id("password")).sendKeys("11111");
        driver.findElement(By.id("signin_b")).click();

     //   String user_name = driver.findElement(By.className("EventItem error")).getText();
        String user_name = driver.findElement(By.xpath("/html/body/div[3]/div")).getText();
        Assert.assertEquals(user_name, "Неверный логин и/или пароль!");
    }

    @Test // Marking this method as part of the test
    public void with_wrong_into_form() {

        driver.get("http://testgrade.sfedu.ru/");
        driver.findElement(By.id("grade")).click();

        driver.findElement(By.id("login")).sendKeys("EA");
        driver.findElement(By.id("password")).sendKeys("111");
        driver.findElement(By.id("signin_b")).click();

       // String user_name = driver.findElement(By.className("EventItem error")).getText();
        String user_name = driver.findElement(By.xpath("/html/body/div[3]/div")).getText();
        Assert.assertEquals(user_name, "Неверный логин и/или пароль!");
    }

    @AfterClass // Runs this method after all the test methods in the current class have been run
    public void tearDown() {
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
        driver=null;}
    }
}
