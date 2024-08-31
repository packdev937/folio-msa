package kr.folio.photo.infrastructure.exception;

import lombok.Getter;

@Getter
public class PhotoNotFoundException extends CustomException {

    public PhotoNotFoundException() {
        super(ErrorCode.PHOTO_NOT_FOUND);
    }
}
