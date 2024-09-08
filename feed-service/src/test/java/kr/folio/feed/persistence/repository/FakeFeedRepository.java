package kr.folio.feed.persistence.repository;

import java.util.*;
import kr.folio.feed.application.ports.output.FeedRepository;
import kr.folio.feed.domain.core.entity.Feed;

public class FakeFeedRepository implements FeedRepository {

    private Map<Long, Feed> feedStorage = new HashMap<>();
    private Map<Long, String> userFeedMap = new HashMap<>();
    private long currentFeedId = 1L;

    @Override
    public Feed save(Feed feed) {
        if (feed.getId() == null) {
            feed.setId(currentFeedId++);
        }
        feedStorage.put(feed.getId(), feed);
        userFeedMap.put(feed.getId(), feed.getUserId());
        return feed;
    }

    @Override
    public Optional<Feed> findByFeedIdAndRequestUserId(Long feedId, String requestUserId) {
        return Optional.ofNullable(feedStorage.get(feedId))
            .filter(feed -> feed.getUserId().equals(requestUserId));
    }

    @Override
    public void deleteFeedById(Long feedId) {
        feedStorage.remove(feedId);
        userFeedMap.remove(feedId);
    }

    @Override
    public Long findPhotoIdByFeedId(Long feedId) {
        Feed feed = feedStorage.get(feedId);
        return feed != null ? feed.getPhotoId() : null;
    }

    @Override
    public int countFeedByPhotoId(Long photoId) {
        return (int) feedStorage.values().stream()
            .filter(feed -> Objects.equals(feed.getPhotoId(), photoId))
            .count();
    }

    @Override
    public List<Feed> findFeedsByUserId(String requestUserId) {
        List<Feed> userFeeds = new ArrayList<>();
        for (Feed feed : feedStorage.values()) {
            if (feed.getUserId().equals(requestUserId)) {
	userFeeds.add(feed);
            }
        }
        return userFeeds;
    }

    @Override
    public Optional<Feed> findFeedById(Long feedId) {
        return Optional.ofNullable(feedStorage.get(feedId));
    }

    @Override
    public Optional<String> findUserIdByFeedId(Long feedId) {
        return Optional.ofNullable(userFeedMap.get(feedId));
    }

    @Override
    public List<Long> findFeedIdsByUserId(String userId) {
        List<Long> feedIds = new ArrayList<>();
        for (Map.Entry<Long, String> entry : userFeedMap.entrySet()) {
            if (entry.getValue().equals(userId)) {
	feedIds.add(entry.getKey());
            }
        }
        return feedIds;
    }
}
