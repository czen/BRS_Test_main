package ru.sfedu.grade.TeacherTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *\brief Тесты страниц просмотра дисциплины

 * Тестирует поиск, добавление и удаление преподователей.
 *  @version 1.0
 *  @author Stepanova
 *  @see AfterClickBtnsTest, MarksForSemestrPageTest, MarksForSessiaPageTest, TeacherTest, MarksOfZachetPageTest, EditDisciplinPageTest, AfterClickBtnsTest, Helper
 */
public class ProsmotrDisciplinPageTest extends Helper{
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
        exit();
        // Close all browser windows and safely end the session
        if(driver != null)
        {driver.quit();
            driver=null;}
    }

    /**
     * Тест-кейс:
     * 1. переходим на страницу
     *
     * Ожидание: страица загрузилась, есть негативный поп-ап о невозможности редактирования
     */
    @Test
    public void check_prosmotr(){
        driver.navigate().to(get_base_url()+"discipline/3723/structure");
        // страница просмотра . семестр 9й (2018 год весна) первая строчка
        if(!IsElementVisible(By.className("Warning"))){
            Assert.fail("Страница не загрузилась/нет предупрежедния/это не страница просмотра ");
        }
        String warning=driver.findElement(By.className("Warning")).getText();
        Assert.assertEquals(warning,"Был добавлен балл. Редактирование базовых настроек, модулей и групп невозможно.",
                "Не соответсвует текст предупреждения");
        go_home();
    }

    /**
     * Тест-кейс:
     * 1. Перейти на страницу
     * 2. Кликнуть таб преподователи
     *
     * Ожидается: загрузилась страница преподователей
     */
    @Test
    public void click_to_teachers() {
        driver.navigate().to(get_base_url()+"discipline/3723/structure");
        // страница просмотра . семестр 9й (2018 год весна) первая строчка
        if(!IsElementVisible(By.className("Warning"))){
            Assert.fail("Страница не загрузилась/нет предупрежедния/это не страница просмотра ");
        }
        List<WebElement> tabs = driver.findElements(By.className("tab"));
        if(tabs.get(2).isDisplayed())
            Assert.assertEquals(tabs.get(2).getText(),"3. Преподаватели",
                    "Третья кнопка таба - не преподователи");
        else
            Assert.fail("Не доступна кнопка преподователи");
        tabs.get(2).click();

        List<WebElement> titles = driver.findElements(By.className("BlueTitle"));
        Assert.assertEquals(titles.get(0).getText(),"Прикрепленные преподаватели",
                "Не загрузилась страница/нет первого тэга h2");
        go_home();
    }

    @Test
    public void give_leads_to_another_teacher(){

        //а надо ли...?
    }


    /**
     * Тест-кейс:
     * 1. Переходим на страницу
     * 2. Вводим в поиск имя преподователя
     * 3. Ищем конкретного преподователя в результатх поиска
     * 4. Нажимаем присоединить
     * 5. В Прикрепленных преподователях ищем этого преподователя
     * 6. нажимаем отсоединить
     *
     * Ожидание: Преподователь нашелся. Преподователб прикрепился. преподователь открепился.
     */
    @Test
    public void add_and_delete_teacher() throws InterruptedException {
        driver.navigate().to(get_base_url()+"discipline/3723/teachers");
        if(!IsElementVisible(By.className("Warning"))){
            Assert.fail("Страница не загрузилась/нет предупрежедния/это не страница просмотра ");
        }

        driver.findElement(By.cssSelector(".InputTeacherName.defaultForm.FLeft.P1Width")).sendKeys("Роман");
        TimeUnit.SECONDS.sleep(2);
        List<WebElement> teachers = driver.findElement(By.className("SearchTeachers")).findElements(By.className("Teacher"));
        for(WebElement teach : teachers) {
            if (teach.findElement(By.className("Name")).getText().equals("Штейнберг Роман Борисович")) {
                if (!teach.findElement(By.cssSelector(".Action.Action_BindTeacher")).isDisplayed())
                    Assert.fail("Не доступна кнопка присоединения преподователя");
                WebElement first_part = driver.findElement(By.className("BindTeachersList"));

                List<WebElement> prikrepl_teach = first_part.findElements(By.className("Teacher"));
                teach.findElement(By.cssSelector(".Action.Action_BindTeacher")).click();
                TimeUnit.SECONDS.sleep(1);
                List<WebElement> prikrepl_teach_now = first_part.findElements(By.className("Teacher"));
//вставить отсоединение

                Assert.assertEquals(prikrepl_teach_now.size(), prikrepl_teach.size() + 1,
                        "Преподователь не прикрепился");

                Assert.assertEquals(prikrepl_teach_now.get(prikrepl_teach_now.size() - 1).findElement(By.className("Name")).getText(),
                        "Штейнберг Роман Борисович", "Прикрепился не тот преподователь");

                if(!prikrepl_teach_now.get(prikrepl_teach_now.size() - 1).findElement(By.cssSelector(".Action_UnbindTeacher.Action")).isDisplayed())
                    Assert.fail("Не видно кнопки отсоединить");
                prikrepl_teach_now.get(prikrepl_teach_now.size() - 1).findElement(By.cssSelector(".Action_UnbindTeacher.Action")).click();

                driver.navigate().refresh();
                List<WebElement> teach_afret_delete=driver.findElement(By.className("BindTeachersList")).findElements(By.className("Teacher"));
                for (WebElement teach1 :teach_afret_delete){
                    if (teach1.findElement(By.className("Name")).getText().equals("Штейнберг Роман Борисович"))
                        Assert.fail("Преподователь не открепился");
                }
                Assert.assertEquals(teach_afret_delete.size(),prikrepl_teach_now.size()-1,
                        "Количество преподователей не верно");
                go_home();
                return;
            }
        }
        go_home();
        Assert.fail("Не нашли искомого преподователя: Штейнберг Роман Борисович ");


    }

    /**
     * Тест-кейс:
     * 1. Переходим на страницу
     * 2. Вводим в поиск уже прикрепленного преподователя
     * Можно его читать, но для этой дисциплины прикреплен Лошкарев
     *
     * Ожидается: результат поиска пустой, есть тескствое предупреждение
     * @throws InterruptedException
     */
    @Test
    public void search_teacher_yet_in_aded() throws InterruptedException {
        driver.navigate().to(get_base_url()+"discipline/3723/teachers");
        if(!IsElementVisible(By.className("Warning"))){
            Assert.fail("Страница не загрузилась/нет предупрежедния/это не страница просмотра ");
        }

        driver.findElement(By.cssSelector(".InputTeacherName.defaultForm.FLeft.P1Width")).sendKeys("Лошкарёв Илья Витальевич");
        TimeUnit.SECONDS.sleep(2);

        if(!IsElementVisible(By.className("notification"))){
            Assert.fail("Нет предупреждения о пустом поиске");
        }
        Assert.assertEquals(driver.findElement(By.className("notification")).getText(),
                "Нет результатов... Возможно, преподаватели, соответствующие критериям поиска, уже прикреплены.",
                "Не сответсвует текст предупреждения");
        if(IsElementExists(By.cssSelector(".Action.Action_BindTeacher"))){
            //ищем кнопку прикрепить, она есть только у результата поиска,
            // можно искать а сами строки, но тоада надо ловить исключение
            Assert.fail("Есть результат поиска, он дожен быть пустой");
        }
    }

}
