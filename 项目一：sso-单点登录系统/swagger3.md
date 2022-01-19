已剪辑自: https://blog.csdn.net/hcksust/article/details/109068483

# **swagger介绍**

[Swagger](https://so.csdn.net/so/search?q=Swagger&spm=1001.2101.3001.7020) 是一套基于 OpenAPI 规范（OpenAPI Specification，OAS）构建的开源工具，可以帮助我们设计、构建、记录以及使用 Rest API。

OAS本身是一个API规范，它用于描述一整套API接口，包括一个接口是哪种请求方式、哪些参数、哪些[header](https://so.csdn.net/so/search?q=header&spm=1001.2101.3001.7020)等，都会被包括在这个文件中。它在设计的时候通常是YAML格式，这种格式书写起来比较方便，而在网络中传输时又会以json形式居多，因为json的通用性比较强。

**Swagger 主要包含了以下三个部分：**

- Swagger Editor：基于浏览器的编辑器，我们可以使用它编写我们 OpenAPI 规范。
- Swagger UI：它会将我们编写的 OpenAPI 规范呈现为交互式的 API 文档，后文我将使用浏览器来查看并且操作我们的 Rest API。
- Swagger Codegen：它可以通过为 OpenAPI（以前称为 Swagger）规范定义的任何 API 生成服务器存根和客户端 SDK 来简化构建过程。

# **springfox介绍**

由于Spring的流行，Marty Pitt编写了一个基于Spring的组件swagger-springmvc，用于将swagger集成到springmvc中来，而springfox则是从这个组件发展而来。

通常SpringBoot项目整合swagger需要用到两个依赖：springfox-swagger2和springfox-swagger-ui，用于自动生成swagger文档。

springfox-swagger2：这个组件的功能用于帮助我们自动生成描述API的json文件 springfox-swagger-ui：就是将描述API的json文件解析出来，用一种更友好的方式呈现出来。

**SpringFox 3.0.0 发布**

**官方说明：**

SpringFox 3.0.0 发布了，SpringFox 的前身是 swagger-springmvc，是一个开源的 API doc 框架，可以将 Controller 的方法以文档的形式展现。

首先，非常感谢社区让我有动力参与这个项目。在这个版本中，在代码、注释、bug报告方面有一些非常惊人的贡献，看到人们在问题论坛上跳槽来解决问题，我感到很谦卑。它确实激励我克服“困难”，开始认真地工作。有什么更好的办法来摆脱科维德的忧郁！

注意：这是一个突破性的变更版本，我们已经尽可能地保持与springfox早期版本的向后兼容性。在2.9之前被弃用的api已经被积极地删除，并且标记了将在不久的将来消失的新api。所以请注意这些，并报告任何遗漏的内容。

# **基本配置**

Swagger3Config.java

```java
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
 
import java.util.Arrays;
import java.util.HashSet;
 
/**
 * swagger访问地址
 * http://localhost:8080/swagger-ui/index.html
 * 一些常用注解说明
 * @Api：用在controller类，描述API接口
 * @ApiOperation：描述接口方法
 * @ApiModel：描述对象
 * @ApiModelProperty：描述对象属性
 * @ApiImplicitParams：描述接口参数
 * @ApiResponses：描述接口响应
 * @ApiIgnore：忽略接口方法
 */
@RequiredArgsConstructor
@EnableOpenApi
@Configuration
public class Swagger3Config {
 
    private final SwaggerProperties swaggerProperties;
 
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)     //文档版本
                .pathMapping("/")
                .enable(swaggerProperties.getEnable())  //是否开启swagger
                .apiInfo(apiInfo())                     //将api的元信息设置为包含在json ResourceListing响应中。
                .select()                               //选择哪些接口作为swagger的doc发布
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .protocols(new HashSet<>(Arrays.asList("http", "https")))   //支持的通讯协议集合
                ;
    }
 
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getApplicationName())
                .description(swaggerProperties.getApplicationDescription())
                .version(swaggerProperties.getApplicationVersion())
                .build();
    }
 
}
```

application.[yaml](https://so.csdn.net/so/search?q=yaml&spm=1001.2101.3001.7020)增加配置

```
# Swagger配置
swagger:
  # 是否开启swagger
  enabled: true
  # 请求前缀
  pathMapping: /dev-api
  # 标题
  title: '标题：${ruoyi-vue-sso.name}后台管理系统_接口文档'
  # 描述
  description: '描述：用于管理集团旗下公司所有人员权限问题'
  # 版本
  version: '版本号: ${ruoyi-vue-sso.version}'
  # 作者信息
  contact:
    name: renq
    email: renqchina@163.com
    url: https://gitee.com/JavaLionLi/RuoYi-Vue-Plus
  groups:
    - name: 系统模块
      basePackage: oauthWeixin
```

附：pom依赖

```
<?xml version="1.0" encoding="UTF-8"?>



<project xmlns="http://maven.apache.org/POM/4.0.0"



         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"



         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">



    <modelVersion>4.0.0</modelVersion>



 



    <groupId>com.h</groupId>



    <artifactId>Swagger3</artifactId>



    <version>1.0-SNAPSHOT</version>



 



    <parent>



        <groupId>org.springframework.boot</groupId>



        <artifactId>spring-boot-starter-parent</artifactId>



        <version>2.3.1.RELEASE</version>



    </parent>



 



    <dependencies>



        <dependency>



            <groupId>org.springframework.boot</groupId>



            <artifactId>spring-boot-starter-web</artifactId>



        </dependency>



        <dependency>



            <groupId>io.springfox</groupId>



            <artifactId>springfox-boot-starter</artifactId>



            <version>3.0.0</version>



        </dependency>



        <dependency>



            <groupId>org.apache.commons</groupId>



            <artifactId>commons-lang3</artifactId>



            <version>3.11</version>



        </dependency>



        <dependency>



            <groupId>org.projectlombok</groupId>



            <artifactId>lombok</artifactId>



        </dependency>



    </dependencies>



</project>
```

 