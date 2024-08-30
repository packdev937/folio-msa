package kr.folio.qr.infrastructure.exception;

public class RedirectUriNotFoundException extends CustomException {
    public RedirectUriNotFoundException() {
        super(ErrorCode.REDIRECT_URI_NOT_FOUND);
    }
}
