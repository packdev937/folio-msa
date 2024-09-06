package kr.folio.feed.presentation.dto.event;

import java.util.List;

public record CreatePhotoEventDTO(
    Long photoId,
    String photoImageUrl,
    List<String> taggedUserIds
) {

}
