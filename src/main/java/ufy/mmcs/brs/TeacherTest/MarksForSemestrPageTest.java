package ufy.mmcs.brs.TeacherTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @warnong Если тесты падают на удалении элементов - зайти на траницу и
 * вручную удалить все оценки из первых двух строчек (они были пустые) селектор - все группы.
 */
public class MarksForSemestrPageTest extends Helper {
    /** \brief Инициализация
     *
     * Инициализация драйвера браузера. Установка неявных ожиданий. Атоизация под аккаунтом dem\22222
     * @see ufy.mmcs.brs.AuthorizationTest.Helper ::timeouts_set, Helper::get_chrome_driver, Helper::get_firefox_driver, terarDown
     * @param browser
     */
    @Parameters("browser")
    @BeforeClass
    public void  getDriver(@Optional("chrome") String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", get_chrome_driver());
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", get_firefox_driver());
            driver = new FirefoxDriver();
        }
        timeouts_set();
        go_home();
        //  if_grade_visiable();
        authorization();
    }

    /**
     * Close all browser windows and safely end the session
     */
    @AfterClass
    public void tearDown() {
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
            driver=null;}
    }

    @Test
    public void check_empty_string(){
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/rate");
        if(!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(),"CS332. Компьютерная графика",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row=1;
        for(int i=1; i<10; i++){ // 10-й это бонусы
            if(!check_emty_mark(row,i))
                Assert.fail("Строка не пустая "+i+" "+Mark(row,i));

        }
        //*[@id="row_1"]/td[15] это Итог
        //*[@id="row_1"]/td[11] это Итог за семестр
        Assert.assertEquals(Semestr_mark(1,15),0,
                "Сумма в Итге для пустой строке не нулевая " + Semestr_mark(1,15));
        Assert.assertEquals(Semestr_mark(1,11),0,
                "Сумма в Итогк за семестр для пустой строке не нулевая " + Semestr_mark(1,11));
        go_home();
    }

    @Test
    public void input_wrong_mark_101() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/rate");
        if(!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(),"CS332. Компьютерная графика",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        //*[@id="row_1"]/td[15] это Итог
        //*[@id="row_1"]/td[11] это Итог за семестр

        int row=1;
        int col=1;
        if(!check_emty_mark(col,row))
            Assert.fail("Ячейка не пустая " + check_emty_mark(col,row));
        set_mark(row,col,"101");
        if(IsElementVisible(By.xpath("/html/body/div[4]/div"))){
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div")).getText();
            Assert.assertEquals(error_text, "Не удалось добавить балл","Не соответсвует сообщения об ошибке" );
        }
        else{
            Assert.fail("Нет сообщения об ошибке");
        }

        Assert.assertEquals(check_emty_mark(row,col),true,"Оценка сохранилась в ячейке");
        Assert.assertEquals(Semestr_mark(row,11),0,"Выставилась оценка в Итог за семестр");
        driver.navigate().refresh();
        Assert.assertEquals(Mark(row,col),0,"Оценка сохранилась в ячейке после перезагрузки");

        go_home();
    }

    @Test
    public void input_wrong_mark_0() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/rate");
        if(!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(),"CS332. Компьютерная графика",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        //*[@id="row_1"]/td[15] это Итог
        //*[@id="row_1"]/td[11] это Итог за семестр

        int row=1;
        int col=1;
        if(!check_emty_mark(row,col))
            Assert.fail("Ячейка не пустая " + check_emty_mark(row,col));
        set_mark(row,col,"0");
        if(IsElementVisible(By.xpath("/html/body/div[4]/div"))){
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div")).getText();
            Assert.assertEquals(error_text, "Балл добавлен","Не соответсвует сообщения о выставлении" );
        }
        else{
            Assert.fail("Нет сообщения о выставлении");
        }
        // Assert.assertFalse(check_emty_mark(row,col),"Оценка не выставилась в Итог");
        Assert.assertEquals(Semestr_mark(row,11),0,
                "Выставилась другая оценка в Итог за семестр " +Semestr_mark(row,11));
        delete_mark(row,col);
        // if(IsElementVisible(By.xpath("/html/body/div[4]/div[2]"))) { //если падает вставить раскоментировать ожидание
        //   TimeUnit.SECONDS.sleep(3);
        if(!check_emty_mark(row,col))
            Assert.fail("Не удалилась оценка row:"+row+" col:"+col);

        go_home();
        //   } else Assert.fail("Не удалился элемент из ячейки");

    }

    @Test
    public void delete_mark_1() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/rate");
        if(!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(),"CS332. Компьютерная графика",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        //*[@id="row_1"]/td[15] это Итог
        //*[@id="row_1"]/td[11] это Итог за семестр

        int row=1;
        int col=2;
        if(!check_emty_mark(row,col))
            Assert.fail("Ячейка не пустая " + Mark(row,col));
        set_mark(row,col,"1");

        if(check_emty_mark(row,col))
            Assert.fail("Не выставилась оценка");
        // TimeUnit.SECONDS.sleep(2);
        delete_mark(row,col);

        if(IsElementVisible(By.xpath("/html/body/div[4]/div[2]"))) { //если падает вставить раскоментировать ожидание
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[2]")).getText();
            Assert.assertEquals(error_text, "Балл удален","Не соответсвует сообщение о удалении" );
        }
        else{
            Assert.fail("Нет сообщение об удалении");
        }

        if(!check_emty_mark(row,col))
            Assert.fail("Не удалилась оценка row:"+row+" col:"+col);

        Assert.assertEquals(Semestr_mark(row,11),0,
                "Не удалилась оценка в  Итоге за семестр " +Semestr_mark(row,11));

    }

    @Test
    public void add_mark_1() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/rate");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        //*[@id="row_1"]/td[15] это Итог
        //*[@id="row_1"]/td[11] это Итог за семестр

        int row = 1;
        int col = 3;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + Mark(row, col));
        set_mark(row, col, "1");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");
        if(IsElementVisible(By.xpath("/html/body/div[4]/div"))){
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div")).getText();
            Assert.assertEquals(error_text, "Балл добавлен","Не соответсвует сообщения о выставлении" );
        }
        else{
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 11), 1,
                "Не выставилась оценка в  Итоге за семестр " + Semestr_mark(row, 11));
        Assert.assertEquals(Semestr_mark(row, 15), 1,
                "Не выставилась оценка в  Итог " + Semestr_mark(row, 15));
        delete_mark(row,col);
        if(!check_emty_mark(row,col))
            Assert.fail("Не удалилась оценка row:"+row+" col:"+col);
        go_home();
    }

    @Test
    public void change_mark_1_for_2() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/rate");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        //*[@id="row_1"]/td[15] это Итог
        //*[@id="row_1"]/td[11] это Итог за семестр

        int row = 1;
        int col = 4;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + Mark(row, col));
        set_mark(row, col, "1");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        set_mark(row, col, "2");

        if(IsElementVisible(By.xpath("/html/body/div[4]/div[2]"))){
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[2]")).getText();
            Assert.assertEquals(error_text, "Балл обновлен","Не соответсвует сообщения о выставлении" );
        }
        else{
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 11), 2,
                "Не изменилась оценка в  Итоге за семестр " + Semestr_mark(row, 11));
        Assert.assertEquals(Semestr_mark(row, 15), 2,
                "Не изменилась оценка в  Итог " + Semestr_mark(row, 15));
        delete_mark(row,col);
        if(!check_emty_mark(row,col))
            Assert.fail("Не удалилась оценка row:"+row+" col:"+col);
        go_home();
    }

    @Test
    public void summ_mark_1_and_2() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/rate");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        //*[@id="row_1"]/td[15] это Итог
        //*[@id="row_1"]/td[11] это Итог за семестр

        int row = 1;
        int col = 5;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + Mark(row, col));
        if (!check_emty_mark(row, col+1))
            Assert.fail("Ячейка не пустая " + Mark(row, col+1));
        set_mark(row, col, "2");
        set_mark(row,col+1,"2");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка 1" + col);
        if (check_emty_mark(row, col+1))
            Assert.fail("Не выставилась оценка 2" + col+1);
        Assert.assertEquals(Semestr_mark(row, 11), 4,
                "Не изменилась оценка в  Итоге за семестр " + Semestr_mark(row, 11));
        Assert.assertEquals(Semestr_mark(row, 15), 4,
                "Не изменилась оценка в  Итог " + Semestr_mark(row, 15));
        delete_mark(row,col);
        if(!check_emty_mark(row,col))
            Assert.fail("Не удалилась оценка row:"+row+" col:"+col);
        delete_mark(row,col+1);
        if(!check_emty_mark(row,col))
            Assert.fail("Не удалилась оценка row:"+row+" col:"+col+1);
        go_home();
    }

    @Test
    public void check_mark_1_with_minus() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/rate");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        //*[@id="row_1"]/td[15] это Итог
        //*[@id="row_1"]/td[11] это Итог за семестр

        int row = 1;
        int col = 7;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + Mark(row, col));
        set_mark(row, col, "-1");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка " + col);

        Assert.assertEquals(Mark(row,col),1,"Не выставилась оценка. минус должен пропадать ");
        Assert.assertEquals(Semestr_mark(row, 11), 1,
                "Не изменилась оценка в  Итоге за семестр " + Semestr_mark(row, 11));
        Assert.assertEquals(Semestr_mark(row, 15), 1,
                "Не изменилась оценка в  Итог " + Semestr_mark(row, 15));
        delete_mark(row,col);
        if(!check_emty_mark(row,col))
            Assert.fail("Не удалилась оценка row:"+row+" col:"+col);

        go_home();
    }

// дописать еще тесты на превышение ограничения по столбцу не забыть рефрешнуть в этом кейск
    //id for regular_35468
    //

}
