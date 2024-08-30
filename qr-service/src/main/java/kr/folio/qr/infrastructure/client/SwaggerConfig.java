package kr.folio.qr.infrastructure.client;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "폴리오 백엔드 API 명세",
        version = "v1",
        description = "사용자 정보 관련 API 명세 목록입니다.",
        license = @License(name = "MIT License", url = "https://opensource.org/licenses/MIT"),
        contact = @io.swagger.v3.oas.annotations.info.Contact(
            name = "깃허브 주소",
            url = "https://github.com/packdev937/folio-jpa-server"
        )
    ),
    servers = {
        @Server(url = "https://folio.kr/", description = "프로덕션 서버 유저 서비스 URL")
    },
    security = @SecurityRequirement(name = "Authorization")
)
@SecuritySchemes({
    @SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "JWT Bearer Token"
    )
})
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .components(new Components());
    }
}