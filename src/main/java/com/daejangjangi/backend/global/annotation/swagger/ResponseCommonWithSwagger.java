package com.daejangjangi.backend.global.annotation.swagger;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented

@ApiResponses(value = {
    @ApiResponse(responseCode = "500", description = "서버 내부 오류",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiGlobalResponse.class),
            examples = @ExampleObject(
                name = "INTERNAL_SERVER_ERROR",
                summary = "서버 내부 오류",
                value = """
                    {
                      "code": "INTERNAL_SERVER_ERROR",
                      "message": "서버 내부 오류 입니다.",
                      "data": null
                    }"""
            )
        )
    )
})
public @interface ResponseCommonWithSwagger {

}
