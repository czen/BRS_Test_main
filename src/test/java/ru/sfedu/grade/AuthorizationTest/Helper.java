/**
 \brief Тесты, посвещенные авторизации и форме авторизации, использующие авторизацию по логину\паролю
 *
 * Исполняемые в пакете тест-кейсы:
 *
 *Тест-кейс
 * 1. Ввести невалидный логин
 * 2. Поле пароль оставить пустым
 * 3. Нажать войти

 * Ожидается: сообщение об ошибке, нет входа в аккаунт
 *
 * Тест кейс:
 * 1. Поле логин оставить пустым
 * 2. Ввести невалидный пароль
 * 3. Нажать войти

 * Ожидается: сообщение об ошибке
 *
 * Тест-кейс:
 * 1. Ввести невалидный логин
 * 2. Ввести невалидный пароль
 * 3. Нажать войти

 * Ожидается: сообщение об ошибке
 *
 * Тест-кейс:
 * 1. Поле логин оставить пустым
 * 2. Поле пароль оставить пустым
 * 3. Нажать войти

 * Ожидается: сообщение об ошибке
 *
 * Тест-кейс:
 * 1. Ввести валидный логин
 * 2. Ввести невалидный пароль
 * 3. Нажать войти

 * Ожидается: сообщение об ошибке
 *
 * Тест-кейс:
 * 1. Ввести невалидный логин
 * 2. Ввести валидный пароль
 * 3. Нажать войти

 * Ожидается: сообщение об ошибке
 *
 * Тест-кейс:
 * 1. Ввести валидный логин
 * 2. Поле пароль оставить пустым
 * 3. Нажать войти

 * Ожидается: сообщение об ошибке
 *
 * Тест-кейс
 * 1. Поле логин оставить пустым
 * 2. Ввести валидный пароль
 * 3. Нажать войти

 * Ожидается: сообщение об ошибке
 *
 * 1. Ввести валидный логин
 * 2. Ввести валидный пароль
 * 3. Нажать войти
 * 4. Выйти из аккаунта

 * Ожидается:
 * 1. Нет сообщения об ошибке
 * 2. Произведен вход в аккаунт
 */
package ru.sfedu.grade.AuthorizationTest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/** \brief Родительский класс для всех классов пакета.
 *
 * Содержит основные функции, используемые тестами:
 * Авторизация, установка ожиданий, инициализация драйвера, выход из аккаунта, переход "домой" и проверки видимости элемента
 * @version 1.0
 * @author Stepanova
 * @see AuthorizationFormTest, AuthorizationTest
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

     * Можно изменить так, что функция будет менять "стандартый" путь к конфигурационному файлу
     * @see get_chrome_driver, get_firefox_driver, use_path_from_env, config_path
     * @return путь к конфигурационному файлу
     */
    public String get_config_file_path_from_env(){
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

    /** \brief Переключение на авторизацию по логину\паролю
     *
     * У главной неавторизированной страницы может быть два варианта загрузки: авторизация по логину и авторизация по логину\паролю
     * В тестах используется авторизация по логину\паролю. И эта функция переключает на эту страницу, если загрузилась дргуая.
     @see authorization
     */
    public void if_grade_visiable(){
        if( IsElementVisible(By.id("grade")))
            driver.findElement(By.id("grade")).click();
    }

    /** \brief Авторизация под аккаунтом студента
     *
     * Происходит авторизация под аккаунтом с доступом студента
     * @return Владелец аккаунта
     */
    public String authorization() {
        //driver.get("http://testgrade.sfedu.ru/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tab-news")));

        // driver.findElement(By.id("grade")).click();
        if(IsElementExists(By.id("grade")))
            driver.findElement(By.id("grade")).click();
        driver.findElement(By.id("login")).sendKeys(student_login);
        driver.findElement(By.id("password")).sendKeys(pwd);
        driver.findElement(By.id("signin_b")).click();
        // wait.until(ExpectedConditions.textToBe(By.className("main_top"),"Дисциплины"));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("main_top"))) ;
        if(! IsElementVisible(By.id("username")))
            Assert.fail("Не удалось войти в аккаунт "+student_login+" "+pwd);
        return "Элла Кораблина";
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
        if( IsElementVisible(By.id("grade")))
            driver.findElement(By.id("grade")).click();
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tab-news")));
        if(IsElementVisible(By.id("grade")))
            driver.findElement(By.id("grade")).click();
        driver.findElement(By.id("login")).sendKeys(login);
        driver.findElement(By.id("password")).sendKeys(pass);
        driver.findElement(By.id("signin_b")).click();
        //    wait.until(ExpectedConditions.textToBe(By.className("main_top"),"Дисциплины"));
        return IsElementExists(By.id("username"));
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
     * \brief  Выход из аккаунта
     *
     * Вначале выход по ссылке- искать кнопку дорого. если не вышло, то тогда нажимаем на кнопку "выход"
     * @see authorization
     */
    public void exit(){
        driver.get("http://testgrade.sfedu.ru/sign/out");
        if(! IsElementVisible(By.id("tab-news"))){
            //поиск элемента = кнопка выхода из аккаунта
            driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[3]/a[2]")).click();   // fa fa-sign-out fa-bg fa-fw
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
            Assert.fail("Семестр с указанным идeнтивикатором не существует: "+sem);

        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("switchSemester")));

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sem)));
            driver.findElement(By.id(sem)).click();
        }
        catch(NoSuchElementException e){
            Assert.fail("Семестр с указанным идeнтивикатором не существует: "+sem);
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("semesterChangerSelection"))); // visibilityOfElementLocated(By.className("main_top")));
        // if(IsElementExists(By.className("semesterSwitcherBtn")))
        Assert.assertEquals(driver.findElement(By.className("semesterChangerSelection")).getText(), was_sem,"Не сменился семестр "+was_sem);
    }

    /**
     * \brief Проверяет наличие элемента на странице
     *
     * @param iClassName By.Id("id"), By.CssSelector("selector") и т.д.
     * @return Наличие элемента
     * @throws NoSuchElementException вызывается методом findElement(By by), если элемент с заданным селектором не найден на странице.
     @see IsElementVisible
     */
    public Boolean IsElementExists(By iClassName) {// в метод передаётся "iClassName" это By.Id("id_elementa"), By.CssSelector("selector") и т.д.
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
}
