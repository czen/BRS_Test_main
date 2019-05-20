package ufy.mmcs.brs.TeacherTest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.swing.text.StyledEditorKit;
import java.util.List;

/**
 * \brief Тесты на нажатие на кнопок в таблице
 *
 * К сожалению, некоторые тесты могут падать просто так.
 * Надо их перезапустить. если не поможет, то запустить конкретный тест и разбираться.
 * @see AfterClickBtnsTest, MarksForSemestrPageTest, MarksForSessiaPageTest, MarksOfZachetPageTest, ProsmotrDisciplinPageTest, EditDisciplinPageTest, AfterClickBtnsTest, Helper
 */
public class TeacherTest extends Helper {
    /** \brief Инициализация
     *
     * Инициализация драйвера браузера. Установка неявных ожиданий. Автоизация под аккаунтом dem\22222
     * @see Helper::timeouts_set, Helper::get_chrome_driver, Helper::get_firefox_driver, tearDown
     * @param browser Передается из xml-файла для выбора браузера, в котором запустятся тесты. По-умолчанию = chrom
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
     * 1. Открыть страницу
     * 2. Авторизироваться
     * 3. Нажать на кнопку Семестр (нажимается на второй строке таблицы, т.к. на данный момент из первой - неактивна
     *
     * Ожидается:
     * Загрузилась страница выставления баллов
     * @warning Предполагается, что в этой строке кнопка Редактировать, а не Просмотр. Исправлять путь к строке таблицы.
     */
    @Test
    public void click_button_semestr_near_redact_exam() {
        //Пока тут кнопка Редактирвоать еще есть
        go_home();

        choose_semestr("S-10");
        List<WebElement> group_tables; // Изменить этот блок везде... таблица может быть пустая в домашнем семестре
        if(IsElementVisible(By.className("group_block")))
            group_tables = driver.findElements(By.className("group_block"));
        else {
            Assert.fail("Нет элементов таблицы");
            return;
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        //  List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_semestr(group_tables.get(1));
        btn_sem.click();
        if(!IsElementVisible(By.className("subject"))){
            choose_semestr("S-"+last_semestr());
            Assert.fail("Не загрузилась страница после нажатия на кнопку Семестр");
        }
        String s=driver.findElement(By.className("subject")).getText();
        choose_semestr("S-"+last_semestr());
        Assert.assertEquals(s,"Компьютерное зрение и обработка изображений",
                "Загрузилась не та страница ");
    }

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Авторизироваться
     * 3. Нажать на кнопку Семестр (нажимается на второй строке таблицы, исторически)
     *
     * Ожидается:
     * Загрузилась страница выставления баллов
     * @warning Предполагается, что в этой строке кнопка Просмотр.
     */
    @Test
    public void click_button_semestr_near_prosmotr_zach() {
        //Пока тут кнопка Редактирвоать еще есть
        go_home();
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        choose_semestr("S-9");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_semestr(group_tables.get(1));
        btn_sem.click();
        if(!IsElementVisible(By.className("subject"))){
            choose_semestr("S-"+last_semestr());
            Assert.fail("Не загрузилась страница после нажатия на кнопку Семестр");
        }
        String s=driver.findElement(By.className("subject")).getText();
        choose_semestr("S-"+last_semestr());
        Assert.assertEquals(s,"Введение в проектную деятельность",
                "Загрузилась не та страница ");
    }

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Авторизироваться
     * 3. Нажать на кнопку Семестр (нажимается на второй строке таблицы, исторически)
     *
     * Ожидается:
     * Загрузилась страница выставления баллов
     * @warning Предполагается, что в этой строке кнопка Просмотр.
     */
    @Test
    public void click_button_semestr_near_prosmotr_exam() {
        go_home();
        choose_semestr("S-9");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_semestr(group_tables.get(0));
        btn_sem.click();
        if(!IsElementVisible(By.className("subject"))) {
            choose_semestr("S-"+last_semestr());
            Assert.fail("Не загрузилась страница после нажатия на кнопку Семестр");
        }
        String s=driver.findElement(By.className("subject")).getText();
        choose_semestr("S-"+last_semestr());
        Assert.assertEquals(s,"CS332. Компьютерная графика",
                "Загрузилась не та страница ");
    }

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Авторизоваться
     * 3. Перейти на другой семест
     * 4. Нажать кнопку Сессия для экзамена
     *
     * Ожидается: Загрузилась страница сессии
     */
    @Test
    public void click_button_sessia_near_exam() {
        go_home();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        choose_semestr("S-9");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_sessia(group_tables.get(0));
        btn_sem.click();
        if(!IsElementVisible(By.className("subject"))) {
            choose_semestr("S-"+last_semestr());
            Assert.fail("Не загрузилась страница после нажатия на кнопку Семестр");
        }
        String s=driver.findElement(By.className("subject")).getText();
        choose_semestr("S-"+last_semestr());
        Assert.assertEquals(s,"CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница ");
    }

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Авторизоваться
     * 3. Перейти на другой семест
     * 4. Нажать кнопку Сессия для зачета
     *
     * Ожидается: Загрузилась страница сессии
     */
    @Test
    public void click_button_sessia_near_zach() {
        go_home();
        choose_semestr("S-9");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_sessia(group_tables.get(1));
        btn_sem.click();
        if(!IsElementVisible(By.className("subject"))) {
            choose_semestr("S-"+last_semestr());
            Assert.fail("Не загрузилась страница после нажатия на кнопку Семестр");
        }
        String s=driver.findElement(By.className("subject")).getText();
        choose_semestr("S-"+last_semestr());
        Assert.assertEquals(s,"Введение в проектную деятельность – Зачет",
                "Загрузилась не та страница ");
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Авторизоваться
     * 3. Сменить семестр
     * 4. Для извесной дисциплины проверить тип дисциплины (экзамен)
     *
     * Ожидается: Тип совпадает с ожидаемым
     */
    @Test
    public void exam_is_exam(){
        go_home();
        choose_semestr("S-9");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        String now_znach = get_type_subject(group_tables.get(0));
        choose_semestr("S-"+last_semestr());
        Assert.assertEquals(now_znach,"Экзамен","Не соответсвует тип дисциплины ");
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Авторизоваться
     * 3. Сменить семестр
     * 4. Для извесной дисциплины проверить тип дисциплины (зачет)
     *
     * Ожидается: Тип совпадает с ожидаемым
     */
    @Test
    public void zach_is_zach(){
        go_home();
        choose_semestr("S-9");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        String now_znach = get_type_subject(group_tables.get(1));
        choose_semestr("S-"+last_semestr());
        Assert.assertEquals(now_znach,"Зачет","Не соответсвует тип дисциплины ");
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Авторизироваться
     * 3. Прочитать название первой кнопки
     *
     * Ожидается: Название = Семестр
     */
    @Test
    public void name_of_btn_sem(){
        go_home();
        List<WebElement> group_tables; // Изменить этот блок везде... таблица может быть пустая в домашнем семестре
        if(IsElementVisible(By.className("group_block")))
            group_tables = driver.findElements(By.className("group_block"));
        else {
            Assert.fail("Нет элементов таблицы");
            return;
        }
        //List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_semestr(group_tables.get(0));
        Assert.assertEquals(btn_sem.getText(),"Семестр","Не соответсвует название кнопки ");
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Авторизироваться
     * 3. Прочитать название второй кнопки
     *
     * Ожидается: Название = Сессия
     */
    @Test
    public void name_of_btn_sessia(){
        go_home();
        List<WebElement> group_tables; // Изменить этот блок везде... таблица может быть пустая в домашнем семестре
        if(IsElementVisible(By.className("group_block")))
            group_tables = driver.findElements(By.className("group_block"));
        else {
            Assert.fail("Нет элементов таблицы");
            return;
        }
        //List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_sessia(group_tables.get(0));
        Assert.assertEquals(btn_sem.getText(),"Сессия","Не соответсвует название кнопки ");
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Авторизироваться
     * 3. Прочитать название третьей кнопки
     *
     * Ожидается: Название = Журнал
     */
    @Test
    public void name_of_btn_journal(){
        go_home();
        List<WebElement> group_tables; // Изменить этот блок везде... таблица может быть пустая в домашнем семестре
        if(IsElementVisible(By.className("group_block")))
            group_tables = driver.findElements(By.className("group_block"));
        else {
            Assert.fail("Нет элементов таблицы");
            return;
        }
        // List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_journal(group_tables.get(0));
        Assert.assertEquals(btn_sem.getText(),"Журнал","Не соответсвует название кнопки ");
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Авторизироваться
     * 3. Прочитать название последней кнопки
     *
     * Ожидается: Название = Просмотр или Редактирование
     */
    @Test
    public void name_of_btn_prosmotr(){
        go_home();
        List<WebElement> group_tables; // Изменить этот блок везде... таблица может быть пустая в домашнем семестре
        if(IsElementVisible(By.className("group_block")))
            group_tables = driver.findElements(By.className("group_block"));
        else {
            Assert.fail("Нет элементов таблицы");
            return;
        }
        //List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        // WebElement btn_sem;// = get_btn_prosmotr(group_tables.get(0));
        List<WebElement> btns;
        try{
            btns=group_tables.get(0).findElements(By.className("action_cell"));
            if(!btns.get(3).isDisplayed())
                Assert.fail("Кнопки Просмотр нет на экране");
        }
        catch (NoSuchElementException e){
            Assert.fail("Не доступены кнопки для дисциплины");
            btns=driver.findElements(By.className("group_block"));
        }
        Assert.assertTrue(btns.get(3).getText().equals("Просмотр")|| btns.get(3).getText().equals("Редактирование"),
                "Не соответсвует название кнопки: ожидалось Редактирование/Просмотр найдено " +btns.get(3).getText());
        //Assert.assertEquals(btn_sem.getText(),"Журнал","Не соответсвует название кнопки ");
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Авторизироваться
     * 3. Нажать кнопку Журнал
     *
     * Ожидается: Загрузился журнал дисциплины
     */
    @Test
    public void click_button_journal_near_zach() {
        go_home();
        choose_semestr("S-9");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        List<WebElement> group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_journal(group_tables.get(1));
        btn_sem.click();
        if(!IsElementVisible(By.className("subject"))) {
            choose_semestr("S-"+last_semestr());
            Assert.fail("Не загрузилась страница после нажатия на кнопку Журнал");
        }
        String s=driver.findElement(By.className("subject")).getText();
        choose_semestr("S-"+last_semestr());
        Assert.assertEquals(s,"Введение в проектную деятельность",
                "Загрузилась не та страница ");
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Авторизироваться
     * 3. Нажать кнопку Журнал
     *
     * Ожидается: Загрузился журнал дисциплины
     */
    @Test
    public void click_button_journal_near_exam() {
        go_home();
        choose_semestr("S-9");
        List<WebElement> group_tables; // Изменить этот блок везде... таблица может быть пустая в домашнем семестре
        if(IsElementVisible(By.className("group_block")))
            group_tables = driver.findElements(By.className("group_block"));
        else {
            Assert.fail("Нет элементов таблицы");
            return;
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        // group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_journal(group_tables.get(0));
        btn_sem.click();
        if(!IsElementVisible(By.className("subject"))) {
            choose_semestr("S-"+last_semestr());
            Assert.fail("Не загрузилась страница после нажатия на кнопку Журнал");
        }
        String s=driver.findElement(By.className("subject")).getText();
        choose_semestr("S-"+last_semestr());
        Assert.assertEquals(s,"CS332. Компьютерная графика",
                "Загрузилась не та страница ");
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Авторизироваться
     * 3. Сменить семестр
     * 4. Нажать кнопку Просмотр
     *
     * Ожидается: Загрузилась страница модулей дисциплины
     */
    @Test
    public void click_button_prosmotr_near_exam() {
        go_home();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));
        choose_semestr("S-9");
        List<WebElement> group_tables; // Изменить этот блок везде... таблица может быть пустая в домашнем семестре
        if(IsElementVisible(By.className("group_block")))
            group_tables = driver.findElements(By.className("group_block"));
        else {
            Assert.fail("Нет элементов таблицы");
            return;
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("group_block")));

        // group_tables = driver.findElements(By.className("group_block"));
        WebElement btn_sem = get_btn_prosmotr(group_tables.get(0));
        btn_sem.click();
        if(!IsElementVisible(By.className("main_top"))) {
            choose_semestr("S-"+last_semestr());
            Assert.fail("Не загрузилась страница после нажатия на кнопку Просмотр");
        }
        String s=driver.findElement(By.className("main_top")).getText();
        choose_semestr("S-"+last_semestr());
        Assert.assertEquals(s,"Редактирование дисциплины №3723",
                "Загрузилась не та страница ");
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Авторизироваться
     * 3. Нажать кнопку Редактирование
     *
     * Ожидается: Загрузилась страница редактирования дисциплины
     *
     * @warning Может падать из-за отсутсвия в таблице кнопки Редактирование, такая кнопка возможно только на последнем семестре.
     * @see Helper::btn_is_radactirovania, Helper::get_btn_redactir
     */
    @Test
    public void click_button_redactir_near_exam() {
        go_home();
        List<WebElement> group_tables; // Изменить этот блок везде... таблица может быть пустая в домашнем семестре
        if(IsElementVisible(By.className("group_block")))
            group_tables = driver.findElements(By.className("group_block"));
        else {
            Assert.fail("Нет элементов таблицы");
            return;
        }
        WebElement btn_sem;
        for(WebElement group: group_tables){
            if(btn_is_radactirovania(group)){
                btn_sem= get_btn_redactir(group);
                btn_sem.click();
                if(!IsElementVisible(By.className("main_top"))) {
                    Assert.fail("Не загрузилась страница после нажатия на кнопку Рдактирование");
                }
                String s=driver.findElement(By.className("main_top")).getText();
                Assert.assertTrue(s.contains("Редактирование дисциплины №"),"Загрузилась не та страница "+s);
                return;
            }
        }
        Assert.fail("Нет строк в таблице с кнопкой Редактирование");
    }

}
