package kr.folio.user.infrastructure.config;

import kr.folio.user.infrastructure.resolver.UserIdParameterResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        configurer.addCustomResolver(new UserIdParameterResolver());
    }

    @Bean("externalWebClient")
    public WebClient externalServiceWebClient() {
        return WebClient.builder()
            .codecs(clientCodecConfigurer -> {
	clientCodecConfigurer
	    .defaultCodecs()
	    // Maximum in-memory size for the WebClient
	    .maxInMemorySize(16 * 1024 * 1024);
            })
            .build();
    }
}
