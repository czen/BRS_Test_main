package ufy.mmcs.brs.RegressionsTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class ForStudentAccaunt {
    private WebDriver driver;
    private WebDriverWait wait;
    private Helpers hhelp;

    private String url1="http://testgrade.sfedu.ru/";
    private String url2="http://testgrade.sfedu.ru/student/discipline/13337";
    private String url3="http://testgrade.sfedu.ru/student/discipline/13337/journal";
    private String url4="http://testgrade.sfedu.ru/student/discipline/3760";
    private String url5="http://testgrade.sfedu.ru/student/discipline/3760/journal";

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

        hhelp = new Helpers(driver);
        wait=new WebDriverWait(driver, 20);

        hhelp.go_home();
        hhelp.authorization("student");
    }

    @AfterClass // Runs this method after all the test methods in the current class have been run
    public void tearDown() {
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
            driver=null;}
    }

    @Test
    public void do_to_home(){
        driver.navigate().to(url1);
        Boolean flag=hhelp.IsElementVisible(By.className("main_top"));
        Assert.assertTrue(flag,"Не загрузилась страница " + url1);
    }

    @Test
    public void do_to_zach_marks(){
        driver.navigate().to(url2);
        Boolean flag=hhelp.IsElementVisible(By.className("blockTitle"));
        if(flag)
            if(driver.findElement(By.className("blockTitle")).getText()!="Баллы за семестр")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url2);
    }

    @Test
    public void do_to_zach_jurnal(){
        driver.navigate().to(url3);
        Boolean flag=hhelp.IsElementVisible(By.className("blockTitle"));
        if(flag)
            if(driver.findElement(By.className("blockTitle")).getText()!="Журнал посещений")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url3);
    }

    @Test
    public void do_to_exam_marks(){
        driver.navigate().to(url4);
        Boolean flag=hhelp.IsElementVisible(By.className("blockTitle"));
        if(flag)
            if(driver.findElement(By.className("blockTitle")).getText()!="Баллы за семестр")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница " +url4);
    }

    @Test
    public void do_to_exam_journal(){
        driver.navigate().to(url5);
        Boolean flag=hhelp.IsElementVisible(By.className("blockTitle"));
        if(flag)
            if(driver.findElement(By.className("blockTitle")).getText()!="Журнал посещений")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница " +url5);
    }



}
