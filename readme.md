Авто-тесты для системы БРС. 
Selenium + Java + TestNG + Doxygen + Maven

Структура проекта:
--------------------------

|	 Пакет	| Класс			| Описание|
|-----------------------|----------------------|-------------------------|
|RegressionsTest	| SimpleTest | Переход по заранее заданным ссылкам |
|		| StudentAccTest| и проверка существования этих страниц|
|		| TeacherAccTest| |
|		| DekanatAccTest||
|AuthorizationTest| AuthorizationFormTest| Тесты авторизации|
|		| AuthorizationTest	||
|UnauthorizedPageTest	| TabsTest	| Тесты элементов на странице для|
|			| FooterLinks	| неавторизированного пользователя|
|			| StudentPageTest| PageOfDisciplin| Кнопки на странице дисциплины у студента |
| TeacherTest | TeacherTest | Тесты для преподовательского  |
|		| AfterClickBtnsTest |аккаунта на кнопки и оценки|
|		| EditDisciplinPageTest ||
|		| MarksForSemestrPageTest ||
|		| MarksForSessiaPageTest ||
|		| MarksOfZachetPageTest ||
|		| ProsmotrDisciplinPageTest ||



В каждом пакете реализован базовый класс Helper, содержащий основные функции взаимодействия со страницей и сервисом. Так как, например: инициализация, чтение настроек, определение видимости, процесс авторизации и т.п. (см. в документации)

От пакета к пакету содержимое этого класса может меняться, добавляются новые функции, удаляются неиспользуемые пакетом.



Установка
--------------
Выполнить установку Maven(сборщик проекта) и Java
или можно использовать эти ссылки:

* https://maven.apache.org/download.cgi

* https://www.java.com/en/download/

* http://www.oracle.com/technetwork/java/javase/downloads/index.html

* https://www.seleniumhq.org/download/

Создать переменную окружения JAVA_HOME=C:\Program Files\Java\jdk-10.0.2

Создать переменную окружения M2_HOME=C:\Program Files\apache-maven-3.5.4

т.е. путь к программе.

Добавить эти пути с добавлением "\bin" в Path.

Среда разработки выбрана IDEA https://www.jetbrains.com/idea/download/index.html#section=windows

Переменная с jdk может не подтянуться после утановки, следует в настройках проекта устанивить путь вручную.

Для изменения документации установить Doxygen http://www.doxygen.nl/download.html#srcbin

Для отображения графов и графиков поставить допольнительно graphviz https://www.graphviz.org/download/

Для создания конфиг файла и работы не через консоль поставить Doxywizard

Настройка запуска
----------------
Файл config.ini задает некоторые свойства для запуска тестов. Он определяет где и в чем будут прогоняться тесты: на локальной машине или удаленно на сервере и также браузер в котором запустятся тесты на удаленной машине.

Файл должен находится в корне каталога. Так же есть программная возможность включить чтение пути к конфиг файлу из переменной окружения `Driver_Path` (флаг `use_path_from_env` в классе Helper), но данная возможность на этапе бета-тестов. Возмодно потребуется спец. вид пути: вместо одного должно быть два слэша \\.

Параметр `LOCALHOST` определяет место запуска: локально или на удаленном сервере, т.е. true и false соответсвенно.

Параметры `CHROME_DRIVER_PATH` и `FIREFOX_DRIVER_PATH` задают месторасположение драйверов браузеров на локальной машине. Эти драйвера позволяют Selenium работать с браузерами (сами браузеры тоже должны быть установлены).

Параметр `BASE_URL` определяет хост тестирования. Например http://testgrade.sfedu.ru/ или http://grade.sfedu.ru/ (но тут нужно учитывать авторизацию, на проде тестовые пароли не пройдут)

Параметр `BROWSER` определяет браузер для удаленного прогона тестов. Должен соотносится с возможностью того хаба, к которуму обращаемся.

Параметр `SERVER` это адрес хаба, по которому будут запущены тесты. На одном хабе возможно "поднимать" только один браузер, т.е. там где работает с браузером хром, браузер фф не запуститься, для этого нужен другой хаб со своей сборкой. 

Запуск
--------
Про создание и настройку проекта здесь написано не будет, т.к. сам проект уже создан и настроен. Создать его можно с помощью консольных команд mvn, конфигурационный файл - pom.xml

<h3> Сборка проекта</h3>

В данном проекте как сборщик исполльзуется мавен. 

Проект можно собирать из среды разработки. Среда сама все соберет, запустит и покажет результат. Для среды IDEA все настроено, а вот для других следует возможно скачивать и подключать плагины.

Для сборки проекта выполнить одну из команд из директории проекта(там где pom-файл):
* `mvn clean compile`
* `mvn clean install`
* `mvn clean package`

Первая команда просто компилирует основной код. Тесты она даже не скомпилирует.

Вторая команда соберет все и запустит тесты и поместит пакет в локальный репозиторий.

Третья еще упакает программу в jar-файл.

* Чтобы скомпилировать тесты, но не выполнять:

`mvn test-compile`

`mvn -DskipTests=true package`

`mvn install -DskipTests`



Если сборка падает с ошибкой:

 ```
 java: cannot access java.util.function.Function
 class file for java.util.function.Function not found
 ```

Нужно раскоментировать строки из pom-файла: 

```xml
 <source>10.0.2</source>
 <target>10.0.2</target>
```

Так же сборка может падать из-за подключенного плагина `maven-compiler-plugin` (в нем же и находятся приведенные выше строки). Удалить его или наоборот подключить.

Другие пути возможного решения:

*Для плагина `maven-surefire-plugin` раскоентирваоть настройки конфигурации

*Для плагина `maven-surefire-plugin` изменить версию на более низкую

*Для плагина `maven-compiler-plugin` раскоментирвоать настройки конфигурации

<h3> Запуск прогона </h3>

Есть разные способы запускать тесты:
* Из среды разработки. 

Самый простой метод. В настройках запуска к тестовому фрейворку можно выбрать параметры. Или вообще запустить нужный класс/пакет/метод через контексное меню. Так же он умеет "запускать" xml-файлы. 
Xml-файлы имеют четкую структуру и хранят информацию о том, что должны запускать. Через них можно конфигурировать тестовые наборы, тестировать только то, что указано, а не все сразу.

* Через командную строку. 

Чтобы запустить сразу все тестирование: `mvn test`

Чтобы запустить тесты в определенном классе: 
`mvn test -Dtest=ru.sfedu.grade.ClassName`

Чтобы запустить один тестовый метод из класса: 
`mvn test -Dtest=ru.sfedu.grade.ClassName#method_name`



Допольнительная информация:
* Запустить сборку без выполнения и компиляции тестов: `mvn -Dmaven.test.skip=true install`
* Запустить несколько тестовых классов\методов: `mvn test -Dtest = CLASS_NAME1#METHOD_NAME1, CLASS_NAME2 #METHOD_NAME2` Возможно понадобятся кавычки
* Команда `clean` очищает папку перед компиляцией

<h3> Результат </h3>

Результат прохождения тестов при запуске из среды разработке показывается там же.

При заупске из командной строки логи запуска печатаются в консоли и результат выполнения тестов testng записывает в файл формата html. Этот файл (index.html) можно найти в папке проекта по пути `\target\surefire-reports`

Документация
----------------
Для создания документации использовался Doxygen.

Документация к проекту находится в папке "Документация". Формат - html.

Для просмотра зайти в папку и открыть index.html - ярлык в папке.

Для удобства эта папка зархивирована.


Для изменения документации `doxygen <config file>` или через программу Doxywizard. Но не забыть проверить пути к программам (например, к graphviz)


 
P.S. Часть тестов уже была написана Ангелиной и находится здесь 
http://gitlab.mmcs.sfedu.ru:82/it-lab/gradeUItests/blob/master/UnitTestProject1/



https://qa-help.ru/questions/kak-zapustit-testng-iz-komandnoj-stroki
http://qaru.site/questions/16768748/maven-testng-include-and-exclude-tests
