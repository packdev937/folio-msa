package kr.folio.qr.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.URL;

@Schema(description = "QR 스캔 요청")
public record ScanQrRequest(
    @URL
    @Schema(description = "QR URL", example = "qr_url")
    String qrUrl) {

}
