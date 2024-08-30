package kr.folio.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.URL;

@Schema(description = "프로필 이미지 URL 변경 요청")
public record UpdateProfileImageUrlRequest(
    @URL
    String profileImageUrl
) {

}
