package kr.folio.user.infrastructure.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("US0001", "사용자를 찾을 수 없습니다"),
    USER_ALREADY_EXISTS("US0002", "이미 존재하는 사용자입니다"),
    USER_INVALID_INPUT("US0003", "사용자 입력값이 올바르지 않습니다"),

    REQUEST_INPUT_NOT_VALID("EX0001", "입력 값이 올바르지 않습니다.");

    private final String code;
    private final String message;
}
