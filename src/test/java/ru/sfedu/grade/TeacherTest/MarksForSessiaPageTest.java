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

/**
 * \brief Тесты страницы сессии для экзамена.
 * @warning Если тесты падают, следует зайти на страницу и вручную убрать все оценки с первой строки. Иначе все и так упадет.
 * @warning Если тесты падают на удалении элементов - зайти на траницу и
 * вручную удалить все оценки из первых двух строчек (они были пустые) селектор - все группы.
 * @warning Первая строка страницы должна быть пустой изначально. чтобы тесты прошли
 * @version 1.0
 *  @author Stepanova
 *  @see  AfterClickBtnsTest, MarksForSemestrPageTest, TeacherTest, MarksOfZachetPageTest, ProsmotrDisciplinPageTest, EditDisciplinPageTest, AfterClickBtnsTest, Helper
 */
public class MarksForSessiaPageTest extends Helper {
    /**
     * \brief Инициализация
     * <p>
     * Инициализация драйвера браузера. Установка неявных ожиданий. Автоизация под аккаунтом dem\22222
     *
     * @param browser Передается из xml-файла для выбора браузера, в котором запустятся тесты. По-умолчанию = chrom
     * @see Helper::timeouts_set, Helper::get_chrome_driver, Helper::get_firefox_driver, tearDown
     */
    @Parameters("browser")
    @BeforeClass
    public void getDriver(@Optional("chrome") String browser) {
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
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    //*[@id="row_1"]/td[2] - Итог за семестр
    //*[@id="row_1"]/td[11] - Бонус
    //*[@id="row_1"]/td[12] - Итог

    // без добора-------------------------------------------------------------------

    /**
     * Тест-кейс:
     * 1. Затий на страницу
     *
     * Ожидается: первая строка пустая. Это необходимо.
     *
     * @throws InterruptedException
     */
    @Test
    public void check_empty_string() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        ArrayList<Integer> cols = new ArrayList<Integer>();
        cols.add(1);
        cols.add(2);
        cols.add(5);
        cols.add(7);
        int row = 1;
        for (Integer i : cols) {
            if (!check_emty_mark(row, i))
                Assert.fail("Строка не пустая " + i + " " + Mark(row, i));
        }
        Assert.assertEquals(Semestr_mark(1, 12), 0,
                "Сумма в Итoге для пустой строке не нулевая " + Semestr_mark(1, 12));
        Assert.assertEquals(Semestr_mark(1, 2), 0,
                "Сумма в Итогк за семестр для пустой строке не нулевая " + Semestr_mark(1, 2));
        Assert.assertEquals(Semestr_mark(1, 11), 0,
                "Сумма в Бонусах для пустой строке не нулевая " + Semestr_mark(1, 11));
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. айти на страницу
     * 2. Выставить в дбор баллов 0
     *
     * Ожидается: Оценка выставилась. Итог не изменился
     *
     * @throws InterruptedException
     */
    @Test
    public void dobor_input_0() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        //TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 1;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        set_mark(row, col, "0");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div")).getText();
            Assert.assertEquals(error_text, "Балл добавлен", "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 0,
                "Выставилась другая оценка в " + Semestr_mark(row, 12));
        delete_mark(row, col);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор баллов 39
     *
     * Ожидается: Негативный поп-ап. Оценка не выставилась
     *
     * @throws InterruptedException
     */
    @Test
    public void dobor_input_39_wrong() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        //TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 1;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        set_mark(row, col, "39");

        if (!check_emty_mark(row, col))
            Assert.fail("Выставилась оценка");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div")).getText();
            Assert.assertEquals(error_text, "Превышение максимума баллов для добора", "Не соответсвует сообщение об ошибке");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 0,
                "Выставилась оценка в Итог " + Semestr_mark(row, 12));
        delete_mark(row, col);
        go_home();
    }

    /**Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить 38 баллов в добор баллов
     *
     * Ожидается: Балл выставлен. Сообщение о выставлении. Итог изменился
     *
     * @throws InterruptedException
     */
    @Test
    public void dobor_input_38() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        //TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 1;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        set_mark(row, col, "38");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div")).getText();
            Assert.assertEquals(error_text, "Балл добавлен", "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 38,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        delete_mark(row, col);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить оценку
     * 3. Изменить оценку
     *
     * Ожидается: Поп-ап. Итог изменился. Оценка другая.
     *
     * @throws InterruptedException
     */
    @Test
    public void change_mark() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        //TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 1;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + Mark(row, col));
        set_mark(row, col, "3");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        set_mark(row, col, "5");

        if (check_emty_mark(row, col))
            Assert.fail("Оценка исчезла");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[2]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[2]")).getText();
            Assert.assertEquals(error_text, "Балл обновлен", "Не соответсвует сообщения об обновлении");
        } else {
            Assert.fail("Нет сообщения об обновлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 5,
                "Не изменилась оценка в  Итог " + Semestr_mark(row, 12));
        delete_mark(row, col);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить оценку
     * 3. Удалить оценку
     *
     * Ожидается: Поп-ап. Оценки нет. Итог нулевой
     *
     * @throws InterruptedException
     */
    @Test
    public void delete_mark() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        //TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 1;

        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + Mark(row, col));
        set_mark(row, col, "3");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        delete_mark(row, col);

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[2]"))) { //если падает вставить раскоментировать ожидание
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[2]")).getText();
            Assert.assertEquals(error_text, "Балл удален", "Не соответсвует сообщение о удалении");
        } else {
            Assert.fail("Нет сообщение об удалении");
        }

        if (!check_emty_mark(row, col))
            Assert.fail("Не удалилась оценка row:" + row + " col:" + col);

        Assert.assertEquals(Semestr_mark(row, 12), 0,
                "Не удалилась оценка в  Итоге " + Semestr_mark(row, 12));

    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить автомат
     *
     * Ожидается: Автомат не выставлен. Негативный поп-ап
     */
    @Test
    public void click_avtomat_without_marks() {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        //TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 4;

        if (driver.findElement(By.id("col_row_" + col + "_" + row)).isSelected())
            Assert.fail("Автомат установлен, а оценок нет");

        driver.findElement(By.id("col_row_" + col + "_" + row)).click();

        if (driver.findElement(By.id("col_row_" + col + "_" + row)).isSelected())
            Assert.fail("Автомат выставился");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[1]"))) { //если падает вставить раскоментировать ожидание
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[1]")).getText();
            Assert.assertEquals(error_text, "Не удалось установить автомат", "Не соответсвует сообщение об ошибке");
        } else {
            Assert.fail("Нет сообщение об автомате");
        }
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить неявку первую по счету
     *
     * Ожидается: Неявка не выставлена. негативный поп-ап
     */
    @Test
    public void click_neaka_without_marks1() {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        //TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 3;

        if (driver.findElement(By.id("col_row_" + col + "_" + row)).isSelected())
            Assert.fail("Неявка установлена, а оценок нет");

        driver.findElement(By.id("col_row_" + col + "_" + row)).click();

        if (driver.findElement(By.id("col_row_" + col + "_" + row)).isSelected())
            Assert.fail("Неявка установилась, а оценок нет");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[1]"))) { //если падает вставить раскоментировать ожидание
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[1]")).getText();
            Assert.assertEquals(error_text, "Не удалось установить неявку", "Не соответсвует сообщение об ошибке");
        } else {
            Assert.fail("Нет сообщение о неявке");
        }

    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить неявку вторую по счету
     *
     * Ожидается: Неявка не выставлена. Негативный поп-ап
     */
    @Test
    public void click_neaka_without_marks2() {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        //TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 6;

        if (driver.findElement(By.id("col_row_" + col + "_" + row)).isSelected())
            Assert.fail("Неявка установлена, а оценок нет");

        driver.findElement(By.id("col_row_" + col + "_" + row)).click();

        if (driver.findElement(By.id("col_row_" + col + "_" + row)).isSelected())
            Assert.fail("Неявка установилась, а оценок нет");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[1]"))) { //если падает вставить раскоментировать ожидание
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[1]")).getText();
            Assert.assertEquals(error_text, "Не удалось установить неявку", "Не соответсвует сообщение об ошибке");
        } else {
            Assert.fail("Нет сообщение о неявке");
        }
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить третью по счету неявку
     *
     * Ожидается: Неявка не выставлена. Негативный поп-ап
     */
    @Test
    public void click_neaka_without_marks3() {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        //TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 8;

        if (driver.findElement(By.id("col_row_" + col + "_" + row)).isSelected())
            Assert.fail("Неявка установлена, а оценок нет");

        driver.findElement(By.id("col_row_" + col + "_" + row)).click();

        if (driver.findElement(By.id("col_row_" + col + "_" + row)).isSelected())
            Assert.fail("Неявка установилась, а оценок нет");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[1]"))) { //если падает вставить раскоментировать ожидание
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[1]")).getText();
            Assert.assertEquals(error_text, "Не удалось установить неявку", "Не соответсвует сообщение об ошибке");
        } else {
            Assert.fail("Нет сообщение о неявке");
        }

    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить первую пересдачу
     *
     * Ожидается: Оценка не выставлена. Негативный поп-ап
     */
    @Test
    public void input_peresdacha1_wrong() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        int row = 1;
        int col = 7;
        int past_mark = Semestr_mark(row, 12);
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        set_mark(row, col, "3");
        if (!check_emty_mark(row, col))
            Assert.fail("выставилась оценка");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div")).getText();
            Assert.assertEquals(error_text, "Недостаточно баллов для сдачи экзамена",
                    "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), past_mark,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Вставить вторую пересдачу
     *
     * Ожидается: Оценка не выставлена. Негативный поп-ап
     */
    @Test
    public void input_peresdacha2_wrong() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        int row = 1;
        int col = 5;
        int past_mark = Semestr_mark(row, 12);
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        set_mark(row, col, "3");
        if (!check_emty_mark(row, col))
            Assert.fail("выставилась оценка");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div")).getText();
            Assert.assertEquals(error_text, "Недостаточно баллов для сдачи экзамена",
                    "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), past_mark,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 37 баллов
     * 3. Выставить в экзамен балл
     *
     * Ожидается: Балл не выставлен в экзамен. Негативный поп-ап.
     */
    @Test
    public void dobor_input_37_input_osn() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 1;
        if (!check_emty_mark(row, col))
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        set_mark(row, col, "37");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        set_mark(row, 2, "8");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[2]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[2]")).getText();
            Assert.assertEquals(error_text, "Недостаточно баллов для сдачи экзамена", "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 37,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));

        click_avtomat_without_marks();
        click_neaka_without_marks3();
        click_neaka_without_marks2();
        click_neaka_without_marks1();
        //   TimeUnit.SECONDS.sleep(2);
        input_peresdacha2_wrong();
        // TimeUnit.SECONDS.sleep(2);
        input_peresdacha1_wrong();
        delete_mark(row, col);
        go_home();
    }

    // добор есть. дпуск к экзамену (38)

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить в экзамен 41 балл
     *
     * Ожидается: Балл не выставлен. негативный поп-ап
     */
    @Test
    public void input_osn_sdacha_a_lot() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 1;
        if (!check_emty_mark(row, col)) {
            if (Mark(row, col) != 38)
                Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        } else
            set_mark(row, col, "38");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        set_mark(row, 2, "41");
        if (!check_emty_mark(row, 2))
            Assert.fail("выставилась оценка");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[2]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[2]")).getText();
            Assert.assertEquals(error_text, "Превышение максимального балла",
                    "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 38,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        delete_mark(row, col);
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить в экзамен балл нормальный
     *
     * Ожидается: Балл выставлен. Итог изменен
     */
    @Test
    public void input_osn_sdacha_22() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 1;
        if (!check_emty_mark(row, col)) {
            if (Mark(row, col) != 38)
                Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        } else
            set_mark(row, col, "38");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        set_mark(row, 2, "22");
        if (check_emty_mark(row, 2))
            Assert.fail("He выставилась оценка");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[2]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[2]")).getText();
            Assert.assertEquals(error_text, "Балл добавлен",
                    "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 60,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));

        click_neaka_without_marks2();
        click_neaka_without_marks3();
        click_avtomat_without_marks();

        delete_mark(row, 2);
        delete_mark(row, col);
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить балл в экзамен
     * 4. Выставить балл в пересдачу1
     *
     * Ожидается: Балл не добавлен в пересдачу. негативный поп-ап
     */
    @Test
    public void input_osn_sdacha_22_peresdacha1() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 1;
        if (!check_emty_mark(row, col)) {
            if (Mark(row, col) != 38)
                Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        } else
            set_mark(row, col, "38");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        set_mark(row, 2, "22");
        if (check_emty_mark(row, 2))
            Assert.fail("He выставилась оценка");

        set_mark(row, 5, "12");
        if (!check_emty_mark(row, 5))
            Assert.fail("выставилась оценка");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[3]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[3]")).getText();
            Assert.assertEquals(error_text, "Для студента сдавшего экзамен следующие пересдачи невозможны",
                    "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 60,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));

        delete_mark(row, 2);
        delete_mark(row, col);
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить в экзамен балл
     * 4. Выставить балл во вторую пересдачу
     *
     * Ожидается: Балл не добавлен. Негативный поп-ап
     */
    @Test
    public void input_osn_sdacha_22_peresdacha2() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 1;
        if (!check_emty_mark(row, col)) {
            if (Mark(row, col) != 38)
                Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        } else
            set_mark(row, col, "38");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        set_mark(row, 2, "22");
        if (check_emty_mark(row, 2))
            Assert.fail("He выставилась оценка");

        set_mark(row, 7, "12");
        if (!check_emty_mark(row, 7))
            Assert.fail("выставилась оценка");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[3]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[3]")).getText();
            Assert.assertEquals(error_text, "Невозможно выставление баллов попытки сдачи экзамена при отсутствии предыдущей попытки",
                    "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 60,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));

        delete_mark(row, 2);
        delete_mark(row, col);
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить балл в экзамен
     * 4. Выставить неявку на экзамен
     *
     * Ожидается: Выставилась неявка. Оценка обнулилась
     */
    @Test
    public void input_osn_sdacha_22_neavka_osn() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 1;
        if (!check_emty_mark(row, col)) {
            if (Mark(row, col) != 38)
                Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        } else
            set_mark(row, col, "38");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        set_mark(row, 2, "22");
        if (check_emty_mark(row, 2)) {
            delete_mark(row, col);
            Assert.fail("He выставилась оценка");
        }
        //
        if (driver.findElement(By.id("col_row_" + 3 + "_" + row)).findElement(By.tagName("input")).isSelected()) {
            delete_mark(row, 2);
            delete_mark(row, col);
            Assert.fail("Неявка выставленна");
        }
        driver.findElement(By.id("col_row_" + 3 + "_" + row)).findElement(By.tagName("input")).click();
        TimeUnit.SECONDS.sleep(1);
        if (driver.findElement(By.id("col_row_" + 3 + "_" + row)).isSelected()) {
            driver.findElement(By.id("col_row_" + 3 + "_" + row)).click();
            delete_mark(row, 2);
            delete_mark(row, col);
            Assert.fail("Неявка не выставилась");
        }

        if (!check_emty_mark(row, 2)) {
            driver.findElement(By.id("col_row_" + 3 + "_" + row)).click();
            delete_mark(row, 2);
            delete_mark(row, col);
            Assert.fail("Оценка не удалилась");
        }
        if (IsElementVisible(By.xpath("/html/body/div[4]/div[3]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[3]")).getText();
            Assert.assertEquals(error_text, "Неявка установлена",
                    "Не соответсвует сообщения о выставлении");
        } else {
            driver.findElement(By.id("col_row_" + 3 + "_" + row)).click();
            delete_mark(row, 2);
            delete_mark(row, col);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 38,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));

        driver.findElement(By.id("col_row_" + 3 + "_" + row)).click();
        delete_mark(row, 2);
        delete_mark(row, col);
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставть неявку на экзамен
     * 4. Выставить оценку за экзамен
     *
     * Ожидается: Неявка исчезла. Балл добавлен
     */
    @Test
    public void input_neavka_osn_sdacha_22_() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 1;
        if (!check_emty_mark(row, col)) {
            if (Mark(row, col) != 38)
                Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        } else
            set_mark(row, col, "38");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        if (driver.findElement(By.id("col_row_" + 3 + "_" + row)).findElement(By.tagName("input")).isSelected()) {
            delete_mark(row, 2);
            delete_mark(row, col);
            Assert.fail("Неявка выставленна");
        }
        driver.findElement(By.id("col_row_" + 3 + "_" + row)).findElement(By.tagName("input")).click();
        TimeUnit.SECONDS.sleep(1);
        if (driver.findElement(By.id("col_row_" + 3 + "_" + row)).isSelected()) {
            driver.findElement(By.id("col_row_" + 3 + "_" + row)).click();
            delete_mark(row, 2);
            delete_mark(row, col);
            Assert.fail("Неявка не выставилась");
        }

        set_mark(row, 2, "22");
        if (check_emty_mark(row, 2)) {
            delete_mark(row, col);
            Assert.fail("He выставилась оценка");
        }

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[3]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[3]")).getText();
            Assert.assertEquals(error_text, "Балл добавлен",
                    "Не соответсвует сообщения о выставлении");
        } else {
            driver.findElement(By.id("col_row_" + 3 + "_" + row)).click();
            delete_mark(row, 2);
            delete_mark(row, col);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 60,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));

        driver.findElement(By.id("col_row_" + 3 + "_" + row)).click();
        delete_mark(row, 2);
        delete_mark(row, col);
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставть в экзамен балл
     * 4. Удалить балл из добора
     *
     * Ожидается: Негативный поп-ап. Балл не удалился
     */
    @Test
    public void delete_dobor_after_cdacha() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 1;
        if (!check_emty_mark(row, col)) {
            if (Mark(row, col) != 38)
                Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        } else
            set_mark(row, col, "38");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        set_mark(row, 2, "22");
        if (check_emty_mark(row, 2)) {
            delete_mark(row, col);
            Assert.fail("He выставилась оценка");
        }

        WebElement element = driver.findElement(By.id("col_row_" + col + "_" + row));
        WebElement field = element.findElement(By.tagName("input"));
        field.sendKeys(Keys.BACK_SPACE + "" + Keys.BACK_SPACE + "\n" + Keys.ESCAPE);
        TimeUnit.SECONDS.sleep(1);

        if (check_emty_mark(row, col))
            Assert.fail("Оценка удалилась");
        if (check_emty_mark(row, 2))
            Assert.fail("Оценка за экзамен удалилась");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[3]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[3]")).getText();
            Assert.assertEquals(error_text, "Недостаточно баллов для сдачи экзамена",
                    "Не соответсвует сообщения о выставлении");
        } else {
            delete_mark(row, 2);
            delete_mark(row, col);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 60,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));

        delete_mark(row, 2);
        delete_mark(row, col);
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить экзамен
     * 4. Удалить балл за экзамен
     *
     * Ожидается: Балл удален. Итог изменился
     */
    @Test
    public void delete_osn_cdacha() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col = 1;
        if (!check_emty_mark(row, col)) {
            if (Mark(row, col) != 38)
                Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        } else
            set_mark(row, col, "38");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        set_mark(row, 2, "22");
        if (check_emty_mark(row, 2)) {
            delete_mark(row, col);
            Assert.fail("He выставилась оценка");
        }

        delete_mark(row, 2);


        if (IsElementVisible(By.xpath("/html/body/div[4]/div[3]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[3]")).getText();
            Assert.assertEquals(error_text, "Балл удален",
                    "Не соответсвует сообщения о выставлении");
        } else {
            delete_mark(row, 2);
            delete_mark(row, col);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 38,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));

        delete_mark(row, 2);
        delete_mark(row, col);
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить балл за экзамен
     * 4. Изменить балл за экзамен
     *
     * Ожидается: Балл изменен
     */
    @Test
    public void change_mark_in_osn_sdacha() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        //TimeUnit.MILLISECONDS.sleep(50);
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();


        int row = 1;
        int col = 1;
        if (!check_emty_mark(row, col)) {
            if (Mark(row, col) != 38)
                Assert.fail("Ячейка не пустая " + check_emty_mark(row, col));
        } else
            set_mark(row, col, "38");

        if (check_emty_mark(row, col))
            Assert.fail("Не выставилась оценка");

        set_mark(row, 2, "22");
        if (check_emty_mark(row, 2)) {
            delete_mark(row, col);
            Assert.fail("He выставилась оценка");
        }
        TimeUnit.SECONDS.sleep(1);

        set_mark(row,2,"3");
        if (check_emty_mark(row, 2)) {
            delete_mark(row, col);
            Assert.fail("оценка исчезла");
        }

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[3]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[3]")).getText();
            Assert.assertEquals(error_text, "Балл обновлен", "Не соответсвует сообщения об обновлении");
        } else {
            delete_mark(row,2);
            delete_mark(row, col);
            Assert.fail("Нет сообщения об обновлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 61,
                "Не изменилась оценка в  Итог " + Semestr_mark(row, 12));
        delete_mark(row,2);
        delete_mark(row, col);
        go_home();
    }

    // выставление пересдач

    /**
     * \brief  Устанавливает\удаляет неявку
     * @param flag устанавливает выставить или убрать неявку
     * @param row строка
     * @param col номер столбца для выставления
     * @return результат операции
     * @throws InterruptedException
     */
    private Boolean change_neavka(Boolean flag, int row, int col) throws InterruptedException {
        if (flag) { //postavit
            if (driver.findElement(By.id("col_row_" + col + "_" + row)).findElement(By.tagName("input")).isSelected()) {
                Assert.fail("Неявка уже выставлена");
                return false;
            }
            driver.findElement(By.id("col_row_" + col + "_" + row)).findElement(By.tagName("input")).click();
            TimeUnit.SECONDS.sleep(1);
            if (!driver.findElement(By.id("col_row_" + col + "_" + row)).findElement(By.tagName("input")).isSelected()) {
                Assert.fail("Неявка не выставилась");
                return false;
            }
            return true;
        } else {
            if (!driver.findElement(By.id("col_row_" + col + "_" + row)).findElement(By.tagName("input")).isSelected()) {
                Assert.fail("Неявка не выставлена");
                return false;
            }
            driver.findElement(By.id("col_row_" + col + "_" + row)).findElement(By.tagName("input")).click();
            TimeUnit.SECONDS.sleep(1);
            if (driver.findElement(By.id("col_row_" + col + "_" + row)).findElement(By.tagName("input")).isSelected()) {
                Assert.fail("Неявка не снялась");
                return false;
            }
            return true;
        }

    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить неявку за экзамен
     * 4. Выставить балл за первую пересдачу
     *
     * Ожидается: Балл выставлен. Итог изменен. поп-ап.
     */
    @Test
    public void add_peresdacha1() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col1 = 1;
        int col2=3;
        if (!check_emty_mark(row, col1)) {
            // if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (check_emty_mark(row, col1))
            Assert.fail("Не выставилась оценка");

        change_neavka(true,1,col2);

        int col3=5;

        set_mark(row,col3,"10");

        if (check_emty_mark(row, col3)) {
            delete_mark(row, col3);
            change_neavka(false,row,col2);
            delete_mark(row, col1);
            Assert.fail("Не выставилась оценка");
        }

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[3]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[3]")).getText();
            Assert.assertEquals(error_text, "Балл добавлен", "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 48,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        delete_mark(row, col3);
        change_neavka(false,row,col2);
        delete_mark(row, col1);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить неявку за экзамен
     * 4. Выставить балл за первую пересдачу
     * 5. Удалить балл за первую пересдачу
     *
     * Ожидается: Балл удален. Итог изменен. поп-ап.
     */
    @Test
    public void delete_mark_from_peresdacha1() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col1 = 1;
        int col2=3;
        if (!check_emty_mark(row, col1)) {
            //  if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (check_emty_mark(row, col1))
            Assert.fail("Не выставилась оценка");

        change_neavka(true,1,col2);

        int col3=5;

        set_mark(row,col3,"10");

        if (check_emty_mark(row, col3)) {
            delete_mark(row, col3);
            change_neavka(false,row,col2);
            delete_mark(row, col1);
            Assert.fail("Не выставилась оценка");
        }
        delete_mark(row,col3);
        if (!check_emty_mark(row, col3)) {
            delete_mark(row, col3);
            change_neavka(false,row,col2);
            delete_mark(row, col1);
            Assert.fail("Не удвлилась оценка");
        }
        if (IsElementVisible(By.xpath("/html/body/div[4]/div[4]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[4]")).getText();
            Assert.assertEquals(error_text, "Балл удален", "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 38,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        delete_mark(row, col3);
        change_neavka(false,row,col2);
        delete_mark(row, col1);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить неявку за экзамен
     * 4. Выставить балл за первую пересдачу
     * 5. Выставить неявку за вторую пересдачу
     *
     * Ожидается: Неявка невыставлена. Негативный поп-ап
     */
    @Test
    public void click_neavka3_after_peresdacha1() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col1 = 1;
        int col2=3;
        if (!check_emty_mark(row, col1)) {
            //  if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (check_emty_mark(row, col1))
            Assert.fail("Не выставилась оценка");

        change_neavka(true,1,col2);

        int col3=5;

        set_mark(row,col3,"10");

        if (check_emty_mark(row, col3)) {
            delete_mark(row, col3);
            change_neavka(false,row,col2);
            delete_mark(row, col1);
            Assert.fail("Не выставилась оценка");
        }

        if (driver.findElement(By.id("col_row_" + 8 + "_" + row)).findElement(By.tagName("input")).isSelected()) {
            Assert.fail("Неявка уже выставлена");
        }
        driver.findElement(By.id("col_row_" + 8 + "_" + row)).findElement(By.tagName("input")).click();
        TimeUnit.SECONDS.sleep(1);
        if (!driver.findElement(By.id("col_row_" + 8 + "_" + row)).findElement(By.tagName("input")).isSelected()) {
            change_neavka(false,row,8);
            delete_mark(row, col3);
            change_neavka(false,row,col2);
            delete_mark(row, col1);
            Assert.fail("Неявка выставилась");
        }

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[4]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[4]")).getText();
            Assert.assertEquals(error_text, "Неявка установлена", "Не соответсвует сообщения о выставлении");
        } else {
            change_neavka(false,row,8);
            delete_mark(row, col3);
            change_neavka(false,row,col2);
            delete_mark(row, col1);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 48,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        change_neavka(false,row,8);
        delete_mark(row, col3);
        change_neavka(false,row,col2);
        delete_mark(row, col1);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить неявку за экзамен
     * 4. Выставить балл за первую пересдачу
     * 5. Выставить неявку за первую пересдачу
     *
     * Ожидается: Неявка стоит. Балл обнулен
     */
    @Test
    public void click_neavka2_after_peresdacha1() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col1 = 1;
        int col2 = 3;
        if (!check_emty_mark(row, col1)) {
            //  if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (check_emty_mark(row, col1))
            Assert.fail("Не выставилась оценка");

        change_neavka(true, 1, col2);

        int col3 = 5;

        set_mark(row, col3, "10");

        if (check_emty_mark(row, col3)) {
            delete_mark(row, col3);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Не выставилась оценка");
        }

        change_neavka(true,row,6);

        if(!check_emty_mark(row,5)){
            change_neavka(false, row, 6);

            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Оценка не удалилась");
        }

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[4]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[4]")).getText();
            Assert.assertEquals(error_text, "Неявка установлена", "Не соответсвует сообщения о выставлении");
        } else {
            change_neavka(false, row, 6);

            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 38,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        change_neavka(false, row, 6);

        change_neavka(false, row, col2);
        delete_mark(row, col1);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить неявку за экзамен
     * 4. Выставить балл за первую пересдачу = 22
     * 5. Выставить неяву вторую пересдачу
     *
     * Ожидается: Неявка невыставлена. Негативный поп-ап
     */
    @Test
    public void peresdacha1_22_set_neavka3() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col1 = 1;
        int col2 = 3;
        if (!check_emty_mark(row, col1)) {
            //  if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (check_emty_mark(row, col1))
            Assert.fail("Не выставилась оценка");

        change_neavka(true, 1, col2);

        int col3 = 5;

        set_mark(row, col3, "22");

        if (check_emty_mark(row, col3)) {
            delete_mark(row, col3);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Не выставилась оценка");
        }

        if (driver.findElement(By.id("col_row_" + 8 + "_" + row)).findElement(By.tagName("input")).isSelected()) {
            Assert.fail("Неявка уже выставлена");
        }
        driver.findElement(By.id("col_row_" + 8 + "_" + row)).findElement(By.tagName("input")).click();
        TimeUnit.SECONDS.sleep(1);
        if (driver.findElement(By.id("col_row_" + 8 + "_" + row)).findElement(By.tagName("input")).isSelected()) {
            Assert.fail("Неявка выставилась");
        }

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[4]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[4]")).getText();
            Assert.assertEquals(error_text, "Не удалось установить неявку",
                    "Не соответсвует сообщения о выставлении");
        } else {
            delete_mark(row, 5);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 60,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        delete_mark(row, 5);
        change_neavka(false, row, col2);
        delete_mark(row, col1);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить неявку за экзамен
     * 4. Выставить балл за первую пересдачу = 22
     * 5. Выставить балл за вторую пересдачу
     *
     * Ожидается: Балл невыставлен. Негативный поп-ап
     */
    @Test
    public void peresdacha1_22_input_peresdacha2() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col1 = 1;
        int col2 = 3;
        if (!check_emty_mark(row, col1)) {
            //  if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (check_emty_mark(row, col1))
            Assert.fail("Не выставилась оценка");

        change_neavka(true, 1, col2);

        int col3 = 5;

        set_mark(row, col3, "22");

        if (check_emty_mark(row, col3)) {
            delete_mark(row, col3);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Не выставилась оценка");
        }

        set_mark(row,7,"10");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[4]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[4]")).getText();
            Assert.assertEquals(error_text, "Для студента сдавшего экзамен следующие пересдачи невозможны",
                    "Не соответсвует сообщения о выставлении");
        } else {
            delete_mark(row, 5);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 60,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        delete_mark(row, 5);
        change_neavka(false, row, col2);
        delete_mark(row, col1);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить неявку за экзамен
     * 4. Выставить балл за первую пересдачу
     * 5. Обновиь балл за первую пересдачу
     *
     * Ожидается: Балл обновлен. Поп-ап
     */
    @Test
    public void update_peresdacha1_22() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col1 = 1;
        int col2 = 3;
        if (!check_emty_mark(row, col1)) {
            //  if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (check_emty_mark(row, col1))
            Assert.fail("Не выставилась оценка");

        change_neavka(true, 1, col2);

        int col3 = 5;

        set_mark(row, col3, "22");

        if (check_emty_mark(row, col3)) {
            delete_mark(row, col3);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Не выставилась оценка");
        }

        set_mark(row,col3,"3");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[4]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[4]")).getText();
            Assert.assertEquals(error_text, "Балл обновлен",
                    "Не соответсвует сообщения о выставлении");
        } else {
            delete_mark(row, 5);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 61,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        delete_mark(row, 5);
        change_neavka(false, row, col2);
        delete_mark(row, col1);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить неявку за экзамен
     * 4. Выставить балл за первую пересдачу
     * 5. Удалить неявку за экзамен
     *
     * Ожидается: Неявка неудалена. Негативный поп-ап
     */
    @Test
    public void delete_osn_neavka_after_peresdacha1() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col1 = 1;
        int col2 = 3;
        if (!check_emty_mark(row, col1)) {
            //  if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (check_emty_mark(row, col1))
            Assert.fail("Не выставилась оценка");

        change_neavka(true, 1, col2);

        int col3 = 5;

        set_mark(row, col3, "22");

        if (check_emty_mark(row, col3)) {
            delete_mark(row, col3);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Не выставилась оценка");
        }

        if (!driver.findElement(By.id("col_row_" + 3 + "_" + row)).findElement(By.tagName("input")).isSelected()) {
            Assert.fail("Неявка не выставлена");
        }
        driver.findElement(By.id("col_row_" + 3 + "_" + row)).findElement(By.tagName("input")).click();
        TimeUnit.SECONDS.sleep(1);
        if (!driver.findElement(By.id("col_row_" + 3 + "_" + row)).findElement(By.tagName("input")).isSelected()) {
            Assert.fail("Неявка снялась");
        }

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[4]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[4]")).getText();
            Assert.assertEquals(error_text, "Не удалось удалить неявку",
                    "Не соответсвует сообщения о выставлении");
        } else {
            delete_mark(row, 5);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 60,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        delete_mark(row, 5);
        change_neavka(false, row, col2);
        delete_mark(row, col1);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить неявку за экзамен
     * 4. Выставить балл за первую пересдачу
     * 5. Удалить балл за добор
     *
     * Ожидается: Балл неудален. Негативный поп-ап
     */
    @Test
    public void delete_dobor_after_peresdacha1() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col1 = 1;
        int col2 = 3;
        if (!check_emty_mark(row, col1)) {
            //  if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (check_emty_mark(row, col1))
            Assert.fail("Не выставилась оценка");

        change_neavka(true, 1, col2);

        int col3 = 5;

        set_mark(row, col3, "22");

        if (check_emty_mark(row, col3)) {
            delete_mark(row, col3);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Не выставилась оценка");
        }

        WebElement element = driver.findElement(By.id("col_row_" + 1 + "_" + row));
        WebElement field = element.findElement(By.tagName("input"));
        //element.clear();
        field.sendKeys(Keys.BACK_SPACE+"" +Keys.BACK_SPACE+"\n"+Keys.ESCAPE);

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[4]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[4]")).getText();
            Assert.assertEquals(error_text, "Недостаточно баллов для сдачи экзамена",
                    "Не соответсвует сообщения о выставлении");
        } else {
            delete_mark(row, 5);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 60,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        delete_mark(row, 5);
        change_neavka(false, row, col2);
        delete_mark(row, col1);
        go_home();
    }

    // выставление пересдачи два

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить неявку за экзамен
     * 4. Выставить неявку на первую пересдачу
     * 5. Выставить балл за вторую пересдачу
     *
     * Ожидается: Балл выставлен. поп-ап. Итог изменен.
     */
    @Test
    public void add_peresdacha2() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col1 = 1;
        int col2 = 3;
        if (!check_emty_mark(row, col1)) {
            //  if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (check_emty_mark(row, col1))
            Assert.fail("Не выставилась оценка");

        change_neavka(true, 1, col2);

        int col3 = 6;

        change_neavka(true,row,col3);

        set_mark(row,7,"10");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[4]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[4]")).getText();
            Assert.assertEquals(error_text, "Балл добавлен",
                    "Не соответсвует сообщения о выставлении");
        } else {
            delete_mark(row, 7);
            change_neavka(false, row, col3);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 48,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        delete_mark(row, 7);
        change_neavka(false, row, col3);
        change_neavka(false, row, col2);
        delete_mark(row, col1);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить неявку за экзамен
     * 4. Выставить неявку на первую пересдачу
     * 5. Выставить балл за вторую пересдачу
     * 6. Обновить балл за вторую пересдачу
     *
     * Ожидается: Балл обновлен. Иог изменен. Поп-ап.
     */
    @Test
    public void update_peresdacha2() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col1 = 1;
        int col2 = 3;
        if (!check_emty_mark(row, col1)) {
            //  if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (check_emty_mark(row, col1))
            Assert.fail("Не выставилась оценка");

        change_neavka(true, 1, col2);

        int col3 = 6;

        change_neavka(true,row,col3);

        set_mark(row,7,"10");

        set_mark(row,7,"2");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[5]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[5]")).getText();
            Assert.assertEquals(error_text, "Балл обновлен",
                    "Не соответсвует сообщения о выставлении");
        } else {
            delete_mark(row, 7);
            TimeUnit.SECONDS.sleep(1);
            change_neavka(false, row, col3);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 50,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        delete_mark(row, 7);
        TimeUnit.SECONDS.sleep(1);
        change_neavka(false, row, col3);
        change_neavka(false, row, col2);
        delete_mark(row, col1);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить неявку за экзамен
     * 4. Выставить неявку на первую пересдачу
     * 5. Выставить балл за вторую пересдачу
     * 6. Удалить балл за вторую пересдачу
     *
     * Ожидается: Балл удален. Поп-ап.
     */
    @Test
    public void delete_peresdacha2() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col1 = 1;
        int col2 = 3;
        if (!check_emty_mark(row, col1)) {
            //  if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (check_emty_mark(row, col1))
            Assert.fail("Не выставилась оценка");

        change_neavka(true, 1, col2);

        int col3 = 6;

        change_neavka(true,row,col3);

        set_mark(row,7,"10");

        WebElement element = driver.findElement(By.id("col_row_" + 7 + "_" + row));
        WebElement field = element.findElement(By.tagName("input"));
        //element.clear();
        field.sendKeys(Keys.BACK_SPACE+"" +Keys.BACK_SPACE+"\n"+Keys.ESCAPE);

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[5]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[5]")).getText();
            Assert.assertEquals(error_text, "Балл удален",
                    "Не соответсвует сообщения о выставлении");
        } else {

            change_neavka(false, row, col3);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 38,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));

        change_neavka(false, row, col3);
        change_neavka(false, row, col2);
        delete_mark(row, col1);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить неявку за экзамен
     * 4. Выставить неявку на первую пересдачу
     * 5. Выставить балл за вторую пересдачу
     * 6. Выставить неявку на вторую пересдачу
     *
     * Ожидается: Стоит неявка. балл обнулился
     */
    @Test
    public void peresdacha2_10_set_neavka() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col1 = 1;
        int col2 = 3;
        if (!check_emty_mark(row, col1)) {
            //  if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (check_emty_mark(row, col1))
            Assert.fail("Не выставилась оценка");

        change_neavka(true, 1, col2);

        int col3 = 6;

        change_neavka(true,row,col3);

        set_mark(row,7,"10");

        if (driver.findElement(By.id("col_row_" + 8 + "_" + row)).findElement(By.tagName("input")).isSelected()) {
            Assert.fail("Неявка уже выставлена");

        }
        driver.findElement(By.id("col_row_" + 8 + "_" + row)).findElement(By.tagName("input")).click();
        TimeUnit.SECONDS.sleep(1);
        if (!driver.findElement(By.id("col_row_" + 8 + "_" + row)).findElement(By.tagName("input")).isSelected()) {
            Assert.fail("Неявка не выставилась");
        }

        if(!check_emty_mark(row,7))
            Assert.fail("Оценка не удалилась");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[5]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[5]")).getText();
            Assert.assertEquals(error_text, "Неявка установлена",
                    "Не соответсвует сообщения о выставлении");
        } else {
            change_neavka(false, row, 8);
            change_neavka(false, row, col3);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 38,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        change_neavka(false, row, 8);
        change_neavka(false, row, col3);
        change_neavka(false, row, col2);
        delete_mark(row, col1);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить неявку за экзамен
     * 4. Выставить неявку на первую пересдачу
     * 5. Выставить балл за вторую пересдачу
     * 6. Удалить неявку на первую пересдачу
     *
     * Ожидается: Неявка неудалена. Негативный поп-ап
     */
    @Test
    public void peresdacha2_delete_neavka2() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col1 = 1;
        int col2 = 3;
        if (!check_emty_mark(row, col1)) {
            //  if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (check_emty_mark(row, col1))
            Assert.fail("Не выставилась оценка");

        change_neavka(true, 1, col2);

        int col3 = 6;

        change_neavka(true,row,col3);

        set_mark(row,7,"10");

        if (!driver.findElement(By.id("col_row_" + 6 + "_" + row)).findElement(By.tagName("input")).isSelected()) {
            Assert.fail("Неявка не выставлена");

        }
        driver.findElement(By.id("col_row_" + 6 + "_" + row)).findElement(By.tagName("input")).click();
        TimeUnit.SECONDS.sleep(1);
        if (!driver.findElement(By.id("col_row_" + 6 + "_" + row)).findElement(By.tagName("input")).isSelected()) {
            Assert.fail("Неявка удалилась");
        }

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[5]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[5]")).getText();
            Assert.assertEquals(error_text, "Не удалось удалить неявку",
                    "Не соответсвует сообщения о выставлении");
        } else {
            delete_mark(row, 7);
            TimeUnit.SECONDS.sleep(1);
            change_neavka(false, row, col3);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 48,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        delete_mark(row, 7);
        TimeUnit.SECONDS.sleep(1);
        change_neavka(false, row, col3);
        change_neavka(false, row, col2);
        delete_mark(row, col1);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить неявку за экзамен
     * 4. Выставить неявку на первую пересдачу
     * 5. Выставить балл за вторую пересдачу
     * 6. Удалить неявку на экзамен
     *
     * Ожидается: Неявка неудалена. Негативный поп-ап
     */
    @Test
    public void peresdacha2_delete_neavka1() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col1 = 1;
        int col2 = 3;
        if (!check_emty_mark(row, col1)) {
            //  if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (check_emty_mark(row, col1))
            Assert.fail("Не выставилась оценка");

        change_neavka(true, 1, col2);

        int col3 = 6;

        change_neavka(true,row,col3);

        set_mark(row,7,"10");

        if (!driver.findElement(By.id("col_row_" + 3 + "_" + row)).findElement(By.tagName("input")).isSelected()) {
            Assert.fail("Неявка не выставлена");

        }
        driver.findElement(By.id("col_row_" + 3 + "_" + row)).findElement(By.tagName("input")).click();
        TimeUnit.SECONDS.sleep(1);
        if (!driver.findElement(By.id("col_row_" + 3 + "_" + row)).findElement(By.tagName("input")).isSelected()) {
            Assert.fail("Неявка удалилась");
        }

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[5]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[5]")).getText();
            Assert.assertEquals(error_text, "Не удалось удалить неявку",
                    "Не соответсвует сообщения о выставлении");
        } else {
            delete_mark(row, 7);
            TimeUnit.SECONDS.sleep(1);
            change_neavka(false, row, col3);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 48,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        delete_mark(row, 7);
        TimeUnit.SECONDS.sleep(1);
        change_neavka(false, row, col3);
        change_neavka(false, row, col2);
        delete_mark(row, col1);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Зайти на страницу
     * 2. Выставить в добор 38 баллов
     * 3. Выставить неявку за экзамен
     * 4. Выставить неявку на первую пересдачу
     * 5. Выставть балл за вторую пересдачу
     * 6. Удалить балл в доборе
     *
     * Ожидается: Балл неудален. Негативный поп-ап
     */
    @Test
    public void peresdacha2_delete_dobor() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int row = 1;
        int col1 = 1;
        int col2 = 3;
        if (!check_emty_mark(row, col1)) {
            //  if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (check_emty_mark(row, col1))
            Assert.fail("Не выставилась оценка");

        change_neavka(true, 1, col2);

        int col3 = 6;

        change_neavka(true,row,col3);

        set_mark(row,7,"10");

        WebElement element = driver.findElement(By.id("col_row_" + 1 + "_" + row));
        WebElement field = element.findElement(By.tagName("input"));
        //element.clear();
        field.sendKeys(Keys.BACK_SPACE+"" +Keys.BACK_SPACE+"\n"+Keys.ESCAPE);

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[5]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[5]")).getText();
            Assert.assertEquals(error_text, "Недостаточно баллов для сдачи экзамена",
                    "Не соответсвует сообщения о выставлении");
        } else {
            delete_mark(row, 7);
            TimeUnit.SECONDS.sleep(1);
            change_neavka(false, row, col3);
            change_neavka(false, row, col2);
            delete_mark(row, col1);
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 48,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));
        delete_mark(row, 7);
        TimeUnit.SECONDS.sleep(1);
        change_neavka(false, row, col3);
        change_neavka(false, row, col2);
        delete_mark(row, col1);
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Открыть страницу семестра
     * 2. Выставить все оценки
     * 3. Открыть страницу сессии
     * 4. Выставить в добор балл
     * 5. Выставить автомат
     * 6. Удалить автомат
     *
     * Ожидается: Оценка не выставлены. Негативный поп-ап. Автомат поставился. Автомат удален.
     * @throws InterruptedException
     */
    @Test
    public void set_avtomat_and_check_dobor() throws InterruptedException {
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/rate");
        if(!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(),"CS332. Компьютерная графика",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();
        int row=2;
        if(Semestr_mark(row,15)!=60) {
            ArrayList<Integer> marks = new ArrayList<Integer>();
            marks.add(5);
            marks.add(8);
            marks.add(7);
            marks.add(5);
            marks.add(9);
            marks.add(6);
            marks.add(5);
            marks.add(9);
            marks.add(6);
            for (int i = 1; i < 10; i++) {
                set_mark(row, i, String.valueOf(marks.get(i - 1)));
                if (check_emty_mark(row, i))
                    Assert.fail("He Выставилась оценка " + Mark(row, i) + " " + i);
            }
        }


        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/exam");
        if (!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(), "CS332. Компьютерная графика – Экзамен",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        int col1=1;

        if (!check_emty_mark(row, col1)) {
            //  if (Mark(row, col1) != 38)
            Assert.fail("Ячейка не пустая " + check_emty_mark(row, col1));
        } else
            set_mark(row, col1, "38");
        if (!check_emty_mark(row, col1))
            Assert.fail("выставилась оценка в добор");

        if (IsElementVisible(By.xpath("/html/body/div[4]/div[1]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[1]")).getText();
            Assert.assertEquals(error_text, "Превышение максимума баллов для добора",
                    "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }

        Assert.assertEquals(Semestr_mark(row, 12), 60,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));

//установка автомата
        if (driver.findElement(By.id("col_row_" + 4+ "_" + row)).findElement(By.tagName("input")).isSelected()) {
            Assert.fail("Автомат уже выставлен");
        }
        driver.findElement(By.id("col_row_" + 4 + "_" + row)).findElement(By.tagName("input")).click();
        TimeUnit.SECONDS.sleep(1);
        if (!driver.findElement(By.id("col_row_" + 4 + "_" + row)).findElement(By.tagName("input")).isSelected()) {
            Assert.fail("Автомат не выставился");
        }
        if (IsElementVisible(By.xpath("/html/body/div[4]/div[2]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[2]")).getText();
            Assert.assertEquals(error_text, "Автомат установлен",
                    "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 60,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));

        //удаление автомата
        driver.findElement(By.id("col_row_" + 4 + "_" + row)).findElement(By.tagName("input")).click();
        TimeUnit.SECONDS.sleep(1);
        if (driver.findElement(By.id("col_row_" + 4 + "_" + row)).findElement(By.tagName("input")).isSelected()) {
            Assert.fail("Автомат не удалился");
        }
        if (IsElementVisible(By.xpath("/html/body/div[4]/div[3]"))) {
            String error_text = driver.findElement(By.xpath("/html/body/div[4]/div[3]")).getText();
            Assert.assertEquals(error_text, "Автомат удален",
                    "Не соответсвует сообщения о выставлении");
        } else {
            Assert.fail("Нет сообщения о выставлении");
        }
        Assert.assertEquals(Semestr_mark(row, 12), 60,
                "Выставилась другая оценка в Итог" + Semestr_mark(row, 12));

        //удаление оценок
        driver.navigate().to("http://testgrade.sfedu.ru/discipline/3723/rate");
        if(!IsElementVisible(By.className("subject")))
            Assert.fail("Страница не загрузилась / не видно элемента названия предмета");
        Assert.assertEquals(driver.findElement(By.className("subject")).getText(),"CS332. Компьютерная графика",
                "Загрузилась не та страница");
        check_or_choose_all_groups_in_selector();

        boolean flag=false;
        String error="";
        for(int i=1;i<10;i++){
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
