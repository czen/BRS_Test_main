package ufy.mmcs.brs.UnauthorizedPageTest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class Helper {
    protected WebDriverWait wait;
    protected WebDriver driver;
    private static final long DEFAULT_TIMEOUT = 10;//300;
    private String ChromeDriver="D:\\MyWork\\Drivers\\chromedriver.exe";
    private String FireFoxDriver="D:\\MyWork\\Drivers\\geckodriver.exe";

    private String student_login="ELLA";
    private String teacher_login="dem";
    private String dekanat_login="bravit";
    private String rs_login="rs";
    private String pwd="22222";

    public  String get_chrome_driver(){ return ChromeDriver;}
    public  String get_firefox_driver(){ return FireFoxDriver;}

    void timeouts_set(){
        driver.manage().timeouts().setScriptTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        wait=new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    public void go_home() {
        driver.get("http://testgrade.sfedu.ru/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("header_wrapper")));
    }

    public Boolean IsElementExists(By iClassName)
    {// в метод передаётся "iClassName" это By.Id("id_elementa"), By.CssSelector("selector") и т.д.
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

    public Boolean IsElementVisible(By iClassName)
    {
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

    public void if_grade_visiable(){
        if( IsElementVisible(By.id("grade"))) driver.findElement(By.id("grade")).click();
    }

}
