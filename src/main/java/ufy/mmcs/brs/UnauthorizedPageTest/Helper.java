package ufy.mmcs.brs.UnauthorizedPageTest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Helper {
    protected WebDriverWait wait;
    protected WebDriver driver;
    private static final long DEFAULT_TIMEOUT = 10;//300;
    //private String ChromeDriver="D:\\MyWork\\Drivers\\chromedriver.exe";
    //private String FireFoxDriver="D:\\MyWork\\Drivers\\geckodriver.exe";
    static private String config_path=".\\config.ini";
    static private boolean use_path_from_env=false;

    private String student_login="ELLA";
    private String teacher_login="dem";
    private String dekanat_login="bravit";
    private String rs_login="rs";
    private String pwd="22222";


    private String get_config_file_path_from_env(){
        //config_path = System.getenv("Driver_Path");
        // можно менять в функции сам путь, тогда просто добавляется ее вызов в гет_драйвер, а так присваивать надо
        return  System.getenv("Driver_Path");
    }

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

    public void if_grade_visiable(){
        if( IsElementVisible(By.id("grade"))) driver.findElement(By.id("grade")).click();
    }

}
