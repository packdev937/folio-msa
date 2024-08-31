package kr.folio.qr.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.folio.qr.domain.core.entity.BrandType;

@Schema(name = "FileResponse", description = "QR 코드 스캔 파일 응답")
public record FileResponse(
    BrandType type,
    byte[] fileByte
) {

}
