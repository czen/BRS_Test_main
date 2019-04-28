package ufy.mmcs.brs.AuthorizationTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class AuthorizationTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private Helper hhelp;

    @Parameters("browser")
    @BeforeClass// @BeforeTest
    protected void  /* WebDriver*/ getDriver(@Optional("chrome") String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "D:\\MyWork\\Drivers\\chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "D:\\MyWork\\Drivers\\geckodriver.exe");
            driver = new FirefoxDriver();
        }
        hhelp = new Helper(driver);
        wait=new WebDriverWait(driver, 20);
    }

    @Test
    public void check_teacher_akk(){
        hhelp.go_home();
        String user_name_exp = hhelp.authorization("teacher");
        String user_name = driver.findElement(By.id("username")).getText();
        driver.findElement(By.id("username")).click();
        Assert.assertTrue(hhelp.IsElementVisible(By.id("profileInfo")),"Не видно панели пользователя");

        String full_username=driver.findElement(By.className("username")).getText();

        hhelp.exit();

        Assert.assertEquals(user_name, user_name_exp,"Вход не в тот аккаунт");
        Assert.assertEquals(full_username,"Яна Михайловна Демяненко","Полное имя пользователя не совпадает");
    }

    @Test
    public void check_student_akk(){
        hhelp.go_home();
        String user_name_exp = hhelp.authorization("student");
        String user_name = driver.findElement(By.id("username")).getText();
        driver.findElement(By.id("username")).click();
        Assert.assertTrue(hhelp.IsElementVisible(By.id("profileInfo")),"Не видно панели пользователя");

        String full_username=driver.findElement(By.className("username")).getText();


        hhelp.exit();

        Assert.assertEquals(user_name, user_name_exp,"Вход не в тот аккаунт");
        Assert.assertEquals(full_username,"Элла Викторовна Кораблина","Полное имя пользователя не совпадает");
    }

    @Test
    public void check_dekanat_akk(){
        hhelp.go_home();
        String user_name_exp = hhelp.authorization("dekanat");
        String user_name = driver.findElement(By.id("username")).getText();

        driver.findElement(By.id("username")).click();
        Assert.assertTrue(hhelp.IsElementVisible(By.id("profileInfo")),"Не видно панели пользователя");

        String full_username=driver.findElement(By.className("username")).getText();

        hhelp.exit();

        Assert.assertEquals(user_name, user_name_exp,"Вход не в тот аккаунт");
        Assert.assertEquals(full_username,"Виталий Николаевич Брагилевский","Полное имя пользователя не совпадает");
    }

    @Test
    public void check_rb_akk(){
        hhelp.go_home();
        String user_name_exp = hhelp.authorization("rb");
        String user_name = driver.findElement(By.id("username")).getText();
         driver.findElement(By.id("username")).click();
        Assert.assertTrue(hhelp.IsElementVisible(By.id("profileInfo")),"Не видно панели пользователя");

        String full_username=driver.findElement(By.className("username")).getText();

        hhelp.exit();

        Assert.assertEquals(user_name, user_name_exp,"Вход не в тот аккаунт");
        Assert.assertEquals(full_username,"Роман Борисович Штейнберг","Полное имя пользователя не совпадает");
    }

    @Test
    public void change_semest_test(){
        hhelp.go_home();
        Boolean flag=hhelp.authorization("rs","22222");
        Assert.assertTrue(flag,"Не вошли в аккаунт");

        for (int i=1;i<=hhelp.last_semestr();i++)
            hhelp.choose_semestr("S-"+String.valueOf(i));


    }

    @AfterClass // Runs this method after all the test methods in the current class have been run
    public void tearDown() {
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
            driver=null;}
    }
}
