/** \brief Тесты для главной страницы, страниц семестра и сесии, редактирования дисциплины
 *
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
 *
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
 *
 * Тест-кейс
 *      1. Перейти на страницу модулей
 *      2. Создать два модуля
 *      3. Дать первому имя "1"
 *      4. Дать второму имя "2"
 *      5. Перезагрузить сртраницу - без этого кнопки сортировки не работают
 *      6. Нажать вверх у второго модуля
 *
 *      Ожидается: модули поменяются местами
 *
 * Тест-кейс
 * 1. Перейти на страницу модулей
 * 2. Создать два модуля
 * 3. Дать первому имя "1"
 * 4. Дать второму имя "2"
 * 5. Перезагрузить сртраницу - без этого кнопки сортировки не работают
 * 6. Нажать вниз у первого модуля
 *
 * Ожидается: модули поменяются местами
 *
 * Тест-кейс:
 * 1. Если нет модуля-создать
 * 2. Нажать у последнего модуля вниз
 *
 * Ожидается негативный поп-ап
 *
 *Тест-кейс:
 * 1. Если нет модуля - создать
 * 2. У первого модуля нажать вверх
 *
 * Ожидается: негативный поп-ап
 *
 * Тест-кейс:
 * 1. Создаем модуль
 * 2. Создаем два подмодуля
 * 3. Даем им имена "1" и "2" соответсвенно
 * 4. Нажимаем кнопку вниз для верхнего подмодуля
 *
 * Ожадание: подмодули поменяются местами
 *
 * Тест-кейс:
 * 1. Создаем модуль
 * 2. Создаем два подмодуля
 * 3. Даем им имена "1" и "2" соответсвенно
 * 4. Нажимаем кнопку вверх для нижнего подмодуля
 *
 * Ожадание: подмодули поменяются местами
 *
 * Тест-кейс:
 * 1. Создаем модуль
 * 2. В модуля есть подмодуль
 * 3. В нем нажимаем кнопку вверх
 *
 * Ожидание: негативный поп-ап
 *
 * Тест-кейс:
 * 1. Создаем модуль
 * 2. В модуля есть подмодуль
 * 3. В нем нажимаем кнопку вниз
 *
 * Ожидание: негативный поп-ап

 * Тест-кейс:
 * 1. Загрузить страницу
 * 2. Нажать Семестр
 * 3. Прочитать табы
 *
 * Ожидается: Название и порядок табов = Сессия, История, Журнал

 * Тест-кейс:
 * 1. Окрыть страницу
 * 2. Нажать Семестр
 * 3. Нажать Сессия
 *
 * Ожидается: загрузилась странца сессии

 * Тест-кейс:
 * 1. Окрыть страницу
 * 2. Нажать Семестр
 * 3. Нажать История
 *
 * Ожидается: загрузилась странца истории выставления баллов

 * Тест-кейс:
 * 1. Окрыть страницу
 * 2. Нажать Сессия
 * 3. Нажать Семестр
 *
 * Ожидается: загрузилась странца семестра

 * Тест-кейс:
 * 1. Окрыть страницу
 * 2. Нажать Сессия
 * 3. Нажать История
 *
 * Ожидается: загрузилась странца истории выставления баллов

 * Тест-кейс:
 * 1. Загрузить страницу
 * 2. Нажать Сессия
 * 3. Прочитать табы
 *
 * Ожидается: Название и порядок табов = Семестр, История, Журнал

 * Тест-кейс:
 * 1. Загрузить страницу
 * 2. Нажать Журнал
 * 3. Прочитать табы
 *
 * Ожидается: Название и порядок табов = Семестр, Сессия, История

 * Страница семестра

 * Тест-кейс:
 * 1. Выставить очень большой балл (101)
 *
 * Ожидается: Балл не выставлен. Сообщение об ошибке. Итоги не изменились.

 * Тест-кейс:
 * 1. Выставить нулевой балл
 *
 * Ожидается: Поставлен ноль. Итоги не изменились.

 * Тест-кейс:
 * 1. Выставить балл
 * 2. Удалить балл
 *
 * Ожидается: Окно с сообщением. Оценка удалилась. Итоги изменились

 * Тест-кейс:
 * 1. Выставить балл
 *
 * Ожидается: Окно с сообщением. Итоги изменятся

 * Тест-кейс:
 * 1. Выставить балл
 * 2. Поменять этот балл
 *
 * Ожидается: окно с сообщением. Итог и Итог за семестр изменятся

 * Тест-кейс:
 * 1. Выставить балл
 * 2. Выставить еще один балл в туж е строку
 *
 * Ожидается: Итог и Итог за семестр изменятся на их сумму

 * Тест-кейс:
 * 1. Выставить балл=-1
 *
 * Ожидается:
 * 1. Выставится балл=1.
 * 2. Итог и Итог за семестр изменятся на 1

 * Тест-кейс:
 * 1. Прочесть максимальный для столбца балл
 * 2. Выставить балл на 1 больше
 *
 * Ожидается: оценка не выставилась, окно с ошибкой

 * Тест-кейс:
 * 1. Выставить бонусные баллы
 *
 * Ожидается: Итог изменится на эту сумму

 * Тест-кейс:
 * 1. Просуммировать 7 строку до бонусов
 * 2. сравнить с Итогом за семестр
 * 3. Добавить бонусы и экзамен
 * 4. Сравнить с Итогом

 Страница сессии зачета

 * Тест-кейс:
 * 1. Открыть страницу
 * 2. Выставить первый добор в 60 баллов
 * 3. Выставить второй добор
 *
 * Ожидается: оценка не выставлена. негативный поп-ап

 * Тест-кейс:
 * 1. Открыть страницу
 * 2. Выставить в первый добор 61 балл
 *
 * Ожидается: Негативный поп-ап. Оценка не выставлена

 * Тест-кейс:
 * 1. Открыть страницу
 * 2. Выставить первый добор
 * 3. Выставить второй добор
 *
 * Ожидается: Оценки выставлены. Сумма итога = сумме доборо. поп-ап о выставлении

 * Тест-кейс:
 * 1. Открыть страницу
 * 2. Выставить второй добор
 *
 * Ожидается: Негативный поп-ап. Оценка не выставлена

 * Тест-кейс:
 * 1. Открыть страницу
 * 2. Выставить добор первый
 * 3. ВЫставить добор второй
 * 4. Удалить оценку из первого добора
 *
 * Ожидается: Негативный поп-ап. Оценка не удалилась

 * Тест-кейс:
 * 1. Открыть страницу
 * 2. Выставить первый добор
 * 3. Выставить второй добор
 * 4. Удалить второй добор
 *
 * Ожидается: Поп-ап. Оценка удалилась.

 * Тест-кейс:
 * 1. Открыть страницу семестра
 * 2. Выставить все оценки
 * 3. Открыть страницу сессии
 * 4. Выставить в первый добор балл
 * 5. Выставить во второй добор балл
 *
 * Ожидается: Оценки не выставлены. Негативный поп-ап.

 Страница сессии экзамена

 * Тест-кейс:
 * 1. Затий на страницу
 *
 * Ожидается: первая строка пустая. Это необходимо.

 * Тест-кейс:
 * 1. айти на страницу
 * 2. Выставить в дбор баллов 0
 *
 * Ожидается: Оценка выставилась. Итог не изменился

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор баллов 39
 *
 * Ожидается: Негативный поп-ап. Оценка не выставилась

 *Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить 38 баллов в добор баллов
 *
 * Ожидается: Балл выставлен. Сообщение о выставлении. Итог изменился

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить оценку
 * 3. Изменить оценку
 *
 * Ожидается: Поп-ап. Итог изменился. Оценка другая.

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить оценку
 * 3. Удалить оценку
 *
 * Ожидается: Поп-ап. Оценки нет. Итог нулевой

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить автомат
 *
 * Ожидается: Автомат не выставлен. Негативный поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить неявку первую по счету
 *
 * Ожидается: Неявка не выставлена. негативный поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить неявку вторую по счету
 *
 * Ожидается: Неявка не выставлена. Негативный поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить третью по счету неявку
 *
 * Ожидается: Неявка не выставлена. Негативный поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить первую пересдачу
 *
 * Ожидается: Оценка не выставлена. Негативный поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Вставить вторую пересдачу
 *
 * Ожидается: Оценка не выставлена. Негативный поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 37 баллов
 * 3. Выставить в экзамен балл
 *
 * Ожидается: Балл не выставлен в экзамен. Негативный поп-ап.

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить в экзамен 41 балл
 *
 * Ожидается: Балл не выставлен. негативный поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить в экзамен балл нормальный
 *
 * Ожидается: Балл выставлен. Итог изменен

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить балл в экзамен
 * 4. Выставить балл в пересдачу1
 *
 * Ожидается: Балл не добавлен в пересдачу. негативный поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить в экзамен балл
 * 4. Выставить балл во вторую пересдачу
 *
 * Ожидается: Балл не добавлен. Негативный поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить балл в экзамен
 * 4. Выставить неявку на экзамен
 *
 * Ожидается: Выставилась неявка. Оценка обнулилась

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставть неявку на экзамен
 * 4. Выставить оценку за экзамен
 *
 * Ожидается: Неявка исчезла. Балл добавлен

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставть в экзамен балл
 * 4. Удалить балл из добора
 *
 * Ожидается: Негативный поп-ап. Балл не удалился

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить экзамен
 * 4. Удалить балл за экзамен
 *
 * Ожидается: Балл удален. Итог изменился

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить балл за экзамен
 * 4. Изменить балл за экзамен
 *
 * Ожидается: Балл изменен

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить неявку за экзамен
 * 4. Выставить балл за первую пересдачу
 *
 * Ожидается: Балл выставлен. Итог изменен. поп-ап.

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить неявку за экзамен
 * 4. Выставить балл за первую пересдачу
 * 5. Удалить балл за первую пересдачу
 *
 * Ожидается: Балл удален. Итог изменен. поп-ап.

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить неявку за экзамен
 * 4. Выставить балл за первую пересдачу
 * 5. Выставить неявку за вторую пересдачу
 *
 * Ожидается: Неявка невыставлена. Негативный поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить неявку за экзамен
 * 4. Выставить балл за первую пересдачу
 * 5. Выставить неявку за первую пересдачу
 *
 * Ожидается: Неявка стоит. Балл обнулен

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить неявку за экзамен
 * 4. Выставить балл за первую пересдачу = 22
 * 5. Выставить неяву вторую пересдачу
 *
 * Ожидается: Неявка невыставлена. Негативный поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить неявку за экзамен
 * 4. Выставить балл за первую пересдачу = 22
 * 5. Выставить балл за вторую пересдачу
 *
 * Ожидается: Балл невыставлен. Негативный поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить неявку за экзамен
 * 4. Выставить балл за первую пересдачу
 * 5. Обновиь балл за первую пересдачу
 *
 * Ожидается: Балл обновлен. Поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить неявку за экзамен
 * 4. Выставить балл за первую пересдачу
 * 5. Удалить неявку за экзамен
 *
 * Ожидается: Неявка неудалена. Негативный поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить неявку за экзамен
 * 4. Выставить балл за первую пересдачу
 * 5. Удалить балл за добор
 *
 * Ожидается: Балл неудален. Негативный поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить неявку за экзамен
 * 4. Выставить неявку на первую пересдачу
 * 5. Выставить балл за вторую пересдачу
 *
 * Ожидается: Балл выставлен. поп-ап. Итог изменен.

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить неявку за экзамен
 * 4. Выставить неявку на первую пересдачу
 * 5. Выставить балл за вторую пересдачу
 * 6. Обновить балл за вторую пересдачу
 *
 * Ожидается: Балл обновлен. Иог изменен. Поп-ап.

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить неявку за экзамен
 * 4. Выставить неявку на первую пересдачу
 * 5. Выставить балл за вторую пересдачу
 * 6. Удалить балл за вторую пересдачу
 *
 * Ожидается: Балл удален. Поп-ап.

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить неявку за экзамен
 * 4. Выставить неявку на первую пересдачу
 * 5. Выставить балл за вторую пересдачу
 * 6. Выставить неявку на вторую пересдачу
 *
 * Ожидается: Стоит неявка. балл обнулился

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить неявку за экзамен
 * 4. Выставить неявку на первую пересдачу
 * 5. Выставить балл за вторую пересдачу
 * 6. Удалить неявку на первую пересдачу
 *
 * Ожидается: Неявка неудалена. Негативный поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить неявку за экзамен
 * 4. Выставить неявку на первую пересдачу
 * 5. Выставить балл за вторую пересдачу
 * 6. Удалить неявку на экзамен
 *
 * Ожидается: Неявка неудалена. Негативный поп-ап

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Выставить в добор 38 баллов
 * 3. Выставить неявку за экзамен
 * 4. Выставить неявку на первую пересдачу
 * 5. Выставть балл за вторую пересдачу
 * 6. Удалить балл в доборе
 *
 * Ожидается: Балл неудален. Негативный поп-ап

 * Тест-кейс:
 * 1. Открыть страницу семестра
 * 2. Выставить все оценки
 * 3. Открыть страницу сессии
 * 4. Выставить в добор балл
 * 5. Выставить автомат
 * 6. Удалить автомат
 *
 * Ожидается: Оценка не выставлены. Негативный поп-ап. Автомат поставился. Автомат удален.

 * Страница просмотра

 * Тест-кейс:
 * 1. переходим на страницу
 *
 * Ожидание: страица загрузилась, есть негативный поп-ап о невозможности редактирования

 * Тест-кейс:
 * 1. Перейти на страницу
 * 2. Кликнуть таб преподователи
 *
 * Ожидается: загрузилась страница преподователей

 * Тест-кейс:
 * 1. Переходим на страницу
 * 2. Вводим в поиск имя преподователя
 * 3. Ищем конкретного преподователя в результатх поиска
 * 4. Нажимаем присоединить
 * 5. В Прикрепленных преподователях ищем этого преподователя
 * 6. нажимаем отсоединить
 *
 * Ожидание: Преподователь нашелся. Преподователб прикрепился. преподователь открепился.

 * Тест-кейс:
 * 1. Переходим на страницу
 * 2. Вводим в поиск уже прикрепленного преподователя
 * Можно его читать, но для этой дисциплины прикреплен Лошкарев
 *
 * Ожидается: результат поиска пустой, есть тескствое предупреждение

 * Домашняя страница

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Авторизироваться
 * 3. Нажать кнопку Редактирование
 *
 * Ожидается: Загрузилась страница редактирования дисциплины

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Авторизироваться
 * 3. сменить семестр
 * 4. Нажать кнопку Просмотр
 *
 * Ожидается: Загрузилась страница модулей дисциплины

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Авторизироваться
 * 3. Нажать кнопку Журнал
 *
 * Ожидается: Загрузился журнал дисциплины

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Авторизироваться
 * 3. Нажать кнопку Журнал
 *
 * Ожидается: Загрузился журнал дисциплины

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Авторизироваться
 * 3. Прочитать название последней кнопки
 *
 * Ожидается: Название = Просмотр или Редактирование

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Авторизироваться
 * 3. Прочитать название третьей кнопки
 *
 * Ожидается: Название = Журнал

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Авторизироваться
 * 3. Прочитать название второй кнопки
 *
 * Ожидается: Название = Сессия

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Авторизироваться
 * 3. Прочитать название первой кнопки
 *
 * Ожидается: Название = Семестр

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Авторизоваться
 * 3. Сменить семестр
 * 4. Для извесной дисциплины проверить тип дисциплины (зачет)
 *
 * Ожидается: Тип совпадает с ожидаемым

 * Тест-кейс:
 * 1. Зайти на страницу
 * 2. Авторизоваться
 * 3. Сменить семестр
 * 4. Для извесной дисциплины проверить тип дисциплины (экзамен)
 *
 * Ожидается: Тип совпадает с ожидаемым

 * Тест-кейс:
 * 1. Открыть страницу
 * 2. Авторизоваться
 * 3. Перейти на другой семест
 * 4. Нажать кнопку Сессия для зачета
 *
 * Ожидается: Загрузилась страница сессии

 * Тест-кейс:
 * 1. Открыть страницу
 * 2. Авторизоваться
 * 3. Перейти на другой семест
 * 4. Нажать кнопку Сессия для экзамена
 *
 * Ожидается: Загрузилась страница сессии

 * Тест-кейс:
 * 1. Открыть страницу
 * 2. Авторизироваться
 * 3. Нажать на кнопку Семестр (нажимается на второй строке таблицы, истоически)
 *
 * Ожидается:
 * Загрузилась страница выставления баллов

 * Тест-кейс:
 * 1. Открыть страницу
 * 2. Авторизироваться
 * 3. Нажать на кнопку Семестр (нажимается на второй строке таблицы, истоически)
 *
 * Ожидается:
 * Загрузилась страница выставления баллов

 * Тест-кейс:
 * 1. Открыть страницу
 * 2. Авторизироваться
 * 3. Нажать на кнопку Семестр (нажимается на второй строке таблицы, т.к. на данный момент из первой - неактивна
 *
 * Ожидается:
 * Загрузилась страница выставления баллов


 */
package ru.sfedu.grade.TeacherTest;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**\brief Родительский класс для всех классов пакета.
 *
 * Содержит основные функции, используемые тестами:
 * Авторизация, установка ожиданий, инициализация драйвера, выход из аккаунта, переход "домой" и проверки видимости элемента.
 * И вспомогательные функции нужные для конкетно этого класса: установка\удаление оценки, поиск кнопок.
 * @version 1.0
 * @author Stepanova
 * @see AfterClickBtnsTest, MarksForSemestrPageTest, MarksForSessiaPageTest, TeacherTest, MarksOfZachetPageTest, ProsmotrDisciplinPageTest, EditDisciplinPageTest, AfterClickBtnsTest
 */
public class Helper {
	/// \brief Переменная для использования явного ожидания
	/// @detailed Пример использования wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tab-news")))
	protected WebDriverWait wait;
	/// Веб-драйвер
	protected WebDriver driver;
	/// Значение в секундах устанавливаемых ожиданий @see timeouts_set
	private static final long DEFAULT_TIMEOUT = 10;//300;
	/// Путь к файлу конфигурации @see get_chrome_driver
	static private String config_path="config.ini";
	/** \brief Флаг, определющий место чтения пути к конфигурационному файлу
	 *
	 * Если значение = true, то путь читается из системной переменной Driver_Path
	 * Если значение = false, то путь считается стандартным, т.е. корнем каталога
	 * @see config_path, get_config_file_path_from_env, config_path
	 */
	static private boolean use_path_from_env=false;

	/// Логин для аккаунта студента @see authorization
	private String student_login="ELLA";
	/// Логин для аккаунта преподавателя @see authorization
	private String teacher_login="dem";
	/// Логин для сотрудника деканата @see authorization
	private String dekanat_login="bravit";
	/// Логин для аккаунта Романа Борисовича @see authorization
	private String rs_login="rs";
	/// Общий пароль для всех аккаунтов @see authorization
	private String pwd="22222";

	/** @brief Читает путь к конфигурационному файлу проекта из системной переменной Driver_Path
	 *
	 * Можно изменить так, что функция будет менять "стандартый" путь к конфигурационному файлу
	 * @see get_chrome_driver, get_firefox_driver, use_path_from_env, config_path
	 * @return путь к конфигурационному файлу
	 */
	private String get_config_file_path_from_env(){
		//config_path = System.getenv("Driver_Path");
		// можно менять в функции сам путь, тогда просто добавляется ее вызов в гет_драйвер, а так присваивать надо
		return  System.getenv("Driver_Path");
	}

	/** \brief Чтение пути к драйверу браузера Хром из конфигурационного файла
	 *
	 * Читает путь к файлу драйвера Хрома из файла настроек, путь к файлу настроек либо указывается через системную переменную
	 * Driever_Path, либо считается стандартным - корнем каталога
	 * @return путь к драйверу браузера хром
	 * @see get_firefox_driver, get_config_file_path_from_env, use_path_from_env
	 * @throws IOException Не удалось прочитать файл
	 */
	public  String get_chrome_driver()  {
		FileInputStream fis=null;
		Properties props = new Properties();
		try
		{
			if(use_path_from_env)
				fis=new FileInputStream(new File(get_config_file_path_from_env()));
			else
				fis = new FileInputStream(new File(config_path));
			props.load(fis);
			String DRIVER_CHROME_PATH= props.getProperty("CHROME_DRIVER_PATH");
			return DRIVER_CHROME_PATH;
		}
		catch (IOException e) {
			System.err.println("ОШИБКА: Файл свойств отсуствует!");
			e.printStackTrace();
			Assert.fail("Не прочелся конфиг файл");
			return "";
		}
		finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/** \brief Чтение пути к драйверу браузера ФФ из конфигурационного файла
	 *
	 * Читает путь к файлу драйвера ФФ из файла настроек, путь к файлу настроек либо указывается через системную переменную
	 * Driever_Path, либо считается стандартным - корнем каталога
	 * @return путь к драйверу браузера хром
	 * @see get_firefox_driver, get_config_file_path_from_env, use_path_from_env
	 * @throws IOException Не удалось прочитать файл
	 */
	public  String get_firefox_driver(){
		FileInputStream fis=null;
		Properties props = new Properties();
		try
		{
			if(use_path_from_env)
				fis=new FileInputStream(new File(get_config_file_path_from_env()));
			else
				fis = new FileInputStream(new File(config_path));
			props.load(fis);
			String DRIVER_FF_PATH= props.getProperty("FIREFOX_DRIVER_PATH");
			return DRIVER_FF_PATH;
		}
		catch (IOException e) {
			System.err.println("ОШИБКА: Файл свойств отсуствует!");
			e.printStackTrace();
			Assert.fail("Не прочелся конфиг файл");
			return "";
		}
		finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * \brief Устанавливает значения ожиданий для драйвера
	 * @see DEFAULT_TIMEOUT, AuthorizationTest::getDriver
	 */
	public void timeouts_set(){
		driver.manage().timeouts().setScriptTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
		wait=new WebDriverWait(driver, DEFAULT_TIMEOUT);
	}

	/**
	 * \brief  Переход по "домашней" ссылке http://testgrade.sfedu.ru/
	 * @see exit
	 */
	public void go_home() {
		driver.get("http://testgrade.sfedu.ru/");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("header_wrapper")));
	}

	/**
	 * \brief Проверяет наличие элемента на странице
	 *
	 * @param iClassName By.Id("id"), By.CssSelector("selector") и т.д.
	 * @return Наличие элемента
	 * @throws NoSuchElementException вызывается методом findElement(By by), если элемент с заданным селектором не найден на странице.
	 @see IsElementVisible
	 */
	public Boolean IsElementExists(By iClassName) {
		// в метод передаётся "iClassName" это By.Id("id_elementa"), By.CssSelector("selector") и т.д.
		try
		{
			driver.findElement(iClassName);
			return true;
		}
		catch (NoSuchElementException e)
		{
			return false;
		}
	}

	/**
	 * \brief Проверяет видимость элемента на странице.
	 *
	 * @param iClassName "iClassName" = By.Id("id"), By.CssSelector("selector") и т.д.
	 * @return Видимость объекта (видимый/не видимый)
	 * @throws NoSuchElementException вызывается методом findElement(By by), если элемент с заданным селектором не найден на странице.
	 @see IsElementExists
	 */
	public Boolean IsElementVisible(By iClassName) {
		try
		{
			boolean iv = driver.findElement(iClassName).isDisplayed();
			if (iv) { return true; } else { return false; }
		}
		catch (NoSuchElementException e) { return false; } // если элемент вообще не найден
	}

	/** \brief Переключение на авторизацию по логину\паролю
	 *
	 * У главной неавторизированной страницы может быть два варианта загрузки: авторизация по логину и авторизация по логину\паролю
	 * В тестах используется авторизация по логину\паролю. И эта функция переключает на эту страницу, если загрузилась дргуая.
	 @see authorization
	 */
	public void if_grade_visiable(){
		if( IsElementVisible(By.id("grade"))) driver.findElement(By.id("grade")).click();
	}

	/** \brief Авторизация под аккаунтом студента
	 *
	 * Происходит авторизация под аккаунтом с доступом преподователя
	 * @return Владелец аккаунта
	 */
	public String authorization() {
		//driver.get("http://testgrade.sfedu.ru/");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tab-news")));

		// driver.findElement(By.id("grade")).click();
		if(IsElementExists(By.id("grade"))) driver.findElement(By.id("grade")).click();
		driver.findElement(By.id("login")).sendKeys(teacher_login);
		driver.findElement(By.id("password")).sendKeys(pwd);
		driver.findElement(By.id("signin_b")).click();

		// wait.until(ExpectedConditions.textToBe(By.className("main_top"),"Дисциплины"));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("main_top"))) ;

		if(! IsElementVisible(By.id("username")))
			Assert.fail("Не удалось войти в аккаунт "+teacher_login+" "+pwd);

		return "Яна Демяненко";
	}

	/** \brief Авторизация в системе с возможностью выбрать аккаунт
	 *
	 * Авторизация в системе для аккаунтов с различным уровнем доступа: студенческий, преподовательский, сотрудник деканата
	 * @param type тип акканта под которым можно авторизироваться: student, teacher, dekanat, rb
	 * @return владелец аккаунта
	 */
	public String authorization(String type){
		String login, result;
		switch (type){
			case ("student"):
				login=student_login;
				result="Элла Кораблина";
				break;
			case("teacher"):
				login=teacher_login;
				result="Яна Демяненко";
				break;
			case("dekanat"):
				login=dekanat_login;
				result="Виталий Брагилевский";
				break;
			case("rb"):
				login=rs_login;
				result="Роман Штейнберг";
				break;
			default:
				login="2";
				result="Wrong type";
				Assert.fail("Неверный логин/пароль для аккаунта");

		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tab-news")));

		// driver.findElement(By.id("grade")).click();
		if( IsElementVisible(By.id("grade"))) driver.findElement(By.id("grade")).click();
		driver.findElement(By.id("login")).sendKeys(login);
		driver.findElement(By.id("password")).sendKeys(pwd);
		driver.findElement(By.id("signin_b")).click();

		//  wait.until(ExpectedConditions.textToBe(By.className("main_top"),"Дисциплины"));

		if(!IsElementExists(By.id("username")))
			Assert.fail("Не удалось войти в аккаунт "+login+" "+pwd);

		return result;
	}

	/** \brief Авторизация в системе под произвольным аккаунтом
	 *
	 * Позволяет авторизироваться в системе под своим или другим извесным Вам аккаунтом. На тестовом сервере для всех аккаунтов пароль = 22222
	 * @param login Логин для входа в систему
	 * @param pass Пароль для этого аккаунта.
	 * @return вошли\не вошли в систему
	 */
	public Boolean authorization(String login, String pass){
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));

		// driver.findElement(By.id("grade")).click();
		if(IsElementVisible(By.id("grade"))) driver.findElement(By.id("grade")).click();
		driver.findElement(By.id("login")).sendKeys(login);
		driver.findElement(By.id("password")).sendKeys(pass);
		driver.findElement(By.id("signin_b")).click();

		//    wait.until(ExpectedConditions.textToBe(By.className("main_top"),"Дисциплины"));

		return IsElementExists(By.id("username"));
	}

	/**
	 * \brief  Выход из аккаунта
	 *
	 * Вначале выход по ссылке- искать кнопку дорого. если не вышло, то тогда нажимаем на кнопку "выход"
	 * @see authorization
	 */
	public void exit(){
		driver.get("http://testgrade.sfedu.ru/sign/out");

		if(! IsElementVisible(By.id("tab-news"))){
			driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[3]/a[2]")).click();   // fa fa-sign-out fa-bg fa-fw //*[@id="wrap"]/div[2]/div[3]/a[2]/i
		}
		else {
			if(! IsElementVisible(By.id("tab-news"))) {
				Assert.fail("Не удалось выйти из аккаунта ");
			}
		}
	}

	/**
	 * \brief Переключение семестра на заданный по идентификатору
	 *
	 * Происходит переключение семестра по его идентификатору в селекторе
	 * @param[in] sem Строковое значение номера семестра, например S-10, на который нужно переключиться
	 * @warning Может работать плохо
	 * @throws ElementNotVisibleException Не виден элемент селектора для переключения семестра
	 * @throws NoSuchElementException Выбран несуществующий семестр (в списке)
	 */
	public void choose_semestr(String sem){ //ID

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("semesterChangerSelection")));
		String was_sem="1";
		try {
			was_sem= driver.findElement(By.className("semesterChangerSelection")).getText();
			driver.findElement(By.className("semesterChangerSelection")).click();
		}
		catch (ElementNotVisibleException e){
			Assert.fail("Не доступен селектор выбора семестра: "+sem);
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("switchSemester")));
		try {
			//if (IsElementVisible(By.id(sem)))
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sem)));
			driver.findElement(By.id(sem)).click();
		}
		catch(NoSuchElementException e){
			Assert.fail("Семестр с указанным идeнтивикатором не существует: "+sem);
		}
		catch (ElementNotVisibleException e)
		{
			Assert.fail("Семестр с указанным идeнтивикатором не виден: "+sem);
		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("semesterChangerSelection"))); // visibilityOfElementLocated(By.className("main_top")));
		// if(IsElementExists(By.className("semesterSwitcherBtn")))
		Assert.assertEquals(driver.findElement(By.className("semesterChangerSelection")).getText(), was_sem,"Не сменился семестр "+was_sem);
	}

	/** \brief Тип дисциплины
	 *
	 * @param table_strung Строка из таблицы
	 * @return Тип дисциплины экзамен\зачет
	 * \throws NoSuchElementException Если не нашли элемента
	 */
	public String get_type_subject(WebElement table_strung){
		String css_to_type=".info_cell.td_control";
		WebElement type_subj;
		try {
			type_subj = table_strung.findElement(By.cssSelector(css_to_type));
		}
		catch (NoSuchElementException e){
			Assert.fail("Не доступен тип дисциплины: экзамен/зачет");
			return "";
		}
		return type_subj.getText();
	}

	/**
	 *\brief Возвращает экземпляр кнопки Семестр
	 *
	 * Ищет в строке кнопку Семестр и возвращает ее для дальнейших действий (нажатие и т.п.)
	 * @param string_table Экземпляр строки
	 * @return Экземпляр кнопки Семестр
	 */
	public WebElement get_btn_semestr(WebElement string_table){
		List<WebElement> btns;
		try{
			btns=string_table.findElements(By.className("action_cell"));
			if(!btns.get(0).isDisplayed())
				Assert.fail("Кнопки Семестр нет на экране");
			return btns.get(0);
		}
		catch (NoSuchElementException e){
			Assert.fail("Не доступены кнопки для дисциплины");
			return string_table;
		}
	}

	/**
	 *\brief Возвращает экземпляр кнопки Сессия
	 *
	 * Ищет в строке кнопку Сессия и возвращает ее для дальнейших действий (нажатие и т.п.)
	 * @param string_table Экземпляр строки из таблицы предметов
	 * @return Экземпляр кнопки Сессия
	 */
	public WebElement get_btn_sessia(WebElement string_table){
		List<WebElement> btns;
		try{
			btns=string_table.findElements(By.className("action_cell"));
			if(!btns.get(1).isDisplayed())
				Assert.fail("Кнопки Сессия нет на экране");
			return btns.get(1);
		}
		catch (NoSuchElementException e){
			Assert.fail("Не доступены кнопки для дисциплины");
			return string_table;
		}
	}

	/**
	 *\brief Возвращает экземпляр кнопки Журнал
	 *
	 * Ищет в строке кнопку Журнал и возвращает ее для дальнейших действий (нажатие и т.п.)
	 * @param string_table Экземпляр строки из таблицы предметов
	 * @return Экземпляр кнопки Журнал
	 */
	public WebElement get_btn_journal(WebElement string_table){
		List<WebElement> btns;
		try{
			btns=string_table.findElements(By.className("action_cell"));
			if(!btns.get(2).isDisplayed())
				Assert.fail("Кнопки Журнал нет на экране");
			return btns.get(2);
		}
		catch (NoSuchElementException e){
			Assert.fail("Не доступены кнопки для дисциплины");
			return string_table;
		}
	}

	/**
	 *\brief Возвращает экземпляр кнопки Просмотр
	 *
	 * Ищет в строке кнопку Просмотр и возвращает ее для дальнейших действий (нажатие и т.п.)
	 * @param string_table Экземпляр строки из таблицы предметов
	 * @return Экземпляр кнопки Просмотр
	 */
	public WebElement get_btn_prosmotr(WebElement string_table){
		List<WebElement> btns;
		try{
			btns=string_table.findElements(By.className("action_cell"));
			if(!btns.get(3).isDisplayed())
				Assert.fail("Кнопки Просмотр нет на экране");
			if(!btns.get(3).getText().equals("Просмотр"))
				Assert.fail("Не совпадает текст кнопки Просмотр: " + btns.get(3).getText());
			return btns.get(3);

		}
		catch (NoSuchElementException e){
			Assert.fail("Не доступены кнопки для дисциплины");
			return string_table;
		}
	}

	/**
	 *\brief Определяет кнопку Редактирование
	 *
	 * Анализирует текст кнопки и говорит Редактирование или Просмотр
	 * @param string_table Экземпляр строки из таблицы предметов
	 * @return кнопка редактирование дисциплины или кнопка просмотр
	 */
	public Boolean btn_is_radactirovania(WebElement string_table){
		List<WebElement> btns;
		try{
			btns=string_table.findElements(By.className("action_cell"));
			if(!btns.get(3).isDisplayed())
				Assert.fail("Кнопки редактирование нет на экране");
			return btns.get(3).getText().equals("Редактирование");
		}
		catch (NoSuchElementException e){
			Assert.fail("Не доступены кнопки для дисциплины");
			return false;
		}
	}


	/**
	 *\brief Возвращает экземпляр кнопки Редактирование
	 *
	 * Ищет в строке кнопку Редактирование и возвращает ее для дальнейших действий (нажатие и т.п.).
	 * Применять при уверености, что она есть.
	 * @param string_table Экземпляр строки из таблицы предметов
	 * @return Экземпляр кнопки Редактирование
	 */
	public WebElement get_btn_redactir(WebElement string_table){
		List<WebElement> btns;
		try{
			btns=string_table.findElements(By.className("action_cell"));
			if(!btns.get(3).isDisplayed())
				Assert.fail("Кнопки Просмотр нет на экране");
			if(!btns.get(3).getText().equals("Редактирование"))
				Assert.fail("Не совпадает текст кнопки Редактирование: " + btns.get(3).getText());
			return btns.get(3);
		}
		catch (NoSuchElementException e){
			Assert.fail("Не доступены кнопки для дисциплины");
			return string_table;
		}
	}

	/** \brief Значение последнего семестра в системе
	 *
	 * Сейчас последний семестр - это 10 семестр
	 * @return Возвращает числовой эквивалент последнего семестра
	 * @warning Каждый семестр следует изменять это значение
	 */
	public String last_semestr(){ return "10";}

	public boolean check_or_choose_all_groups_in_selector(){
		WebElement selectElem = driver.findElement(By.tagName("select"));
		Select select = new Select(selectElem);
		if(select.getFirstSelectedOption().getText().equals("Все группы")){
			return true;
		}
		select.selectByValue("0");
		return (select.getFirstSelectedOption().getText().equals("Все группы"));
	}

	/**
	 * \brief Читает из ячейки выставленный балл
	 *
	 * Если ячейка пуста, вернется 0
	 * @param rowNum Номер строки
	 * @param colNum Номер колонки
	 * @return Выставленный балл
	 */
	public int Mark(int rowNum, int colNum) {
		WebElement element = driver.findElement(By.id("col_row_" + colNum + "_" + rowNum));
		WebElement field = element.findElement(By.tagName("input"));
		try {
			return Integer.parseInt(field.getAttribute("value"));
		} catch (NumberFormatException e) {
			return 0;
		}
		//   return Integer.parseInt(field.getAttribute("value"));
	}

	/**
	 *\brief Проверка ячейки на пустоту
	 *
	 * @param rowNum Номер строки
	 * @param colNum Номер колонки
	 * @return пусто\не пусто
	 */
	public boolean check_emty_mark(int rowNum, int colNum) {
		WebElement element = driver.findElement(By.id("col_row_" + colNum + "_" + rowNum));
		WebElement field = element.findElement(By.tagName("input"));
		try {
			Integer.parseInt(field.getAttribute("value"));
		} catch (NumberFormatException e) {
			return true;
		}
		return false;
	}

	/**
	 * \brief Возвращает балл из колонок Итог, Экзамен, Итог за семестр
	 *
	 * Нужно указать какой это столбец по номеру. Там другие стили и классы, поэтому отдельная функция
	 * @param rowNum Номер строки
	 * @param col Номер колонки
	 * @return выставленный баллл
	 */
	public int Semestr_mark(int rowNum,int col) {
		WebElement row = driver.findElement(By.id("row_" + String.valueOf(rowNum)));
		//ячейка, используется для итогов
		WebElement field = row.findElement(By.xpath("//*[@id=\"row_"+rowNum+"\"]/td["+col+"]"));
		//*[@id="row_1"]/td[7]
		try {
			return Integer.parseInt(field.getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * \brief Выставляет балл
	 * @param rowNum Номер строки
	 * @param colNum Номер колонки
	 * @param mark Балл, который выставляется. Строка
	 * @throws InterruptedException от ожидания
	 */
	public void set_mark(int rowNum, int colNum, String mark) throws InterruptedException {
		WebElement element = driver.findElement(By.id("col_row_" + colNum + "_" + rowNum));
		WebElement field = element.findElement(By.tagName("input"));
		field.sendKeys(Keys.BACK_SPACE+mark+"\n"+Keys.ESCAPE);
		TimeUnit.MILLISECONDS.sleep(50);
	}

	/**
	 * \brief Удаляет выставленную оценку
	 * @param rowNum Номер строки
	 * @param colNum Номер колонки
	 * @throws InterruptedException от ожидания
	 */
	public void delete_mark(int rowNum, int colNum) throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(50);
		WebElement element = driver.findElement(By.id("col_row_" + colNum + "_" + rowNum));
		WebElement field = element.findElement(By.tagName("input"));
		//element.clear();
		field.sendKeys(Keys.BACK_SPACE+"" +Keys.BACK_SPACE+"\n"+Keys.ESCAPE);

		TimeUnit.SECONDS.sleep(1);
		// field.sendKeys("\n");
		if(!check_emty_mark(rowNum,colNum))
			Assert.fail("Не удалилась оценка row:"+rowNum+" col:"+colNum);
	}


















}
