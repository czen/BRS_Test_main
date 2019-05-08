package ufy.mmcs.brs.StudentPageTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class StudentBtnTest {

protected WebDriver driver;

        @Parameters("browser")
        @BeforeClass// @BeforeTest
        protected void  /* WebDriver*/ getDriver(@Optional("chrome") String browser) {
            if (browser.equals("chrome")) {
                System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
                driver = new ChromeDriver();
            } else if (browser.equals("firefox")) {
                System.setProperty("webdriver.gecko.driver", "C:\\Users\\Sara\\Desktop\\Drivers\\geckodriver.exe");
                driver = new FirefoxDriver();
            }
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebDriverWait wait=new WebDriverWait(driver, 20);


            driver.get("http://testgrade.sfedu.ru/");
            driver.findElement(By.id("grade")).click();
            driver.findElement(By.id("login")).sendKeys("ELLA");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            driver.findElement(By.id("password")).sendKeys("11111");
            driver.findElement(By.id("signin_b")).click();
        }

        @Test // Marking this method as part of the test
        public void btn_setting() {

           driver.findElement(By.id("settingsButton")).click();
         String name=   driver.findElement(By.className("window-title")).getText();
         //   driver.findElement(By.className("window-close")).click();
            driver.get("javascript:wnd.close()");
            Assert.assertEquals(name, "Настройки аккаунта");
        }

    @Test // Marking this method as part of the test
    public void btn_mail() {

        driver.findElement(By.id("errButton_img")).click();
        String name=   driver.findElement(By.className("window-title")).getText();
       // driver.findElement(By.className("window-close")).click();
        driver.get("javascript:wnd.close()");
        Assert.assertEquals(name, "Служба поддержки");
    }

    @Test // Marking this method as part of the test
    public void btn_help() {
        driver.findElement(By.id("openHelp")).click();

        driver.findElement(By.className("ECTS-A"));
        driver.findElement(By.className("ECTS-B"));
        String name=   driver.findElement(By.className("window-title")).getText();
        ///driver.wait();
    //    driver.findElement(By.className("window-shadow")).click();
        driver.get("javascript:wnd.close()");
        Assert.assertEquals(name, "Дисциплины - справка");
    }

  /*  @Test // Marking this method as part of the test
    public void btn_exit() {
        driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[3]/a[2]")).click();
        //driver.findElement(By.id("errButton_img")).click();
        String name=   driver.findElement(By.tagName("h2")).getText();
       // driver.findElement(By.className("window-close")).click();
        driver.findElement(By.id("grade")).click();
        driver.findElement(By.id("login")).sendKeys("ELLA");
        driver.findElement(By.id("password")).sendKeys("11111");
        driver.findElement(By.id("signin_b")).click();
        Assert.assertEquals(name, "Аутентификация");
    }*/

    @Test // Marking this method as part of the test
    public void btn_home() {
        driver.findElement(By.className("logotype")).click();
        Assert.assertEquals( driver.getCurrentUrl(), "http://testgrade.sfedu.ru/");
    }


    @AfterClass // Runs this method after all the test methods in the current class have been run
        public void tearDown() {
            // Close all browser windows and safely end the session
            if(driver != null)
            {
                driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[3]/a[2]")).click();
                driver.quit();
                driver=null;}
        }
    }




