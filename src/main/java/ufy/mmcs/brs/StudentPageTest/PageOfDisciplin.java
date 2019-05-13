package ufy.mmcs.brs.StudentPageTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

/**\brief Тесты страницы дисциплины у студента
 *
 * @version 1.0
 * @author Stepanova
 * @see Helper
 */
public class PageOfDisciplin  extends Helper{
    /** \brief Инициализация
     *
     * Этот метод вызывается перед выполнением всех функций этого класса
     *
     * Инициализация драйвера браузера. По-умолчанию - хром. Установка ожиданий. Авторизация под студентом.
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
        go_home();
        if_grade_visiable();
        authorization("student");
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
     * Тест-кейс:
     * 1. Открыть страницу с дисциплинами
     * 2. Нажать на элемент таблицы

     Ожидается: Загрузилась страница этой дисципплины
     */
    @Test
    public void go_to_balls(){
        go_home();
        List<WebElement> strs=driver.findElements(By.className("disciplineRow"));
        Assert.assertTrue(!strs.isEmpty(),"В таблице нет дисциплин");
        if (!strs.get(1).findElement(By.className("discTitle")).isDisplayed())
            Assert.fail("Нет ссылки в таблиц на дисциплину ");
        strs.get(1).findElement(By.className("discTitle")).click();
        //IsElementVisible(By.className("blockTitle"));
        Assert.assertEquals(driver.findElement(By.className("main_top")).getText(),
                "Учебная карта дисциплины","Загрузилась не та страница ");
    }

    /**
     *Тест-кейс:
     1. Зайти на страницу дисциплины
     2. Нажать Журнал

     Ожидается: Загрузилась страница журнала
     */
    @Test
    public void go_to_journal(){
        driver.navigate().to("http://testgrade.sfedu.ru/student/discipline/13337");
        //List<WedElement> strs=driver.findElements(By.className("disciplineRow"));
//	Assert.assertTrue(strs.isNull(),"В таблице нет дисуиплин");

/*	if(
	strs.get(0).findElement(By.className("discTitle")).isDisplayed())
	Asser.fail("Нет ссылки в таблиц на дисциплину");

	strs.get(0).findElement(By.className("discTitle")).click();*/

        Assert.assertEquals(driver.findElement(By.className("main_top")).getText(),
                "Учебная карта дисциплины","He Загрузилась страница");

        if(!IsElementVisible(By.xpath("/html/body/div[1]/div[4]/div[1]/div[2]/div[4]/div/div[2]")))
            Assert.fail("нет кнопки Журнал посещений");

        driver.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div[2]/div[4]/div/div[2]")).click(); //кнопка журанл
        if(!IsElementVisible(By.className("blockTitle")))
            Assert.fail("Не загрузилась страница/нет элемента");

        Assert.assertEquals(driver.findElement(By.className("blockTitle")).getText(),
                "Журнал посещений","Загрузился не журнал посещений");

    }

    /**
     * Тест-кейс:
     1. Открыть страницу дисциплины
     2. Нажать Журнал
     3. Нажать Баллы

     Ожидается: Загрузилась страница с баллами
     */
    @Test
    public void go_to_baals_after_journal(){
        driver.navigate().to("http://testgrade.sfedu.ru/student/discipline/13337/journal");
        Assert.assertEquals(driver.findElement(By.className("blockTitle")).getText(),
                "Журнал посещений","Загрузился не журнал посещений");
        if(!IsElementVisible(By.xpath("/html/body/div[1]/div[4]/div[1]/div[2]/div[4]/div/div[1]")))
            Assert.fail("Нет кнопки Баллы");
        driver.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div[2]/div[4]/div/div[1]")).click(); //кнопка баллы
        if(!IsElementVisible(By.className("pageTitle")))
            Assert.fail("Не загрузилась страница");
        Assert.assertEquals(driver.findElement(By.className("pageTitle")).getText(),"Базы данных","Загрузилась не та страница");
    }

}
