package ufy.mmcs.brs.RegressionsTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

/** \brief Переход по ссылкам на преподовательском аккаунте

 * @version 1.0
 * @author Stepanova
 * @see Helpers, SimpleTests, ForStudentAccaunt, ForDekanatAccaunt
 */
public class ForTeacherAccaunt extends Helpers{
    private String url1="http://testgrade.sfedu.ru/";
    private String url2="http://testgrade.sfedu.ru/discipline/13406/rate";
    private String url3="http://testgrade.sfedu.ru/discipline/13406/exam";
    private String url4="http://testgrade.sfedu.ru/discipline/13406/journal";
    private String url5="http://testgrade.sfedu.ru/discipline/13406/structure";
    private String url6="http://testgrade.sfedu.ru/discipline/13406/settings";
    private String url7="http://testgrade.sfedu.ru/discipline/13406/teachers";
    private String url8="http://testgrade.sfedu.ru/discipline/13406/groups";
    private String url9="http://testgrade.sfedu.ru/discipline/13406/students";
    private String url10="http://testgrade.sfedu.ru/office";
    private String url11="http://testgrade.sfedu.ru/office/reports/bill";
    private String url12="http://testgrade.sfedu.ru/discipline/3723/rate";
    private String url13="http://testgrade.sfedu.ru/discipline/3723/exam";
    private String url14="http://testgrade.sfedu.ru/discipline/3723/journal";
    private String url15="http://testgrade.sfedu.ru/discipline/3723/structure";
    private String url16="http://testgrade.sfedu.ru/discipline/3723/settings";
    private String url17="http://testgrade.sfedu.ru/discipline/3723/teachers";
    private String url18="http://testgrade.sfedu.ru/discipline/3723/groups";
    private String url19="http://testgrade.sfedu.ru/discipline/3723/students";
    private String url20="http://testgrade.sfedu.ru/discipline/3666/rate"; //zachet
    private String url21="http://testgrade.sfedu.ru/discipline/3666/exam";
    private String url22="http://testgrade.sfedu.ru/discipline/3666/structure";
    private String url23="http://testgrade.sfedu.ru/discipline/3666/settings";
    private String url24="http://testgrade.sfedu.ru/discipline/3666/teachers";

    /** \brief Инициализация
     *
     * Этот метод вызывается перед выполнением всех функций этого класса
     *
     * Инициализация драйвера браузера. По-умолчанию - хром. Установка ожиданий. Авторизация под dem/22222
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
