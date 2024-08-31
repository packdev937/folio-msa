package kr.folio.qr.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.folio.qr.domain.core.entity.BrandType;

@Schema(description = "QR 스캔 응답")
public record ScanQrResponse(
    String photoImageUrl,
    BrandType brandType
) {

}
