package ufy.mmcs.brs.UnauthorizedPageTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

/** \brief Тесты ссылок под формой авторизации
 *
 * @version 1.0
 * @author Stepanova
 * @see Helper, FooterLinks
 */
public class FooterLinks extends Helper
{
    /** \brief Инициализация
     *
     * Этот метод вызывается перед выполнением всех функций этого класса
     *
     * Инициализация драйвера браузера. По-умолчанию - хром. Установка ожиданий.
     * @param browser Передается из xml-файла для выбора браузера, в котором запустятся тесты. По-умолчанию = chrom
     * @see Helper::timeouts_set, Helper::get_chrome_driver, Helper::get_firefox_driver, tearDown
     */
    @Parameters("browser")
    @BeforeClass
    public void  /* WebDriver*/ getDriver(@Optional("chrome") String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", get_chrome_driver());
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", get_firefox_driver());
            driver = new FirefoxDriver();
        }
        timeouts_set();
    }

    /** \brief Завершение работы
     *
     * Runs this method after all the test methods in the current class have been run.
     * Close all browser windows and safely end the session
     *
     * Закрытие браузера
     * @see getDriver
     */
    @AfterClass
    public void tearDown() {
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
            driver=null;}
    }
    /**
     Тест-кейс:
     1. Нажать Забыли пароль
     Ожидается:
     1. Кнопка Восстановить
     */
    @Test
    public void go_to_forgotten_pwd(){
        go_home();
        if_grade_visiable();
        if(!IsElementVisible(By.xpath("//*[@id=\"GradeAuthDiv\"]/div[2]/a[2]")))
            Assert.fail("Нет элемента Забыли пароль");
        WebElement href=driver.findElement(By.xpath("//*[@id=\"GradeAuthDiv\"]/div[2]/a[3]"));
        Assert.assertEquals(href.getText(),"Забыли пароль?","Не соответсвет текст");
        href.click();
        Assert.assertTrue(IsElementVisible(By.id("remind")),"Не появилась кнопка Восстановить");
    }

    /**	Тест-кейс:
     1. Нажать Забыли пароль
     2. Нажать я вспомнил

     Ожидается: загрузилась страница аутентификации
     */
    @Test
    public void go_to_home_after_forgotten_pwd(){
        go_home();
        if_grade_visiable();

        if(!IsElementVisible(By.xpath("//*[@id=\"GradeAuthDiv\"]/div[2]/a[2]")))
            Assert.fail("Нет элемента Забыли пароль");
        WebElement href=driver.findElement(By.xpath("//*[@id=\"GradeAuthDiv\"]/div[2]/a[3]"));
        Assert.assertEquals(href.getText(),"Забыли пароль?","е соответсвет текст");
        href.click();
        //Assert.assertTrue(IsElementVisible(By.id("remind")),"Не появилась кнопка Восстановить");
        Assert.assertTrue(IsElementVisible(By.xpath("//*[@id=\"wrap\"]/div[2]/div[1]/div/div[1]/div[2]/a")),"Нет элемента перехода по сссылке домой");
        WebElement ref=driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[1]/div/div[1]/div[2]/a"));
        Assert.assertEquals(ref.getText(),"Я вспомнил! Просто дайте мне войти.","Не соответсвует текст");
        ref.click();
        Assert.assertTrue(IsElementVisible(By.id("signin_b")),"Не появилась кнопка Восстановить");
    }

    /** Тест-кейс:
     * 1. Нажать активировать аккаунт

     Ожидается: Кнопка активировать
     */
    @Test
    public void go_to_activ_akk(){
        go_home();
        if_grade_visiable();
        if(!IsElementVisible(By.xpath("//*[@id=\"GradeAuthDiv\"]/div[2]/a[2]")))
            Assert.fail("Нет элемента Активировать аккаунт");
        WebElement href=driver.findElement(By.xpath("//*[@id=\"GradeAuthDiv\"]/div[2]/a[2]"));
        Assert.assertEquals(href.getText(),"Активировать аккаунт","Не соответсвет текст");
        href.click();
        Assert.assertTrue(IsElementVisible(By.id("signup_b")),"Не появилась кнопка Активировать");
    }

    /** Тест-кейс:
     * 1. Нажать Активировать аккаунт
     * 2. Нажать Войти в существующий

     Ожидается: загрузилась страница аутентификации
     */
    @Test
    public void go_to_home_after_activ_akk(){
        go_home();
        if_grade_visiable();
        if(!IsElementVisible(By.xpath("//*[@id=\"GradeAuthDiv\"]/div[2]/a[2]")))
            Assert.fail("Нет элемента Активировать аккаунт");
        WebElement href=driver.findElement(By.xpath("//*[@id=\"GradeAuthDiv\"]/div[2]/a[2]"));
        // Assert.assertEquals(href.getText(),"Активировать аккаунт","Не соответсвет текст");
        href.click();
        //Assert.assertTrue(IsElementVisible(By.id("signup_b")),"Не появилась кнопка Восстановить");
        Assert.assertTrue(IsElementVisible(By.xpath("//*[@id=\"wrap\"]/div[2]/div[1]/div/div[1]/div/a")),"Нет элемента перехода по сссылке домой");
        WebElement ref=driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[1]/div/div[1]/div/a"));
        Assert.assertEquals(ref.getText(),"Войти в существующий аккаунт","Не соответсвует текст");
        ref.click();
        Assert.assertTrue(IsElementVisible(By.id("signin_b")),"Не появилась кнопка Восстановить");
    }

    /**
     *Тест-кейс:
     1. Перейти на страницу активации

     Ожидается: присутсвие всех инпутов и табов на странице
     */
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

    /**Тест-кейс:
     1. Перейти на страницу восстановления пароля

     Ожидается: присутсвие всех инпутов и табов на странице*/
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

    /**
     *Тест-кейс:
     1. перейти на забыли пароль
     2. Ввести не емайл

     Ожидается: Сообщение об ошибке
     */
    @Test
    public void forgotten_pwd_page_click_wrong_inputs() {
        driver.navigate().to("http://testgrade.sfedu.ru/remind");
        String errors = "";
        Boolean flag = false;

        if (!IsElementVisible(By.id(("email"))))
            Assert.fail("Нет поле для ввода емейла");

        WebElement field = driver.findElement(By.id(("email")));
        String input_chars = "123";
        field.sendKeys(input_chars);
        driver.findElement(By.id("remind")).click();

        String error_text;
        if (IsElementVisible(By.xpath("/html/body/div[3]/div"))) {
            error_text = driver.findElement(By.xpath("/html/body/div[3]/div")).getText(); //EventItem error

            if (!error_text.equals("Введенная строка не является e‑mail адресом!")) {
                flag = true;
                errors = "Не соответсвует текст " + error_text + ". ";
            }
            //Assert.assertEquals(error_text, "Введенная строка не является e-mail адресом!");
        } else {
            // Assert.fail("Нет сообщения об ошибке");
            flag = true;
            errors += " Нет сообщения об ошибке при вводе ";
            errors += input_chars + ". ";
        }
        Assert.assertFalse(flag,errors);
    }

    /**
     *Тест-кейс:
     1. Перейти на страницу восстановления пароля
     2. Ввести пустую строку в поле емайл

     Ожидается: Сообщение об ошибке
     */
    @Test
    public void forgotten_pwd_page_click_empty() {
        driver.navigate().to("http://testgrade.sfedu.ru/remind");
        String errors = "";
        Boolean flag = false;

        if (!IsElementVisible(By.id(("email"))))
            Assert.fail("Нет поле для ввода емейла");

        WebElement field = driver.findElement(By.id(("email")));
        String input_chars = "";
        field.sendKeys(input_chars);
        driver.findElement(By.id("remind")).click();

        String error_text;
        if (IsElementVisible(By.xpath("/html/body/div[3]/div"))) {
            error_text = driver.findElement(By.xpath("/html/body/div[3]/div")).getText(); //EventItem error

            if (!error_text.equals("Введенная строка не является e‑mail адресом!")) {
                flag = true;
                errors = "Не соответсвует текст " + error_text + ". ";
            }
            //Assert.assertEquals(error_text, "Введенная строка не является e-mail адресом!");
        } else {
            // Assert.fail("Нет сообщения об ошибке");
            flag = true;
            errors += " Нет сообщения об ошибке при вводе ";
            errors += input_chars + ". ";
        }
        Assert.assertFalse(flag,errors);
    }

    /**
     *Тест-кейс:
     1. Перейти на страницу восстановления пароля
     2. Ввести невалидные емайл в поле емайл

     Ожидается: Сообщение об ошибке
     */
    @Test
    public void forgotten_pwd_page_click_wrong_email(){
        driver.navigate().to("http://testgrade.sfedu.ru/remind");
        String errors="";
        Boolean flag=false;
        String error_text;
        String input_chars;

        if(!IsElementVisible(By.id(("email"))))
            Assert.fail("Нет поле для ввода емейла");

        WebElement field=driver.findElement(By.id(("email")));
        input_chars="123@mail.ru";
        field.sendKeys("");
        field.sendKeys(input_chars);
        driver.findElement(By.id("remind")).click();
        if(IsElementVisible(By.xpath("/html/body/div[3]/div"))) {
            error_text = driver.findElement(By.xpath("/html/body/div[3]/div")).getText(); //EventItem error

            if(! error_text.equals("Пользователь с таким e-mail адресом не зарегистрирован в системе!")) {
                flag = true;
                errors = "Не соответсвует текст: Пользователь с таким e-mail адресом не зарегистрирован в системе! and " + error_text+". ";
            }
        }
        else{
            // Assert.fail("Нет сообщения об ошибке");
            flag=true;
            errors+=" Нет сообщения об ошибке при вводе: ";
            errors+=input_chars+". ";
        }

        Assert.assertFalse(flag,errors);
    }
}
