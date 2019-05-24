package ru.sfedu.grade.RegressionsTest;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

/** \brief Переход по ссылкам на студенческом аккаунте

 * @version 1.0
 * @author Stepanova
 * @see Helpers, SimpleTests, ForTeacherAccaunt, ForDekanatAccaunt
 */
public class ForStudentAccaunt extends Helpers{

    private String url1;
    private String url2;
    private String url3;
    private String url4;
    private String url5;

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
        go_home();
        authorization("student");

        url1=get_base_url()+"";
        url2=get_base_url()+"student/discipline/13337";
        url3=get_base_url()+"student/discipline/13337/journal";
        url4=get_base_url()+"student/discipline/3760";
        url5=get_base_url()+"student/discipline/3760/journal";
    }

    /** \brief Завершение работы
     *
     * Runs this method after all the test methods in the current class have been run.
     * Close all browser windows and safely end the session
     *
     * Закрытие браузера
     * @see getDriver
     */
    @AfterClass // Runs this method after all the test methods in the current class have been run
    public void tearDown() {
        exit();
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
            driver=null;}
    }

    @Test
    public void do_to_home(){
        driver.navigate().to(url1);
        Boolean flag=IsElementVisible(By.className("main_top"));
        Assert.assertTrue(flag,"Не загрузилась страница " + url1);
    }

    @Test
    public void do_to_zach_marks(){
        driver.navigate().to(url2);
        Boolean flag=IsElementVisible(By.className("blockTitle"));
        if(flag)
            if(!driver.findElement(By.className("blockTitle")).getText().equals("Баллы за семестр"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url2);
    }

    @Test
    public void do_to_zach_jurnal(){
        driver.navigate().to(url3);
        Boolean flag=IsElementVisible(By.className("blockTitle"));
        if(flag)
            if(!driver.findElement(By.className("blockTitle")).getText().equals("Журнал посещений"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url3);
    }

    @Test
    public void do_to_exam_marks(){
        driver.navigate().to(url4);
        Boolean flag=IsElementVisible(By.className("blockTitle"));
        if(flag)
            if(!driver.findElement(By.className("blockTitle")).getText().equals("Баллы за семестр"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница " +url4);
    }

    @Test
    public void do_to_exam_journal(){
        driver.navigate().to(url5);
        Boolean flag=IsElementVisible(By.className("blockTitle"));
        if(flag)
            if(!driver.findElement(By.className("blockTitle")).getText().equals("Журнал посещений"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница " +url5);
    }



}
