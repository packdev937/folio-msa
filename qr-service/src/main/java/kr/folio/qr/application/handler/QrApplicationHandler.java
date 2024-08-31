package kr.folio.qr.application.handler;

import java.util.Optional;
import kr.folio.qr.domain.core.entity.BrandType;
import kr.folio.qr.domain.core.strategy.QrCodeProcessor;
import kr.folio.qr.infrastructure.adapter.QrCodeProcessorFactory;
import kr.folio.qr.infrastructure.exception.RedirectUriNotFoundException;
import kr.folio.qr.presentation.dto.request.ScanQrRequest;
import kr.folio.qr.presentation.dto.response.FileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Slf4j
@RequiredArgsConstructor
@Component
public class QrApplicationHandler {

    private final String UTC = "UTC";
    private final QrCodeProcessorFactory qrCodeProcessorFactory;

    public Mono<FileResponse> extractFileFromQrUrl(ScanQrRequest scanQrRequest) {
        BrandType brandType = Optional.ofNullable(BrandType.matchBrandType(scanQrRequest.qrUrl()))
            .orElseThrow(RedirectUriNotFoundException::new);

        QrCodeProcessor qrCodeProcessor = qrCodeProcessorFactory.getQrCodeProcessor(brandType);

        return createFileResponse(
            brandType,
            qrCodeProcessor.extractImageFromQrUrl(scanQrRequest.qrUrl())
        );
    }

    private Mono<FileResponse> createFileResponse(BrandType brandType, Mono<byte[]> fileMono) {
        return fileMono.map(file -> new FileResponse(brandType, file));
    }
}
