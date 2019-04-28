package ufy.mmcs.brs.UnauthorizedPageTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.swing.text.StyledEditorKit;

public class FooterLinks extends Helper
{
    @Parameters("browser")
    @BeforeClass
    protected void  /* WebDriver*/ getDriver(@Optional("chrome") String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", get_chrome_driver());
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", get_firefox_driver());
            driver = new FirefoxDriver();
        }

        //hhelp = new Helpers(driver);
        //wait=new WebDriverWait(driver, 20);
        timeouts_set();

    }

    @AfterClass
    public void tearDown() {
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
            driver=null;}
    }

    @Test
    public void go_to_forgotten_pwd(){
        go_home();
        if(!IsElementVisible(By.xpath("//*[@id=\"GradeAuthDiv\"]/div[2]/a[2]")))
            Assert.fail("Нет элемента Забыли пароль");
        WebElement href=driver.findElement(By.xpath("//*[@id=\"GradeAuthDiv\"]/div[2]/a[2]"));
        Assert.assertEquals(href.getText(),"Забыли пароль?","Не соответсвет текст");
        href.click();
        Assert.assertTrue(IsElementVisible(By.id("remind")),"Не появилась кнопка Восстановить");
    }

    @Test
    public void go_to_home_after_forgotten_pwd(){
        go_home();
        if(!IsElementVisible(By.xpath("//*[@id=\"GradeAuthDiv\"]/div[2]/a[2]")))
            Assert.fail("Нет элемента Забыли пароль");
        WebElement href=driver.findElement(By.xpath("//*[@id=\"GradeAuthDiv\"]/div[2]/a[2]"));
        // Assert.assertEquals(href.getText(),"Забыли пароль?","е соответсвет текст");
        href.click();
        //Assert.assertTrue(IsElementVisible(By.id("remind")),"Не появилась кнопка Восстановить");
        Assert.assertTrue(IsElementVisible(By.xpath("//*[@id=\"wrap\"]/div[2]/div[1]/div/div[1]/div[2]/a")),"Нет элемента перехода по сссылке домой");
        WebElement ref=driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[1]/div/div[1]/div[2]/a"));
        Assert.assertEquals(ref.getText(),"Я вспомнил! Просто дайте мне войти.","Не соответсвует текст");
        ref.click();
        Assert.assertTrue(IsElementVisible(By.id("signin_b")),"Не появилась кнопка Восстановить");
    }

    @Test
    public void go_to_activ_akk(){
        go_home();
        if(!IsElementVisible(By.xpath("//*[@id=\"GradeAuthDiv\"]/div[2]/a[1]")))
            Assert.fail("Нет элемента Активировать аккаунт");
        WebElement href=driver.findElement(By.xpath("//*[@id=\"GradeAuthDiv\"]/div[2]/a[1]"));
        Assert.assertEquals(href.getText(),"Активировать аккаунт","Не соответсвет текст");
        href.click();
        Assert.assertTrue(IsElementVisible(By.id("signup_b")),"Не появилась кнопка Восстановить");
    }

    @Test
    public void go_to_home_after_activ_akk(){
        go_home();
        if(!IsElementVisible(By.xpath("//*[@id=\"GradeAuthDiv\"]/div[2]/a[1]")))
            Assert.fail("Нет элемента Активировать аккаунт");
        WebElement href=driver.findElement(By.xpath("//*[@id=\"GradeAuthDiv\"]/div[2]/a[1]"));
        // Assert.assertEquals(href.getText(),"Активировать аккаунт","Не соответсвет текст");
        href.click();
        //Assert.assertTrue(IsElementVisible(By.id("signup_b")),"Не появилась кнопка Восстановить");
        Assert.assertTrue(IsElementVisible(By.xpath("//*[@id=\"wrap\"]/div[2]/div[1]/div/div[1]/div/a")),"Нет элемента перехода по сссылке домой");
        WebElement ref=driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[1]/div/div[1]/div/a"));
        Assert.assertEquals(ref.getText(),"Войти в существующий аккаунт","Не соответсвует текст");
        ref.click();
        Assert.assertTrue(IsElementVisible(By.id("signin_b")),"Не появилась кнопка Восстановить");
    }

    @Test
    public void activ_akk_inputs(){
        driver.navigate().to("http://testgrade.sfedu.ru/sign/up");
        String errors="";
        Boolean flag=false;
        if (!IsElementVisible(By.id("activation_code")))
        {
            flag=true;
            errors+="Нет элемента код активации. ";
        }
        if (!IsElementVisible(By.id("login")))
        {
            flag=true;
            errors+="Нет элемента login. ";
        }
        if (!IsElementVisible(By.id("password")))
        {
            flag=true;
            errors+="Нет элемента password. ";
        }
        if (!IsElementVisible(By.id("confirm_password")))
        {
            flag=true;
            errors+="Нет элемента confirm_password. ";
        }
        if (!IsElementVisible(By.id("email")))
        {
            flag=true;
            errors+="Нет элемента email. ";
        }

        if (!IsElementVisible(By.id("tab-news")))
        {
            flag=true;
            errors+="Нет элемента tab-news. ";
        }
        if (!IsElementVisible(By.id("tab-updates")))
        {
            flag=true;
            errors+="Нет элемента tab-updates. ";
        }
        if (!IsElementVisible(By.id("tab-syncs")))
        {
            flag=true;
            errors+="Нет элемента tab-syncs. ";
        }

        Assert.assertTrue(!flag,errors);
    }

    @Test
    public void forgotten_pwd_inputs(){
        driver.navigate().to("http://testgrade.sfedu.ru/remind");
        String errors="";
        Boolean flag=false;

        if (!IsElementVisible(By.id("email")))
        {
            flag=true;
            errors+="Нет элемента email. ";
        }

        if (!IsElementVisible(By.id("tab-news")))
        {
            flag=true;
            errors+="Нет элемента tab-news. ";
        }
        if (!IsElementVisible(By.id("tab-updates")))
        {
            flag=true;
            errors+="Нет элемента tab-updates. ";
        }
        if (!IsElementVisible(By.id("tab-syncs")))
        {
            flag=true;
            errors+="Нет элемента tab-syncs. ";
        }

        Assert.assertFalse(flag,errors);
    }

    //не работает. фиг знает почему
    @Test
    public void forgotten_pwd_page_click(){
        driver.navigate().to("http://testgrade.sfedu.ru/remind");
        String errors="";
        Boolean flag=false;

        if(!IsElementVisible(By.id(("email"))))
            Assert.fail("Нет поле для ввода емейла");

        WebElement field=driver.findElement(By.id(("email")));
        String input_chars="123";
        field.sendKeys(input_chars);

driver.findElement(By.id("remind")).click();

        String error_text;
        if(IsElementVisible(By.xpath("/html/body/div[3]/div"))) {
            error_text = driver.findElement(By.xpath("/html/body/div[3]/div")).getText(); //EventItem error
            /*if(hhelp.IsElementVisible(By.id("username")))
                Assert.fail("Выполнен вход в аккаунт");*/
            if(error_text!="Введенная строка не является e‑mail адресом!") {
                flag = true;
                errors = "Не соответсвует текст " + error_text;
            }
            //Assert.assertEquals(error_text, "Введенная строка не является e-mail адресом!");
        }
        else{
           // Assert.fail("Нет сообщения об ошибке");
            flag=true;
            errors+=" Нет сообщения об ошибке при вводе ";
            errors+=input_chars;
        }


Assert.assertFalse(flag,errors);
    }
}
