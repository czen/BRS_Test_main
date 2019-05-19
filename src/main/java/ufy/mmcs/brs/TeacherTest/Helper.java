/** \brief Тесты для главной страницы, страниц семестра и сесии, редактирования дисциплины
 *
 *
 */
package ufy.mmcs.brs.TeacherTest;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.swing.text.StyledEditorKit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**\brief Родительский класс для всех классов пакета.
 *
 * Содержит основные функции, используемые тестами:
 * Авторизация, установка ожиданий, инициализация драйвера, выход из аккаунта, переход "домой" и проверки видимости элемента
 * @version 1.0
 * @author Stepanova
 * @see AfterClickBtnsTest, MarksForSemestrPageTest, MarksForSessiaPageTest, TeacherTest, MarksOfZachetPageTest, ProsmotrDisciplinPageTest, EditDisciplinPageTest, AfterClickBtnsTest
 */
public class Helper {
    /// \brief Переменная для использования явного ожидания
    /// @detailed Пример использования wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tab-news")))
    protected WebDriverWait wait;
    /// Веб-драйвер
    protected WebDriver driver;
    /// Значение в секундах устанавливаемых ожиданий @see timeouts_set
    private static final long DEFAULT_TIMEOUT = 10;//300;
    /// Путь к файлу конфигурации @see get_chrome_driver
    static private String config_path=".\\config.ini";
    /** \brief Флаг, определющий место чтения пути к конфигурационному файлу
     *
     * Если значение = true, то путь читается из системной переменной Driver_Path
     * Если значение = false, то путь считается стандартным, т.е. корнем каталога
     * @see config_path, get_config_file_path_from_env, config_path
     */
    static private boolean use_path_from_env=false;

    /// Логин для аккаунта студента @see authorization
    private String student_login="ELLA";
    /// Логин для аккаунта преподавателя @see authorization
    private String teacher_login="dem";
    /// Логин для сотрудника деканата @see authorization
    private String dekanat_login="bravit";
    /// Логин для аккаунта Романа Борисовича @see authorization
    private String rs_login="rs";
    /// Общий пароль для всех аккаунтов @see authorization
    private String pwd="22222";

    /** @brief Читает путь к конфигурационному файлу проекта из системной переменной Driver_Path
     *
     * Можно изменить так, что функция будет менять "стандартый" путь к конфигурационному файлу
     * @see get_chrome_driver, get_firefox_driver, use_path_from_env, config_path
     * @return путь к конфигурационному файлу
     */
    private String get_config_file_path_from_env(){
        //config_path = System.getenv("Driver_Path");
        // можно менять в функции сам путь, тогда просто добавляется ее вызов в гет_драйвер, а так присваивать надо
        return  System.getenv("Driver_Path");
    }

    /** \brief Чтение пути к драйверу браузера Хром из конфигурационного файла
     *
     * Читает путь к файлу драйвера Хрома из файла настроек, путь к файлу настроек либо указывается через системную переменную
     * Driever_Path, либо считается стандартным - корнем каталога
     * @return путь к драйверу браузера хром
     * @see get_firefox_driver, get_config_file_path_from_env, use_path_from_env
     * @throws IOException Не удалось прочитать файл
     */
    public  String get_chrome_driver()  {
        FileInputStream fis=null;
        Properties props = new Properties();
        try
        {
            if(use_path_from_env)
                fis=new FileInputStream(new File(get_config_file_path_from_env()));
            else
                fis = new FileInputStream(new File(config_path));
            props.load(fis);
            String DRIVER_CHROME_PATH= props.getProperty("CHROME_DRIVER_PATH");
            return DRIVER_CHROME_PATH;
        }
        catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
            e.printStackTrace();
            Assert.fail("Не прочелся конфиг файл");
            return "";
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** \brief Чтение пути к драйверу браузера ФФ из конфигурационного файла
     *
     * Читает путь к файлу драйвера ФФ из файла настроек, путь к файлу настроек либо указывается через системную переменную
     * Driever_Path, либо считается стандартным - корнем каталога
     * @return путь к драйверу браузера хром
     * @see get_firefox_driver, get_config_file_path_from_env, use_path_from_env
     * @throws IOException Не удалось прочитать файл
     */
    public  String get_firefox_driver(){
        FileInputStream fis=null;
        Properties props = new Properties();
        try
        {
            if(use_path_from_env)
                fis=new FileInputStream(new File(get_config_file_path_from_env()));
            else
                fis = new FileInputStream(new File(config_path));
            props.load(fis);
            String DRIVER_FF_PATH= props.getProperty("FIREFOX_DRIVER_PATH");
            return DRIVER_FF_PATH;
        }
        catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
            e.printStackTrace();
            Assert.fail("Не прочелся конфиг файл");
            return "";
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * \brief Устанавливает значения ожиданий для драйвера
     * @see DEFAULT_TIMEOUT, AuthorizationTest::getDriver
     */
    public void timeouts_set(){
        driver.manage().timeouts().setScriptTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        wait=new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    /**
     * \brief  Переход по "домашней" ссылке http://testgrade.sfedu.ru/
     * @see exit
     */
    public void go_home() {
        driver.get("http://testgrade.sfedu.ru/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("header_wrapper")));
    }

    /**
     * \brief Проверяет наличие элемента на странице
     *
     * @param iClassName By.Id("id"), By.CssSelector("selector") и т.д.
     * @return Наличие элемента
     * @throws NoSuchElementException вызывается методом findElement(By by), если элемент с заданным селектором не найден на странице.
     @see IsElementVisible
     */
    public Boolean IsElementExists(By iClassName) {
        // в метод передаётся "iClassName" это By.Id("id_elementa"), By.CssSelector("selector") и т.д.
        try
        {
            driver.findElement(iClassName);
            return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    /**
     * \brief Проверяет видимость элемента на странице.
     *
     * @param iClassName "iClassName" = By.Id("id"), By.CssSelector("selector") и т.д.
     * @return Видимость объекта (видимый/не видимый)
     * @throws NoSuchElementException вызывается методом findElement(By by), если элемент с заданным селектором не найден на странице.
     @see IsElementExists
     */
    public Boolean IsElementVisible(By iClassName) {
        try
        {
            boolean iv = driver.findElement(iClassName).isDisplayed();
            if (iv) { return true; } else { return false; }
        }
        catch (NoSuchElementException e) { return false; } // если элемент вообще не найден
    }

    /** \brief Переключение на авторизацию по логину\паролю
     *
     * У главной неавторизированной страницы может быть два варианта загрузки: авторизация по логину и авторизация по логину\паролю
     * В тестах используется авторизация по логину\паролю. И эта функция переключает на эту страницу, если загрузилась дргуая.
     @see authorization
     */
    public void if_grade_visiable(){
        if( IsElementVisible(By.id("grade"))) driver.findElement(By.id("grade")).click();
    }

    /** \brief Авторизация под аккаунтом студента
     *
     * Происходит авторизация под аккаунтом с доступом преподователя
     * @return Владелец аккаунта
     */
    public String authorization() {
        //driver.get("http://testgrade.sfedu.ru/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tab-news")));

        // driver.findElement(By.id("grade")).click();
        if(IsElementExists(By.id("grade"))) driver.findElement(By.id("grade")).click();
        driver.findElement(By.id("login")).sendKeys(teacher_login);
        driver.findElement(By.id("password")).sendKeys(pwd);
        driver.findElement(By.id("signin_b")).click();

        // wait.until(ExpectedConditions.textToBe(By.className("main_top"),"Дисциплины"));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("main_top"))) ;

        if(! IsElementVisible(By.id("username")))
            Assert.fail("Не удалось войти в аккаунт "+teacher_login+" "+pwd);

        return "Яна Демяненко";
    }

    /** \brief Авторизация в системе с возможностью выбрать аккаунт
     *
     * Авторизация в системе для аккаунтов с различным уровнем доступа: студенческий, преподовательский, сотрудник деканата
     * @param type тип акканта под которым можно авторизироваться: student, teacher, dekanat, rb
     * @return владелец аккаунта
     */
    public String authorization(String type){
        String login, result;
        switch (type){
            case ("student"):
                login=student_login;
                result="Элла Кораблина";
                break;
            case("teacher"):
                login=teacher_login;
                result="Яна Демяненко";
                break;
            case("dekanat"):
                login=dekanat_login;
                result="Виталий Брагилевский";
                break;
            case("rb"):
                login=rs_login;
                result="Роман Штейнберг";
                break;
            default:
                login="2";
                result="Wrong type";
                Assert.fail("Неверный логин/пароль для аккаунта");

        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tab-news")));

        // driver.findElement(By.id("grade")).click();
        if( IsElementVisible(By.id("grade"))) driver.findElement(By.id("grade")).click();
        driver.findElement(By.id("login")).sendKeys(login);
        driver.findElement(By.id("password")).sendKeys(pwd);
        driver.findElement(By.id("signin_b")).click();

        //  wait.until(ExpectedConditions.textToBe(By.className("main_top"),"Дисциплины"));

        if(!IsElementExists(By.id("username")))
            Assert.fail("Не удалось войти в аккаунт "+login+" "+pwd);

        return result;
    }

    /** \brief Авторизация в системе под произвольным аккаунтом
     *
     * Позволяет авторизироваться в системе под своим или другим извесным Вам аккаунтом. На тестовом сервере для всех аккаунтов пароль = 22222
     * @param login Логин для входа в систему
     * @param pass Пароль для этого аккаунта.
     * @return вошли\не вошли в систему
     */
    public Boolean authorization(String login, String pass){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));

        // driver.findElement(By.id("grade")).click();
        if(IsElementVisible(By.id("grade"))) driver.findElement(By.id("grade")).click();
        driver.findElement(By.id("login")).sendKeys(login);
        driver.findElement(By.id("password")).sendKeys(pass);
        driver.findElement(By.id("signin_b")).click();

        //    wait.until(ExpectedConditions.textToBe(By.className("main_top"),"Дисциплины"));

        return IsElementExists(By.id("username"));
    }

    /**
     * \brief  Выход из аккаунта
     *
     * Вначале выход по ссылке- искать кнопку дорого. если не вышло, то тогда нажимаем на кнопку "выход"
     * @see authorization
     */
    public void exit(){
        driver.get("http://testgrade.sfedu.ru/sign/out");

        if(! IsElementVisible(By.id("tab-news"))){
            driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[3]/a[2]")).click();   // fa fa-sign-out fa-bg fa-fw //*[@id="wrap"]/div[2]/div[3]/a[2]/i
        }
        else {
            if(! IsElementVisible(By.id("tab-news"))) {
                Assert.fail("Не удалось выйти из аккаунта ");
            }
        }
    }

    /**
     * \brief Переключение семестра на заданный по идентификатору
     *
     * Происходит переключение семестра по его идентификатору в селекторе
     * @param[in] sem Строковое значение номера семестра, например S-10, на который нужно переключиться
     * @warning Может работать плохо
     * @throws ElementNotVisibleException Не виден элемент селектора для переключения семестра
     * @throws NoSuchElementException Выбран несуществующий семестр (в списке)
     */
    public void choose_semestr(String sem){ //ID

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("semesterChangerSelection")));
        String was_sem="1";
        try {
            was_sem= driver.findElement(By.className("semesterChangerSelection")).getText();
            driver.findElement(By.className("semesterChangerSelection")).click();
        }
        catch (ElementNotVisibleException e){
            Assert.fail("Не доступен селектор выбора семестра: "+sem);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("switchSemester")));
        try {
            //if (IsElementVisible(By.id(sem)))
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sem)));
            driver.findElement(By.id(sem)).click();
        }
        catch(NoSuchElementException e){
            Assert.fail("Семестр с указанным идeнтивикатором не существует: "+sem);
        }
        catch (ElementNotVisibleException e)
        {
            Assert.fail("Семестр с указанным идeнтивикатором не виден: "+sem);
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("semesterChangerSelection"))); // visibilityOfElementLocated(By.className("main_top")));
        // if(IsElementExists(By.className("semesterSwitcherBtn")))
        Assert.assertEquals(driver.findElement(By.className("semesterChangerSelection")).getText(), was_sem,"Не сменился семестр "+was_sem);
    }

    /** \brief Тип дисциплины
     *
     * @param table_strung Строка из таблицы
     * @return Тип дисциплины экзамен\зачет
     * \throws NoSuchElementException Если не нашли элемента
     */
    public String get_type_subject(WebElement table_strung){
        String css_to_type=".info_cell.td_control";
        WebElement type_subj;
        try {
            type_subj = table_strung.findElement(By.cssSelector(css_to_type));
        }
        catch (NoSuchElementException e){
            Assert.fail("Не доступен тип дисциплины: экзамен/зачет");
            return "";
        }
        return type_subj.getText();
    }

    public WebElement get_btn_semestr(WebElement string_table){
        List<WebElement> btns;
        try{
            btns=string_table.findElements(By.className("action_cell"));
            if(!btns.get(0).isDisplayed())
                Assert.fail("Кнопки Семестр нет на экране");
            return btns.get(0);
        }
        catch (NoSuchElementException e){
            Assert.fail("Не доступены кнопки для дисциплины");
            return string_table;
        }
    }

    public WebElement get_btn_sessia(WebElement string_table){
        List<WebElement> btns;
        try{
            btns=string_table.findElements(By.className("action_cell"));
            if(!btns.get(1).isDisplayed())
                Assert.fail("Кнопки Сессия нет на экране");
            return btns.get(1);
        }
        catch (NoSuchElementException e){
            Assert.fail("Не доступены кнопки для дисциплины");
            return string_table;
        }
    }

    public WebElement get_btn_journal(WebElement string_table){
        List<WebElement> btns;
        try{
            btns=string_table.findElements(By.className("action_cell"));
            if(!btns.get(2).isDisplayed())
                Assert.fail("Кнопки Журнал нет на экране");
            return btns.get(2);
        }
        catch (NoSuchElementException e){
            Assert.fail("Не доступены кнопки для дисциплины");
            return string_table;
        }
    }

    public WebElement get_btn_prosmotr(WebElement string_table){
        List<WebElement> btns;
        try{
            btns=string_table.findElements(By.className("action_cell"));
            if(!btns.get(3).isDisplayed())
                Assert.fail("Кнопки Просмотр нет на экране");
            if(!btns.get(3).getText().equals("Просмотр"))
                Assert.fail("Не совпадает текст кнопки Просмотр: " + btns.get(3).getText());
            return btns.get(3);

        }
        catch (NoSuchElementException e){
            Assert.fail("Не доступены кнопки для дисциплины");
            return string_table;
        }
    }

    public Boolean btn_is_radactirovania(WebElement string_table){
        List<WebElement> btns;
        try{
            btns=string_table.findElements(By.className("action_cell"));
            if(!btns.get(3).isDisplayed())
                Assert.fail("Кнопки редактирование нет на экране");
            return btns.get(3).getText().equals("Редактирование");
        }
        catch (NoSuchElementException e){
            Assert.fail("Не доступены кнопки для дисциплины");
            return false;
        }
    }

    public WebElement get_btn_redactir(WebElement string_table){
        List<WebElement> btns;
        try{
            btns=string_table.findElements(By.className("action_cell"));
            if(!btns.get(3).isDisplayed())
                Assert.fail("Кнопки Просмотр нет на экране");
            if(!btns.get(3).getText().equals("Редактирование"))
                Assert.fail("Не совпадает текст кнопки Редактирование: " + btns.get(3).getText());
            return btns.get(3);
        }
        catch (NoSuchElementException e){
            Assert.fail("Не доступены кнопки для дисциплины");
            return string_table;
        }
    }

    /** \brief Значение последнего семестра в системе
     *
     * Сейчас последний семестр - это 10 семестр
     * @return Возвращает числовой эквивалент последнего семестра
     * @warning Каждый семестр следует изменять это значение
     */
    public String last_semestr(){ return "10";}

    public boolean check_or_choose_all_groups_in_selector(){
        WebElement selectElem = driver.findElement(By.tagName("select"));
        Select select = new Select(selectElem);
        if(select.getFirstSelectedOption().getText().equals("Все группы")){
            return true;
        }
        select.selectByValue("0");
        return (select.getFirstSelectedOption().getText().equals("Все группы"));
    }

    /**
     * \brief Читает из ячейки выставленный балл
     *
     * Если ячейка пуста, вернется 0
     * @param rowNum Номер строки
     * @param colNum Номер колонки
     * @return Выставленный балл
     */
    public int Mark(int rowNum, int colNum) {
        WebElement element = driver.findElement(By.id("col_row_" + colNum + "_" + rowNum));
        WebElement field = element.findElement(By.tagName("input"));
        try {
            return Integer.parseInt(field.getAttribute("value"));
        } catch (NumberFormatException e) {
            return 0;
        }
        //   return Integer.parseInt(field.getAttribute("value"));
    }

    /**
     *\brief Проверка ячейки на пустоту
     *
     * @param rowNum Номер строки
     * @param colNum Номер колонки
     * @return пусто\не пусто
     */
    public boolean check_emty_mark(int rowNum, int colNum) {
        WebElement element = driver.findElement(By.id("col_row_" + colNum + "_" + rowNum));
        WebElement field = element.findElement(By.tagName("input"));
        try {
            Integer.parseInt(field.getAttribute("value"));
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    /**
     * \brief Возвращает балл из колонок Итог, Экзамен, Итог за семестр
     *
     * Нужно указать какой это столбец по номеру. Там другие стили и классы, поэтому отдельная функция
     * @param rowNum Номер строки
     * @param col Номер колонки
     * @return выставленный баллл
     */
    public int Semestr_mark(int rowNum,int col) {
        WebElement row = driver.findElement(By.id("row_" + String.valueOf(rowNum)));
        //ячейка, используется для итогов
        WebElement field = row.findElement(By.xpath("//*[@id=\"row_"+rowNum+"\"]/td["+col+"]"));
        //*[@id="row_1"]/td[7]
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * \brief Выставляет балл
     * @param rowNum Номер строки
     * @param colNum Номер колонки
     * @param mark Балл, который выставляется. Строка
     * @throws InterruptedException от ожидания
     */
    public void set_mark(int rowNum, int colNum, String mark) throws InterruptedException {
        WebElement element = driver.findElement(By.id("col_row_" + colNum + "_" + rowNum));
        WebElement field = element.findElement(By.tagName("input"));
        field.sendKeys(Keys.BACK_SPACE+mark+"\n"+Keys.ESCAPE);
        TimeUnit.MILLISECONDS.sleep(50);
    }

    /**
     * \brief Удаляет выставленную оценку
     * @param rowNum Номер строки
     * @param colNum Номер колонки
     * @throws InterruptedException от ожидания
     */
    public void delete_mark(int rowNum, int colNum) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(50);
        WebElement element = driver.findElement(By.id("col_row_" + colNum + "_" + rowNum));
        WebElement field = element.findElement(By.tagName("input"));
        //element.clear();
        field.sendKeys(Keys.BACK_SPACE+"" +Keys.BACK_SPACE+"\n"+Keys.ESCAPE);

        TimeUnit.SECONDS.sleep(1);
        // field.sendKeys("\n");
        if(!check_emty_mark(rowNum,colNum))
            Assert.fail("Не удалилась оценка row:"+rowNum+" col:"+colNum);
    }


















}
