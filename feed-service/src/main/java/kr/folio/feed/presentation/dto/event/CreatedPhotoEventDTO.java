package kr.folio.feed.presentation.dto.event;

import java.util.List;

public record CreatedPhotoEventDTO(
    Long photoId,
    String photoImageUrl,
    List<String> taggedUserIds
) {

}
