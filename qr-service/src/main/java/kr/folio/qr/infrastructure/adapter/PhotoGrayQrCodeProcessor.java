package kr.folio.qr.infrastructure.adapter;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import kr.folio.qr.infrastructure.exception.PhotoQrUrlExpiredException;
import kr.folio.qr.domain.core.strategy.QrCodeProcessor;
import kr.folio.qr.infrastructure.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class PhotoGrayQrCodeProcessor implements QrCodeProcessor {
    private final WebClient webClient;

    @Override
    public Mono<byte[]> extractImageFromQrUrl(String qrUrl) {
        return WebClientUtil // https://pgshort.aprd.io/{qr}
                .getRedirectUri(webClient, qrUrl) //https://photogray-download.aprd.io?id={base64}
                .flatMap((currentUrl) -> {
                    String encodedStr = extractIdFromUrl(currentUrl);
                    String decodedStr = new String(Base64.getDecoder().decode(encodedStr));
                    String sessionId = extractSessionIdFromQueryString(decodedStr);
                    String imageUrl = String.format("https://pg-qr-resource.aprd.io/%s/image.jpg", sessionId);
                    return WebClientUtil.getBlob(webClient, imageUrl);
                }) //https://pg-qr-resource.aprd.io/{sessionId}/image.jpg
                .onErrorMap(e -> new PhotoQrUrlExpiredException());
    }

    private String extractIdFromUrl(String url) {
        return UriComponentsBuilder
                .fromUriString(url)
                .build()
                .getQueryParams()
                .getFirst("id");
    }

    private String extractSessionIdFromQueryString(String queryString) {
        return URLEncodedUtils.parse(queryString, StandardCharsets.UTF_8)
                .stream()
                .filter(e -> e.getName().equals("sessionId"))
                .findFirst()
                .orElseThrow()
                .getValue();
    }
}
