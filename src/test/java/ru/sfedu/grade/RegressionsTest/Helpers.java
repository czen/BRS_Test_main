/**\brief Тесты перехода по ссылкам
 *
 * Тест-кейс:
 * 1. Перейти по ссылке
 *
 Ожидание: страница загрузилась
 *
 * Если падают эти тесты, то все печально
 */
package ru.sfedu.grade.RegressionsTest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/** \brief Родительский класс для всех классов пакета.
 *
 * Содержит основные функции, используемые тестами:
 * Авторизация, установка ожиданий, инициализация драйвера, выход из аккаунта и переход "домой"
 * @version 1.0
 * @author Stepanova
 * @see SimpleTests, ForTeacherAccaunt, ForStudentAccaunt, ForDekanatAccaunt
 */
public class Helpers {
    /// Значение в секундах устанавливаемых ожиданий @see timeouts_set
    private static final long DEFAULT_TIMEOUT = 10;//300;
    /// \brief Переменная для использования явного ожидания
    /// @detailed Пример использования wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tab-news")))
    protected WebDriverWait wait;
    /// Веб-драйвер
    protected WebDriver driver;
    /// Путь к файлу конфигурации @see get_chrome_driver
    static private String config_path="config.ini";
    /** \brief Флаг, определющий место чтения пути к конфигурационному файлу
     *
     * Если значение = true, то путь читается из системной переменной Driver_Path
     * Если значение = false, то путь считается стандартным, т.е. корнем каталога
     * @see config_path, get_config_file_path_from_env, config_path
     */
    static private boolean use_path_from_env=false;

    /**
     * \brief Устанавливает значения ожиданий для драйвера
     * @see DEFAULT_TIMEOUT, AuthorizationTest::getDriver
     */
    void timeouts_set(){
        driver.manage().timeouts().setScriptTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        wait=new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    /** @brief Читает путь к конфигурационному файлу проекта из системной переменной Driver_Path

     * Можно изменить так, что функция будет менять "стандартый" путь к конфигурационному файлу
     * @see get_chrome_driver, get_firefox_driver, use_path_from_env, config_path
     * @return путь к конфигурационному файлу
     */
    private String get_config_file_path_from_env(){
        //config_path = System.getenv("Driver_Path");
        // можно менять в функции сам путь, тогда просто добавляется ее вызов в гет_драйвер, а так присваивать надо
        return  System.getenv("Driver_Path");
    }

    /// Запускать локально или удалено
    private Boolean run_local = true;
    ///Путь к драйверу браузера Chrome (локальный)
    private String CHROME_DRIVER_PATH = "";
    /// Путь к драйверу браузера FireFox (локальный)
    private String FIREFOX_DRIVER_PATH  = "";
    ///Браузер, запускаемый удалено. Название должно указывать тот браузер, сервер которого запущен на хабе(сервере), указанном ниже
    ///@see SERVER
    private String BROWSER = "";
    /// Адресс хаба(сервера), к которому обращается для запуска не нем тестов и браузера
    /// @see BROWSER
    private String SERVER = "";
    /// Адресс запуска тестов
    private String BASE_URL = "";

    /**
     *\brief Чтение конфиг файла
     *
     * Читает файл настроек и записывает в переменные свойства.
     * Для добавления локальных браузеров - добавить свойства в  файл и их чтение
     * @throws IOException Не удалось прочитать файл
     */
    public void read_propities(){
        FileInputStream fis=null;
        Properties props = new Properties();
        try
        {
            if(use_path_from_env)
                fis=new FileInputStream(new File(get_config_file_path_from_env()));
            else
                fis = new FileInputStream(new File(config_path));
            props.load(fis);
            //if add local browsers then add read propeteis
            run_local = Boolean.valueOf(props.getProperty("LOCALHOST"));
            CHROME_DRIVER_PATH = props.getProperty("CHROME_DRIVER_PATH");
            FIREFOX_DRIVER_PATH = props.getProperty("FIREFOX_DRIVER_PATH");
            BROWSER = props.getProperty("BROWSER");
            SERVER = props.getProperty("SERVER");
            BASE_URL = props.getProperty("BASE_URL");
        }
        catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует! " + config_path);
            e.printStackTrace();
            Assert.fail("Не прочелся конфиг файл " + config_path);
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
     * \brief Инициализирует драевер для работы
     *
     * Если запуск локальный (устанавливается из конфига) - по-умолчанию используется * браузер хром, TestNG умеет запускать кроссбраузерно,
     * параметр прокидывается оттуда из xml файла для запуска. Запуская его, можно настроить разные варианты запуска не только браузеров
     *
     * При нелокальном запуске, читает имя браузера из конфига и "сервер" по которому будет обращаться. Для добаления новых вариантов браузеров
     * добавить if и не забыть менять адрес сервера в конфиг в файле (+имя браузера)
     * @param browser Определяет в каком браузере будут запускаться тесты локально
     * @see read_propities
     */
    public void initialization_driver(String browser) {
        if(run_local){
            if (browser.equals("chrome")) {
                System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
                driver = new ChromeDriver();
            } else if (browser.equals("firefox")) {
                System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_PATH);
                driver = new FirefoxDriver();
            }
        }
        else {
            //для добавления еще браузеров добавить такие же ифы
            if(BROWSER.equals("chrome")) {
                DesiredCapabilities capability = DesiredCapabilities.chrome();
                capability.setPlatform(Platform.LINUX);
                capability.setBrowserName(BROWSER);
                try {
                    driver = new RemoteWebDriver(new URL(SERVER), capability);
                }
                catch (MalformedURLException err){
                    System.err.println("ОШИБКА: Что-то с классом URL " + SERVER);
                    err.printStackTrace();
                    Assert.fail("Нет инициализации " + SERVER);
                }
            }
            if(BROWSER.equals("firefox")) {
                DesiredCapabilities capability = DesiredCapabilities.firefox();
                capability.setPlatform(Platform.LINUX);
                capability.setBrowserName(BROWSER);
                try {
                    driver = new RemoteWebDriver(new URL(SERVER), capability);
                }
                catch (MalformedURLException err){
                    System.err.println("ОШИБКА: Что-то с классом URL " + SERVER);
                    err.printStackTrace();
                    Assert.fail("Нет инициализации " + SERVER);
                }
            }
        }
    }

    /**
     * \brief Выдает базовый веб адрес по которому тестируеься система
     *
     * @warning Если указан не тестовый сервер следует ввести все нужные пароли для аккаунтов
     * @return URL адрес
     */
    public String get_base_url(){
        if(BASE_URL.isEmpty())
        {
            System.out.println("Config url didn't read, used: http://testgrade.sfedu.ru/ BASE_URL="+BASE_URL);
            return "http://testgrade.sfedu.ru/";
        }
        return BASE_URL;
    }

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

    /** \brief Переключение на авторизацию по логину\паролю
     *
     * У главной неавторизированной страницы может быть два варианта загрузки: авторизация по логину и авторизация по логину\паролю
     * В тестах используется авторизация по логину\паролю. И эта функция переключает на эту страницу, если загрузилась дргуая.
     @see authorization
     */
    public void if_grade_visiable(){
        if( IsElementVisible(By.id("grade"))) driver.findElement(By.id("grade")).click();
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

        // driver.findElement(By.id("grade")).click();
        if(IsElementVisible(By.id("grade"))) driver.findElement(By.id("grade")).click();
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
        driver.get(get_base_url()+"");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("header_wrapper")));
    }

    /**
     * \brief  Выход из аккаунта
     *
     * Вначале выход по ссылке- искать кнопку дорого. если не вышло, то тогда нажимаем на кнопку "выход"
     * @see authorization
     */
    public void exit(){
        driver.get(get_base_url()+"sign/out");
        //    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        //   if ( !driver.findElement(By.id("password")).isDisplayed()) {
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

    //не используется, для справки и истории, нужен другой формат пути: с двумя слэшами
    /** \brief Не используется, для истории
     *
     * Тоже инициализация драйвера, чтение пути из системной переменной Driver_Path
     */
    public  String get_config_file_path_from_env_another_variant(){
        //но должен быть другой формат значения переменной: с двумя слэшами а не одним
        String myEnvVar="";
        try{
            Properties env = new Properties();
            env.load(Runtime.getRuntime().exec("cmd /c set").getInputStream());
            myEnvVar = (String)env.get("Driver_Path");
        }
        catch(IOException bomErr){
            bomErr.printStackTrace();
            Assert.fail("Не читается системная переменная " + bomErr);
        }
        return  myEnvVar;
    }


}
