# swagger3快速开始

\# swagger介绍

对于 Rest API 来说很重要的一部分内容就是文档，Swagger 为我们提供了一套通过代码和注解自动生成文档的方法，这一点对于保证 API 文档的及时性将有很大的帮助。

Swagger 是一套基于 OpenAPI 规范（OpenAPI Specification，OAS）构建的开源工具，可以帮助我们设计、构建、记录以及使用 Rest API。

OAS本身是一个API规范，它用于描述一整套API接口，包括一个接口是哪种请求方式、哪些参数、哪些header等，都会被包括在这个文件中。它在设计的时候通常是YAML格式，这种格式书写起来比较方便，而在网络中传输时又会以json形式居多，因为json的通用性比较强。



![img](https://ask.qcloudimg.com/http-save/developer-news/jr5ahyy3cn.png?imageView2/2/w/1620)

Swagger 主要包含了以下三个部分：

Swagger Editor：基于浏览器的编辑器，我们可以使用它编写我们 OpenAPI 规范。

Swagger UI：它会将我们编写的 OpenAPI 规范呈现为交互式的 API 文档，后文我将使用浏览器来查看并且操作我们的 Rest API。

Swagger Codegen：它可以通过为 OpenAPI（以前称为 Swagger）规范定义的任何 API 生成服务器存根和客户端 SDK 来简化构建过程。

\# springfox介绍

由于Spring的流行，Marty Pitt编写了一个基于Spring的组件swagger-springmvc，用于将swagger集成到springmvc中来，而springfox则是从这个组件发展而来。

通常SpringBoot项目整合swagger需要用到两个依赖：springfox-swagger2和springfox-swagger-ui，用于自动生成swagger文档。

springfox-swagger2：这个组件的功能用于帮助我们自动生成描述API的json文件

springfox-swagger-ui：就是将描述API的json文件解析出来，用一种更友好的方式呈现出来。

\# SpringFox 3.0.0 发布

官方说明：

SpringFox 3.0.0 发布了，SpringFox 的前身是 swagger-springmvc，是一个开源的 API doc 框架，可以将 Controller 的方法以文档的形式展现。

新特性：

Remove explicit dependencies on springfox-swagger2

Remove any @EnableSwagger2… annotations

Add the springfox-boot-starter dependency

Springfox 3.x removes dependencies on guava and other 3rd party libraries (not zero dep yet! depends on spring plugin and open api libraries for annotations and models) so if you used guava predicates/functions those will need to transition to java 8 function interfaces.

此版本的亮点：

Spring5，Webflux支持（仅支持请求映射，尚不支持功能端点）。

Spring Integration支持（非常感谢反馈）。

SpringBoot支持springfox Boot starter依赖性（零配置、自动配置支持）。

具有自动完成功能的文档化配置属性。

更好的规范兼容性与2.0。

支持OpenApi 3.0.3。

零依赖。几乎只需要spring-plugin，swagger-core ，现有的swagger2注释将继续工作并丰富openapi3.0规范。

兼容性说明：

需要Java 8

需要Spring5.x（未在早期版本中测试）

需要SpringBoot 2.2+（未在早期版本中测试）

注意：

应用主类增加注解@EnableOpenApi，删除之前版本的SwaggerConfig.java。

\# 整合使用

Maven项目中引入springfox-boot-starter依赖：

application.yml配置

使用@EnableOpenApi注解，启用swagger配置

自定义swagger配置类SwaggerProperties

‍

一个完整详细的springfox swagger配置示例：

\# 一些常用注解说明

@Api：用在controller类，描述API接口

@ApiOperation：描述接口方法

@ApiModel：描述对象

@ApiModelProperty：描述对象属性

@ApiImplicitParams：描述接口参数

@ApiResponses：描述接口响应

@ApiIgnore：忽略接口方法

效果图：



![img](https://ask.qcloudimg.com/http-save/developer-news/1cx5nx3var.png?imageView2/2/w/1620)

\# 资料

swagger 官网：https://swagger.io

springfox 官网：http://springfox.github.io

springfox Github 仓库：springfox / springfox

springfox-demos Github 仓库：https://github.com/springfox/springfox