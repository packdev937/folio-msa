package kr.folio.user.infrastructure.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends CustomException {

    public UserAlreadyExistsException() {
        super(ErrorCode.USER_ALREADY_EXISTS);
    }
}
