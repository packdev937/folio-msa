package kr.folio.qr.infrastructure.exception;

public class PhotoQrUrlExpiredException extends CustomException {
    public PhotoQrUrlExpiredException() {
        super(ErrorCode.PHOTO_QR_URL_EXPIRED);
    }
}
