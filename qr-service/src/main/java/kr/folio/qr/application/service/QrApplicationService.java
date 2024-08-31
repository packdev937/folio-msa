package kr.folio.qr.application.service;

import kr.folio.qr.application.handler.ObjectStorageHandler;
import kr.folio.qr.application.handler.QrApplicationHandler;
import kr.folio.qr.application.ports.input.QrApplicationUseCase;
import kr.folio.qr.infrastructure.annotation.ApplicationService;
import kr.folio.qr.presentation.dto.request.ScanQrRequest;
import kr.folio.qr.presentation.dto.response.ScanQrResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
public class QrApplicationService implements QrApplicationUseCase {

    private final QrApplicationHandler qrApplicationHandler;
    private final ObjectStorageHandler objectStorageHandler;

    @Override
    public ScanQrResponse extractFileFromQrUrl(ScanQrRequest scanQrRequest) {
        String extractedImageUrl = qrApplicationHandler
            .extractFileFromQrUrl(scanQrRequest)
            .flatMap(fileResponse ->
	objectStorageHandler.uploadFile(fileResponse.fileByte())
            ).block();

        return new ScanQrResponse(extractedImageUrl);
    }
}