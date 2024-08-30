package kr.folio.photo.presentation.dto.response;

/**
 * 포토 생성 응답
 *
 * @param photoId
 * @param message
 */
public record CreatePhotoResponse(
    Long photoId,
    String message
) {

}
