package kr.folio.feed.application.service;

import java.util.List;
import kr.folio.common.domain.core.event.user.UserDeletedExternalEvent;
import kr.folio.feed.application.ports.input.FeedApplicationUseCase;
import kr.folio.feed.application.ports.input.UserDeletedMessageListener;
import kr.folio.feed.domain.core.event.DeleteUserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDeletedMessageProcessor implements UserDeletedMessageListener {

    private final FeedApplicationUseCase feedApplicationUseCase;

    // todo : 나중에 리팩토링
    @Override
    public void handleEvent(UserDeletedExternalEvent userDeletedExternalEvent) {
        log.info("Handling event to delete feeds by DeleteUserEvent: {}", userDeletedExternalEvent);
        List<Long> feedIds = feedApplicationUseCase.retrieveFeedIdsByUserId(
            userDeletedExternalEvent.getUserId());
        try {
            feedIds.forEach(feedId -> {
	try {
	    feedApplicationUseCase.deleteFeed(feedId);
	} catch (Exception exception) {
	    log.error("Failed to delete feed for feedId: {}", feedId, exception);
	}
            });
        } catch (Exception exception) {
            log.error("Failed to delete feeds for userId : {}", userDeletedExternalEvent.getUserId(), exception);
        }
    }
}
