package kr.folio.photo.application.ports.input;

import jakarta.validation.Valid;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import kr.folio.photo.presentation.dto.request.UpdatePhotoImageRequest;
import kr.folio.photo.presentation.dto.response.CreatePhotoResponse;
import kr.folio.photo.presentation.dto.response.DeletePhotoResponse;
import kr.folio.photo.presentation.dto.response.RetrievePhotoResponse;
import kr.folio.photo.presentation.dto.response.TrendResponse;
import kr.folio.photo.presentation.dto.response.UpdatePhotoResponse;

public interface PhotoApplicationUseCase {

    CreatePhotoResponse createPhoto(@Valid CreatePhotoRequest createPhotoRequest);

    RetrievePhotoResponse retrievePhoto(String requestUserId, Long photoId);

    UpdatePhotoResponse updatePhotoImage(String requestUserId, UpdatePhotoImageRequest updatePhotoRequest);

    DeletePhotoResponse deletePhoto(Long photoId);

    TrendResponse retrievePhotoTrend(String requestUserId);
}
