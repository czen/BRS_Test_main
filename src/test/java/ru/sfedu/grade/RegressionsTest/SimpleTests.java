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

    private String url1;
    private String url2;
    private String url3;
    private String url4;

    /** \brief Чтение конфиг файла. Инициализация драайвера. Установка ожиданий.
     *
     * Этот метод вызывается перед выполнением всех функций этого класса, т.е. тестов.
     *
     *  По-умолчанию используется браузер хром. Xml файлом можно настраивать запуск в разных браузерах
     *  (следует тогда запускать именно его, а не класс или проект)
     * @param browser Передается из xml-файла для выбора браузера, в котором запустятся тесты. По-умолчанию =  chrome
     * @see Helpers::timeouts_set, Helper::read_propities, Helper::initialization_driver, tearDown
     */
    @Parameters("browser")
    @BeforeClass
    public void getDriver(@Optional("chrome") String browser) {
        read_propities();
        initialization_driver(browser);
        timeouts_set();

          url1=get_base_url()+"";
          url2=get_base_url()+"remind";
          url3=get_base_url()+"sign/in";
          url4=get_base_url()+"sign/up";
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
