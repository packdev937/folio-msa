package kr.folio.photo.domain.core.event;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import kr.folio.photo.domain.core.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhotoCreatedExternalEvent implements PhotoExternalEvent {

    private final String eventType = "PhotoCreatedExternalEvent";
    private Long aggregateId;
    private String photoImageUrl;
    private List<String> userIds;
    private ZonedDateTime timeStamp;


    public static PhotoCreatedExternalEvent of(Photo photo) {
        return new PhotoCreatedExternalEvent(
            photo.getPhotoId(),
            photo.getPhotoImageUrl(),
            photo.getTaggedUserIds(),
            ZonedDateTime.now(ZoneId.of("UTC")));
    }
}
