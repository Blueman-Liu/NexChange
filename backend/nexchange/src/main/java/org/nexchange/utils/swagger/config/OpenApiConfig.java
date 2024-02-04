package org.nexchange.utils.swagger.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: knife4j配置类
 * swagger 接口文档默认地址：http://localhost:8080/swagger-ui.html#
 * Knife4j 接口文档默认地址：http://127.0.0.1:8080/doc.html
 **/
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                // 接口文档标题
                .info(new Info().title("NexChangeAPI接口文档")
                        // 接口文档简介
                        .description("这是基于Knife4j OpenApi3的接口文档")
                        // 接口文档版本
                        .version("v1.0")
                        // 开发者联系方式
                        .contact(new Contact().name("Felix").email("1483879133@qq.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot基础框架")
                        .url("http://127.0.0.1:8088"));
    }

    //以下分组可省略
//    @Bean
//    public GroupedOpenApi systemApi() {
//        return GroupedOpenApi.builder().group("System系统模块")
//                .pathsToMatch("/system/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi authApi() {
//        return GroupedOpenApi.builder().group("Auth权限模块")
//                .pathsToMatch("/captcha", "/login")
//                .build();
//    }
}
