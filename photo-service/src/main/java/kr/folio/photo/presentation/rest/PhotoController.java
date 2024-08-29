package kr.folio.photo.presentation.rest;

import kr.folio.photo.application.ports.input.PhotoApplicationUseCase;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import kr.folio.photo.presentation.dto.request.UpdatePhotoImageRequest;
import kr.folio.photo.presentation.dto.response.CreatePhotoResponse;
import kr.folio.photo.presentation.dto.response.DeletePhotoResponse;
import kr.folio.photo.presentation.dto.response.RetrievePhotoResponse;
import kr.folio.photo.presentation.dto.response.TrendResponse;
import kr.folio.photo.presentation.dto.response.UpdatePhotoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PhotoController implements PhotoControllerDocs {

    private final PhotoApplicationUseCase photoApplicationUseCase;

    @GetMapping("/health_check")
    public String welcome() {
        return "Welcome to photo service";
    }

    @Override
    public ResponseEntity<CreatePhotoResponse> createPhoto(
        CreatePhotoRequest request) {
        return new ResponseEntity<>(
            photoApplicationUseCase.createPhoto(request)
            , HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<RetrievePhotoResponse> retrievePhoto(String requestUserId, Long photoId) {
        return new ResponseEntity<>(
            photoApplicationUseCase.retrievePhoto(requestUserId, photoId),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<UpdatePhotoResponse> updatePhotoImage(String requestUserId,
        UpdatePhotoImageRequest updatePhotoImageRequest) {
        return new ResponseEntity<>(
            photoApplicationUseCase.updatePhotoImage(requestUserId, updatePhotoImageRequest),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DeletePhotoResponse> deletePhoto(Long photoId) {
        return new ResponseEntity<>(
            photoApplicationUseCase.deletePhoto(photoId),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<TrendResponse> retrieveRecommendationPhoto(String requestUserId) {
        // todo : User-Service에 요청해서 조회한 유저의 AgeGroup을 알아야 함
        return new ResponseEntity<>(
            photoApplicationUseCase.retrievePhotoTrend(requestUserId),
            HttpStatus.OK
        );
    }
}
