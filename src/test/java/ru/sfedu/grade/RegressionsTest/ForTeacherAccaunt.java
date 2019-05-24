package ru.sfedu.grade.RegressionsTest;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

/** \brief Переход по ссылкам на преподовательском аккаунте

 * @version 1.0
 * @author Stepanova
 * @see Helpers, SimpleTests, ForStudentAccaunt, ForDekanatAccaunt
 */
public class ForTeacherAccaunt extends Helpers{
    //Переместить их в инициализирующий метод и можно написать такие же тесты...

  /*  private String url1=get_base_url()+"";
    private String url2=get_base_url()+"discipline/13406/rate";
    private String url3=get_base_url()+"discipline/13406/exam";
    private String url4=get_base_url()+"discipline/13406/journal";
    private String url5=get_base_url()+"discipline/13406/structure";
    private String url6=get_base_url()+"discipline/13406/settings";
    private String url7=get_base_url()+"discipline/13406/teachers";
    private String url8=get_base_url()+"discipline/13406/groups";
    private String url9=get_base_url()+"discipline/13406/students";
    private String url10=get_base_url()+"office";
    private String url11=get_base_url()+"office/reports/bill";
    private String url12=get_base_url()+"discipline/3723/rate";
    private String url13=get_base_url()+"discipline/3723/exam";
    private String url14=get_base_url()+"discipline/3723/journal";
    private String url15=get_base_url()+"discipline/3723/structure";
    private String url16=get_base_url()+"discipline/3723/settings";
    private String url17=get_base_url()+"discipline/3723/teachers";
    private String url18=get_base_url()+"discipline/3723/groups";
    private String url19=get_base_url()+"discipline/3723/students";
    private String url20=get_base_url()+"discipline/3666/rate"; //zachet
    private String url21=get_base_url()+"discipline/3666/exam";
    private String url22=get_base_url()+"discipline/3666/structure";
    private String url23=get_base_url()+"discipline/3666/settings";
    private String url24=get_base_url()+"discipline/3666/teachers";*/

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
        authorization("teacher");
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


}
