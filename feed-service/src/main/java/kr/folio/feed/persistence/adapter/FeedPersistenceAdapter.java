package kr.folio.feed.persistence.adapter;

import java.util.List;
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
    public Optional<Feed> findByFeedIdAndRequestUserId(Long feedId, String requestUserId) {
        return Optional.ofNullable(
            feedPersistenceMapper.toDomain(
	feedJpaRepository.findByFeedIdAndRequestUserId(feedId, requestUserId)
	    .orElse(null)
            )
        );
    }

    @Override
    public void deleteFeedById(Long photoId) {
        feedJpaRepository.deleteById(photoId);
    }

    @Override
    public Long findPhotoIdByFeedId(Long feedId) {
        return feedJpaRepository.findPhotoIdByFeedId(feedId);
    }

    @Override
    public int countFeedByPhotoId(Long photoId) {
        return feedJpaRepository.countFeedByPhotoId(photoId);
    }

    @Override
    public List<Feed> findFeedsByUserId(String requestUserId) {
        return feedPersistenceMapper.toDomainList(
            feedJpaRepository.findFeedsByUserId(requestUserId)
        );
    }

    @Override
    public Optional<Feed> findFeedById(Long feedId) {
        return Optional.ofNullable(
            feedPersistenceMapper.toDomain(
	feedJpaRepository.findById(feedId)
	    .orElse(null)
            )
        );
    }

    @Override
    public Optional<String> findUserIdByFeedId(Long feedId) {
        return Optional.ofNullable(
            feedJpaRepository.findUserIdByFeedId(feedId)
        );
    }

    @Override
    public List<Long> findFeedIdsByUserId(String userId) {
        return feedJpaRepository.findFeedIdsByUserId(userId);
    }
}
