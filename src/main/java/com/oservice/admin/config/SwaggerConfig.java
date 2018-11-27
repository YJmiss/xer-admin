package com.oservice.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    /*
    * 手动配置@EnableMvc默认的静态资源路径
    * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        // 注:如果是Linux的话，直接指定文件夹路径即可，不需要指定哪个盘(Linux就一个可用盘)
        registry.addResourceHandler("/fileData/**").addResourceLocations("file:E:/fileData/");

    }



    @Bean
    public Docket createRestApi() {

        //添加head参数start
       /* ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add((Parameter) tokenPar.build());*/

        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            //加了ApiOperation注解的类，才生成接口文档
        //   .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            //包下的类，才生成接口文档
            .apis(RequestHandlerSelectors.basePackage("com.oservice.admin.modules"))
            .paths(PathSelectors.any())
            .build().securitySchemes(security());

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("疆恒科技学而用接口文档")
            .description("学而用接口文档")
            .termsOfServiceUrl("http://www.oservice.net")
                //.contact("YJmiss")
            .version("2.0.0")
            .build();
    }

    private List<ApiKey> security() {
        return newArrayList(
            new ApiKey("token", "token", "header")
        );
    }

}