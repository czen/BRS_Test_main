package ufy.mmcs.brs.AuthorizationTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
//import Helpers;

/**
 * \brief Тесты формы авторизации
 *
 * Тест-кейс:
 * 1. Открыть страницу
 * 2. Ввести неверные данные
 *
 * Ожидается: окно с информацией об ошибке
 * @version 1.0
 * @author Stepanova
 * @see AuthorizationTest, Helper
 */
public class AuthorizationFormTest extends Helper{

   /* private WebDriver driver;
    private WebDriverWait wait;
    private Helper hhelp;*/
    /*  @BeforeClass // Runs this method before the first test method in the current class is invoked
      public void setUp() {
          // Create a new instance of the Firefox driver
          System.setProperty("webdriver.gecko.driver","C:\\Users\\Workstation\\Desktop\\Drivers\\geckodriver.exe");

          driver = new FirefoxDriver();
          driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      }*/

    /**\brief Инициализация
     *
     * Runs this method before the first test method in the current class is invoked.
     * Инициализация драйвера и установка неявных ожиданий. По-умолчанию используется браузер хром.
     *
     * @param browser Выбор браузера ля запуска тестов: chrome, firefox
     * @see tearDown, Helper::timeouts_set, Helper::get_chrome_driver
     */
    @Parameters("browser")
    @BeforeClass// @BeforeTest
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

    /*  @Test // Marking this method as part of the test
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
       } */

    /** \brief Случай с пустыми полями
     *
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Перейти на авторизацию по логну\паролю
     * 3. Оставить все поля пустыми
     * 4. Нажать войти
     *
     * Ожидается:
     * Окно с ошибкой
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
            //элемент окно с ошибкой
            error_text = driver.findElement(By.xpath("/html/body/div[3]/div")).getText();
         /*   if(hhelp.IsElementVisible(By.id("username")))
                Assert.fail("Выполнен вход в аккаунт");*/
         //Можно оставить, время выполнения увеличится, покроется еще один случай, но только в этом месте
            Assert.assertEquals(error_text, "Неверный логин и/или пароль!","Не соответсвует текст ошибки");
        }
        else{
            exit();
            Assert.fail("Нет сообщения об ошибке");
        }
    }

    /** \brief Случай с пустым паролем
     *
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Перейти на авторизацию по логну\паролю
     * 3. Оставить поле пароля пустым
     * 4. Ввести валидный логин
     * 5. Нажать войти
     *
     * Ожидается:
     * Окно с ошибкой
     */
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

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Перейти на авторизацию по логну\паролю
     * 3. Оставить поле логина пустым
     * 4. Ввести валидный пароль
     * 5. Нажать войти
     *
     * Ожидается:
     * Окно с ошибкой
     */
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

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Перейти на авторизацию по логну\паролю
     * 3. Ввести невалидный логин
     * 4. Ввести невалидный пароль
     * 5. Нажать войти
     *
     * Ожидается:
     * Окно с ошибкой
     */
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

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Перейти на авторизацию по логну\паролю
     * 3. Ввести невалидный логин
     * 4. Поле пароль оставить пустым
     * 5. Нажать войти
     *
     * Ожидается:
     * Окно с ошибкой
     */
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

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Перейти на авторизацию по логну\паролю
     * 3. Ввести невалидный пароль
     * 4. Поле логин оставить пустым
     * 5. Нажать войти
     *
     * Ожидается:
     * Сообщение об ошибке
     */
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

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Перейти на авторизацию по логну\паролю
     * 3. Ввести валидный пароль
     * 4. Ввести невалидный логин
     * 5. Нажать войти
     *
     * Ожидается:
     * Сообщение об ошибке
     */
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

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Перейти на авторизацию по логну\паролю
     * 3. Ввести валидный логин
     * 4. Ввести невалидный пароль
     * 5. Нажать войти
     *
     * Ожидается:
     * Сообщение об ошибке
     */
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

    /** \brief Close all browser windows and safely end the session
     *
     * Runs this method after all the test methods in the current class have been run
     * @see getDriver
     */
    @AfterClass // Runs this method after all the test methods in the current class have been run
    public void tearDown() {
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
            driver=null;}
    }
}

