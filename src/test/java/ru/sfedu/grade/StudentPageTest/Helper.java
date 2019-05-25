/** \brief Тесты для студенческого аккаунта
 *
 *Большая часть сделана Ангелиной, здесь недостающие.
 *
 * Исполняемые в пакете тест-кейсы:
 *
 * Тест-кейс:
 1. Открыть страницу дисциплины
 2. Нажать Журнал
 3. Нажать Баллы

 Ожидается: Загрузилась страница с баллами

 *Тест-кейс:
 1. Зайти на страницу дисциплины
 2. Нажать Журнал

 Ожидается: Загрузилась страница журнала

 * Тест-кейс:
 * 1. Открыть страницу с дисциплинами
 * 2. Нажать на элемент таблицы

 Ожидается: Загрузилась страница этой дисципплины
 */
package ru.sfedu.grade.StudentPageTest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
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
 * Авторизация, установка ожиданий, инициализация драйвера, выход из аккаунта, переход "домой" и проверки видимости элемента
 * @version 1.0
 * @author Stepanova
 * @see PageOfDisciplin
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
    static private String config_path="config.ini";
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
    private String get_config_file_path_from_env(){
        //config_path = System.getenv("Driver_Path");
        // можно менять в функции сам путь, тогда просто добавляется ее вызов в гет_драйвер, а так присваивать надо
        return  System.getenv("Driver_Path");
    }

    /// Использовать локальный или удаленный запуск
    private Boolean run_local=true;
    ///Путь к драйверу браузера Хром
    private String CHROME_DRIVER_PATH = "";
    /// Путь к драйверу браузера ФФ
    private String FIREFOX_DRIVER_PATH  = "";
    ///Используемый браузер для удаленого запуска. Должен совпадать с тем, что запущено на хабе
    private String BROWSER="";
    /// Адресс хаба, к которому обращаются для запуска браузера
    private String SERVER = "";
    /// Адресс по которому запускаются тесты
    private String BASE_URL = "";

    /**
     *\brief Чтение конфиг файла
     *
     * Читает файл настроек и записываетв переменные свойства.
     * Для добавление локальных браузеров добавить свойства и чтение
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
     * Если запуск локальный - по-умолчанию использует браузер хром, TestNG умеет запускать кроссбраузерно,
     * параметр прокидывается оттуда из xml файла для запуска. Запуская его, можно настроить разные варианты запуска не только браузеров
     *
     * При нелокальном запуске, читает имя браузера из конфига и "сервер" по которому будет обращаться. Для добаления новых вариантов браузеров
     * добавить if и изменить сервер в конфиг в файле (+имя браузера)
     * @param browser Определяет в каком браузере будут запускаться тесты локально
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
                ChromeOptions capability =new ChromeOptions();// DesiredCapabilities.chrome();
                capability.setCapability(CapabilityType.BROWSER_NAME,"chrome");
                capability.setCapability(CapabilityType.PLATFORM_NAME,"LINUX");
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

    /**
     * \brief Устанавливает значения ожиданий для драйвера
     * @see DEFAULT_TIMEOUT, PageOfDisciplin::getDriver
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
        driver.get(get_base_url()+"");
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
     * Происходит авторизация под аккаунтом с доступом студента
     * @return Владелец аккаунта
     */
    public String authorization() {
        //driver.get(get_base_url()+"");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tab-news")));

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
     * \brief  Выход из аккаунта
     *
     * Вначале выход по ссылке- искать кнопку дорого. если не вышло, то тогда нажимаем на кнопку "выход"
     * @see authorization
     */
    public void exit(){
        driver.get(get_base_url()+"sign/out");

        if(! IsElementVisible(By.id("tab-news"))){
            driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[3]/a[2]")).click();   // fa fa-sign-out fa-bg fa-fw //*[@id="wrap"]/div[2]/div[3]/a[2]/i
        }
        else {
            if(! IsElementVisible(By.id("tab-news"))) {
                Assert.fail("Не удалось выйти из аккаунта ");
            }
        }
    }
}
