package kr.folio.photo.domain.core.event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import kr.folio.photo.domain.core.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhotoCreatedExternalEvent implements PhotoExternalEvent {

    private final String eventType = "PhotoCreatedExternalEvent";
    private UUID sagaId;
    private Long photoId;
    private String photoImageUrl;
    private List<String> userIds;
    private LocalDateTime createdAt;


    public static PhotoCreatedExternalEvent of(Photo photo) {
        return new PhotoCreatedExternalEvent(
            UUID.randomUUID(),
            photo.getPhotoId(),
            photo.getPhotoImageUrl(),
            photo.getTaggedUserIds(),
            photo.getCreatedAt()
        );
    }
}
