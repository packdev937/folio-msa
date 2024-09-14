package kr.folio.feed.application.saga;

import java.util.List;
import kr.folio.feed.application.ports.input.FeedApplicationUseCase;
import kr.folio.feed.domain.core.event.PhotoCreatedExternalEvent;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.infrastructure.saga.SagaStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class FeedCreationSaga implements SagaStep<PhotoCreatedExternalEvent> {

    private final FeedApplicationUseCase feedApplicationUseCase;

    @Override
    public void process(PhotoCreatedExternalEvent event) {
        log.info("Processing Feed creation in SAGA for event: {} at FeedCreationSaga", event);

        if (event.getUserIds() != null) {
            event.getUserIds().forEach(userId -> {
                try {
                    List<String> taggedUserIds = event.getUserIds();
                    taggedUserIds.remove(userId);

                    feedApplicationUseCase.createFeed(
                        new CreateFeedRequest(
                            userId,
                            event.getAggregateId(),
                            event.getPhotoImageUrl(),
                            taggedUserIds
                        )
                    );
                } catch (Exception exception) {
                    log.error("Failed to create feed for user: {} at FeedCreationSaga", userId, exception);

                    throw new RuntimeException("피드 생성에 실패하였습니다. userId : " + userId, exception);
                }
            });
        } else {
            log.warn("태그된 유저가 존재하지 않습니다.");
        }
    }

    @Override
    public void rollback(PhotoCreatedExternalEvent event) {
        // 롤백 로직: 피드가 잘못 생성된 경우 삭제 등의 작업을 수행
        log.info("Rolling back feed creation for event: {}", event);
        // 피드 롤백 로직 구현
        // todo : 다시 이벤트를 발행해야 하나?
    }
}
