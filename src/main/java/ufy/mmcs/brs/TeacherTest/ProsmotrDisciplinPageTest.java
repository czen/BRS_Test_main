package ufy.mmcs.brs.TeacherTest;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 *\brief Тесты страниц просмотра дисциплины
 * @warning Если падает тест передачи дисциплины - зайти под rs/22222  и вручную передать дисциплину на
 * Яну Демьяненко. CS332. Компьютерная графика. 2018 год весна.
 *  @version 1.0
 *  @author Stepanova
 *  @see AfterClickBtnsTest, Helper, MarksForSessiaPageTest, TeacherTest, MarksForSemestrPageTest
 */
public class ProsmotrDisciplinPageTest extends Helper{
    /** \brief Инициализация
     *
     * Этот метод вызывается перед выполнением всех функций этого класса
     *
     * Инициализация драйвера браузера. Установка неявных ожиданий. Авторизация под аккаунтом dem\22222
     * @see Helper:timeouts_set, Helper::get_chrome_driver, Helper::get_firefox_driver, terarDown
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
        exit();
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
            driver=null;}
    }

    /**
     *
     */
    @Test
    public void check_prosmotr(){
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/structure");
        // страница просмотра . семестр 9й (2018 год весна) первая строчка
        if(!IsElementVisible(By.className("Warning"))){
         Assert.fail("Страница не загрузилась/нет предупрежедния/это не страница просмотра ");
        }
        String warning=driver.findElement(By.className("Warning")).getText();
        Assert.assertEquals(warning,"Был добавлен балл. Редактирование базовых настроек, модулей и групп невозможно.",
                "Не соответсвует текст предупреждения");
        go_home();
    }

    @Test
    public void check_first_page(){
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/structure");
        // страница просмотра . семестр 9й (2018 год весна) первая строчка
        /*if(!IsElementVisible(By.className("Warning"))){
            Assert.fail("Страница не загрузилась/нет предупрежедния/это не страница просмотра ");
        }
        String warning=driver.findElement(By.className("Warning")).getText();
        Assert.assertEquals(warning,"Был добавлен балл. Редактирование базовых настроек, модулей и групп невозможно.",
                "Не соответсвует текст предупреждения");*/
        go_home();
    }

    @Test
    public void click_to_base_settings() {

    }

    @Test
    public void click_to_modukes() {

    }

    @Test
    public void click_to_teachers() {

    }

    @Test
    public void click_to_groups() {

    }

    @Test
    public void click_to_students() {

    }

    @Test
    public void click_to_go_perehod_k_ochenk() {

    }

    @Test
    public void base_settings_is_nedostupn(){

    }

    @Test
    public void give_leads_to_another_teacher(){

    }

    @Test
    public void search_teachers_another(){

    }

    @Test
    public void search_teacher_and_add(){

    }

    @Test
    public void search_teacher_and_delete(){

    }

    @Test
    public void search_teacher_whom_yet_added(){

    }

    @Test
    public void search_teacher_leaders(){

    }

//открыть закрыть список студентов -??
    //перестановка модулей в редактировании !! + добавление и удаление
}
