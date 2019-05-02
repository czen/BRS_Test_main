package ufy.mmcs.brs.StudentPageTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class StudentLinkTest {

    protected WebDriver driver;

    @Parameters("browser")
    @BeforeClass// @BeforeTest
    protected void  /* WebDriver*/ getDriver(@Optional("chrome") String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "/home/user/chromedriver");
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "/home/user/chromedriver");
            driver = new FirefoxDriver();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://testgrade.sfedu.ru/");
        driver.findElement(By.id("grade")).click();
        driver.findElement(By.id("login")).sendKeys("ELLA");
        driver.findElement(By.id("password")).sendKeys("11111");
        driver.findElement(By.id("signin_b")).click();
    }
    @AfterClass // Runs this method after all the test methods in the current class have been run
    public void tearDown() {
        // Close all browser windows and safely end the session
        if(driver != null)
        {
            driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[3]/a[2]")).click();
            driver.quit();
            driver=null;}
    }

    @Test // Marking this method as part of the test
    public void table_links() {
       // List<WebElement>
         int count = driver.findElements(By.tagName("tr")).size();
        //To calculate no of rows In table.
     //   int rows_count = rows_table.size();

        for(int i=2;i<count+1;i++) {
            String link=driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[4]/div[1]/div[2]/table/tbody/tr["+Integer.toString(i)+"]/td[2]/a")).getText();
            driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[4]/div[1]/div[2]/table/tbody/tr["+Integer.toString(i)+"]/td[2]/a")).click();
             // String name = driver.findElement(By.className("main_top")).getText();
         //   Assert.assertEquals(name, "Учебная карта дисциплины");
            String subject=driver.findElement(By.className("pageTitle")).getText();
             driver.findElement(By.className("logotype")).click();
            Assert.assertEquals(link, subject);
        }
    }

 /*   @Test // Marking this method as part of the test
    public void semestr_links() {
        driver.findElement(By.className("semesterChangerTitle")).click();
        int count = driver.findElements(By.className("switchSemester")).size();

        for(int i=1;i<=count;i++) {

            driver.findElement(By.id("S-"+Integer.toString(i))).click();
       driver.findElement(By.className("main_top"));
//driver.findElement(By.id("changeSemester")).click();
        /*    int count1 = driver.findElements(By.tagName("tr")).size();

            if (count1 ==2){
                String name1= driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[4]/div[1]/div[2]/table/tbody/tr[2]/td/h2")).getText();
                Assert.assertEquals(name1, "В настоящий момент Вы не подписаны ни на одну из существующих дисциплин.");
            }
            else
                for(int j=2;j<count1+1;j++) {
                    driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[4]/div[1]/div[2]/table/tbody/tr["+Integer.toString(j)+"]/td[2]/a")).click();
                    String name = driver.findElement(By.className("main_top")).getText();
                    Assert.assertEquals(name, "Учебная карта дисциплины");
                    driver.findElement(By.className("logotype")).click();
                }*/
  /*          driver.findElement(By.className("semesterChangerSelection")).click();
        }
    }*/

}
