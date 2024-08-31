package kr.folio.qr.presentation.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.folio.qr.infrastructure.annotation.CurrentUserId;
import kr.folio.qr.presentation.dto.request.ScanQrRequest;
import kr.folio.qr.presentation.dto.response.ScanQrResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "유저 API", description = "유저 정보를 다루는 API")
@Validated
@RequestMapping("/qrs")
public interface QrControllerDocs {

    @Operation(summary = "QR 코드 스캔", description = "네 컷 사진의 QR 코드를 스캔합니다.")
    @PostMapping("/scan")
    ResponseEntity<ScanQrResponse> scanQrUrl(
        @CurrentUserId String requestUserId,
        @RequestBody ScanQrRequest scanQrRequest
    );

}
