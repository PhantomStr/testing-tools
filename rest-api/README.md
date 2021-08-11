# TESTING REST API

The module provide testing REST with Retrofit 2.

## Getting Started

**example:**

```java
// --------------------------------------------------------------------------------
// -------------------------------- config ----------------------------------------

public class TestConfig implements RestConfig {
    @Override
    public String getBaseUrl() {
        return AppConfig.getTectConfiguration("my.env.at.host");  
    }

    @Override
    public String getApiRoot() {
        return "/api/v1/";
    }
}

// --------------------------------------------------------------------------------
// -------------------------------- dto model -------------------------------------

@Data
@Builder
@JsonDeserialize(builder = InfoDto.InfoDtoBuilder.class)
public class InfoDto {
    String name;
    Integer height;
    Integer width;
}

// --------------------------------------------------------------------------------
// -------------------------------- service ---------------------------------------

public interface IconsService {
    @GET("emoticons/{filename}")
    Call<ResponseBody> getEmoticon(@Path("filename") String fileName); 

    @GET("emoticons/{filename}/info")
    Call<InfoDto> getIconInfo(@Path("filename") String fileName); 
}

// --------------------------------------------------------------------------------
// -------------------------------- test ------------------------------------------

public class TestClass {
    @Test
    public void test() {
        Rest config = new TestConfig();
        IconsService service = new ServiceFactory(config)
            .getService(IconsService.class);

        assertTrue(iconService
            .getEmoticon(fileName)
            .execute()
            .isSuccessful());     

        InfoDto info = iconService
            .getIconInfo(fileName)
            .execute()
            .body();
        
        assertThat(info)
            .hasName(fileName)
            .hasHeight(16)
            .hasWidth(16);
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
                <groupId>com.phantomstr.testing.tool</groupId>
                <artifactId>rest-api</artifactId>
                <version>1.2.0</version>
            </dependency>
        </dependencies>
    </project>
  ```

### Prerequisites

- Lombok plugin required File | Settings | Plugins | Marketplace | find "lombok" | install

- Annotation processing required (check option): <br>
  File | Settings | Build, Execution, Deployment | Compiler | Annotation Processors |Enable annotation processing

## Running the tests

- execute ```maven clean test``` or any latest goal

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.