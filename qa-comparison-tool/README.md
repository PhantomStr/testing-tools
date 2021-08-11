# COMPARISON TOOL
Модуль позволяет сравнивать xml файлы и обрабатывать результаты несовпадений 

## Использование 
**Пример:**
```text
// --------------------------------------------------------------------------------
// -------------------------------- resources -------------------------------------
 resources
  └──reports                              
     ├──env-1 
     │  └──7761f76b-67ad-46f4-8826-c68fe991d1ae.xml       
     │      <prodect>
     │          <name>Tove</name>
     │          <updated>2020-04-22T21:42:12+03:00</updated>
     │      </prodect>
     └──env-2
        └──7761f76b-67ad-46f4-8826-c68fe991d1ae.xml
            <prodect>
                <name>Tove</name>
                <updated>2019-05-23T20:56:17+03:00</updated>
            </prodect>
```
Без рулов будет выбрасывать любые несовпадения в файлах
```java
// --------------------------------------------------------------------------------
// -------------------------------- rule ------------------------------------------
    public class ProjectUpdatedRule extends AbstractTemplateMatcher {
        public ProjectUpdatedRule() {
            super("^/prodect\\[\\d*]/updated\\[\\d*]/text\\(\\)\\[\\d*]$");
        }

        @Override
        protected AssertionError checkRule(Difference difference) {
            return defaultIfNull(checkFormat(difference, ISO_OFFSET_DATE_TIME), new AssertionError(difference.toString()));
        }

        private AssertionError checkFormat(Difference difference, DateTimeFormatter format) {
            try {
                OffsetDateTime.parse(difference.getComparison().getTestDetails().getValue().toString(), format);
            } catch (DateTimeParseException e) {
                return new AssertionError(e);
            }
            return null;
        }

    }

// --------------------------------------------------------------------------------
// -------------------------------- test ------------------------------------------

    public class TestClass {
        @Test(expectedExceptions = AssertionError.class)
        public void test() {
            String sourceEnv = "env-1";
            String targetEnv = "env-2";
            UUID runId = UUID.fromString("7761f76b-67ad-46f4-8826-c68fe991d1ae");
    
            ResourcesReportComparator.create()
                    .add(new ProjectUpdatedRule()) 
                    .compare(sourceEnv, targetEnv, runId)
                    .validate()
                    .handle(new CollectAndThrowExceptions());
        }
    
    }   
```
## Обработка исключений
Есть возможность реализовать функционал обработки ошибок имплементировав интерфейс RuleErrorHandler<R>
где R возвращаемый результат
```java
    public class CollectExceptionsInString implements RuleErrorHandler<String> {
        @Override
        public String handle(Collection<AssertionError> list) {
            return list.stream()
                    .map(AssertionError::getMessage)
                    .collect(Collectors.joining(","));
        }
    
    }
```

```java
    public class ComparisonExampleTest {
    
        @Test
        public void shouldCollectAndPrintExceptions() {
            String sourceEnv = "env-1";
            String targetEnv = "env-2";
            UUID runId = UUID.fromString("7761f76b-67ad-46f4-8826-c68fe991d1ae");
    
            String errors = comparator.compare(sourceEnv, targetEnv, runId)
                    .validate()
                    .handle(new CollectExceptionsInString());
    
            System.out.println(errors);
        }
    }
```
## Кастомный компаратор
Свой провайдер ресурсов:
```java
    public class CustomReportProvider implements ReportProvider {
        @Override
        public Report getReport(UUID runId, String env) {
            return Report.builder()
                    .id(runId)
                    .content(readFileContent(runId, env))
                    .build();
        }
    
        @SneakyThrows
        private String readFileContent(UUID id, String env) {
            // получение контента
        }
    }
```
Создание компаратора
```java
    public class CustomComparator extends AbstractComparator<CustomComparator> {
        private CustomComparator() {
            super();
        }
    
        public CustomComparator(ResourcesReportProvider reportProvider, DefaultValidationRules validationRules) {
            super(new CustomComparator(), reportProvider, validationRules);
        }
    
        public static CustomComparator create() {
            return new CustomComparator(new CustomReportProvider(), new DefaultValidationRules());
        }
    
    }
```
 
### Установка
  Для добавления библиотеки добавьте следующий блок в pom.xml:
  ```xml
    <project>
        <dependencies>
            <!-- Testing core -->
            <dependency>
                <groupId>com.phantomstr.testing.tool</groupId>
                <artifactId>qa-comparison-tool</artifactId>
                <version>1.2.0</version>
            </dependency>
        </dependencies>
    </project>
  ```

## Запуск тестов
  - выполнить ```maven clean test``` или любые последующие задачи (goal)   

## Сборка
* [Maven](https://maven.apache.org/) - Dependency Management

## Версионирование
Используется версионирование [SemVer](http://semver.org/).