package kr.folio.user.presentation.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record UpdateProfileImageRequest(
    MultipartFile profileImage
) {

}
