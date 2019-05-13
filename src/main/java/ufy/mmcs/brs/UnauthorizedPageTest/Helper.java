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
package ufy.mmcs.brs.UnauthorizedPageTest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

     * @see get_chrome_driver, get_firefox_driver, use_path_from_env, config_path
     * Можно изменить так, что функция будет менять "стандартый" путь к конфигурационному файлу
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
