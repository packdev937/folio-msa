package kr.folio.photo.infrastructure.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    PHOTO_NOT_FOUND("PT0001", "포토를 찾을 수 없습니다"),
    PHOTO_NOT_CREATED("PT0002", "포토를 생성할 수 없습니다"),
    PHOTO_NOT_UPDATED("PT0003", "포토를 수정할 수 없습니다"),

    REQUEST_INPUT_NOT_VALID("EX0001", "입력 값이 올바르지 않습니다");

    private final String code;
    private final String message;
}
