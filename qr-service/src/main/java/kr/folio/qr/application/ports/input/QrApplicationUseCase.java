package kr.folio.qr.application.ports.input;

import kr.folio.qr.presentation.dto.request.ScanQrRequest;
import kr.folio.qr.presentation.dto.response.ScanQrResponse;

public interface QrApplicationUseCase {

    ScanQrResponse extractFileFromQrUrl(ScanQrRequest scanQrRequest);
}
