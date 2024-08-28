package kr.folio.user.infrastructure.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    // todo : 유저의 정보를 넘겨주어 정확히 어떤 에러인지 파악할 수 있도록 조치
    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
