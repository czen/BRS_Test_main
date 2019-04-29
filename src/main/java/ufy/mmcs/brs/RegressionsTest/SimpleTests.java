package ufy.mmcs.brs.RegressionsTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class SimpleTests extends  Helpers{

   /* private WebDriver driver;
    private WebDriverWait wait;
    private Helpers hhelp;*/

    private String url1="http://testgrade.sfedu.ru/";
    private String url2="http://testgrade.sfedu.ru/remind";
    private String url3="http://testgrade.sfedu.ru/sign/in";
    private String url4="http://testgrade.sfedu.ru/sign/up";


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

       /* hhelp = new Helpers(driver);
        wait=new WebDriverWait(driver, 20);*/
        timeouts_set();
    }

    @AfterClass
    public void tearDown() {
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
            driver=null;}
    }

    @Test
    public void go_to_home(){
        driver.navigate().to(url1);
        Boolean flag=IsElementVisible(By.id("signin_b"));
        Assert.assertTrue(flag,"Не загрузилась страница");
    }

    @Test
    public void go_to_remind(){
        driver.navigate().to(url2);
        Boolean flag=IsElementVisible(By.id("remind"));
        Assert.assertTrue(flag,"Не загрузилась страница");
    }

    @Test
    public void go_to_sign_in(){
        driver.navigate().to(url3);
        Boolean flag=IsElementVisible(By.id("signin_b"));
        Assert.assertTrue(flag,"Не загрузилась страница");
    }

    @Test
    public void go_to_sign_up(){
        driver.navigate().to(url4);
        Boolean flag=IsElementVisible(By.id("signup_b"));
        Assert.assertTrue(flag,"Не загрузилась страница");
    }
}
