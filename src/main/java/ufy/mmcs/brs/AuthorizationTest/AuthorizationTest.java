package ufy.mmcs.brs.AuthorizationTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * \brief Проверки входа в аккаунт
 *
 *
 * @version 1.0
 * @author Stepanova
 * @see Helper, AuthorizationFormTest
 */
public class AuthorizationTest extends Helper{

    /** \brief Инициализация
     *
     * Этот метод вызывается перед выполнением всех функций этого класса
     *
     * Инициализация драйвера браузера. По-умолчанию - хром. Установка ожиданий.
     * @param browser Передается из xml-файла для выбора браузера, в котором запустятся тесты. По-умолчанию = chrom
     * @see Helper::timeouts_set, Helper::get_chrome_driver, Helper::get_firefox_driver, tearDown
     */
    @Parameters("browser")
    @BeforeClass
    public void getDriver(@Optional("chrome") String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", get_chrome_driver());
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", get_firefox_driver());
            driver = new FirefoxDriver();
        }
        timeouts_set();
    }

    /**
     * \brief Авторизация под преподовательским аккаунтом
     *
     * Тест-кейс:
     * 1. Открыть страницу авторизации
     * 2. Перейти на авторизацию по логину\паролю
     * 3. Авторизоваться под dem\22222
     *
     * Ожидается:
     * 1. Наличие панели пользователя
     * 2. Совпадение имени владельца в панели с ожидаемым
     * 3. Совпадение полного имени владельца аккаунта с ожидаемым (Яна Михайловна Демяненко)
     * @see Helper::if_grade_visiable, Helper::authorization
     */
    @Test
    public void check_teacher_akk(){
        go_home();
        if_grade_visiable();
        String user_name_exp = authorization("teacher");
        String user_name = driver.findElement(By.id("username")).getText();
        driver.findElement(By.id("username")).click();
        Assert.assertTrue(IsElementVisible(By.id("profileInfo")),"Не видно панели пользователя");
        String full_username=driver.findElement(By.className("username")).getText();
        exit();
        Assert.assertEquals(user_name, user_name_exp,"Вход не в тот аккаунт");
        Assert.assertEquals(full_username,"Яна Михайловна Демяненко","Полное имя пользователя не совпадает");
    }

    /**
     * \brief Авторизация под студенческим аккаунтом
     *
     * Тест-кейс:
     * 1. Открыть страницу авторизации
     * 2. Перейти на авторизацию по логину\паролю
     * 3. Авторизоваться под ELLA\22222
     *
     * Ожидается:
     * 1. Наличие панели пользователя
     * 2. Совпадение имени владельца в панели с ожидаемым
     * 3. Совпадение полного имени владельца аккаунта с ожидаемым (Элла Викторовна Кораблина)
     * @see Helper::if_grade_visiable, Helper::authorization
     */
    @Test
    public void check_student_akk(){
        go_home();
        if_grade_visiable();
        String user_name_exp = authorization("student");
        String user_name = driver.findElement(By.id("username")).getText();
        driver.findElement(By.id("username")).click();
        Assert.assertTrue(IsElementVisible(By.id("profileInfo")),"Не видно панели пользователя");
        String full_username=driver.findElement(By.className("username")).getText();
        exit();
        Assert.assertEquals(user_name, user_name_exp,"Вход не в тот аккаунт");
        Assert.assertEquals(full_username,"Элла Викторовна Кораблина","Полное имя пользователя не совпадает");
    }

    /**
     * \brief Авторизация под аккаунтом сотрудника деканата
     *
     * Тест-кейс:
     * 1. Открыть страницу авторизации
     * 2. Перейти на траницу авторизации по логину\паролю
     * 3. Авторизоваться под bravit\22222
     *
     * Ожидается:
     * 1. Наличие панели пользователя
     * 2. Совпадение имени владельца в панели с ожидаемым
     * 3. Совпадение полного имени владельца аккаунта с ожидаемым (Виталий Николаевич Брагилевский)
     * @see Helper::if_grade_visiable, Helper::authorization
     */
    @Test
    public void check_dekanat_akk(){
        go_home();
        if_grade_visiable();
        String user_name_exp = authorization("dekanat");
        String user_name = driver.findElement(By.id("username")).getText();

        driver.findElement(By.id("username")).click();
        Assert.assertTrue(IsElementVisible(By.id("profileInfo")),"Не видно панели пользователя");

        String full_username=driver.findElement(By.className("username")).getText();

        exit();

        Assert.assertEquals(user_name, user_name_exp,"Вход не в тот аккаунт");
        Assert.assertEquals(full_username,"Виталий Николаевич Брагилевский","Полное имя пользователя не совпадает");
    }

    /**
     * \brief Авторизация под извесным аккаунтом
     *
     * Тест-кейс:
     * 1. Открыть страницу авторизации
     * 2. Перейти на траницу авторизации по логину\паролю
     * 3. Авторизоваться под rs\22222
     *
     * Ожидается:
     * 1. Наличие панели пользователя
     * 2. Совпадение имени владельца в панели с ожидаемым
     * 3. Совпадение полного имени владельца аккаунта с ожидаемым (Роман Борисович Штейнберг)
     * @see Helper::if_grade_visiable, Helper::authorization
     */
    @Test
    public void check_rb_akk(){
        go_home();
        if_grade_visiable();
        String user_name_exp = authorization("rb");
        String user_name = driver.findElement(By.id("username")).getText();
        driver.findElement(By.id("username")).click();
        Assert.assertTrue(IsElementVisible(By.id("profileInfo")),"Не видно панели пользователя");

        String full_username=driver.findElement(By.className("username")).getText();

        exit();

        Assert.assertEquals(user_name, user_name_exp,"Вход не в тот аккаунт");
        Assert.assertEquals(full_username,"Роман Борисович Штейнберг","Полное имя пользователя не совпадает");
    }

 /*   @Test
    public void change_semest_test(){
        go_home();
        if_grade_visiable();
        Boolean flag=authorization("rs","22222");
        Assert.assertTrue(flag,"Не вошли в аккаунт");

        for (int i=1;i<=last_semestr();i++)
            choose_semestr("S-"+String.valueOf(i));
    }*/

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
        if(driver != null)
        {driver.quit();
            driver=null;}
    }
}
