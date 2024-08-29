package kr.folio.feed.persistence.adapter;

import java.util.Optional;
import kr.folio.feed.application.ports.output.FeedRepository;
import kr.folio.feed.domain.core.entity.Feed;
import kr.folio.feed.infrastructure.annotation.Adapter;
import kr.folio.feed.persistence.mapper.FeedPersistenceMapper;
import kr.folio.feed.persistence.repository.FeedJpaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
public class FeedPersistenceAdapter implements FeedRepository {

    private final FeedJpaRepository feedJpaRepository;
    private final FeedPersistenceMapper feedPersistenceMapper;

    @Override
    public Feed save(Feed feed) {
        return feedPersistenceMapper.toDomain(
            feedJpaRepository.save(
	feedPersistenceMapper.toEntity(feed)
            )
        );
    }

    @Override
    public Optional<Feed> findFeedById(Long feedId) {
        return Optional.ofNullable(
            feedPersistenceMapper.toDomain(
	feedJpaRepository.findById(feedId).orElse(null)
            )
        );
    }
}
