package kr.folio.feed.infrastructure.exception;

import lombok.Getter;

@Getter
public class FeedNotFoundException extends CustomException {

    public FeedNotFoundException() {
        super(ErrorCode.FEED_NOT_FOUND);
    }
}
