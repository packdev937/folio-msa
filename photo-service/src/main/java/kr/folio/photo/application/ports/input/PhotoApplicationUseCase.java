package kr.folio.photo.application.ports.input;

import jakarta.validation.Valid;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import kr.folio.photo.presentation.dto.response.CreatePhotoResponse;
import kr.folio.photo.presentation.dto.response.RetrievePhotoResponse;

public interface PhotoApplicationUseCase {

    CreatePhotoResponse createPhoto(@Valid CreatePhotoRequest createPhotoRequest);

    RetrievePhotoResponse retrievePhoto(String requestUserId, Long photoId);
}
