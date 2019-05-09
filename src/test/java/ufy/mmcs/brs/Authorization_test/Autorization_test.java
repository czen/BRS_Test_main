package ufy.mmcs.brs.Authorization_test;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import ufy.mmcs.brs.AuthorizationTest.Helper;

public class Autorization_test extends Helper {

    @Parameters("browser")
    @BeforeClass// @BeforeTest
    protected void  /* WebDriver*/ getDriver(@Optional("chrome") String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", get_chrome_driver());
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", get_firefox_driver());
            driver = new FirefoxDriver();
        }
       /* hhelp = new Helper(driver);
        wait=new WebDriverWait(driver, 20);*/
        timeouts_set();
    }

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

    @AfterClass // Runs this method after all the test methods in the current class have been run
    public void tearDown() {
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
            driver=null;}
    }
}


