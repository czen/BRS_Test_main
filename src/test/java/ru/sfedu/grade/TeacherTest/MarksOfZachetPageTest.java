package ru.sfedu.grade.TeacherTest;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**\brief Тест страницы сессии для зачета
 * @warning Если тесты падают на удалении элементов - зайти на траницу и
 * вручную удалить все оценки из первых двух строчек (они были пустые) селектор - все группы.
 * @warning Первая строка страницы должна быть пустой изначально. чтобы тесты прошли
 * @version 1.0
 * @author Stepanova
 *  @see  AfterClickBtnsTest, MarksForSemestrPageTest, MarksForSessiaPageTest, TeacherTest, ProsmotrDisciplinPageTest, EditDisciplinPageTest, AfterClickBtnsTest, Helper
 */
public class MarksOfZachetPageTest extends Helper {
    /** \brief Чтение конфиг файла. Инициализация драайвера. Установка ожиданий.
     *
     * Этот метод вызывается перед выполнением всех функций этого класса, т.е. тестов.
     *
     *  По-умолчанию используется браузер хром. Xml файлом можно настраивать запуск в разных браузерах
     *  (следует тогда запускать именно его, а не класс или проект)
     * @param browser Передается из xml-файла для выбора браузера, в котором запустятся тесты. По-умолчанию =  chrome
     * @see Helper::timeouts_set, Helper::read_propities, Helper::initialization_driver, tearDown
     */
    @Parameters("browser")
    @BeforeClass
    public void getDriver(@Optional("chrome") String browser) {
        read_propities();
        initialization_driver(browser);
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
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    //*[@id="row_1"]/td[5]

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Выставить балл в добор
     * 3. Удалить балл
     *
     * Ожидается: Балл добавлен. Поп-ап. Балл удален. Поп-ап.
     * @throws InterruptedException
     */
    @Test
    public void add_delete_dobor_mark() throws InterruptedException {
        driver.navigate().to(get_base_url()+"discipline/3666/exam");
        TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "Практикум на ЭВМ (яз. прогр.) (87) – Зачет",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        int row=1;
        int col=1;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        set_mark(row,col,"20");
        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div")).getText();
            Assert.assertEquals(error_text, "Балл добавлен", "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 5), 20,
                "Выставилась другая оценка в " + Semestr_mark(row, 5));
        delete_mark(row, col);
        if (IsElementVisible(By.xpath("/html/body/div[4]/div[2]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[2]")).getText();
            Assert.assertEquals(error_text, "Балл удален", "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 5), 0,
                "Выставилась другая оценка в " + Semestr_mark(row, 5));
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Выставить первый добор в 60 баллов
     * 3. Выставить второй добор
     *
     * Ожидается: оценка не выставлена. негативный поп-ап
     * @throws InterruptedException
     */
    @Test
    public void add_dobor_mark_check_second_dobor_60() throws InterruptedException {
        driver.navigate().to(get_base_url()+"discipline/3666/exam");
        TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "Практикум на ЭВМ (яз. прогр.) (87) – Зачет",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        int row=1;
        int col=1;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        set_mark(row,col,"60");
        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        set_mark(row,2,"1");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[2]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[2]")).getText();
            Assert.assertEquals(error_text, "Превышение максимума баллов для добора",
                    "Не соответсвует сообщения о выставлении");
        } else {
            delete_mark(row, col);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 5), 60,
                "Выставилась другая оценка в " + Semestr_mark(row, 5));
        delete_mark(row, col);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Выставить в первый добор 61 балл
     *
     * Ожидается: Негативный поп-ап. Оценка не выставлена
     * @throws InterruptedException
     */
    @Test
    public void add_dobor_mark_61_wrong() throws InterruptedException {
        driver.navigate().to(get_base_url()+"discipline/3666/exam");
        TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "Практикум на ЭВМ (яз. прогр.) (87) – Зачет",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        int row=1;
        int col=1;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        set_mark(row,col,"61");
        if (!check_emty_mark(row, col))
            Assert.fail("выставилась оценка");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div")).getText();
            Assert.assertEquals(error_text, "Превышение максимума баллов для добора",
                    "Не соответсвует сообщения о выставлении");
        } else {
            delete_mark(row, col);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 5), 0,
                "Выставилась другая оценка в " + Semestr_mark(row, 5));
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Выставить первый добор
     * 3. Выставить второй добор
     *
     * Ожидается: Оценки выставлены. Сумма итога = сумме доборо. поп-ап о выставлении
     * @throws InterruptedException
     */
    @Test
    public void add_dobor_mark_add_dobor2() throws InterruptedException {
        driver.navigate().to(get_base_url()+"discipline/3666/exam");
        TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "Практикум на ЭВМ (яз. прогр.) (87) – Зачет",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        int row=1;
        int col=1;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        set_mark(row,col,"20");
        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        set_mark(row,2,"20");
        if (check_emty_mark(row, 2))
            Assert.fail("Не выставилась оценка");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[2]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[2]")).getText();
            Assert.assertEquals(error_text, "Балл добавлен",
                    "Не соответсвует сообщения о выставлении");
        } else {
            delete_mark(row, 2);
            delete_mark(row, col);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 5), 40,
                "Выставилась другая оценка в " + Semestr_mark(row, 5));
        delete_mark(row, 2);
        delete_mark(row,col);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Выставить второй добор
     *
     * Ожидается: Негативный поп-ап. Оценка не выставлена
     * @throws InterruptedException
     */
    @Test
    public void add_dobor2_mark_without_dobor1() throws InterruptedException {
        driver.navigate().to(get_base_url()+"discipline/3666/exam");
        TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "Практикум на ЭВМ (яз. прогр.) (87) – Зачет",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        int row=1;
        int col=2;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        set_mark(row,col,"20");
        if (!check_emty_mark(row, col))
            Assert.fail("выставилась оценка");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[1]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[1]")).getText();
            Assert.assertEquals(error_text, "Невозможно выставление баллов добора при отсутствии предыдущего добора",
                    "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 5), 0,
                "Выставилась другая оценка в " + Semestr_mark(row, 5));
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Выставить добор первый
     * 3. ВЫставить добор второй
     * 4. Удалить оценку из первого добора
     *
     * Ожидается: Негативный поп-ап. Оценка не удалилась
     * @throws InterruptedException
     */
    @Test
    public void add_dobor_mark_add_dobor2_delete_dobor1() throws InterruptedException {
        driver.navigate().to(get_base_url()+"discipline/3666/exam");
        TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "Практикум на ЭВМ (яз. прогр.) (87) – Зачет",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        int row=1;
        int col=1;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        set_mark(row,col,"20");
        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        set_mark(row,2,"20");
        if (check_emty_mark(row, 2))
            Assert.fail("Не выставилась оценка");

        WebElement element = driver.findElement(By.id("col_row_" + col + "_" + row));
        WebElement field = element.findElement(By.tagName("input"));
        field.sendKeys(Keys.BACK_SPACE+"" +Keys.BACK_SPACE+"\n"+Keys.ESCAPE);
        TimeUnit.SECONDS.sleep(1);
        if (check_emty_mark(row, col))
            Assert.fail("удалилась оценка");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[3]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[3]")).getText();
            Assert.assertEquals(error_text, "Невозможно удаление баллов добора при наличии следующего добора",
                    "Не соответсвует сообщения о выставлении");
        } else {
            delete_mark(row, 2);
            delete_mark(row, col);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 5), 40,
                "Выставилась другая оценка в " + Semestr_mark(row, 5));
        delete_mark(row, 2);
        delete_mark(row,col);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Открыть страницу
     * 2. Выставить первый добор
     * 3. Выставить второй добор
     * 4. Удалить второй добор
     *
     * Ожидается: Поп-ап. Оценка удалилась.
     * @throws InterruptedException
     */
    @Test
    public void add_dobor_mark_add_dobor2_delete_dobor2() throws InterruptedException {
        driver.navigate().to(get_base_url()+"discipline/3666/exam");
        TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "Практикум на ЭВМ (яз. прогр.) (87) – Зачет",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        int row=1;
        int col=1;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        set_mark(row,col,"20");
        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        set_mark(row,2,"20");
        if (check_emty_mark(row, 2))
            Assert.fail("Не выставилась оценка");

        WebElement element = driver.findElement(By.id("col_row_" + 2 + "_" + row));
        WebElement field = element.findElement(By.tagName("input"));
        field.sendKeys(Keys.BACK_SPACE+"" +Keys.BACK_SPACE+"\n"+Keys.ESCAPE);
        TimeUnit.SECONDS.sleep(1);
        if (!check_emty_mark(row, 2))
            Assert.fail("Не удалилась оценка");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[3]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[3]")).getText();
            Assert.assertEquals(error_text, "Балл удален",
                    "Не соответсвует сообщения о выставлении");
        } else {
           // delete_mark(row, 2);
            delete_mark(row, col);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 5), 20,
                "Выставилась другая оценка в " + Semestr_mark(row, 5));
        //delete_mark(row, 2);
        delete_mark(row,col);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Открыть страницу семестра
     * 2. Выставить все оценки
     * 3. Открыть страницу сессии
     * 4. Выставить в первый добор балл
     * 5. Выставить во второй добор балл
     *
     * Ожидается: Оценки не выставлены. Негативный поп-ап.
     * @throws InterruptedException
     */
    @Test
    public void set_avtomat_and_check_dobor() throws InterruptedException {
        driver.navigate().to(get_base_url()+"discipline/3666/rate");
        if(!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(),"Практикум на ЭВМ (яз. прогр.) (87)",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        int row=2;
        //*[@id="row_2"]/td[12]
        if(Semestr_mark(row,12)!=100) {
            ArrayList<Integer> marks = new ArrayList<Integer>();
            marks.add(10);
            marks.add(14);
            marks.add(10);
            marks.add(16);
            marks.add(14);
            marks.add(10);
            marks.add(12);
            marks.add(14);
            for (int i = 1; i < 9; i++) {
                set_mark(row, i, String.valueOf(marks.get(i - 1)));
                if (check_emty_mark(row, i))
                    Assert.fail("He Выставилась оценка " + Mark(row, i) + " " + i);
            }
        }


        driver.navigate().to(get_base_url()+"discipline/3666/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "Практикум на ЭВМ (яз. прогр.) (87) – Зачет",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int col1=1;

        if (!check_emty_mark(row, col1)) {
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        }

        set_mark(row,col1,"10");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[1]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[1]")).getText();
            Assert.assertEquals(error_text, "Превышение максимума баллов для добора",
                    "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }

        Assert.assertEquals(Semestr_mark(row, 5), 100,
                "Выставилась другая оценка в Итог " + Semestr_mark(row, 5));

        set_mark(row,2,"10");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[2]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[2]")).getText();
            Assert.assertEquals(error_text, "Превышение максимума баллов для добора",
                    "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }

        Assert.assertEquals(Semestr_mark(row, 5), 100,
                "Выставилась другая оценка в Итог " + Semestr_mark(row, 5));

        //удаление оценок
        driver.navigate().to(get_base_url()+"discipline/3666/rate");
        if(!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(),"Практикум на ЭВМ (яз. прогр.) (87)",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        boolean flag=false;
        String error="";
        for(int i=1;i<9;i++){
            delete_mark(row,i);
            if (!check_emty_mark(row, i)) {
                flag=true;
                error+="He удалилась оценка " + Mark(row, i) + " " + i;
            }
        }
        if(flag)
            Assert.fail(error);

        go_home();


    }

}
