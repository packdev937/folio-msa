package kr.folio.photo.presentation.dto.response;

public record UpdatePhotoResponse(
    Long photoId,
    String updatedField,
    String message
) {

}
