package kr.folio.feed.common;

import kr.folio.feed.domain.core.entity.Feed;

public class FeedSteps {

    static public Feed 피드_생성() {
        return Feed.builder()
            .id(1L)
            .userId("packdev937")
            .photoId(1L)
            .imageUrl("https://www.naver.com")
            .build();
    }
}
