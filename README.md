# Спецификация программы

## Исходные требования к программе

### Требования ко входным данным
Граф, мосты которого требуется найти, может быть предоставлен программе тремя способами:
* Задан пользователем при помощи графического интерфейса: нажатие кнопки `Draw Node` (см. эскиз) и последующий клик мыши по рабочей области создают в указанном месте вершину графа; нажатие кнопки `Draw Edge` и последующие клики по двум _различным_ вершинам создают ребро между этими вершинами графа. Попытка создать уже существующее ребро повторно не имеет эффекта. Нажатие кнопки `Erase` и клик мыши по компоненте (ребру или вершине графа) удаляют эту компоненту. Вершина удаляется со всеми инцидентными ей рёбрами.
* Задан пользователем в виде текстового файла, содержащего список рёбер графа. Каждая строка текстового файла должна иметь вид `<ключ 1> <ключ 2>`, где `<ключ 1>` и `<ключ 2>` — _различные_ символы-ключи вершин графа. Попытка описать уже существующее ребро повторно не имеет эффекта. При задании графа таким способом расположение узлов в рамках рабочей области определяется автоматически.
* Загружен из файла сохранения. Файл сохранения — файл, содержащий заголовок известного программе формата, после которого следуют: число — количество строк, описывающих вершины графа; указанное количество строчек, каждая из которых содержит ключ вершины (см. предыдущий пункт) и два числа — координаты вершины на рабочей области окна программы; список рёбер в том же формате, что и в предыдущем пункте.

В случае некорректных входных данных — например, нарушении формата файла или попытке описать петлю (ребро, соединяющее вершину графа саму с собой), — программа выдаёт информативное сообщение об ошибке, а не завершает работу.

### Требования к визуализации алгоритма
Визуализируемый алгоритм — [алгоритм Тарьяна](https://e-maxx.ru/algo/bridge_searching) поиска мостов в графе. Режим визуализации включается нажатием кнопки `Start` (см. эскиз). При этом кнопки добавления вершины и ребра в граф становятся неактивными, что делает невозможным изменение графа во время прогонки алгоритма. Кнопка `Stop` прекращает прогонку алгоритма и возвращает приложение в обычный режим работы. Визуализация алгоритма производится пошагово. Переход к визуализации следующего шага алгоритма осуществляется нажатием кнопки `Next`. 

Алгоритм содержит несколько этапов, каждый из которых будет визуализирован:
1. Первый этап — поиск в глубину и нумерация вершин. Вершина, из которой на данном шаге производится поиск, покрашена в яркий цвет; уже посещённые вершины покрашены в цвет, отличающийся от цвета ещё не посещённых вершин. Номера посещённых вершин отображаются рядом с вершинами. Пройденные рёбра отображаются как ориентированные. Количество детей вершины отображается рядом с вершиной, когда у неё не остаётся непосещённых соседей. Текстовое описание текущего шага (какое ребро выбрано для продолжения поиска, полученные на этом шаге численные значения) печатается в поле текстового вывода.
2. Второй этап — вычисление дополнительных коэффициентов (они обозначены в [статье](https://ru.wikipedia.org/wiki/%D0%9C%D0%BE%D1%81%D1%82_(%D1%82%D0%B5%D0%BE%D1%80%D0%B8%D1%8F_%D0%B3%D1%80%D0%B0%D1%84%D0%BE%D0%B2)#%D0%90%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC_%D0%BF%D0%BE%D0%B8%D1%81%D0%BA%D0%B0_%D0%BC%D0%BE%D1%81%D1%82%D0%BE%D0%B2_%D0%A2%D0%B0%D1%80%D1%8C%D1%8F%D0%BD%D0%B0) как _L(v)_ и _H(v)_). На каждом шаге этого этапа обрабатываемая вершина выделяется цветом, и рядом с ней отображаются вычисленные значения. В поле текстового вывода печатается краткое обоснование того, как был получен очередной коэффициент.
3. Третий этап — проверка рёбер. На каждом шаге данного этапа проверяемое ребро подсвечивается одним из двух цветов в зависимости от того, является ли оно мостом. В поле текстового вывода печатается, на основании чего алгоритмом было принято такое решение.

### Требования к пользовательскому интерфейсу
* Приложение должно иметь графический оконный интерфейс.
* Способы взаимодействия пользователя с графом посредством графического интерфейса описаны в разделе "Требования ко входным данным". 
* Загрузка графа из файла сохранения осуществляется путём нажатия кнопки `Open File` (см. эскиз) и выбора нужного файла в возникшем диалоге.
* Сохранение графа в файл осуществляется путём нажатия кнопки `Save File` и ввода имени файла в возникшем диалоге.

### Эскиз пользовательского интерфейса
![Эскиз](https://i.ibb.co/HGX5MZ2/interface-draft.png "Эскиз пользовательского интерфейса")

### Предполагаемая UML-диаграмма основных классов программы
![UML-диаграмма классов](https://i.ibb.co/6YmNygn/UML-draft.png "UML-диаграмма классов программы")

# План разработки и распределение ролей в бригаде

## План разработки
1. К 5 июля — прототип приложения, демонстрирующий внешний вид интерфейса, а также графическое отображение вершин и рёбер графа.
2. К 7 июля — первая версия. Ожидаемые изменения по сравнению с прототипом: считывание графа из файла, возможность запуска алгоритма и получения результата в текстовом (не графическом) виде.
3. К 9 июля — вторая версия. Ожидаемые изменения по сравнению с первой версией: визуализация результатов работы (подсветка мостов), подсветка компоненты графа, с которой происходит работа, вывод текстовых пояснений.
4. К 12 июля — отчёт по результатам практики и третья версия приложения. Ожидаемые изменения по сравнению со второй версией: печать числовых характеристик вершин в графическом поле и отрисовка орентированных ребёр на первом этапе работы алгоритма (см. требования к визуализации алгоритма), вывод информативных сообщений об ошибках.

## Распределение ролей
* Ахримов Антон — разработка графического интерфейса пользователя и графического представления графа;
* Максимов Евгений — разработка внутренней логики приложения и реализация алгоритма Тарьяна поиска мостов в графе.
* Эйсвальд Михаил — связь графических компонент и внутренней логики приложения; управление визуализацией алгоритма.
