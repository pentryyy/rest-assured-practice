# Тест-кейсы для YouTrack


Для очистки директории `target`

```
mvn clean
```

## Страница Projects

Запуск тест-сьюта

```
mvn test -Dtest=ProjectsTest
```

## Страница Issues 

Запуск тест-сьюта

```
mvn test -Dtest=IssuesTest  
```

Пример запуска Allure

```
allure serve target/allure-results
```
