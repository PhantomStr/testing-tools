# Release Notes

# 1.3.1 - 20.09.2021

## Features

|                               `Description`                               |
| ------------------------------------------------------------------------- |
| renamed package by convention                                               |
| deploy into maven preparing                                               |

## Bug fix

|                               `Description`                               |
| ------------------------------------------------------------------------- |
|                                                                           |

# 1.2.0 - 11.08.2021
## Features
|                               `Description`                               |
| ------------------------------------------------------------------------- |
| Generic конфигурация для ConverterFactoryManager                          |
| Добавлен поиск ресурсов по имени                                          |
| Добавлен параметр INDENT_OUTPUT в ConverterConfig                         |
| Добавлен класс ResponseUtils                                              |
| Добавлен параметр ACCEPT_CASE_INSENSITIVE_ENUMS в ConverterConfig         |
| Переделан DefaultRequestInterceptors.                                     |
| Добален логгер запросов в аллюр отчёт                                     |
| Добален интерсептор AddHeaderInterceptor для добавления хедера к запросу  |
| Добавлен новый модуль qa-comparison-tool                                  |

## Bug fix
|                               `Description`                               |
| ------------------------------------------------------------------------- |
| Fix JsonUtils package name                                                |
| fix WARN in ResponseUtils "IllegalArgumentException: charset == null"     |
| исправлено логгирование мультипарт респонза                               |
| Конфликт версий jdk для библиотек                                         |

# 1.1.0 - 13.01.2020
## Features
|                               `Description`                               |
| ------------------------------------------------------------------------- |
| Created configuration and rest-api modules                                |

## Bug fix
|                               `Description`                               |
| ------------------------------------------------------------------------- |
| Fix ConcurrentException on configuration loading                          |

## Technical improvement
 - [SemVer](http://semver.org/) for versioning.
 - changes in ServiceFactory

## Breaking changes and required actions
___
#### change in constructor signature:
 old: ``` public ServiceFactory(RestConfig restConfig, Factory factory, RequestInterceptors interceptors)```
 
 new: ``` public ServiceFactory(RestConfig restConfig, ConverterFactoryManager factoryManager, RequestInterceptors interceptors)```
##### required actions
 - to use ``` ServiceFactory(RestConfig restConfig, RequestInterceptors interceptors)``` that involve *DefaultConverterFactoryManager* instead of Factory
 - if need own ConverterFactory class you must implement *ConverterFactoryManager* interface; 
  
# 1.0 - 20.12.2019
### Technical improvement
  - initial commit
