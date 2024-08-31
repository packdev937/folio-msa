package kr.folio.qr.infrastructure.adapter;

import kr.folio.qr.domain.core.strategy.QrCodeProcessor;
import kr.folio.qr.infrastructure.exception.PhotoQrUrlExpiredException;
import kr.folio.qr.infrastructure.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class LifeFourCutsQrCodeProcessor implements QrCodeProcessor {

    private final WebClient webClient;

    @Override
    public Mono<byte[]> extractImageFromQrUrl(String qrUrl) {
        return WebClientUtil.getRedirectUri(webClient, qrUrl)
            .flatMap(redirectUri -> {
	String imageUrl = redirectUri.split("path=")[1].replace("index.html", "image.jpg");

	return WebClientUtil.getBlob(webClient, imageUrl);
            })
            .onErrorMap(e -> new PhotoQrUrlExpiredException());
    }
}
