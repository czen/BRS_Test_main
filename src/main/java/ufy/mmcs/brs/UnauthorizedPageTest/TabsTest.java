package ufy.mmcs.brs.UnauthorizedPageTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class TabsTest extends Helper {
    @Parameters("browser")
    @BeforeClass
    protected void  /* WebDriver*/ getDriver(@Optional("chrome") String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", get_chrome_driver());
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", get_firefox_driver());
            driver = new FirefoxDriver();
        }

        //hhelp = new Helpers(driver);
        //wait=new WebDriverWait(driver, 20);
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
    public void tabs_names(){
        go_home();
        if(IsElementVisible(By.id("tab-news"))) {
            WebElement news = driver.findElement(By.id("tab-news"));
            Assert.assertEquals(news.getText(), "Новости", "Название <Новости>  не соответсвет");
        }
        else{
            Assert.fail("Таб новости не виден");
        }
        if(IsElementVisible(By.id("tab-updates"))) {
            WebElement news = driver.findElement(By.id("tab-updates"));
            Assert.assertEquals(news.getText(), "Обновления", "Название <Обновления>  не соответсвет");
        }
        else{
            Assert.fail("Таб обновления не виден");
        }
        if(IsElementVisible(By.id("tab-syncs"))) {
            WebElement news = driver.findElement(By.id("tab-syncs"));
            Assert.assertEquals(news.getText(), "Синхронизации", "Название <Синхронизации>  не соответсвет");
        }
        else{
            Assert.fail("Таб Синхронизации не виден");
        }
    }

    @Test
    public void news_is_select_home(){
        go_home();
        Boolean flag = IsElementVisible(By.id("tab-news"));
        Assert.assertTrue(flag,"Таб новости не виден");
        //WebElement news=driver.findElement(By.id("tab-news"));
        // Assert.assertEquals(news.getText(),"Новости","Название таба не соответсвет");
        Assert.assertTrue(IsElementSelectTab(By.id("tab-news"))," Таб новости не выбран");
    }

    @Test
    public void news_is_select_yet(){
        go_home();
        Boolean flag = IsElementVisible(By.id("tab-news"));
        Assert.assertTrue(flag,"Таб новости не виден");
        WebElement news=driver.findElement(By.id("tab-news"));
        news.click();
        Assert.assertTrue(IsElementSelectTab(By.id("tab-news"))," Таб новости не выбран");
    }

    @Test
    void updates_is_not_select_home(){
        go_home();
        Boolean flag = IsElementVisible(By.id("tab-news"));
        Assert.assertTrue(flag,"Таб Обновления не виден");
        Assert.assertTrue(!IsElementSelectTab(By.id("tab-updates"))," Таб обновления выбран");
    }

    @Test
    void syncs_is_not_select_home(){
        go_home();
        Boolean flag = IsElementVisible(By.id("tab-syncs"));
        Assert.assertTrue(flag,"Таб Синхронизация не виден");
        Assert.assertTrue(!IsElementSelectTab(By.id("tab-syncs"))," Таб синхронизация выбран");
    }

    @Test
    void syncs_click(){
        go_home();
        Boolean flag = IsElementVisible(By.id("tab-syncs"));
        Assert.assertTrue(flag,"Таб Синхронизация не виден");
        driver.findElement(By.id("tab-syncs")).click();
        Assert.assertTrue(IsElementSelectTab(By.id("tab-syncs"))," Таб синхронизация не выбран");
    }

    @Test
    public void syncs_expand_click(){
        go_home();
        Boolean flag = IsElementVisible(By.id("tab-syncs"));
        Assert.assertTrue(flag,"Таб tab-syncs не виден");
        driver.findElement(By.id("tab-syncs")).click();
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("expand")));
        List<WebElement> expand=driver.findElements(By.className("expand"));

        if(!expand.get(2).isDisplayed()) Assert.fail("Нет кнопки еще");
        // Assert.assertTrue(IsElementVisible(),"Нет кнопки Еще");

        Assert.assertEquals(expand.get(2).getText(),"Ещё","Не соответсвует название кнопки");
        expand.get(2).click();
        Assert.assertEquals(expand.get(2).getText(),"Скрыть","Не соответсвует наззвание кнопки после нажатия");
    }

    @Test
    void upd_is_not_select_syncs(){
        go_home();
        Boolean flag = IsElementVisible(By.id("tab-updates"));
        Assert.assertTrue(flag,"Таб Синхронизация не виден");
        driver.findElement(By.id("tab-syncs")).click();
        Assert.assertTrue(!IsElementSelectTab(By.id("tab-updates"))," Таб синхронизация выбран");
    }

    @Test
    void news_is_not_select_syncs(){
        go_home();
        Boolean flag = IsElementVisible(By.id("tab-news"));
        Assert.assertTrue(flag,"Таб News не виден");

        driver.findElement(By.id("tab-syncs")).click();
        Assert.assertTrue(!IsElementSelectTab(By.id("tab-news"))," Таб news выбран");
    }

    @Test
    void update_click(){
        go_home();
        Boolean flag = IsElementVisible(By.id("tab-updates"));
        Assert.assertTrue(flag,"Таб Обновления не виден");
        driver.findElement(By.id("tab-updates")).click();
        Assert.assertTrue(IsElementSelectTab(By.id("tab-updates"))," Таб updates не выбран");
    }

    @Test
    public void upd_expand_click(){
        go_home();
        Boolean flag = IsElementVisible(By.id("tab-updates"));
        Assert.assertTrue(flag,"Таб Обновления не виден");
        driver.findElement(By.id("tab-updates")).click();
        List<WebElement> expand=driver.findElements(By.className("expand"));

        if(!expand.get(1).isDisplayed()) Assert.fail("Нет кнопки еще");
        // Assert.assertTrue(IsElementVisible(By.className("expand")),"Нет кнопки Еще");
        //WebElement expand=driver.findElement(By.className("expand"));
        Assert.assertEquals(expand.get(1).getText(),"Ещё","Не соответсвует название кнопки");
        expand.get(1).click();
        Assert.assertEquals(expand.get(1).getText(),"Скрыть","Не соответсвует наззвание кнопки после нажатия");
    }

    @Test
    void syncs_is_not_select_upd(){
        go_home();
        Boolean flag = IsElementVisible(By.id("tab-syncs"));
        Assert.assertTrue(flag,"Таб Синхронизация не виден");
        driver.findElement(By.id("tab-updates")).click();
        Assert.assertTrue(!IsElementSelectTab(By.id("tab-syncs"))," Таб синхронизация выбран");
    }

    @Test
    void news_is_not_select_upd(){
        go_home();
        Boolean flag = IsElementVisible(By.id("tab-news"));
        Assert.assertTrue(flag,"Таб News не виден");
        driver.findElement(By.id("tab-updates")).click();
        Assert.assertTrue(!IsElementSelectTab(By.id("tab-news"))," Таб news выбран");
    }

    @Test
    public void upd_expand_click_click(){
        go_home();
        Boolean flag = IsElementVisible(By.id("tab-updates"));
        Assert.assertTrue(flag,"Таб Обновления не виден");
        driver.findElement(By.id("tab-updates")).click();
        List<WebElement> expand=driver.findElements(By.className("expand"));

        if(!expand.get(1).isDisplayed()) Assert.fail("Нет кнопки еще");
        // Assert.assertTrue(IsElementVisible(By.className("expand")),"Нет кнопки Еще");
        //WebElement expand=driver.findElement(By.className("expand"));
        expand.get(1).click();
        //Assert.assertEquals(expand.get(1).getText(),"Скрыть","Не соответсвует наззвание кнопки после нажатия");
        expand.get(1).click();
        Assert.assertEquals(expand.get(1).getText(),"Ещё","Не соответсвует наззвание кнопки после двойного нажатия");
    }

    @Test
    public void syncs_expand_click_click(){
        go_home();
        Boolean flag = IsElementVisible(By.id("tab-updates"));
        Assert.assertTrue(flag,"Таб Syncs не виден");
        driver.findElement(By.id("tab-syncs")).click();
        List<WebElement> expand=driver.findElements(By.className("expand"));

        if(!expand.get(2).isDisplayed()) Assert.fail("Нет кнопки еще");
        // Assert.assertTrue(IsElementVisible(By.className("expand")),"Нет кнопки Еще");
        //WebElement expand=driver.findElement(By.className("expand"));
        expand.get(2).click();
        //Assert.assertEquals(expand.get(1).getText(),"Скрыть","Не соответсвует наззвание кнопки после нажатия");
        expand.get(2).click();
        Assert.assertEquals(expand.get(2).getText(),"Ещё","Не соответсвует наззвание кнопки после двойного нажатия");
    }
}
