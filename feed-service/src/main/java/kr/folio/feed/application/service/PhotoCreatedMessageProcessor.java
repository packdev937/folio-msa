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

// todo : 추후 이벤트는 Avro 데이터로 수신한 뒤 Message로 변환될거기 때문에 Message를 붙이는게 맞는듯
