package kr.folio.qr.infrastructure.adapter;

import kr.folio.qr.infrastructure.exception.PhotoQrUrlExpiredException;
import kr.folio.qr.domain.core.strategy.QrCodeProcessor;
import kr.folio.qr.infrastructure.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class PhotoSignatureQrCodeProcessor implements QrCodeProcessor {
    private final WebClient webClient;

    @Override
    public Mono<byte[]> extractImageFromQrUrl(String qrUrl) {
        String imageUrl = qrUrl.replace("index.html", "a.jpg");
        return WebClientUtil
                .getBlobByAnyMediaType(webClient, imageUrl)
                .onErrorMap(e -> new PhotoQrUrlExpiredException());
    }
}
