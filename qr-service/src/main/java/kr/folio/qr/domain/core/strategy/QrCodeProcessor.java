package kr.folio.qr.domain.core.strategy;

import reactor.core.publisher.Mono;

public interface QrCodeProcessor {

    Mono<byte[]> extractImageFromQrUrl(String qrUrl);
}
