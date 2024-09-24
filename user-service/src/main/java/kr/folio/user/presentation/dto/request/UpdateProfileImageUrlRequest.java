package kr.folio.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "프로필 이미지 변경 요청")
public record UpdateProfileImageUrlRequest(
    MultipartFile profileImageFile
) {

}
