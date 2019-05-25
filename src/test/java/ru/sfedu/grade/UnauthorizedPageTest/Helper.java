/** \brief Тесты неавторизированной страницы
 *
 Исполняемые в пакете тест-кейсы:

 *Тест-кейс
 1. Загрузить страницу

 Ожидается: Таб новости активен

 Тест-кейс
 1. Загрузить страницу
 2. нажать таб Новости

 Ожидается: Ничего не поменялось

 Тест-кейс
 1. Загрузить страницу
 2. Переключиться на другой таб

 Ожидается:
 1. Новый таб активен
 2. Текст сменился

 Тест-кейс:
 1. Загрузить страницу
 2. Переключиться на другой таб
 3. Нажать Еще

 Ожидается:
 1. Еще поменялось на Скрыть
 2. Появился новый текст

 Тест-кейс:
 1. Загрузить страницу
 2. Переключиться на другой таб
 3. Нажать Еще
 4. Нажать скрыть

 Ожидается:
 1. скрыть поменялось на Еще
 2. Текст сократился

 Тест-кейс:
 1. Нажать Забыли пароль
 2. Нажать я вспомнил

 Ожидается: загрузилась страница аутентификации

 Тест-кейс:
 1. Нажать Забыли пароль
 Ожидается:
 1. Кнопка Восстановить

 Тест-кейс:
 1. Нажать Забыли пароль
 2. Нажать я вспомнил

 Ожидается: загрузилась страница аутентификации

 Тест-кейс:
 * 1. Нажать активировать аккаунт

 Ожидается: Кнопка активировать

 Тест-кейс:
 * 1. Нажать Активировать аккаунт
 * 2. Нажать Войти в существующий

 Ожидается: загрузилась страница аутентификации

 Тест-кейс:
 1. Перейти на страницу активации

 Ожидается: присутсвие всех инпутов и табов на странице

 Тест-кейс:
 1. Перейти на страницу восстановления пароля

 Ожидается: присутсвие всех инпутов и табов на странице

 Тест-кейс:
 1. Перейти на страницу восстановления пароля
 2. Ввести пустую строку в поле емайл

 Ожидается: Сообщение об ошибке

 Тест-кейс:
 1. перейти на забыли пароль
 2. Ввести невалидный емайл

 Ожидается: Сообщение об ошибке

 Тест-кейс:
 1. Перейти на страницу восстановления пароля
 2. Ввести невалидные емайл в поле емайл

 Ожидается: Сообщение об ошибке
 */
package ru.sfedu.grade.UnauthorizedPageTest;

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
 * Установка ожиданий, инициализация драйвера, переход "домой" и проверки видимости элемента
 * @version 1.0
 * @author Stepanova
 * @see TabsTest, FooterLinks
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

    public Boolean IsElementSelectTab(By classname){

        String s=driver.findElement(classname).getCssValue("background");
        return s.contains("rgb(1, 131, 206)");

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

}
