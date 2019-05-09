package ufy.mmcs.brs.Authorization_test;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import ufy.mmcs.brs.AuthorizationTest.Helper;

public class AuthorizationForm_test extends Helper {

    @Parameters("browser")
    @BeforeClass// @BeforeTest
    protected void  /* WebDriver*/ getDriver(@Optional("chrome") String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", get_chrome_driver());
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", get_firefox_driver());
            driver = new FirefoxDriver();
        }

        //  hhelp = new Helper(driver);
        //  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //  wait=new WebDriverWait(driver, 20);
        timeouts_set();
    }


    /*   @Test // Marking this method as part of the test
       public void with_input_into_form() {
          // driver.get("http://testgrade.sfedu.ru/");
           //driver.findElement(By.id("grade")).click();
           hhelp.go_home();
           hhelp.if_grade_visiable();
           wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
           driver.findElement(By.id("login")).sendKeys("ELLA");
           driver.findElement(By.id("password")).sendKeys("22222");
           driver.findElement(By.id("signin_b")).click();
           wait.until(ExpectedConditions.textToBe(By.className("main_top"),"Дисциплины"));
           String user_name = driver.findElement(By.id("username")).getText();

           //driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[3]/a")).click();   // fa fa-sign-out fa-bg fa-fw
           hhelp.exit();

           Assert.assertEquals(user_name, "Элла Кораблина");
       }
   */
    @Test // Marking this method as part of the test
    public void non_login_non_pswd_into_form() {
        go_home();
        if_grade_visiable();

        //driver.findElement(By.id("login")).sendKeys("ELLA");
        //driver.findElement(By.id("password")).sendKeys("22222");
        driver.findElement(By.id("signin_b")).click();

        String error_text;
        if(IsElementVisible(By.xpath("/html/body/div[3]/div"))) {
            error_text = driver.findElement(By.xpath("/html/body/div[3]/div")).getText();
         /*   if(hhelp.IsElementVisible(By.id("username")))
                Assert.fail("Выполнен вход в аккаунт");*/ //Можно оставить, время выполнения увеличится, покроется еще один случай, но только в этом месте
            Assert.assertEquals(error_text, "Неверный логин и/или пароль!");
        }
        else{
            exit();
            Assert.fail("Нет сообщения об ошибке");
        }
    }

    @Test // Marking this method as part of the test
    public void right_login_non_pswd_into_form() {
        go_home();
        if_grade_visiable();

        driver.findElement(By.id("login")).sendKeys("ELLA");
        //driver.findElement(By.id("password")).sendKeys("22222");
        driver.findElement(By.id("signin_b")).click();

        String error_text;
        if(IsElementVisible(By.xpath("/html/body/div[3]/div"))) {
            error_text = driver.findElement(By.xpath("/html/body/div[3]/div")).getText(); //EventItem error
            /*if(hhelp.IsElementVisible(By.id("username")))
                Assert.fail("Выполнен вход в аккаунт");*/
            Assert.assertEquals(error_text, "Неверный логин и/или пароль!");
        }
        else{
            exit();
            Assert.fail("Нет сообщения об ошибке");
        }
    }

    @Test // Marking this method as part of the test
    public void non_login_right_pwd_into_form() {
        go_home();
        if_grade_visiable();// driver.findElement(By.id("grade")).click();

        //driver.findElement(By.id("login")).sendKeys("ELLA");
        driver.findElement(By.id("password")).sendKeys("22222");
        driver.findElement(By.id("signin_b")).click();

        String error_text;
        if(IsElementVisible(By.xpath("/html/body/div[3]/div"))) {
            error_text = driver.findElement(By.xpath("/html/body/div[3]/div")).getText(); //EventItem error
           /* if(hhelp.IsElementExists(By.id("username")))
                Assert.fail("Выполнен вход в аккаунт");*/
            Assert.assertEquals(error_text, "Неверный логин и/или пароль!");
        }
        else{
            exit();
            Assert.fail("Нет сообщения об ошибке");
        }
    }

    @Test // Marking this method as part of the test
    public void wrong_login_wrong_pwd_into_form() {
        go_home();
        if_grade_visiable();

        driver.findElement(By.id("login")).sendKeys("EL");
        driver.findElement(By.id("password")).sendKeys("2222");
        driver.findElement(By.id("signin_b")).click();

        String error_text;
        if(IsElementVisible(By.xpath("/html/body/div[3]/div"))) {
            error_text = driver.findElement(By.xpath("/html/body/div[3]/div")).getText(); //EventItem error
           /* if(hhelp.IsElementVisible(By.id("username")))
                Assert.fail("Выполнен вход в аккаунт");*/
            Assert.assertEquals(error_text, "Неверный логин и/или пароль!");
        }
        else{
            exit();
            Assert.fail("Нет сообщения об ошибке");
        }
    }

    @Test // Marking this method as part of the test
    public void wrong_login_non_pwd_into_form() {
        go_home();
        if_grade_visiable();

        driver.findElement(By.id("login")).sendKeys("EL");
        //driver.findElement(By.id("password")).sendKeys("2222");
        driver.findElement(By.id("signin_b")).click();

        String error_text;
        if(IsElementVisible(By.xpath("/html/body/div[3]/div"))) {
            error_text = driver.findElement(By.xpath("/html/body/div[3]/div")).getText(); //EventItem error
           /* if(hhelp.IsElementVisible(By.id("username")))
                Assert.fail("Выполнен вход в аккаунт");*/
            Assert.assertEquals(error_text, "Неверный логин и/или пароль!");
        }
        else{
            exit();
            Assert.fail("Нет сообщения об ошибке");
        }
    }

    @Test // Marking this method as part of the test
    public void non_login_wrong_pwd_into_form() {
        go_home();
        if_grade_visiable();

        //driver.findElement(By.id("login")).sendKeys("EL");
        driver.findElement(By.id("password")).sendKeys("2222");
        driver.findElement(By.id("signin_b")).click();

        String error_text;
        if(IsElementVisible(By.xpath("/html/body/div[3]/div"))) {
            error_text = driver.findElement(By.xpath("/html/body/div[3]/div")).getText(); //EventItem error
           /* if(hhelp.IsElementVisible(By.id("username")))
                Assert.fail("Выполнен вход в аккаунт");*/
            Assert.assertEquals(error_text, "Неверный логин и/или пароль!");
        }
        else{
            exit();
            Assert.fail("Нет сообщения об ошибке");
        }
    }

    @Test // Marking this method as part of the test
    public void wrong_login_right_pwd_into_form() {
        go_home();
        if_grade_visiable();

        driver.findElement(By.id("login")).sendKeys("EL");
        driver.findElement(By.id("password")).sendKeys("22222");
        driver.findElement(By.id("signin_b")).click();

        String error_text;
        if(IsElementVisible(By.xpath("/html/body/div[3]/div"))) {
            error_text = driver.findElement(By.xpath("/html/body/div[3]/div")).getText(); //EventItem error
            /*if(hhelp.IsElementVisible(By.id("username")))
                Assert.fail("Выполнен вход в аккаунт");*/
            Assert.assertEquals(error_text, "Неверный логин и/или пароль!");
        }
        else{
            exit();
            Assert.fail("Нет сообщения об ошибке");
        }
    }

    @Test // Marking this method as part of the test
    public void right_login_wrong_pwd_into_form() {
        go_home();
        if_grade_visiable();

        driver.findElement(By.id("login")).sendKeys("ELLA");
        driver.findElement(By.id("password")).sendKeys("2222");
        driver.findElement(By.id("signin_b")).click();

        String error_text;
        if(IsElementVisible(By.xpath("/html/body/div[3]/div"))) {
            error_text = driver.findElement(By.xpath("/html/body/div[3]/div")).getText(); //EventItem error
           /* if(hhelp.IsElementVisible(By.id("username")))
                Assert.fail("Выполнен вход в аккаунт");*/
            Assert.assertEquals(error_text, "Неверный логин и/или пароль!");
        }
        else{
            exit();
            Assert.fail("Нет сообщения об ошибке");
        }
    }

    @AfterClass // Runs this method after all the test methods in the current class have been run
    public void tearDown() {
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
            driver=null;}
    }
}



