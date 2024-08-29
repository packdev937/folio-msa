package kr.folio.photo.presentation.dto.request;

public record UpdatePhotoImageRequest(
    Long photoId,
    String updatedPhotoUrl
    // todo : 태그된 유저, AccessLevel 도 수정 가능
) {

}
