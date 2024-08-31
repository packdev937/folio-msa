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
public class HaruFilmQrCodeProcessor implements QrCodeProcessor {

    private final WebClient webClient;

    @Override
    public Mono<byte[]> extractImageFromQrUrl(String qrUrl) {
        String[] urlValueList = qrUrl.split("/@");
        String albumCode = urlValueList[1];

        String baseUrl = urlValueList[0] + "/base_api?command=albumdn&albumCode=";
        String imageUrl =
            baseUrl + albumCode + "&type=photo&file_name=output.jpg&max=10&limit=+24 hours";

        return WebClientUtil.getBlob(webClient, imageUrl)
            .onErrorMap(e -> new PhotoQrUrlExpiredException());
    }
}
