package com.kkorchyts.config.swagger;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String swaggerBasePackage;

    private static class SwaggerPageable {

        @ApiParam(value = "Number of records per page", example = "0")
        @Nullable
        private Integer size;

        @ApiParam(value = "Results page you want to retrieve (0..N)", example = "0")
        @Nullable
        private Integer page;

/*        @ApiParam(value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.")
        @Nullable
        private String sort;*/

        public Integer getSize() {
            return size;
        }

        public Integer getPage() {
            return page;
        }

/*        public String getSort() {
            return sort;
        }*/
    }

    //https://springfox.github.io/springfox/docs/current/#quick-start-guides
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                // select() returns an instance of ApiSelectorBuilder to give fine grained control over the endpoints exposed via swagger.
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerBasePackage))
                //.paths(PathSelectors.any())
                .build()
                .directModelSubstitute(Pageable.class, SwaggerPageable.class)
                .apiInfo(apiInfo())
                .securitySchemes(Collections.singletonList(apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("E-scooter v.1")
                .description("E-scooter rental service.")
                .version("1")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("jwtToken", "Authorization", "header");
    }


    @Value("${swagger.base.package}")
    public void setSwaggerBasePackage(String swaggerBasePackage) {
        this.swaggerBasePackage = swaggerBasePackage;
    }
}
