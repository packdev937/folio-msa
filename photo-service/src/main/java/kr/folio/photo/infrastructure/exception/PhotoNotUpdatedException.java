package kr.folio.photo.infrastructure.exception;

public class PhotoNotUpdatedException extends CustomException{

    public PhotoNotUpdatedException() {
        super(ErrorCode.PHOTO_NOT_UPDATED);
    }
}
