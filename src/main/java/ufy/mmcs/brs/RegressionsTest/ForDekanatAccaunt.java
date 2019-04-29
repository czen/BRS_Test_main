package ufy.mmcs.brs.RegressionsTest;
//На странице редактирования дисциплины при увелечении высоты последнего таба увеличивать и все остальные
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
            System.setProperty("webdriver.chrome.driver", "D:\\MyWork\\Drivers\\chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "D:\\MyWork\\Drivers\\geckodriver.exe");
            driver = new FirefoxDriver();
        }

        hhelp = new Helpers(driver);
        wait=new WebDriverWait(driver, 20);
		
		hhelp.go_home();
		hhelp.authorization("dekanat");
    }

    @AfterClass 
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
    public void go_to_zach_sem(){
        driver.navigate().to(url2);
        Boolean flag=hhelp.IsElementVisible(By.className("main_top"));
        if(flag)
            if(driver.findElement(By.className("main_top")).getText()!="Выставление баллов")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url2);
    }
	
	@Test
    public void go_to_exam_sem(){
        driver.navigate().to(url3);
        Boolean flag=hhelp.IsElementVisible(By.className("main_top"));
        if(flag)
            if(driver.findElement(By.className("main_top")).getText()!="Выставление баллов")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url3);
    }
	
	@Test
    public void go_to_redact_disciplin_modul(){
        driver.navigate().to(url4);
        Boolean flag=hhelp.IsElementVisible(By.className("rateIndicatorDIV"));
        if(flag)
            if(driver.findElement(By.className("rateIndicatorDIV")).getText()!="Количество баллов: ")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url4);
    }
	
	@Test
    public void go_to_redact_disciplin_teach(){
        driver.navigate().to(url6);
        Boolean flag=hhelp.IsElementVisible(By.className("BlueTitle"));
        if(flag)
            if(driver.findElement(By.className("BlueTitle")).getText()!="Прикрепленные преподаватели")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url6);
    }
	@Test
    public void go_to_redact_disciplin_setting(){
        driver.navigate().to(url5);
        Boolean flag=hhelp.IsElementVisible(By.className("select2-chosen"));
        if(flag)
            if(driver.findElement(By.className("select2-chosen")).getText()!="CS203. Теория алгоритмов")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url5);
    }
	
	@Test
    public void go_to_redact_disciplin_groups(){
        driver.navigate().to(url8);
        Boolean flag=hhelp.IsElementVisible(By.className("main_top"));
        if(flag)
            if(driver.findElement(By.className("main_top")).getText()!="Редактирование дисциплины	№13355")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url8);
    }

	@Test
    public void go_to_redact_disciplin_module(){
        driver.navigate().to(url9);
        Boolean flag=hhelp.IsElementVisible(By.className("rateIndicatorDIV"));
        if(flag)
            if(driver.findElement(By.className("rateIndicatorDIV")).getText()!="Количество баллов: ")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url9);
    }
	
    @Test
    public void go_to_redact_disciplin_settings(){
        driver.navigate().to(url10);
        Boolean flag=hhelp.IsElementVisible(By.className("select2-chosen"));
        if(flag)
            if(driver.findElement(By.className("select2-chosen")).getText()!="CS203. Теория алгоритмов")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url10);
    }
	
	@Test
    public void go_to_redact_disciplin_teach(){
        driver.navigate().to(url11);
        Boolean flag=hhelp.IsElementVisible(By.className("BlueTitle"));
        if(flag)
            if(driver.findElement(By.className("BlueTitle")).getText()!="Прикрепленные преподаватели")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url11);
    }
	
	@Test
    public void go_to_zach_sem1(){
        driver.navigate().to(url12);
        Boolean flag=hhelp.IsElementVisible(By.className("main_top"));
        if(flag)
            if(driver.findElement(By.className("main_top")).getText()!="Выставление баллов")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url12);
    }
	
	@Test
    public void go_to_exam_sem1(){
        driver.navigate().to(url13);
        Boolean flag=hhelp.IsElementVisible(By.className("main_top"));
        if(flag)
            if(driver.findElement(By.className("main_top")).getText()!="Выставление баллов")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url13);
    }
	
	@Test
    public void go_to_redact_disciplin(){
        driver.navigate().to(url20);
        Boolean flag=hhelp.IsElementVisible(By.className("rateIndicatorDIV"));
        if(flag)
            if(driver.findElement(By.className("rateIndicatorDIV")).getText()!="Количество баллов: ")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url20);
    }
	
	@Test
    public void go_to_office(){
        driver.navigate().to(url14);
        Boolean flag=hhelp.IsElementVisible(By.className("main_top"));
        if(flag)
            if(driver.findElement(By.className("main_top")).getText()!="Панель управления")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url14);
    }
	
	@Test
    public void go_to_office_teach(){
        driver.navigate().to(url15);
        Boolean flag=hhelp.IsElementVisible(By.className("BlueTitle"));
        if(flag)
            if(driver.findElement(By.className("BlueTitle")).getText()!="Поиск преподавателей")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url15);
    }
	
	@Test
    public void go_to_office_stud(){
        driver.navigate().to(url16);
        Boolean flag=hhelp.IsElementVisible(By.className("BlueTitle"));
        if(flag)
            if(driver.findElement(By.className("BlueTitle")).getText()!="Поиск студентов")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url16);
    }
	
			    @Test
    public void go_to_office_discipl(){
        driver.navigate().to(url17);
        Boolean flag=hhelp.IsElementVisible(By.className("main_top"));
        if(flag)
            if(driver.findElement(By.className("main_top")).getText()!="Панель управления")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url17);
    }
	
	
	@Test
    public void go_to_office_repotrs(){
        driver.navigate().to(url18);
        Boolean flag=hhelp.IsElementVisible(By.className("BlueTitle"));
        if(flag)
            if(driver.findElement(By.className("BlueTitle")).getText()!="Сводная ведомость по группе")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url18);
    }
	
	@Test
    public void go_to_office_repotrs(){
        driver.navigate().to(url19);
        Boolean flag=hhelp.IsElementVisible(By.className("main_top"));
        if(flag)
            if(driver.findElement(By.className("main_top")).getText()!="Панель управления ")
                Assert.fail("Не та страница");
        Assert.assertTrue(flag,"Не загрузилась страница "+url19);
    }
}
