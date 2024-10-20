package com.daejangjangi.backend.global.annotation;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
@ApiResponse(responseCode = "403", description = "권한 없음",
    content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = ApiGlobalResponse.class),
        examples =
        @ExampleObject(
            name = "FORBIDDEN",
            summary = "권한 없음",
            value = """
                {
                  "code": "FORBIDDEN",
                  "message": "리소스에 권한이 없습니다.",
                  "data": null
                }
                """
        )
    )
)
public @interface Response403WithSwagger {

}