package ufy.mmcs.brs.TeacherTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class AfterClickBtnsTest extends Helper {
    /** \brief Инициализация
     *
     * Инициализация драйвера браузера. Установка неявных ожиданий. Атоизация под аккаунтом dem\22222
     * @see ufy.mmcs.brs.AuthorizationTest.Helper ::timeouts_set, Helper::get_chrome_driver, Helper::get_firefox_driver, terarDown
     * @param browser
     */
    @Parameters("browser")
    @BeforeClass
    public void  getDriver(@Optional("chrome") String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", get_chrome_driver());
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", get_firefox_driver());
            driver = new FirefoxDriver();
        }
        timeouts_set();
        go_home();
        //  if_grade_visiable();
        authorization();
    }

    /**
     * Close all browser windows and safely end the session
     */
    @AfterClass
    public void tearDown() {
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
            driver=null;}
    }

    @Test
    public void semestr_page_tabs() {
        go_home();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_semestr(group_tables.get(1));
        btn_sem.click();
        if(!IsElementVisible(By.className("subject"))) {
            Assert.fail("Не загрузилась страница после нажатия на кнопку Семестр");
        }
        List<WebElement> btns_tab = driver.findElements(By.className("button"));
        String tab1_sem=btns_tab.get(0).getText();
        String tab2_history=btns_tab.get(1).getText();
        String tab3_journal=btns_tab.get(2).getText();

        Assert.assertEquals(tab1_sem,"Сессия", "Не соответсвует название ссылки на странице семестра");
        Assert.assertEquals(tab2_history,"История", "Не соответсвует название ссылки на странице семестра");
        Assert.assertEquals(tab3_journal,"Журнал", "Не соответсвует название ссылки на странице семестра");
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Окрыть страницу
     * 2. Нажать Семестр
     * 3. Нажать Сессия
     *
     * Ожидается: загрузилась странца сессии
     */
    @Test
    public void semestr_page_tabs_click_sessia() {
        go_home();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_semestr(group_tables.get(1));
        String type_d=get_type_subject(group_tables.get(1));
        btn_sem.click();
        if(!IsElementVisible(By.className("subject"))) {
            Assert.fail("Не загрузилась страница после нажатия на кнопку Семестр");
        }
        List<WebElement> btns_tab = driver.findElements(By.className("button"));
        btns_tab.get(0).click();
        String s=driver.findElement(By.className("subject")).getText();
        Assert.assertTrue(s.contains(type_d),"Не та страница "+type_d+" "+s);

        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Окрыть страницу
     * 2. Нажать Семестр
     * 3. Нажать История
     *
     * Ожидается: загрузилась странца истории выставления баллов
     */
    @Test
    public void semestr_page_tabs_click_history() {
        go_home();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_semestr(group_tables.get(1));
        // String type_d=get_type_subject(group_tables.get(1));
        btn_sem.click();
        if(!IsElementVisible(By.className("subject"))) {
            Assert.fail("Не загрузилась страница после нажатия на кнопку Семестр");
        }
        List<WebElement> btns_tab = driver.findElements(By.className("button"));
        btns_tab.get(1).click();
        String s=driver.findElement(By.className("main_top")).getText();
        Assert.assertEquals(s,"История выставления баллов","Не та страница ");
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Окрыть страницу
     * 2. Нажать Сессия
     * 3. Нажать Семестр
     *
     * Ожидается: загрузилась странца семестра
     */
    @Test
    public void sessia_page_tabs_click_semestr() {
        go_home();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_sessia(group_tables.get(1));
        String type_d=get_type_subject(group_tables.get(1));
        btn_sem.click();
        if(!IsElementVisible(By.className("subject"))) {
            Assert.fail("Не загрузилась страница после нажатия на кнопку Семестр");
        }
        List<WebElement> btns_tab = driver.findElements(By.className("button"));
        btns_tab.get(0).click();
        String s=driver.findElement(By.className("subject")).getText();
        Assert.assertFalse(s.contains(type_d),"Не та страница "+type_d+" "+s);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Окрыть страницу
     * 2. Нажать Сессия
     * 3. Нажать История
     *
     * Ожидается: загрузилась странца истории выставления баллов
     */
    @Test
    public void sessia_page_tabs_click_history() {
        go_home();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_sessia(group_tables.get(1));
        // String type_d=get_type_subject(group_tables.get(1));
        btn_sem.click();
        if(!IsElementVisible(By.className("subject"))) {
            Assert.fail("Не загрузилась страница после нажатия на кнопку Семестр");
        }
        List<WebElement> btns_tab = driver.findElements(By.className("button"));
        btns_tab.get(1).click();
        String s=driver.findElement(By.className("main_top")).getText();
        Assert.assertEquals(s,"История выставления баллов","Не та страница ");
        go_home();
    }

    @Test
    public void sessia_page_tabs() {
        go_home();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_sessia(group_tables.get(1));
        btn_sem.click();
        if(!IsElementVisible(By.className("subject"))) {
            Assert.fail("Не загрузилась страница после нажатия на кнопку Семестр");
        }
        List<WebElement> btns_tab = driver.findElements(By.className("button"));
        String tab1_sem=btns_tab.get(0).getText();
        String tab2_history=btns_tab.get(1).getText();
        String tab3_journal=btns_tab.get(2).getText();

        Assert.assertEquals(tab1_sem,"Семестр", "Не соответсвует название ссылки на странице семестра");
        Assert.assertEquals(tab2_history,"История", "Не соответсвует название ссылки на странице семестра");
        Assert.assertEquals(tab3_journal,"Журнал", "Не соответсвует название ссылки на странице семестра");
        go_home();
    }

    @Test
    public void journal_page_tabs() {
        go_home();
        List<WebElement> group_tables; // Изменить этот блок везде... таблица может быть пустая в домашнем семестре
        if(IsElementVisible(By.className("group_block")))
            group_tables = driver.findElements(By.className("group_block"));
        else {
            Assert.fail("Нет элементов таблицы");
            return;
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        //List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_journal(group_tables.get(1));
        btn_sem.click();
        if(!IsElementVisible(By.className("subject"))) {
            Assert.fail("Не загрузилась страница после нажатия на кнопку Семестр");
        }
        List<WebElement> btns_tab = driver.findElements(By.className("button"));
        String tab1_sem=btns_tab.get(0).getText();
        String tab2_history=btns_tab.get(1).getText();
        String tab3_journal=btns_tab.get(2).getText();

        Assert.assertEquals(tab1_sem,"Семестр", "Не соответсвует название ссылки на странице семестра");
        Assert.assertEquals(tab2_history,"Сессия", "Не соответсвует название ссылки на странице семестра");
        Assert.assertEquals(tab3_journal,"История", "Не соответсвует название ссылки на странице семестра");
        go_home();
    }

}
