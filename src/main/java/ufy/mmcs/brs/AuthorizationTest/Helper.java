package ufy.mmcs.brs.AuthorizationTest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

//import java.util.NoSuchElementException;

public class Helper {
    private WebDriverWait wait;
    private WebDriver driver;
    private static final long DEFAULT_TIMEOUT = 10;//300;
    private String ChromeDriver="/home/user/chromedriver";
    private String FireFoxDriver="/home/user/chromedriver";

    private String student_login="ELLA";
    private String teacher_login="dem";
    protected String dekanat_login="bravit";
    protected String rs_login="rs";
    protected String pwd="22222";

    public  String get_chrome_driver(){ return ChromeDriver;}
    public  String get_firefox_driver(){ return FireFoxDriver;}

    public int last_semestr(){ return 10;}

    Helper(){
        System.setProperty("webdriver.chrome.driver", ChromeDriver);
        driver = new ChromeDriver();
        driver.manage().timeouts().setScriptTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        wait=new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    Helper(WebDriver driver){
        driver.manage().timeouts().setScriptTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        wait=new WebDriverWait(driver, DEFAULT_TIMEOUT);
        this.driver=driver;
    }

    //роде как не нyжно....
    static void timeouts_set(WebDriver driver,WebDriverWait wait){
        driver.manage().timeouts().setScriptTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        wait=new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    public void if_grade_visiable(){
        if( IsElementVisible(By.id("grade"))) driver.findElement(By.id("grade")).click();
    }

    public String authorization() {
        //driver.get("http://testgrade.sfedu.ru/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));

        // driver.findElement(By.id("grade")).click();
        if(IsElementExists(By.id("grade"))) driver.findElement(By.id("grade")).click();
        driver.findElement(By.id("login")).sendKeys(student_login);
        driver.findElement(By.id("password")).sendKeys(pwd);
        driver.findElement(By.id("signin_b")).click();

        // wait.until(ExpectedConditions.textToBe(By.className("main_top"),"Дисциплины"));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("main_top"))) ;

        if(! IsElementVisible(By.id("username")))
            Assert.fail("Не удалось войти в аккаунт "+student_login+" "+pwd);

        return "Элла Кораблина";
    }

    public String authorization(String type){
        String login, result;
        switch (type){
            case ("student"):
                login=student_login;
                result="Элла Кораблина";
                break;
            case("teacher"):
                login=teacher_login;
                result="Яна Демяненко";
                break;
            case("dekanat"):
                login=dekanat_login;
                result="Виталий Брагилевский";
                break;
            case("rb"):
                login=rs_login;
                result="Роман Штейнберг";
                break;
            default:
                login="2";
                result="Wrong type";
                Assert.fail("Неверный логин/пароль для аккаунта");

        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));

        // driver.findElement(By.id("grade")).click();
        if( IsElementVisible(By.id("grade"))) driver.findElement(By.id("grade")).click();
        driver.findElement(By.id("login")).sendKeys(login);
        driver.findElement(By.id("password")).sendKeys(pwd);
        driver.findElement(By.id("signin_b")).click();

        //  wait.until(ExpectedConditions.textToBe(By.className("main_top"),"Дисциплины"));

        if(!IsElementExists(By.id("username")))
            Assert.fail("Не удалось войти в аккаунт "+login+" "+pwd);

        return result;
    }

    public Boolean authorization(String login, String pass){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));

        // driver.findElement(By.id("grade")).click();
        if(IsElementVisible(By.id("grade"))) driver.findElement(By.id("grade")).click();
        driver.findElement(By.id("login")).sendKeys(login);
        driver.findElement(By.id("password")).sendKeys(pass);
        driver.findElement(By.id("signin_b")).click();

        //    wait.until(ExpectedConditions.textToBe(By.className("main_top"),"Дисциплины"));

        return IsElementExists(By.id("username"));
    }

    public void go_home() {
        driver.get("http://testgrade.sfedu.ru/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("header_wrapper")));
    }

    public void exit(){
        driver.get("http://testgrade.sfedu.ru/sign/out");
        //    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        //   if ( !driver.findElement(By.id("password")).isDisplayed()) {
        if(! IsElementVisible(By.id("password"))){
            driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[3]/a[2]")).click();   // fa fa-sign-out fa-bg fa-fw //*[@id="wrap"]/div[2]/div[3]/a[2]/i
        }
        else {
            if(! IsElementVisible(By.id("password"))) {
                Assert.fail("Не удалось выйти из аккаунта ");
            }
        }
    }

    void choose_semestr(String sem){ //ID

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("semesterChangerSelection")));
        String was_sem="1";
        try {
           was_sem= driver.findElement(By.className("semesterChangerSelection")).getText();
           driver.findElement(By.className("semesterChangerSelection")).click();
       }
       catch (ElementNotVisibleException e){
           Assert.fail("Семестр с указанным идeнтивикатором не существует: "+sem);

       }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("switchSemester")));

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sem)));
            driver.findElement(By.id(sem)).click();
        }
      catch(NoSuchElementException e){
            Assert.fail("Семестр с указанным идeнтивикатором не существует: "+sem);
        }

         wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("semesterChangerSelection"))); // visibilityOfElementLocated(By.className("main_top")));
        // if(IsElementExists(By.className("semesterSwitcherBtn")))
        Assert.assertEquals(driver.findElement(By.className("semesterChangerSelection")).getText(), was_sem,"Не сменился семестр "+was_sem);
    }

    /// <summary>
/// Метод проверяет наличие элемента на странице и возвращает true/false (существует/не существует).
/// "iClassName" = By.Id("id"), By.CssSelector("selector") и т.д.
/// </summary>
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

    /// <summary>
/// Метод проверяет видимость элемента на странице и возвращает true/false (видимый/не видимый).
/// "iClassName" = By.Id("id"), By.CssSelector("selector") и т.д.
/// </summary>
    public Boolean IsElementVisible(By iClassName)
    {
        try
        {
            boolean iv = driver.findElement(iClassName).isDisplayed();
            if (iv) { return true; } else { return false; }
        }
        catch (NoSuchElementException e) { return false; } // если элемент вообще не найден
    }
}
