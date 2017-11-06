# FormalLangsHW

##### Запуск

`sbt "run grammarFile graphFile.dot outputFile"`

##### grammarFile

grammarFile: грамматика -- в формате из pdf-ки

##### формат автомата

Стартовая вершина автомата -- минимальная лексиографически

На каждом ребре автомата должна быть указана буква (например: `0 -> 1 [label="a"]`)

Для терманальности вершины нужно указать, что её форма doublecirle, например:

`3 [shape = "doublecircle"]`