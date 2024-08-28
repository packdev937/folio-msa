package kr.folio.photo.application.ports.input;

import jakarta.validation.Valid;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import kr.folio.photo.presentation.dto.response.CreatePhotoResponse;

public interface PhotoApplicationUseCase {

    CreatePhotoResponse createPhoto(@Valid CreatePhotoRequest createPhotoRequest);
}
