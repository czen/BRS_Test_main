package ufy.mmcs.brs.RegressionsTest;
//На странице редактирования дисциплины при увелечении высоты последнего таба увеличивать и все остальные

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class ForDekanatAccaunt extends Helpers{
   /* private WebDriver driver;
    private WebDriverWait wait;
    private Helpers hhelp;*/

    private String url1="http://testgrade.sfedu.ru/";
    private String url2="http://testgrade.sfedu.ru/discipline/13355/rate";
    private String url3="http://testgrade.sfedu.ru/discipline/13355/exam";
    private String url4="http://testgrade.sfedu.ru/discipline/13355/structure";
    private String url5="http://testgrade.sfedu.ru/discipline/13355/settings";
    private String url6="http://testgrade.sfedu.ru/discipline/13355/teachers";
    private String url7="http://testgrade.sfedu.ru/discipline/13355/groups";
    private String url8="http://testgrade.sfedu.ru/discipline/13355/students";
    private String url9="http://testgrade.sfedu.ru/discipline/14074/structure";
    private String url10="http://testgrade.sfedu.ru/discipline/14074/settings";
    private String url11="http://testgrade.sfedu.ru/discipline/14074/teachers";
    private String url12="http://testgrade.sfedu.ru/discipline/13358/rate";
    private String url13="http://testgrade.sfedu.ru/discipline/13358/exam";
    private String url14="http://testgrade.sfedu.ru/office";
    private String url15="http://testgrade.sfedu.ru/office/teachers";
    private String url16="http://testgrade.sfedu.ru/office/students";
    private String url17="http://testgrade.sfedu.ru/office/disciplines";
    private String url18="http://testgrade.sfedu.ru/office/reports/bill";
    private String url19="http://testgrade.sfedu.ru/office/support";
    private String url20="http://testgrade.sfedu.ru/discipline/14075/structure";

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

        // hhelp = new Helpers(driver);
        //  wait=new WebDriverWait(driver, 20);
        timeouts_set();
        go_home();
        authorization("dekanat");
    }

    @AfterClass
    public void tearDown() {
        exit();
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
            driver=null;}
    }

    @Test
    public void do_to_home(){
        driver.navigate().to(url1);
        Boolean flag=IsElementVisible(By.className("main_top"));
        Assert.assertTrue(flag,"Не загрузилась страница " + url1);
    }

    @Test
    public void go_to_zach_sem(){
        driver.navigate().to(url2);
        Boolean flag=IsElementVisible(By.className("main_top"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Выставление баллов"))
                Assert.fail("Не та страница "+driver.findElement(By.className("main_top")).getText());
        Assert.assertTrue(flag,"Не загрузилась страница "+url2);
    }

    @Test
    public void go_to_exam_sem(){
        driver.navigate().to(url3);
        Boolean flag=IsElementVisible(By.className("main_top"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Выставление баллов"))
                Assert.fail("Не та страница "+url3);
        Assert.assertTrue(flag,"Не загрузилась страница "+url3);
    }

    @Test
    public void go_to_redact_disciplin_modul(){
        driver.navigate().to(url4);
        Boolean flag=IsElementVisible(By.className("rateIndicatorDIV"));
        if(flag)
            if(!driver.findElement(By.className("rateIndicatorDIV")).getText().equals("Количество баллов: 100"))
                Assert.fail("Не та страница "+driver.findElement(By.className("rateIndicatorDIV")).getText()+" " +url4);
        Assert.assertTrue(flag,"Не загрузилась страница "+url4);
    }

    @Test
    public void go_to_redact_disciplin_teach(){
        driver.navigate().to(url6);
        Boolean flag=IsElementVisible(By.className("BlueTitle"));
        if(flag)
            if(!driver.findElement(By.className("BlueTitle")).getText().equals("Прикрепленные преподаватели"))
                Assert.fail("Не та страница "+url6);
        Assert.assertTrue(flag,"Не загрузилась страница "+url6);
    }

    @Test
    public void go_to_redact_disciplin_groups(){
        driver.navigate().to(url7);
        Boolean flag=IsElementVisible(By.className("BlueTitle"));
        if(flag)
            if(!driver.findElement(By.className("BlueTitle")).getText().equals("Прикрепленные группы"))
                Assert.fail("Не та страница "+url7);
        Assert.assertTrue(flag,"Не загрузилась страница "+url7);
    }

    @Test
    public void go_to_redact_disciplin_setting(){
        driver.navigate().to(url5);
        Boolean flag=IsElementVisible(By.className("select2-chosen"));
        if(flag)
            if(!driver.findElement(By.className("select2-chosen")).getText().equals("CS203. Теория алгоритмов"))
                Assert.fail("Не та страница "+url5);
        Assert.assertTrue(flag,"Не загрузилась страница "+url5);
    }



    @Test
    public void go_to_redact_disciplin_stud(){
        driver.navigate().to(url8);
        Boolean flag=IsElementVisible(By.className("main_top"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Редактирование дисциплины №13355"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url8);
    }

    @Test
    public void go_to_redact_disciplin_module(){
        driver.navigate().to(url9);
        Boolean flag=IsElementVisible(By.className("rateIndicatorDIV"));
        if(flag)
            if(!driver.findElement(By.className("rateIndicatorDIV")).getText().equals("Количество баллов: 100"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url9);
    }

    @Test
    public void go_to_redact_disciplin_settings(){
        driver.navigate().to(url10);
        Boolean flag=IsElementVisible(By.className("select2-chosen"));
        if(flag)
            if(!driver.findElement(By.className("select2-chosen")).getText().equals("CS203. Теория алгоритмов"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url10);
    }

    @Test
    public void go_to_redact_disciplin_teach1(){
        driver.navigate().to(url11);
        Boolean flag=IsElementVisible(By.className("BlueTitle"));
        if(flag)
            if(! driver.findElement(By.className("BlueTitle")).getText().equals("Прикрепленные преподаватели"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url11);
    }

    @Test
    public void go_to_zach_sem1(){
        driver.navigate().to(url12);
        Boolean flag=IsElementVisible(By.className("main_top"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Выставление баллов"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url12);
    }

    @Test
    public void go_to_exam_sem1(){
        driver.navigate().to(url13);
        Boolean flag=IsElementVisible(By.className("main_top"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Выставление баллов"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url13);
    }

    @Test
    public void go_to_redact_disciplin(){
        driver.navigate().to(url20);
        Boolean flag=IsElementVisible(By.className("rateIndicatorDIV"));
        if(flag)
            if(!driver.findElement(By.className("rateIndicatorDIV")).getText().equals("Количество баллов: 0"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url20);
    }

    @Test
    public void go_to_office(){
        driver.navigate().to(url14);
        Boolean flag=IsElementVisible(By.className("main_top"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Панель управления"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url14);
    }

    @Test
    public void go_to_office_teach(){
        driver.navigate().to(url15);
        Boolean flag=IsElementVisible(By.className("BlueTitle"));
        if(flag)
            if(!driver.findElement(By.className("BlueTitle")).getText().equals("Поиск преподавателей"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url15);
    }

    @Test
    public void go_to_office_stud(){
        driver.navigate().to(url16);
        Boolean flag=IsElementVisible(By.className("BlueTitle"));
        if(flag)
            if(!driver.findElement(By.className("BlueTitle")).getText().equals("Поиск студентов"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url16);
    }

    @Test
    public void go_to_office_discipl(){
        driver.navigate().to(url17);
        Boolean flag=IsElementVisible(By.className("main_top"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Панель управления"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url17);
    }


    @Test
    public void go_to_office_vedomost(){
        driver.navigate().to(url18);
        Boolean flag=IsElementVisible(By.className("main_side_content"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Панель управления"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url18);
    }

    @Test
    public void go_to_office_repotrs1(){
        driver.navigate().to(url19);
        Boolean flag=IsElementVisible(By.className("main_top"));
        if(flag)
            if(!driver.findElement(By.className("main_top")).getText().equals("Панель управления"))
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url19);
    }
}