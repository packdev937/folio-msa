package kr.folio.photo.infrastructure.exception;

public class PhotoNotCreatedException extends CustomException{

    public PhotoNotCreatedException() {
        super(ErrorCode.PHOTO_NOT_CREATED);
    }
}
