package kr.folio.feed.application.ports.input;

import kr.folio.feed.domain.core.event.PhotoCreatedExternalEvent;

public interface PhotoCreatedMessageListener {

    void handleEvent(PhotoCreatedExternalEvent event);
}
