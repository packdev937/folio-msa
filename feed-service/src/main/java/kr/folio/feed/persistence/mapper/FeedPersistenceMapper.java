package kr.folio.feed.persistence.mapper;

import java.util.List;
import java.util.stream.Collectors;
import kr.folio.feed.domain.core.entity.Feed;
import kr.folio.feed.persistence.entity.FeedEntity;
import org.springframework.stereotype.Component;

@Component
public class FeedPersistenceMapper {

    public Feed toDomain(FeedEntity feedEntity) {
        return Feed.builder()
            .id(feedEntity.getId())
            .userId(feedEntity.getUserId())
            .photoId(feedEntity.getPhotoId())
            .accessRange(feedEntity.getAccessRange())
            .build();
    }

    public FeedEntity toEntity(Feed feed) {
        return FeedEntity.builder()
            .id(feed.getId())
            .userId(feed.getUserId())
            .photoId(feed.getPhotoId())
            .build();
    }

    public List<Feed> toDomainList(List<FeedEntity> feedsByUserId) {
        return feedsByUserId.stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }
}
