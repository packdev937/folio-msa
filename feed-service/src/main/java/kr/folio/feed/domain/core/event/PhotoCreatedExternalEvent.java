package kr.folio.feed.domain.core.event;

import java.time.ZonedDateTime;
import java.util.List;
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
}
