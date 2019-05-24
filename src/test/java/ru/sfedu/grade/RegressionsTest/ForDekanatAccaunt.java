package ru.sfedu.grade.RegressionsTest;
//На странице редактирования дисциплины при увелечении высоты последнего таба увеличивать и все остальные

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

/** \brief Переход по ссылкам на студенческом аккаунте

 * @version 1.0
 * @author Stepanova
 * @see Helpers, SimpleTests, ForTeacherAccaunt, ForStudentAccaunt
 */
public class ForDekanatAccaunt extends Helpers{
    private String url1;
    private String url2;
    private String url3;
    private String url4;
    private String url5;
    private String url6;
    private String url7;
    private String url8;
    private String url9;
    private String url10;
    private String url11;
    private String url12;
    private String url13;
    private String url14;
    private String url15;
    private String url16;
    private String url17;
    private String url18;
    private String url19;
    private String url20;

    /** \brief Чтение конфиг файла. Инициализация драайвера. Установка ожиданий.
     *
     * Этот метод вызывается перед выполнением всех функций этого класса, т.е. тестов.
     *
     *  По-умолчанию используется браузер хром. Xml файлом можно настраивать запуск в разных браузерах
     *  (следует тогда запускать именно его, а не класс или проект)
     * @param browser Передается из xml-файла для выбора браузера, в котором запустятся тесты. По-умолчанию =  chrome
     * @see Helper::timeouts_set, Helper::read_propities, Helper::initialization_driver, tearDown
     */
    @Parameters("browser")
    @BeforeClass
    public void getDriver(@Optional("chrome") String browser) {
        read_propities();
        initialization_driver(browser);
        timeouts_set();
        go_home();
        authorization("dekanat");

        url1=get_base_url()+"";
        url2=get_base_url()+"discipline/13355/rate";
        url3=get_base_url()+"discipline/13355/exam";
        url4=get_base_url()+"discipline/13355/structure";
        url5=get_base_url()+"discipline/13355/settings";
        url6=get_base_url()+"discipline/13355/teachers";
        url7=get_base_url()+"discipline/13355/groups";
        url8=get_base_url()+"discipline/13355/students";
        url9=get_base_url()+"discipline/14074/structure";
        url10=get_base_url()+"discipline/14074/settings";
        url11=get_base_url()+"discipline/14074/teachers";
        url12=get_base_url()+"discipline/13358/rate";
        url13=get_base_url()+"discipline/13358/exam";
        url14=get_base_url()+"office";
        url15=get_base_url()+"office/teachers";
        url16=get_base_url()+"office/students";
        url17=get_base_url()+"office/disciplines";
        url18=get_base_url()+"office/reports/bill";
        url19=get_base_url()+"office/support";
        url20=get_base_url()+"discipline/14075/structure";
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
    public void go_to_zach_sem(){
        driver.navigate().to(url2);
        Boolean flag=IsElementVisible(By.className("main_top"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Выставление баллов"))
                Assert.fail("Не та страница "+driver.findElement(By.className("main_top")).getText());
        Assert.assertTrue(flag,"Не загрузилась страница "+url2);
    }

    @Test
    public void go_to_exam_sem(){
        driver.navigate().to(url3);
        Boolean flag=IsElementVisible(By.className("main_top"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Выставление баллов"))
                Assert.fail("Не та страница "+url3);
        Assert.assertTrue(flag,"Не загрузилась страница "+url3);
    }

    @Test
    public void go_to_redact_disciplin_modul(){
        driver.navigate().to(url4);
        Boolean flag=IsElementVisible(By.className("rateIndicatorDIV"));
        if(flag)
            if(!driver.findElement(By.className("rateIndicatorDIV")).getText().equals("Количество баллов: 100"))
                Assert.fail("Не та страница "+driver.findElement(By.className("rateIndicatorDIV")).getText()+" " +url4);
        Assert.assertTrue(flag,"Не загрузилась страница "+url4);
    }

    @Test
    public void go_to_redact_disciplin_teach(){
        driver.navigate().to(url6);
        Boolean flag=IsElementVisible(By.className("BlueTitle"));
        if(flag)
            if(!driver.findElement(By.className("BlueTitle")).getText().equals("Прикрепленные преподаватели"))
                Assert.fail("Не та страница "+url6);
        Assert.assertTrue(flag,"Не загрузилась страница "+url6);
    }

    @Test
    public void go_to_redact_disciplin_groups(){
        driver.navigate().to(url7);
        Boolean flag=IsElementVisible(By.className("BlueTitle"));
        if(flag)
            if(!driver.findElement(By.className("BlueTitle")).getText().equals("Прикрепленные группы"))
                Assert.fail("Не та страница "+url7);
        Assert.assertTrue(flag,"Не загрузилась страница "+url7);
    }

    @Test
    public void go_to_redact_disciplin_setting(){
        driver.navigate().to(url5);
        Boolean flag=IsElementVisible(By.className("select2-chosen"));
        if(flag)
            if(!driver.findElement(By.className("select2-chosen")).getText().equals("CS203. Теория алгоритмов"))
                Assert.fail("Не та страница "+url5);
        Assert.assertTrue(flag,"Не загрузилась страница "+url5);
    }



    @Test
    public void go_to_redact_disciplin_stud(){
        driver.navigate().to(url8);
        Boolean flag=IsElementVisible(By.className("main_top"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Редактирование дисциплины №13355"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url8);
    }

    @Test
    public void go_to_redact_disciplin_module(){
        driver.navigate().to(url9);
        Boolean flag=IsElementVisible(By.className("rateIndicatorDIV"));
        if(flag)
            if(!driver.findElement(By.className("rateIndicatorDIV")).getText().equals("Количество баллов: 100"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url9);
    }

    @Test
    public void go_to_redact_disciplin_settings(){
        driver.navigate().to(url10);
        Boolean flag=IsElementVisible(By.className("select2-chosen"));
        if(flag)
            if(!driver.findElement(By.className("select2-chosen")).getText().equals("CS203. Теория алгоритмов"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url10);
    }

    @Test
    public void go_to_redact_disciplin_teach1(){
        driver.navigate().to(url11);
        Boolean flag=IsElementVisible(By.className("BlueTitle"));
        if(flag)
            if(! driver.findElement(By.className("BlueTitle")).getText().equals("Прикрепленные преподаватели"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url11);
    }

    @Test
    public void go_to_zach_sem1(){
        driver.navigate().to(url12);
        Boolean flag=IsElementVisible(By.className("main_top"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Выставление баллов"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url12);
    }

    @Test
    public void go_to_exam_sem1(){
        driver.navigate().to(url13);
        Boolean flag=IsElementVisible(By.className("main_top"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Выставление баллов"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url13);
    }

    @Test
    public void go_to_redact_disciplin(){
        driver.navigate().to(url20);
        Boolean flag=IsElementVisible(By.className("rateIndicatorDIV"));
        if(flag)
            if(!driver.findElement(By.className("rateIndicatorDIV")).getText().contains("Количество баллов:"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url20);
    }

    @Test
    public void go_to_office(){
        driver.navigate().to(url14);
        Boolean flag=IsElementVisible(By.className("main_top"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Панель управления"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url14);
    }

    @Test
    public void go_to_office_teach(){
        driver.navigate().to(url15);
        Boolean flag=IsElementVisible(By.className("BlueTitle"));
        if(flag)
            if(!driver.findElement(By.className("BlueTitle")).getText().equals("Поиск преподавателей"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url15);
    }

    @Test
    public void go_to_office_stud(){
        driver.navigate().to(url16);
        Boolean flag=IsElementVisible(By.className("BlueTitle"));
        if(flag)
            if(!driver.findElement(By.className("BlueTitle")).getText().equals("Поиск студентов"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url16);
    }

    @Test
    public void go_to_office_discipl(){
        driver.navigate().to(url17);
        Boolean flag=IsElementVisible(By.className("main_top"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Панель управления"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url17);
    }


    @Test
    public void go_to_office_vedomost(){
        driver.navigate().to(url18);
        Boolean flag=IsElementVisible(By.className("main_side_content"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Панель управления"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url18);
    }

    @Test
    public void go_to_office_repotrs1(){
        driver.navigate().to(url19);
        Boolean flag=IsElementVisible(By.className("main_top"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Панель управления"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url19);
    }
}