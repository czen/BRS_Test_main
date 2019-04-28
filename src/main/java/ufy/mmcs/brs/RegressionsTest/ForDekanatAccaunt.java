package ufy.mmcs.brs.RegressionsTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class ForDekanatAccaunt {
    private WebDriver driver;
    private WebDriverWait wait;
    private Helpers hhelp;
    /*  @BeforeClass // Runs this method before the first test method in the current class is invoked
      public void setUp() {
          // Create a new instance of the Firefox driver
          System.setProperty("webdriver.gecko.driver","C:\\Users\\Workstation\\Desktop\\Drivers\\geckodriver.exe");

          driver = new FirefoxDriver();
          driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      }*/
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
    }

    @AfterClass // Runs this method after all the test methods in the current class have been run
    public void tearDown() {
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
            driver=null;}
    }
}
