package kr.folio.feed.application.mapper;

import java.util.List;
import kr.folio.feed.domain.core.vo.AccessRange;
import kr.folio.feed.domain.core.entity.Feed;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.response.CreateFeedResponse;
import kr.folio.feed.presentation.dto.response.FeedResponse;
import kr.folio.feed.presentation.dto.response.FeedsResponse;
import kr.folio.feed.presentation.dto.response.RetrieveFeedDetailResponse;
import org.springframework.stereotype.Component;

@Component
public class FeedDataMapper {

    public Feed toDomain(CreateFeedRequest createPhotoRequest) {
        return Feed.builder()
            .userId(createPhotoRequest.userId())
            .photoId(createPhotoRequest.photoId())
            .photoImageUrl(createPhotoRequest.photoImageUrl())
            .taggedUsers(createPhotoRequest.taggedUserIds())
            .accessRange(AccessRange.PUBLIC)
            .build();
    }

    public RetrieveFeedDetailResponse toRetrieveFeedDetailResponse(Feed feed) {
        return RetrieveFeedDetailResponse.builder()
            .feedId(feed.getId())
            .photoImageUrl(feed.getPhotoImageUrl())
            .accessRange(feed.getAccessRange())
            .taggedUsers(feed.getTaggedUsers())
            .createdAt(feed.getCreatedAt())
            .build();
    }

    public RetrieveFeedDetailResponse toRetrieveNonAuthorizedFeedDetailResponse(Feed feed) {
        return RetrieveFeedDetailResponse.builder()
            .feedId(feed.getId())
            .photoImageUrl(feed.getPhotoImageUrl())
            .taggedUsers(feed.getTaggedUsers())
            .accessRange(null)
            .createdAt(feed.getCreatedAt())
            .build();
    }

    public FeedsResponse toFeedsResponse(List<Feed> feeds) {
        return new FeedsResponse(feeds.stream()
            .map(this::toFeedResponse)
            .toList());
    }

    public FeedResponse toFeedResponse(Feed feed) {
        return new FeedResponse(feed.getId(), feed.getPhotoImageUrl());
    }

    public CreateFeedResponse toCreateResponse(Feed savedFeed) {
        return new CreateFeedResponse(savedFeed.getId(), "피드가 성공적으로 생성되었습니다.");
    }
}
