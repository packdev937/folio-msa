package kr.folio.user.domain.core.exception;

public class UserAlreadyExistsException extends UserDomainException {

    private final static String ERROR_MESSAGE = "해당 {}({})로 생성된 유저가 이미 존재합니다.";

    public UserAlreadyExistsException(String type, String value) {
        super(String.format(ERROR_MESSAGE, type, value));
    }
}
