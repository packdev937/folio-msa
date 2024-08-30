package kr.folio.feed.infrastructure.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    FEED_NOT_FOUND("FD0001", "피드를 찾을 수 없습니다");

    private final String code;
    private final String message;
}
