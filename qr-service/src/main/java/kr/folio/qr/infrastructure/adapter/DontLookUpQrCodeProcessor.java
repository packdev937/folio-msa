package kr.folio.qr.infrastructure.adapter;

import kr.folio.qr.infrastructure.exception.PhotoQrUrlExpiredException;
import kr.folio.qr.infrastructure.exception.RedirectUriNotFoundException;
import kr.folio.qr.domain.core.strategy.QrCodeProcessor;
import kr.folio.qr.infrastructure.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class DontLookUpQrCodeProcessor implements QrCodeProcessor {

    private final WebClient webClient;

    @Override
    public Mono<byte[]> extractImageFromQrUrl(String qrUrl) {
        String postfix = qrUrl.split(".kr/image/")[1];

        String baseUrl = "https://x.dontlxxkup.kr/uploads/";
        String imageUrl = baseUrl + postfix;

        return WebClientUtil.getRedirectUri(webClient, qrUrl)
            .flatMap(redirectUri -> {
	if (redirectUri.endsWith("/delete")) {
	    return Mono.error(new PhotoQrUrlExpiredException());
	} else {
	    return WebClientUtil.getBlob(webClient, imageUrl);
	}
            })
            .onErrorResume(
	RedirectUriNotFoundException.class, e -> WebClientUtil.getBlob(webClient, imageUrl)
            );
    }
}
