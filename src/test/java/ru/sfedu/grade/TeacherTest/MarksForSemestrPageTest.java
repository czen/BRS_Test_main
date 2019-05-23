package ru.sfedu.grade.TeacherTest;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

/**\brief Тесты выставления баллов за семестр
 *
 * Используется страница http://testgrade.sfedu.ru/discipline/3723/rate
 * @warning Если тесты падают на удалении элементов - зайти на траницу и
 * вручную удалить все оценки из первых двух строчек (они были пустые) селектор - все группы.
 * @warning Первая строка страницы должна быть пустой изначально. чтобы тесты прошли
 *  @version 1.0
 *  @author Stepanova
 *  @see AfterClickBtnsTest, MarksForSessiaPageTest, TeacherTest, MarksOfZachetPageTest, ProsmotrDisciplinPageTest, EditDisciplinPageTest, AfterClickBtnsTest, Helper
 */
public class MarksForSemestrPageTest extends Helper {
    /** \brief Инициализация
     *
     * Этот метод вызывается перед выполнением всех функций этого класса
     *
     * Инициализация драйвера браузера. Установка неявных ожиданий. Авторизация под аккаунтом dem\22222
     * @see Helper:timeouts_set, Helper::get_chrome_driver, Helper::get_firefox_driver, tearDown
     * @param browser Передается из xml-файла для выбора браузера, в котором запустятся тесты. По-умолчанию = chrom
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

    /** \brief Завершение работы
     *
     * Runs this method after all the test methods in the current class have been run.
     * Close all browser windows and safely end the session
     *
     * Закрытие браузера
     * @see getDriver
     */
    @AfterClass
    public void tearDown() {
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
            driver=null;}
    }

    /**
     * 1. Балл в Итогах для пустой строки должен быть = 0
     */
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

    /**Тест-кейс:
     * 1. Выставить очень большой балл (101)
     *
     * Ожидается: Балл не выставлен. Сообщение об ошибке. Итоги не изменились.
     * @throws InterruptedException
     */
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

    /**Тест-кейс:
     * 1. Выставить нулевой балл
     *
     * Ожидается: Поставлен ноль. Итоги не изменились.
     * @throws InterruptedException
     */
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
        if(check_emty_mark(row,col))
            Assert.fail("Не выставилась оценка");

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
   /*     if(!check_emty_mark(row,col))
            Assert.fail("Не удалилась оценка row:"+row+" col:"+col);*/

        go_home();
        //   } else Assert.fail("Не удалился элемент из ячейки");

    }

    /**
     * Тест-кейс:
     * 1. Выставить балл
     * 2. Удалить балл
     *
     * Ожидается: Окно с сообщением. Оценка удалилась. Итоги изменились
     * @throws InterruptedException
     */
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

    /**
     * Тест-кейс:
     * 1. Выставить балл
     *
     * Ожидается: Окно с сообщением. Итоги изменятся
     * @throws InterruptedException
     */
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
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Выставить балл
     * 2. Поменять этот балл
     *
     * Ожидается: окно с сообщением. Итог и Итог за семестр изменятся
     * @throws InterruptedException
     */
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
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Выставить балл
     * 2. Выставить еще один балл в туж е строку
     *
     * Ожидается: Итог и Итог за семестр изменятся на их сумму
     * @throws InterruptedException
     */
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
        go_home();
    }

    /**Тест-кейс:
     * 1. Выставить балл=-1
     *
     * Ожидается:
     *
     * 1. Выставится балл=1.
     * 2. Итог и Итог за семестр изменятся на 1
     * @throws InterruptedException
     */
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

        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Прочесть максимальный для столбца балл
     * 2. Выставить балл на 1 больше
     *
     * Ожидается: оценка не выставилась, окно с ошибкой
     * @throws InterruptedException
     */
    @Test
    public void check_mark_over_max() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/rate");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        //*[@id="row_1"]/td[15] это Итог
        //*[@id="row_1"]/td[11] это Итог за семестр

        int row = 1;
        int col = 8;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + Mark(row, col));

        // regular_35475 / для восьмой колоки макс балл
        if(!IsElementVisible(By.id("regular_35475")))
            Assert.fail("Не доступна строчка с макс баллами / или восьмая колонка");

        String max_mark=driver.findElement(By.id("regular_35475")).getText();
        int temp;
        try {
            temp=Integer.parseInt(max_mark);
        } catch (NumberFormatException e) {
            Assert.fail("В строке с максимальными баллами не число "+max_mark);
            temp=-2;
        }
        temp++;

        set_mark(row, col, String.valueOf(temp));

        if (!check_emty_mark(row, col))
            Assert.fail("Выставилась оценка " + Mark(row,col));

        if(IsElementVisible(By.xpath("/html/body/div[4]/div"))){
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div")).getText();
            Assert.assertEquals(error_text, "Превышение максимального балла",
                    "Не соответсвует ошибка о выставлении" );
        }
        else{
            Assert.fail("Нет сообщения о ошибке");
        }
        TimeUnit.MILLISECONDS.sleep(50);
        if (!check_emty_mark(row, col))
            Assert.fail("Выставилась оценка " + Mark(row,col)+" "+check_emty_mark(row, col));

        Assert.assertEquals(Semestr_mark(row, 11), 0,
                "Изменилась оценка в  Итоге за семестр " + Semestr_mark(row, 11));
        Assert.assertEquals(Semestr_mark(row, 15), 0,
                "Изменилась оценка в  Итог " + Semestr_mark(row, 15));
        driver.navigate().refresh();
        if (!check_emty_mark(row, col))
            Assert.fail("Сохранилась оценка после перезагрузки " + col);

        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Выставить бонусные баллы
     *
     * Ожидается: Итог изменится на эту сумму
     * @throws InterruptedException
     */
    @Test
    public void check_mark_to_bonus() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/rate");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        //*[@id="row_1"]/td[15] это Итог
        //*[@id="row_1"]/td[11] это Итог за семестр

        int row = 1;
        int col = 10;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + Mark(row, col));
        set_mark(row, col, "9");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка " + col);

        Assert.assertEquals(Mark(row,col),9,"Не выставилась другая оценка. "+Mark(row,col));
        Assert.assertEquals(Semestr_mark(row, 11), 0,
                "Изменилась оценка в  Итоге за семестр " + Semestr_mark(row, 11));
        TimeUnit.MILLISECONDS.sleep(20);
        Assert.assertEquals(Semestr_mark(row, 15), 9,
                "Не изменилась оценка в  Итог " + Semestr_mark(row, 15));
        delete_mark(row,col);

        Assert.assertEquals(Semestr_mark(row, 15), 0,
                "Не изменилась оценка после удаления бонусов в Итоге " + Semestr_mark(row, 15));
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Просуммировать 7 строку до бонусов
     * 2. сравнить с Итогом за семестр
     * 3. Добавить бонусы и экзамен
     * 4. Сравнить с Итогом
     * @warning Если сумма балов больше 100 тест будет падать. Выставляется ведь "100+"
     * @throws InterruptedException
     */
    @Test
    public void check_summ_mark_in_string() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/rate");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        //*[@id="row_1"]/td[15] это Итог
        //*[@id="row_1"]/td[11] это Итог за семестр

        int row = 7;
        //int col = 1;
        int sum=0;
        for(int col=1; col<=9;col++){
            sum+=Mark(row,col);
        }

        Assert.assertEquals(Semestr_mark(row, 11), sum,
                "Оценка в  Итоге за семестр не совпадает " + Semestr_mark(row, 11));
        sum+=Mark(row,10);
        sum+=Semestr_mark(row,14);
        Assert.assertEquals(Semestr_mark(row, 15), sum,
                "Оценка в  Итоге не совпадает " + Semestr_mark(row, 15));
        //*[@id="row_6"]/td[14] - экзамен столбец
        go_home();
    }


}
