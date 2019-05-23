package ru.sfedu.grade.RegressionsTest;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

/** \brief Переход по ссылкам без авторизации

 * @version 1.0
 * @author Stepanova
 * @see Helpers, ForTeacherAccaunt, ForStudentAccaunt, ForDekanatAccaunt
 */
public class SimpleTests extends  Helpers{

    private String url1="http://testgrade.sfedu.ru/";
    private String url2="http://testgrade.sfedu.ru/remind";
    private String url3="http://testgrade.sfedu.ru/sign/in";
    private String url4="http://testgrade.sfedu.ru/sign/up";

    /** \brief Инициализация
     *
     * Этот метод вызывается перед выполнением всех функций этого класса
     *
     * Инициализация драйвера браузера. По-умолчанию - хром. Установка ожиданий.
     * @param browser Передается из xml-файла для выбора браузера, в котором запустятся тесты. По-умолчанию - chrom
     * @see Helpers::timeouts_set, Helpers::get_chrome_driver, Helpers::get_firefox_driver, tearDown
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

    @Test
    public void go_to_home(){
        driver.navigate().to(url1);
        Boolean flag=IsElementVisible(By.id("signopenidin_b"));
        Assert.assertTrue(flag,"Не загрузилась страница");
    }

    @Test
    public void go_to_remind(){
        driver.navigate().to(url2);
        Boolean flag=IsElementVisible(By.id("remind"));
        Assert.assertTrue(flag,"Не загрузилась страница");
    }

    @Test
    public void go_to_sign_in(){
        driver.navigate().to(url3);
        Boolean flag=IsElementVisible(By.id("signopenidin_b"));
        Assert.assertTrue(flag,"Не загрузилась страница");
    }

    @Test
    public void go_to_sign_up(){
        driver.navigate().to(url4);
        Boolean flag=IsElementVisible(By.id("signup_b"));
        Assert.assertTrue(flag,"Не загрузилась страница");
    }
}
