package ru.sfedu.grade.TeacherTest;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/** \brief Тесты страницы модуля
 *
 * Остальное было уже реализовано.
 *
 * Если падает, желательно удлаить созданные мероприятия вручную
 *
 *Так же здесь происходит поиск кнопки редактирования дисциплины,
 * если упадет по причине ее отсутсвия далье можно и не смотреть.
 * @warning Два теста связанные с сортировкой модулей будут падать - они нашли баг.
 * @todo Можно удаление\добавление сделать отдельными функциями.
 *  @todo Продумать и переписать обработку исключительных ситуаций так, чтобы модуль при падении всегда удалялся при падении теста
 * @todo  Разнести по разным тестам кейс отмены удаления и удаление
 *  @version 1.0
 *  @author Stepanova
 *  @see AfterClickBtnsTest, MarksForSemestrPageTest, MarksForSessiaPageTest, TeacherTest, MarksOfZachetPageTest, ProsmotrDisciplinPageTest, AfterClickBtnsTest, Helper
 */
public class EditDisciplinPageTest extends Helper {
    private String url="";
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
        the_page_of_redact_is_exist();
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


    /**\brief Ищет страницу редактирования, ее может не быть
     *
     * Для обеспечения независимости это не тест, а отдельная функция. Чтобы не искать кнопку каждый раз,
     * вызывается до выполнения всех функций.
     * @warning Падает это - падает все
     */
    private void the_page_of_redact_is_exist(){
        go_home();
        List<WebElement> group_tables; // Изменить этот блок везде... таблица может быть пустая в домашнем семестре
        if(IsElementVisible(By.className("group_block")))
            group_tables = driver.findElements(By.className("group_block"));
        else {
            Assert.fail("Нет элементов таблицы. Дальше можно не смотреть");
            return;
        }
        WebElement btn_sem;
        for(WebElement group: group_tables){
            if(btn_is_radactirovania(group)){
                btn_sem= get_btn_redactir(group);
                btn_sem.click();
                if(!IsElementVisible(By.className("main_top"))) {
                    Assert.fail("Не загрузилась страница после нажатия на кнопку Рдактирование");
                }
                String s=driver.findElement(By.className("main_top")).getText();
                Assert.assertTrue(s.contains("Редактирование дисциплины №"),"Загрузилась не та страница "+s);
                url=driver.getCurrentUrl();
                return;
            }
        }
        Assert.fail("Нет строк в таблице с кнопкой Редактирование. Дальше можно не смотреть");
    }

    /**
     * Тест-кейс:
     * 1. Перейти на страницу
     * 2. Нажать кнопку создания модуля
     *
     * Ожидание: модуль создался
     *
     * 3. Нажать для этого модуля удалить
     * 4. Нажать отмену
     *
     * Ожидание: модуль не удалился
     *
     * 5. Нажать удаление
     * 6. Нажать подтвеждение
     *
     * Ожидание: модуль удалился
     * @throws InterruptedException
     */
    @Test
    public void add_and_delete_modules() throws InterruptedException {
        //предупреждаем с помощью throws,
// что метод может выбросить исключение
        if(url.isEmpty()){
            Assert.fail("No button to edit");
        }
        driver.navigate().to(url);
        String s=driver.findElement(By.className("main_top")).getText();
        Assert.assertTrue(s.contains("Редактирование дисциплины №"),"Загрузилась не та страница "+s);

        if(!IsElementVisible(By.className("addModule"))){
            Assert.fail("No button to add module");
        }
//добавлеие
        List<WebElement> modules_was=driver.findElements(By.cssSelector(".moduleGroup._info"));
        //moduleHead - можно искать для определения количества, это класс для строчки модуля
        driver.findElement(By.className("addModule")).click();
        TimeUnit.SECONDS.sleep(1); // чтобы отработал код
        List<WebElement> modules_now=driver.findElements(By.cssSelector(".moduleGroup._info"));

        Assert.assertNotEquals(modules_was.size(),modules_now.size(),
                "Новый модуль не создался. was:"+modules_was.size()+" now:"+modules_now.size());
//удаление

        //delete icon
        if(!modules_now.get(modules_now.size()-1).findElement(By.cssSelector(".deleteModule.icon.delete")).isDisplayed())
            Assert.fail("Невидно кнопки удаления");
        modules_now.get(modules_now.size()-1).findElement(By.cssSelector(".deleteModule.icon.delete")).click();

        if(!modules_now.get(modules_now.size()-1).findElement(By.className("confirmDeleteModule")).isDisplayed())
            Assert.fail("Не доступна кнопка подтверждения удаления модуля");
        if(!modules_now.get(modules_now.size()-1).findElement(By.className("cancel")).isDisplayed())
            Assert.fail("Не доступна кнопка отмены удаления модуля");
//отмена удаления
        modules_now.get(modules_now.size()-1).findElement(By.className("cancel")).click();
        // TimeUnit.SECONDS.sleep(1);
        List<WebElement> modules_will= driver.findElements(By.cssSelector(".moduleGroup._info"));
        Assert.assertEquals(modules_now.size(),modules_will.size(),
                "Новый модуль удалился хотя нажата была отмена. was:"+modules_now.size()+" now:"+modules_will.size());
//само удаление
        if(!modules_now.get(modules_now.size()-1).findElement(By.cssSelector(".deleteModule.icon.delete")).isDisplayed())
            Assert.fail("Не появилось заново кнопки удалении после отмены удаления");
        modules_now.get(modules_now.size()-1).findElement(By.cssSelector(".deleteModule.icon.delete")).click();

        modules_now.get(modules_now.size()-1).findElement(By.className("confirmDeleteModule")).click(); // подтверждение удаления

        TimeUnit.SECONDS.sleep(1); // чтобы отработал код
        List<WebElement> modules_will2=driver.findElements(By.cssSelector(".moduleGroup._info"));

        Assert.assertNotEquals(modules_now.size(),modules_will2.size(),
                "Новый модуль не удалился. Желательно удалить самому was:"+modules_now.size()+" now:"+modules_will2.size());


    }

    /**\brief Удаляет последний модуль
     *
     * Чтобы не писать каждый раз этот код, вынесли отдельно
     * @throws InterruptedException от ожидания
     */
    private void delete_module() throws InterruptedException {
        List<WebElement> modules_now=driver.findElements(By.cssSelector(".moduleGroup._info"));
        if(!modules_now.get(modules_now.size()-1).findElement(By.cssSelector(".deleteModule.icon.delete")).isDisplayed())
            Assert.fail("Не появилось кнопки удалении модулю");
        modules_now.get(modules_now.size()-1).findElement(By.cssSelector(".deleteModule.icon.delete")).click();

        modules_now.get(modules_now.size()-1).findElement(By.className("confirmDeleteModule")).click(); // подтверждение удаления

        TimeUnit.SECONDS.sleep(1); // чтобы отработал код
        List<WebElement> modules_will2=driver.findElements(By.cssSelector(".moduleGroup._info"));

        Assert.assertNotEquals(modules_now.size(),modules_will2.size(),
                "Новый модуль не удалился. Желательно удалить самому was:"+modules_now.size()+" now:"+modules_will2.size());

    }

    //вставить где падает - удаление модуля

    /**
     * Тест-кейс:
     * 1. Создать модуль
     * 2. Нажать для этого модуля создать подмодуль
     *
     * Ожидание подмодуль создался
     *
     * 3. Нажать удаление для подмодуля
     * 4. Нажать отмену
     *
     * Ожидание: модуль неудалился
     *
     * 5. Нажать удаление подмодуля
     * 6. Подтвердить
     *
     * Ожидание: подмодуль удалился
     * @throws InterruptedException
     */
    @Test
    public void add_and_delete_submodule() throws InterruptedException {
        if(url.isEmpty()){
            Assert.fail("No button to edit");
        }
        driver.navigate().to(url);
        String s=driver.findElement(By.className("main_top")).getText();
        Assert.assertTrue(s.contains("Редактирование дисциплины №"),"Загрузилась не та страница "+s);

        if(!IsElementVisible(By.className("addModule"))){
            Assert.fail("No button to add module");
        }
//добавление модуля
        List<WebElement> modules_was=driver.findElements(By.cssSelector(".moduleGroup._info"));
        //moduleHead - можно искать для определения количества, это класс для строчки модуля
        driver.findElement(By.className("addModule")).click();
        TimeUnit.SECONDS.sleep(1); // чтобы отработал код
        List<WebElement> modules_now=driver.findElements(By.cssSelector(".moduleGroup._info"));
        Assert.assertNotEquals(modules_was.size(),modules_now.size(),
                "Новый модуль не создался. was:"+modules_was.size()+" now:"+modules_now.size());

        //добавление подмодуля

        if(!modules_now.get(modules_now.size()-1).findElement(By.className("addSubmodule")).isDisplayed()) {
            delete_module();
            Assert.fail("Нет кнопки добавления подмодуля");
        }
        modules_now.get(modules_now.size()-1).findElement(By.className("addSubmodule")).click();
        TimeUnit.SECONDS.sleep(1);
        List<WebElement> submodules = modules_now.get(modules_now.size()-1).findElements(By.cssSelector(".submodule._info"));
        Assert.assertEquals(submodules.size(),2,"Не создался подмодуль,ожидалось 2 ,стало: "+submodules.size());

        if(!submodules.get(0).findElement(By.cssSelector(".deleteSubmodule.icon.delete")).isDisplayed()){
            delete_module();
            Assert.fail("Не видно кнопки удаление подмодуля");
        }
        submodules.get(0).findElement(By.cssSelector(".deleteSubmodule.icon.delete")).click();

        if(!submodules.get(0).findElement(By.className("confirmDeleteSubmodule")).isDisplayed()) {
            delete_module();
            Assert.fail("Не доступна кнопка подтверждения удаления подмодуля");
        }
        if(!submodules.get(0).findElement(By.className("cancel")).isDisplayed()) {
            delete_module();
            Assert.fail("Не доступна кнопка отмены удаления подмодуля");
        }

        //отмена удаления
        submodules.get(0).findElement(By.className("cancel")).click();
        // TimeUnit.SECONDS.sleep(1);
        TimeUnit.SECONDS.sleep(1);
        List<WebElement> submodules1= modules_now.get(modules_now.size()-1).findElements(By.cssSelector(".submodule._info"));
        Assert.assertEquals(submodules1.size(),submodules.size(),
                "Новый подмодуль удалился хотя нажата была отмена. was:"+submodules.size()+" now:"+submodules1.size());
//само удаление
        if(!submodules1.get(0).findElement(By.cssSelector(".deleteSubmodule.icon.delete")).isDisplayed()) {
            delete_module();
            Assert.fail("Не появилось заново кнопки удалении после отмены удаления");
        }
        submodules1.get(0).findElement(By.cssSelector(".deleteSubmodule.icon.delete")).click();

        submodules1.get(0).findElement(By.className("confirmDeleteSubmodule")).click(); // подтверждение удаления

        TimeUnit.SECONDS.sleep(1); // чтобы отработал код

        driver.navigate().refresh();
        modules_now=driver.findElements(By.cssSelector(".moduleGroup._info"));
        List<WebElement> submodules2=modules_now.get(modules_now.size()-1).findElements(By.cssSelector(".submodule._info")); // не работало верно...
        // List<WebElement> submodules2=modules_now.get(modules_now.size()-1).findElements(By.className("landmarkControl"));
        Assert.assertNotEquals(submodules2.size(),submodules1.size(),
                "Новый подмодуль не удалился. Желательно удалить самому was:"+submodules1.size()+" now:"+(submodules2.size()-1));

        delete_module();
    }

    /**
     * Тест-кейс
     *      1. Перейти на страницу модулей
     *      2. Создать два модуля
     *      3. Дать первому имя "1"
     *      4. Дать второму имя "2"
     *      5. Перезагрузить сртраницу - без этого кнопки сортировки не работают
     *      6. Нажать вверх у второго модуля
     *
     *      Ожидается: модули поменяются местами
     * @throws InterruptedException
     */
    @Test
    public void sort_to_up_moduls() throws InterruptedException {
        if(url.isEmpty()){
            Assert.fail("No button to edit");
        }
        driver.navigate().to(url);
        String s=driver.findElement(By.className("main_top")).getText();
        Assert.assertTrue(s.contains("Редактирование дисциплины №"),"Загрузилась не та страница "+s);

        if(!IsElementVisible(By.className("addModule"))){
            Assert.fail("No button to add module");
        }
//добавлеие
        List<WebElement> modules_was=driver.findElements(By.cssSelector(".moduleGroup._info"));
        //moduleHead - можно искать для определения количества, это класс для строчки модуля
        driver.findElement(By.className("addModule")).click();
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.className("addModule")).click();
        TimeUnit.SECONDS.sleep(1); // чтобы отработал код
        List<WebElement> modules=driver.findElements(By.cssSelector(".moduleGroup._info"));

        Assert.assertEquals(modules_was.size()+2,modules.size(),
                "Новый модуль не создался. was:"+modules_was.size()+" now:"+modules.size());

        WebElement mod1=modules.get(modules.size()-2); //первый созданный модуль
        WebElement mod2=modules.get(modules.size()-1);

        mod1.findElement(By.tagName("input")).sendKeys("1"+Keys.ESCAPE);
        mod2.findElement(By.tagName("input")).sendKeys("2"+ Keys.ESCAPE);

        TimeUnit.SECONDS.sleep(1);

        driver.navigate().refresh();

        //ьез рефреша сортировка не работает - баг (хром)

        modules=driver.findElements(By.cssSelector(".moduleGroup._info"));
        mod1=modules.get(modules.size()-2); //первый созданный модуль
        mod2=modules.get(modules.size()-1);
//Если хочется чтоб тест проходил, то раскоментить. А так это баг, удаляет имя почему то
        //     mod1.findElement(By.tagName("input")).sendKeys("1"+Keys.ESCAPE);

        if( !mod2.findElement(By.cssSelector(".moveUp.icon.up")).isEnabled()) {//кнопка вниз
            delete_module();
            delete_module();
            Assert.fail("Нет кнопки вниз для модуля");
        }

        mod2.findElement(By.cssSelector(".moveUp.icon.up")).click();
        TimeUnit.SECONDS.sleep(1); // чтобы отработал код

        modules=driver.findElements(By.cssSelector(".moduleGroup._info"));
        mod1=modules.get(modules.size()-2); //первый созданный модуль
        mod2=modules.get(modules.size()-1);

        TimeUnit.SECONDS.sleep(1); // чтобы отработал код

        String name1=mod1.findElement(By.cssSelector(".inputName.moduleName")).getAttribute("value");
        String name2=mod2.findElement(By.cssSelector(".inputName.moduleName")).getAttribute("value");//.getText();

        delete_module();
        delete_module();

        Assert.assertEquals(name1,"2",
                "Не переместился вниз модуль 1й (нажатия нет)");
        Assert.assertEquals(name2,"1",
                "Не переместился вверх модуль 2й (нажатие наверх)");
    }

    /**Тест-кейс
     * 1. Перейти на страницу модулей
     * 2. Создать два модуля
     * 3. Дать первому имя "1"
     * 4. Дать второму имя "2"
     * 5. Перезагрузить сртраницу - без этого кнопки сортировки не работают
     * 6. Нажать вниз у первого модуля
     *
     * Ожидается: модули поменяются местами
     *
     * @throws InterruptedException
     */
    @Test
    public void sort_to_down_moduls() throws InterruptedException {
        if(url.isEmpty()){
            Assert.fail("No button to edit");
        }
        driver.navigate().to(url);
        String s=driver.findElement(By.className("main_top")).getText();
        Assert.assertTrue(s.contains("Редактирование дисциплины №"),"Загрузилась не та страница "+s);

        if(!IsElementVisible(By.className("addModule"))){
            Assert.fail("No button to add module");
        }
//добавлеие
        List<WebElement> modules_was=driver.findElements(By.cssSelector(".moduleGroup._info"));
        //moduleHead - можно искать для определения количества, это класс для строчки модуля
        driver.findElement(By.className("addModule")).click();
        //TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.className("addModule")).click();
        TimeUnit.SECONDS.sleep(1); // чтобы отработал код
        List<WebElement> modules=driver.findElements(By.cssSelector(".moduleGroup._info"));

        Assert.assertEquals(modules_was.size()+2,modules.size(),
                "Новый модуль не создался. was:"+modules_was.size()+" now:"+modules.size());

        WebElement mod1=modules.get(modules.size()-2); //первый созданный модуль
        WebElement mod2=modules.get(modules.size()-1);

        mod1.findElement(By.tagName("input")).sendKeys("1"+Keys.ESCAPE);
        mod2.findElement(By.tagName("input")).sendKeys("2"+ Keys.ESCAPE);

        TimeUnit.SECONDS.sleep(1);

        driver.navigate().refresh();

        //ьез рефреша сортировка не работает - баг (хром)

        modules=driver.findElements(By.cssSelector(".moduleGroup._info"));
        mod1=modules.get(modules.size()-2); //первый созданный модуль
        mod2=modules.get(modules.size()-1);

        //Если хочется чтоб тест проходил, то раскоментить. А так это баг, удаляет имя почему то
        //mod1.findElement(By.tagName("input")).sendKeys("1"+Keys.ESCAPE);

        if( !mod1.findElement(By.cssSelector(".moveDown.icon.down")).isEnabled()) {//кнопка вниз
            delete_module();
            delete_module();
            Assert.fail("Нет кнопки вниз для модуля");
        }

        mod1.findElement(By.cssSelector(".moveDown.icon.down")).click();
        TimeUnit.SECONDS.sleep(1); // чтобы отработал код

        modules=driver.findElements(By.cssSelector(".moduleGroup._info"));
        mod1=modules.get(modules.size()-2); //первый созданный модуль
        mod2=modules.get(modules.size()-1);

        TimeUnit.SECONDS.sleep(1); // чтобы отработал код

        String name1=mod1.findElement(By.cssSelector(".inputName.moduleName")).getAttribute("value");
        String name2=mod2.findElement(By.cssSelector(".inputName.moduleName")).getAttribute("value");//.getText();

        delete_module();
        delete_module();

        Assert.assertEquals(name1,"2",
                "Не переместился вниз модуль 1й (нажатие вниз)");
        Assert.assertEquals(name2,"1",
                "Не переместился вверх модуль 2й (нет нажатия)");

    }

    /**
     * Тест-кейс:
     * 1. Если нет модуля-создать
     * 2. Нажать у последнего модуля вниз
     *
     * Ожидается негативный поп-ап
     * @throws InterruptedException
     */
    @Test
    public void click_down_from_lower_moduls() throws InterruptedException {
        if(url.isEmpty()){
            Assert.fail("No button to edit");
        }
        driver.navigate().to(url);
        String s=driver.findElement(By.className("main_top")).getText();
        Assert.assertTrue(s.contains("Редактирование дисциплины №"),"Загрузилась не та страница "+s);

        List<WebElement> modules=driver.findElements(By.cssSelector(".moduleGroup._info"));
        WebElement modul=modules.get(modules.size()-1);//last
        if(modules.size()==0){
            driver.findElement(By.className("addModule")).click();
            modules=driver.findElements(By.cssSelector(".moduleGroup._info"));
        }
        if( !modul.findElement(By.cssSelector(".moveDown.icon.down")).isEnabled()) {//кнопка вниз
            delete_module();
            delete_module();
            Assert.fail("Нет кнопки вниз для модуля");
        }

        modul.findElement(By.cssSelector(".moveDown.icon.down")).click();
        //   TimeUnit.SECONDS.sleep(1); // чтобы отработал код

        if(!IsElementVisible(By.cssSelector(".EventItem.error")))
            Assert.fail("Не видно негативного поп-апа");
        String errors=driver.findElement(By.cssSelector(".EventItem.error")).getText();
        Assert.assertEquals(errors,"Переместить ниже нельзя",
                "Не возникло предупреждение о перемещении вниз");
    }

    /**Тест-кейс:
     * 1. Если нет модуля - создать
     * 2. У первого модуля нажать вверх
     *
     * Ожидается: негативный поп-ап
     * @throws InterruptedException
     */
    @Test
    public void click_down_from_up_modul() throws InterruptedException {
        if(url.isEmpty()){
            Assert.fail("No button to edit");
        }
        driver.navigate().to(url);
        String s=driver.findElement(By.className("main_top")).getText();
        Assert.assertTrue(s.contains("Редактирование дисциплины №"),"Загрузилась не та страница "+s);

        List<WebElement> modules=driver.findElements(By.cssSelector(".moduleGroup._info"));
        if(modules.size()==0){
            driver.findElement(By.className("addModule")).click();
            modules=driver.findElements(By.cssSelector(".moduleGroup._info"));
        }

        WebElement modul=modules.get(0);//last

        if( !modul.findElement(By.cssSelector(".moveUp.icon.up")).isEnabled()) {//кнопка вниз
            delete_module();
            delete_module();
            Assert.fail("Нет кнопки вниз для модуля");
        }

        modul.findElement(By.cssSelector(".moveUp.icon.up")).click();
        //  TimeUnit.SECONDS.sleep(1); // чтобы отработал код

        if(!IsElementVisible(By.cssSelector(".EventItem.error")))
            Assert.fail("Не видно негативного поп-апа");
        String errors=driver.findElement(By.cssSelector(".EventItem.error")).getText();
        Assert.assertEquals(errors,"Переместить выше нельзя",
                "Не возникло предупреждение о перемещении вверх");
    }

    /**
     * Тест-кейс:
     * 1. Создаем модуль
     * 2. Создаем два подмодуля
     * 3. Даем им имена "1" и "2" соответсвенно
     * 4. Нажимаем кнопку вниз для верхнего подмодуля
     *
     * Ожадание: подмодули поменяются местами
     * @throws InterruptedException
     */
    @Test
    public void sort_submodules_to_down() throws InterruptedException {
        if(url.isEmpty()){
            Assert.fail("No button to edit");
        }
        driver.navigate().to(url);
        String s=driver.findElement(By.className("main_top")).getText();
        Assert.assertTrue(s.contains("Редактирование дисциплины №"),"Загрузилась не та страница "+s);

        if(!IsElementVisible(By.className("addModule"))){
            Assert.fail("No button to add module");
        }
//добавлеие
        List<WebElement> modules_was=driver.findElements(By.cssSelector(".moduleGroup._info"));
        //moduleHead - можно искать для определения количества, это класс для строчки модуля
        driver.findElement(By.className("addModule")).click();

        TimeUnit.SECONDS.sleep(1); // чтобы отработал код
        List<WebElement> modules=driver.findElements(By.cssSelector(".moduleGroup._info"));

        Assert.assertEquals(modules_was.size()+1,modules.size(),
                "Новый модуль не создался. was:"+modules_was.size()+" now:"+modules.size());

        WebElement mod=modules.get(modules.size()-1); //созданный модуль
        if(!mod.findElement(By.className("addSubmodule")).isDisplayed()) {
            delete_module();
            Assert.fail("Нет кнопки добавления подмодуля");
        }
        mod.findElement(By.className("addSubmodule")).click();
        TimeUnit.SECONDS.sleep(1);
        List<WebElement> submodules = mod.findElements(By.cssSelector(".submodule._info"));
        Assert.assertEquals(submodules.size(),2,
                "Не создался подмодуль,ожидалось 2 ,стало: "+submodules.size());

        List<WebElement> submods=mod.findElements(By.cssSelector(".submodule._info"));

        submods.get(0).findElement(By.tagName("input")).sendKeys("1");
        submods.get(1).findElement(By.tagName("input")).sendKeys("2");

        if( !submods.get(0).findElement(By.cssSelector(".moveDown.icon.down")).isEnabled()) {//кнопка вниз
            delete_module();
            Assert.fail("Нет кнопки вниз для модуля");
        }
        submods.get(0).findElement(By.cssSelector(".moveDown.icon.down")).click();
        TimeUnit.SECONDS.sleep(1);
        submods=mod.findElements(By.cssSelector(".submodule._info"));

        String name1=submods.get(0).findElement(By.tagName("input")).getAttribute("value");
        String name2=submods.get(1).findElement(By.tagName("input")).getAttribute("value");

        delete_module();

        Assert.assertEquals(name1,"2",
                "Не переместился вниз подмодуль 1й (нажатие вниз)");
        Assert.assertEquals(name2,"1",
                "Не переместился вверх подмодуль 2й (нет нажатия)");
        //      moveDown icon down downModule icon down
    }

    /**
     * Тест-кейс:
     * 1. Создаем модуль
     * 2. Создаем два подмодуля
     * 3. Даем им имена "1" и "2" соответсвенно
     * 4. Нажимаем кнопку вверх для нижнего подмодуля
     *
     * Ожадание: подмодули поменяются местами
     * @throws InterruptedException
     */
    @Test
    public void sort_submodules_to_up() throws InterruptedException {
        if(url.isEmpty()){
            Assert.fail("No button to edit");
        }
        driver.navigate().to(url);
        String s=driver.findElement(By.className("main_top")).getText();
        Assert.assertTrue(s.contains("Редактирование дисциплины №"),"Загрузилась не та страница "+s);

        if(!IsElementVisible(By.className("addModule"))){
            Assert.fail("No button to add module");
        }
//добавлеие
        List<WebElement> modules_was=driver.findElements(By.cssSelector(".moduleGroup._info"));
        //moduleHead - можно искать для определения количества, это класс для строчки модуля
        driver.findElement(By.className("addModule")).click();

        TimeUnit.SECONDS.sleep(1); // чтобы отработал код
        List<WebElement> modules=driver.findElements(By.cssSelector(".moduleGroup._info"));

        Assert.assertEquals(modules_was.size()+1,modules.size(),
                "Новый модуль не создался. was:"+modules_was.size()+" now:"+modules.size());

        WebElement mod=modules.get(modules.size()-1); //созданный модуль
        if(!mod.findElement(By.className("addSubmodule")).isDisplayed()) {
            delete_module();
            Assert.fail("Нет кнопки добавления подмодуля");
        }
        mod.findElement(By.className("addSubmodule")).click();
        TimeUnit.SECONDS.sleep(1);
        List<WebElement> submodules = mod.findElements(By.cssSelector(".submodule._info"));
        Assert.assertEquals(submodules.size(),2,
                "Не создался подмодуль,ожидалось 2 ,стало: "+submodules.size());

        List<WebElement> submods=mod.findElements(By.cssSelector(".submodule._info"));

        submods.get(0).findElement(By.tagName("input")).sendKeys("1");
        submods.get(1).findElement(By.tagName("input")).sendKeys("2");

        if( !submods.get(1).findElement(By.cssSelector(".moveUp.icon.up")).isEnabled()) {//кнопка вниз
            delete_module();
            Assert.fail("Нет кнопки вверх для подмодуля");
        }
        submods.get(1).findElement(By.cssSelector(".moveUp.icon.up")).click();
        TimeUnit.SECONDS.sleep(1);
        submods=mod.findElements(By.cssSelector(".submodule._info"));

        String name1=submods.get(0).findElement(By.tagName("input")).getAttribute("value");
        String name2=submods.get(1).findElement(By.tagName("input")).getAttribute("value");

        delete_module();

        Assert.assertEquals(name1,"2",
                "Не переместился вниз подмодуль 1й (нажатие вниз)");
        Assert.assertEquals(name2,"1",
                "Не переместился вверх подмодуль 2й (нет нажатия)");
    }

    /**
     * Тест-кейс:
     * 1. Создаем модуль
     * 2. В модуля есть подмодуль
     * 3. В нем нажимаем кнопку вверх
     *
     * Ожидание: негативный поп-ап
     * @throws InterruptedException
     */
    @Test
    public void click_submodule_to_up_from_higher() throws InterruptedException {
        if(url.isEmpty()){
            Assert.fail("No button to edit");
        }
        driver.navigate().to(url);
        String s=driver.findElement(By.className("main_top")).getText();
        Assert.assertTrue(s.contains("Редактирование дисциплины №"),"Загрузилась не та страница "+s);

        if(!IsElementVisible(By.className("addModule"))){
            Assert.fail("No button to add module");
        }
        //добавлеие
        List<WebElement> modules_was=driver.findElements(By.cssSelector(".moduleGroup._info"));
        //moduleHead - можно искать для определения количества, это класс для строчки модуля
        driver.findElement(By.className("addModule")).click();

        TimeUnit.SECONDS.sleep(1); // чтобы отработал код
        List<WebElement> modules=driver.findElements(By.cssSelector(".moduleGroup._info"));

        Assert.assertEquals(modules_was.size()+1,modules.size(),
                "Новый модуль не создался. was:"+modules_was.size()+" now:"+modules.size());

        WebElement mod=modules.get(modules.size()-1); //созданный модуль


        List<WebElement> submodules = mod.findElements(By.cssSelector(".submodule._info"));
        Assert.assertEquals(submodules.size(),1,
                "Подмодуль должен быть при создании,ожидалось 1 ,стало: "+submodules.size());

        if( !submodules.get(0).findElement(By.cssSelector(".moveUp.icon.up")).isEnabled()) {//кнопка вниз
            delete_module();
            Assert.fail("Нет кнопки вверх для подмодуля");
        }
        submodules.get(0).findElement(By.cssSelector(".moveUp.icon.up")).click();

        if(!IsElementVisible(By.cssSelector(".EventItem.error"))) {
            delete_module();
            Assert.fail("Не видно негативного поп-апа");
        }
        String errors=driver.findElement(By.cssSelector(".EventItem.error")).getText();

        delete_module();

        Assert.assertEquals(errors,"Переместить выше нельзя",
                "Не возникло предупреждение о перемещении вверх");
    }

    /**
     * Тест-кейс:
     * 1. Создаем модуль
     * 2. В модуля есть подмодуль
     * 3. В нем нажимаем кнопку вниз
     *
     * Ожидание: негативный поп-ап
     * @throws InterruptedException
     */
    @Test
    public void click_submodule_to_down_from_lower() throws InterruptedException {
        if(url.isEmpty()){
            Assert.fail("No button to edit");
        }
        driver.navigate().to(url);
        String s=driver.findElement(By.className("main_top")).getText();
        Assert.assertTrue(s.contains("Редактирование дисциплины №"),"Загрузилась не та страница "+s);

        if(!IsElementVisible(By.className("addModule"))){
            Assert.fail("No button to add module");
        }
        //добавлеие
        List<WebElement> modules_was=driver.findElements(By.cssSelector(".moduleGroup._info"));
        //moduleHead - можно искать для определения количества, это класс для строчки модуля
        driver.findElement(By.className("addModule")).click();

        TimeUnit.SECONDS.sleep(1); // чтобы отработал код
        List<WebElement> modules=driver.findElements(By.cssSelector(".moduleGroup._info"));

        Assert.assertEquals(modules_was.size()+1,modules.size(),
                "Новый модуль не создался. was:"+modules_was.size()+" now:"+modules.size());

        WebElement mod=modules.get(modules.size()-1); //созданный модуль

        List<WebElement> submodules = mod.findElements(By.cssSelector(".submodule._info"));
        Assert.assertEquals(submodules.size(),1,
                "Подмодуль должен быть при создании,ожидалось 1 ,стало: "+submodules.size());

        if( !submodules.get(0).findElement(By.cssSelector(".moveDown.icon.down")).isEnabled()) {//кнопка вниз
            delete_module();
            Assert.fail("Нет кнопки вниз для подмодуля");
        }
        submodules.get(0).findElement(By.cssSelector(".moveDown.icon.down")).click();

        if(!IsElementVisible(By.cssSelector(".EventItem.error"))) {
            delete_module();
            Assert.fail("Не видно негативного поп-апа");
        }
        String errors=driver.findElement(By.cssSelector(".EventItem.error")).getText();

        delete_module();

        Assert.assertEquals(errors,"Переместить ниже нельзя",
                "Не возникло предупреждение о перемещении вниз");
    }
}
