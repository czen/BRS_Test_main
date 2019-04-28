package ufy.mmcs.brs.DekanatTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.TimeUnit;


public class SumOfMarks {
    protected WebDriver driver;
    WebDriverWait wait;

    @Parameters("browser")
    @BeforeClass
    protected void  getDriver(@Optional("chrome") String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\Sara\\Desktop\\Drivers\\chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver",
                    "C:\\Users\\Sara\\Desktop\\Drivers\\geckodriver.exe");
            driver = new FirefoxDriver();
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait=new WebDriverWait(driver, 20);


        driver.get("http://testgrade.sfedu.ru/");
        driver.findElement(By.id("grade")).click();
        driver.findElement(By.id("login")).sendKeys("bravit");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        driver.findElement(By.id("password")).sendKeys("11111");
        driver.findElement(By.id("signin_b")).click();
    }

    @Test // Marking this method as part of the test
    public void check_autentification() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));

        String name= driver.findElement(By.id("username")).getText();

        Assert.assertEquals(name, "Виталий Брагилевский","Не найдено имя пользователя");
    }

    @Test
    public void check_inf_of_user(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        driver.findElement(By.id("username")).click();
        String name_us="Виталий Николаевич Брагилевский";
        String name=driver.findElement(By.className("username")).getText();
        String type_akk=driver.findElement(By.xpath("//*[@id=\"profileInfo\"]/div[3]/div[2]")).getText();
        driver.findElement(By.id("username")).click();
        Assert.assertEquals(name,name_us,"Имя пользователя неверно");
        Assert.assertEquals(type_akk,"Сотрудник деканата","Тип аккаунта не совпадает");
    }

    void choose_semestr(String sem){ //ID
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("semesterChangerSelection")));
        driver.findElement(By.className("semesterChangerSelection")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("switchSemester")));
        driver.findElement(By.id(sem)).click();

        wait.until(ExpectedConditions.textToBe(By.className("main_top"),"Дисциплины")); // visibilityOfElementLocated(By.className("main_top")));

        Assert.assertEquals(driver.findElement(By.className("main_top")).getText(),"Дисциплины","Не сменился семестр");
    }

    Boolean choose_subject(String subj, String control, String action, String text){
        List<WebElement> groups=  driver.findElements(By.className("discipline_groups"));
        for (WebElement group:groups
             ) {
            WebElement groupTitle = group.findElement(By.className("grade_title"));
            if (groupTitle != null && groupTitle.getText().trim().startsWith(subj)) {
                List<WebElement> rows = group.findElements(By.className("group_block"));
                for (WebElement row : rows
                ) {
                    WebElement type = row.findElement(By.className("td_control"));
                    if (type != null && type.getText().trim().contains(control)) {
                        if (text != "") {
                           List< WebElement> btn = row.findElements(By.className(action));
                            for (WebElement b : btn
                            ) {
                                if (b.getText().trim().contains(text)) {
                                    if (action != "inactive") b.click();
                                    return true;

                                }
                            }
                        } else {
                            WebElement button = row.findElement(By.className(action));
                            button.click();
                            return true;
                        }
                    }

                }
            }
        }
  return false;
    }

    protected int Mark(int rowNum, int colNum) {
        WebElement element = driver.findElement(By.id("col_row_" + colNum + "_" + rowNum));
        WebElement field = element.findElement(By.tagName("input"));
        try {
            return Integer.parseInt(field.getAttribute("value"));
        } catch (NumberFormatException e) {
            return 0;
        }
     //   return Integer.parseInt(field.getAttribute("value"));
    }

    protected int Exam(int rowNum, int col) {
        WebElement row = driver.findElement(By.id("row_" + rowNum));
        WebElement field = row.findElement(By.xpath("//*[@id=\"row_"+rowNum+"\"]/td["+col+"]"));
        try {
            return Integer.parseInt(field.getText());
        }  catch (NumberFormatException e) {
            return 0;
        }
       // return field.getText();
    }

/*    protected int Result(int rowNum,int col) {
        WebElement row = driver.findElement(By.id("row_" + String.valueOf(rowNum)));
        WebElement field = row.findElement(By.xpath("//*[@id=\"row_"+rowNum+"\"]/td["+col+"]"));
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
      //  return field.Text;
    }

    protected int Extra(int rowNum,int col) {
        WebElement row = driver.findElement(By.id("row_" + String.valueOf(rowNum)));
        WebElement field = row.findElement(By.xpath("//*[@id=\"row_"+rowNum+"\"]/td["+col+"]"));
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
        //  return field.Text;
    }
*/

    protected int Semestr_mark(int rowNum,int col) {
        WebElement row = driver.findElement(By.id("row_" + String.valueOf(rowNum)));
        WebElement field = row.findElement(By.xpath("//*[@id=\"row_"+rowNum+"\"]/td["+col+"]"));
        //*[@id="row_1"]/td[7]
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
        //  return field.Text;
    }

    protected boolean isElementPresent(By by) {// проверка наличия элемента на странице
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List list = driver.findElements(by);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return list.size() > 0;
    }

    @Test // Marking this method as part of the test
    public void check_control_sum_exam() {
        choose_semestr("S-8");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("main_top")));
            String subject = "CS203. Теория алгоритмов";
            String control = "Экзамен";
        if(!choose_subject(subject,control,"active","Семестр"))
            Assert.fail("Дисциплина не найдена");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("main_top")));
        int row=1; //change for 3x stdents
        int k=0;
        while(k<=3) {
            if(!isElementPresent(By.id("row_" + String.valueOf(row)))) break;
            int sum = 0;
            for (int i = 1; i < 11; ++i)
                if (Mark(row, i) != 0)
                    sum += Mark(row, i);
            //    sum += Exam(row);
            if(sum>0)k++;

            Assert.assertTrue(Semestr_mark(row,12) == sum, "Итоговая оценка за семестр рассчитана неверно. строка: "+String.valueOf(row));
            row++;
            //  Assert.assertEquals(driver.findElement(By.className("grade_title")).getText(), "CS203. Теория алгоритмов");
        }
        if(k==0)
            Assert.fail("Нет прикрепленных студентов к дисциплине с выставленными баллами");
    }

    @Test // Marking this method as part of the test
    public void check_control_sum_exam_exam() {
        choose_semestr("S-8");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("main_top")));
        String subject = "CS203. Теория алгоритмов";
        String control = "Экзамен";
        if(!choose_subject(subject,control,"active","Семестр"))
            Assert.fail("Дисциплина не найдена");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("main_top")));
        int row=1; //change for 3x stdents
        int k=0;
        while(k<=3) {
            if(!isElementPresent(By.id("row_" + String.valueOf(row)))) break;
            int sum = 0;
            for (int i = 1; i < 11; ++i)
                if (Mark(row, i) != 0)
                    sum += Mark(row, i);
                sum += Exam(row,14); // экзамен
                sum+= Exam(row,13); //добор баллов
            if(sum>0)k++;

            Assert.assertTrue(Semestr_mark(row,15) == sum, "Итоговая оценка рассчитана неверно. строка: "+String.valueOf(row));
            row++;
            //  Assert.assertEquals(driver.findElement(By.className("grade_title")).getText(), "CS203. Теория алгоритмов");
        }
        if(k==0)
            Assert.fail("Нет прикрепленных студентов к дисциплине с выставленными баллами");
    }

    @Test // Marking this method as part of the test
    public void check_control_sum_zach() {
        choose_semestr("S-7");
   //     wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("main_top")));
        String subject = "CS212. Парадигмы и технологии программирования";
        String control = "Зачет";
        if(!choose_subject(subject,control,"active","Семестр"))
            Assert.fail("Дисциплина не найдена");

        wait.until(ExpectedConditions.textToBe(By.className("main_top"),"Выставление баллов")); // visibilityOfElementLocated(By.className("main_top")));

        int row=1; //change for 3x stdents
        int k=0;
        while(k<=3) {
            if(!isElementPresent(By.id("row_" + String.valueOf(row)))) break;
            int sum = 0;
            for (int i = 1; i < 6; ++i)
                if (Mark(row, i) != 0)
                    sum += Mark(row, i);
            //    sum += Exam(row);
            if(sum>0)k++;

            Assert.assertTrue(Semestr_mark(row,7) == sum, "Итоговая оценка рассчитана неверно. строка: "+String.valueOf(row));
            row++;
            //  Assert.assertEquals(driver.findElement(By.className("grade_title")).getText(), "CS203. Теория алгоритмов");
        }
        if(k==0)
            Assert.fail("Нет прикрепленных студентов к дисциплине с выставленными баллами");

    }

    @Test // Marking this method as part of the test
    public void check_control_sum_zach_all() {
        choose_semestr("S-7");
        //     wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("main_top")));
        String subject = "CS212. Парадигмы и технологии программирования";
        String control = "Зачет";
        if(!choose_subject(subject,control,"active","Семестр"))
            Assert.fail("Дисциплина не найдена");

        wait.until(ExpectedConditions.textToBe(By.className("main_top"),"Выставление баллов")); // visibilityOfElementLocated(By.className("main_top")));

        int row=1; //change for 3x stdents
        int k=0;
        while(k<=3) {
            if(!isElementPresent(By.id("row_" + String.valueOf(row)))) break;
            int sum = 0;
            for (int i = 1; i < 6; ++i)
                if (Mark(row, i) != 0)
                    sum += Mark(row, i);
                sum += Exam(row,9); // добор
            sum += Mark(row,6); // бонус
            if(sum>0)k++;

            Assert.assertTrue(Semestr_mark(row,10) == sum, "Итоговая оценка рассчитана неверно. строка: "+String.valueOf(row));
            row++;
            //  Assert.assertEquals(driver.findElement(By.className("grade_title")).getText(), "CS203. Теория алгоритмов");
        }
        if(k==0)
            Assert.fail("Нет прикрепленных студентов к дисциплине с выставленными баллами");

    }

  /*  @AfterTest
    public void to_home(){
        driver.findElement(By.className("logotype")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("main_top")));
    }*/

    @AfterClass // Runs this method after all the test methods in the current class have been run
    public void tearDown() {
        // Close all browser windows and safely end the session
        if(driver != null)
        {
            driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[3]/a[2]")).click();
            driver.quit();
            driver=null;}
    }
}
