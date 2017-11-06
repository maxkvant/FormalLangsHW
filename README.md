# FormalLangsHW

##### Запуск

`sbt "run grammarFile graphFile outputFile"`

##### grammarFile

grammarFile: грамматика -- в формате из pdf-ки

##### graphFile

graphFile: автомат в формате dot

Стартовая вершина автомата -- минимальная лексикографически.

На каждом ребре автомата должна быть указана буква (например: `0 -> 1 [label="a"]`).

Для терминальности вершины нужно указать, что её форма doublecirle (например: `3 [shape = "doublecircle"]`).
