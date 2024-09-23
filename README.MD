Библиотека для конвертации Json в объект и объект в Json.

Основной класс Converter.

-метод - <T> T mappingJsonToDomain(String json, Class<T> clazz) - для конвертации строки в объект;

-метод - <T> T mappingJsonToDomain(String json, AbstractContainer<T> container) - для конвертации 
строки в коллекцию (). 

 Пример: 
List<Person> persons = converter.mappingJsonToDomain(json, new AbstractContainer<List<Person>(){});

Библиотека работает с коллекциями: List, Set, Map;


