package kr.folio.photo.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import kr.folio.photo.domain.core.vo.BrandType;
import org.hibernate.validator.constraints.URL;

/**
 * 포토 생성 요청 합니다. Qr-Service에서 QR Code 스캔 후 생성 요청을 보냅니다.
 * @param requestUserId
 * @param brandType
 * @param photoImageUrl
 * @param userIds
 */
@Schema(description = "포토 생성 요청")
public record CreatePhotoRequest(
    @NotNull
    Long requestUserId,
    @NotNull
    BrandType brandType,
    @URL
    String photoImageUrl,
    @NotNull
    ArrayList<String> userIds
) {

}
