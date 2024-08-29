package kr.folio.feed.application.mapper;

import kr.folio.feed.domain.core.entity.AccessRange;
import kr.folio.feed.domain.core.entity.Feed;
import kr.folio.feed.persistence.entity.FeedEntity;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.response.RetrieveFeedResponse;
import org.springframework.stereotype.Component;

@Component
public class FeedDataMapper {

    public Feed toDomain(CreateFeedRequest createPhotoRequest) {
        return Feed.builder()
            .userId(createPhotoRequest.userId())
            .photoId(createPhotoRequest.photoId())
            .accessRange(AccessRange.PUBLIC) // Default 값
            .build();
    }

    public RetrieveFeedResponse toRetrieveFeedResponse(Feed feed) {
        return RetrieveFeedResponse.builder()
            .id(feed.getId())
            .userId(feed.getUserId())
            .photoId(feed.getPhotoId())
            .accessRange(feed.getAccessRange())
            .taggedUsers(feed.getTaggedUsers())
            .createdAt(feed.getCreatedAt())
            .build();
    }
}
