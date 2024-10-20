package com.daejangjangi.backend.global.annotation;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@ApiResponse(responseCode = "401", description = "미인증",
    content = @Content(
        mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
        examples = {
            @ExampleObject(
                name = "NOT_AUTHENTICATED_ACCESS",
                summary = "인증되지 않은 접근",
                value = """
                    {
                      "code": "UNAUTHENTICATED",
                      "message": "로그인 후 이용 바랍니다.",
                      "data": null
                    }
                    """
            ),
            @ExampleObject(
                name = "EXPIRED_TOKEN",
                summary = "만료된 토큰",
                value = """
                    {
                      "code": "EXPIRED_TOKEN",
                      "message": "만료된 토큰입니다.",
                      "data": null
                    }
                    """
            ),
            @ExampleObject(
                name = "INVALID_JWT_SIGNATURE",
                summary = "유효하지 않은 서명",
                value = """
                    {
                      "code": "INVALID_JWT_SIGNATURE",
                      "message": "유효하지 않은 서명입니다.",
                      "data": null
                    }
                    """
            ),
            @ExampleObject(
                name = "UNAUTHENTICATED",
                summary = "인증되지 않음",
                value = """
                    {
                      "code": "UNAUTHENTICATED",
                      "message": "로그인 후 이용 바랍니다.",
                      "data": null
                    }
                    """
            ),
            @ExampleObject(
                name = "INVALID_TOKEN_ERROR",
                summary = "유효하지 않은 토큰",
                value = """
                    {
                      "code": "INVALID_TOKEN_ERROR",
                      "message": "유효하지 않는 토큰입니다.",
                      "data": null
                    }
                    """
            )
        }
    )
)
public @interface Response401WithSwagger {

}