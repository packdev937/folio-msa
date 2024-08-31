package kr.folio.qr.application.service;

import kr.folio.qr.application.handler.ObjectStorageHandler;
import kr.folio.qr.application.handler.QrApplicationHandler;
import kr.folio.qr.application.ports.input.QrApplicationUseCase;
import kr.folio.qr.infrastructure.annotation.ApplicationService;
import kr.folio.qr.presentation.dto.request.ScanQrRequest;
import kr.folio.qr.presentation.dto.response.FileResponse;
import kr.folio.qr.presentation.dto.response.ScanQrResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
public class QrApplicationService implements QrApplicationUseCase {

    private final QrApplicationHandler qrApplicationHandler;
    private final ObjectStorageHandler objectStorageHandler;

    @Override
    public ScanQrResponse extractFileFromQrUrl(ScanQrRequest scanQrRequest) {
        Mono<FileResponse> fileResponse = qrApplicationHandler.extractFileFromQrUrl(
            scanQrRequest);

        Mono<String> storedImageUrl = objectStorageHandler.uploadFile(
            fileResponse.block().fileByte());

        return new ScanQrResponse(storedImageUrl.block(), fileResponse.block().type());
    }
}