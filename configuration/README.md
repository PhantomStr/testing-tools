# TESTING CONFIGURATION

The module for work with configurations in project.  
structure of configuration files:
```text
  resources
  ├──conf                               // common configuration directory. Now NOT under config-scan.
  │  ├──env                             // environment configs root  
  │  │  ├──AT                           // environment name  
  │  │  │  └──application.properties    // high priority properties - for environment specific options
  │  │  └──application.properties       // medium priority properties - prefer for properties same in every environment
  │  └──application.properties          // lowest priority properties - not recommended to use because broke resource structure
  └──configuration.properties           // configuration for environment and root directory with configurations
 
```

this structure can be changed in configuration.properties file
```properties
 config.env.root=conf/env
 env.name=DEFAULT
```
or with java-options 
```properties
 -Dconfig.env.root=my/path -Denv.name=myEnvName
```
 
in example above all properties will be merged with matching priority

#read configuration example
```java
import org.apache.commons.configuration2.Configuration;

import static ConfigProvider.getConfiguration;
import static ConfigProvider.getEnvName;


public class ApplicationConfiguration {
    public static final String APPLICATION_PROPERTIES_FILE_NAME = "application.properties";

    public static Configuration getApplicationConfiguration() {
        return getApplicationConfiguration(getEnvName());
    }

    public static Configuration getApplicationConfiguration(String env) {
        return getConfiguration(env, APPLICATION_PROPERTIES_FILE_NAME);
    }
}


```

## Getting Started 
In common case can be used ConfigProvider class, but recommended create class for work with properties in your project
**example:**
```java
package com.phantomstr.myproject.utils.config;

import ConfigProvider;
import com.phantomstr.testing.tool.config.Credentials;
import com.phantomstr.testing.tool.utils.json.JsonUtils;
import lombok.experimental.UtilityClass;

import static com.phantomstr.myproject.step.db.entity.create.CreateUserStep.DEFAULT_USER_PASSWORD;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.stripEnd;
import static org.apache.commons.lang3.StringUtils.stripStart;

@UtilityClass
public class ConfigUtils extends ConfigProvider {
    public static String CREDENTIALS_CONFIG_NAME = "credentials.properties";

    public static Credentials getCredentialsConfig(String userOrMail) {
        String credentialsJsonString = getConfiguration(CREDENTIALS_CONFIG_NAME).getString(userOrMail);
        Credentials credentials = JsonUtils.tryParse(Credentials.class, credentialsJsonString);
        return defaultIfNull(credentials, getNewCredentials(userOrMail));
    }

    private static Credentials getNewCredentials(String mail) {
        Credentials credentials = new Credentials();
        credentials.setEmail(mail);
        credentials.setPassword(DEFAULT_USER_PASSWORD);
        return credentials;
    }

    public static String getAppUrl() {
        return getApplicationConfiguration().getString("app.url");
    }

    public static String getAppUrl(String subUrl) {
        String appUrl = getAppUrl();
        if (subUrl.startsWith(appUrl)) {
            return appUrl;
        }
        return stripEnd(appUrl, "/\\").concat("/").concat(stripStart(subUrl, "/\\"));
    }
}

```

 
### setup
  To include core as a dependency, add it to your <dependencies> block like so:
  ```xml
    <project>
        <dependencies>
            <!-- Testing core -->
            <dependency>
                <groupId>io.github.phantomstr.testing-tools</groupId>
                <artifactId>configuration</artifactId>
                <version>1.3.1</version>
            </dependency>
        </dependencies>
    </project>
  ```
    
### Prerequisites
- Lombok plugin required
File | Settings | Plugins | Marketplace | find "lombok" | install

- Annotation processing required (check option): <br>
File | Settings | Build, Execution, Deployment | Compiler | Annotation Processors |Enable annotation processing

## Running the tests  
  - execute ```maven clean test``` or any latest goal   

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.