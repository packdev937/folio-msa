package kr.folio.qr.presentation.rest;

import kr.folio.qr.application.ports.input.QrApplicationUseCase;
import kr.folio.qr.presentation.dto.request.ScanQrRequest;
import kr.folio.qr.presentation.dto.response.ScanQrResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class QrController implements QrControllerDocs {

    private final QrApplicationUseCase qrApplicationUseCase;

    @GetMapping("/health_check")
    public String healthCheck() {
        return "Welcome to qr service";
    }

    @Override
    public ResponseEntity<ScanQrResponse> scanQrUrl(String requestUserId,
        ScanQrRequest scanQrRequest) {
        return new ResponseEntity<>(
            qrApplicationUseCase.extractFileFromQrUrl(scanQrRequest)
            , HttpStatus.OK
        );
    }
}
