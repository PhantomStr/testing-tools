# Release Notes

# 1.3.2

## Features

| `Description`               |
|-----------------------------|
| Dependency versions updated |

## Bug fix

|                               `Description`                               |
| ------------------------------------------------------------------------- |
|                                                                           |

# Release Notes

# 1.3.1 - 20.09.2021

## Features

|                               `Description`                               |
| ------------------------------------------------------------------------- |
| renamed package by convention                                             |
| deploy into maven preparing                                               |

## Bug fix

|                               `Description`                               |
| ------------------------------------------------------------------------- |
|                                                                           |

# 1.2.0 - 11.08.2021
## Features
| `Description`                                                  |
|----------------------------------------------------------------|
| Generic configuration for ConverterFactoryManager              |
| Resources searching by name                                    |
| New parameter INDENT_OUTPUT in ConverterConfig                 |
| ResponseUtils are added                                        |
| New parameter ACCEPT_CASE_INSENSITIVE_ENUMS in ConverterConfig |
| DefaultRequestInterceptors updated.                            |
| Request/response logger for allure report                      |
| New interceptor AddHeaderInterceptor                           |
| New module qa-comparison-tool                                  |

## Bug fix
| `Description`                                                         |
|-----------------------------------------------------------------------|
| Fix JsonUtils package name                                            |
| fix WARN in ResponseUtils "IllegalArgumentException: charset == null" |
| fixed  multipart response logging                                     |
| jdk libs version conflict resolved                                    |

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
