package kr.folio.feed.application.service;

import kr.folio.feed.application.ports.input.PhotoCreatedMessageListener;
import kr.folio.feed.application.saga.FeedCreationSaga;
import kr.folio.feed.domain.core.event.PhotoCreatedExternalEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PhotoCreatedMessageProcessor implements PhotoCreatedMessageListener {

    private final FeedCreationSaga feedCreationSaga;

    @Override
    public void handleEvent(PhotoCreatedExternalEvent event) {
        log.info("Handling event for creating feeds: {} at PhotoCreatedMessageProcessor", event);

        try {
            feedCreationSaga.process(event);
        } catch (Exception exception) {
            log.error("Failed to process feed creation at PhotoCreatedMessageProcessor", exception);
            feedCreationSaga.rollback(event);
        }
    }
}

