package kr.folio.photo.presentation.rest;

import java.util.HashMap;
import java.util.Map;
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
    public Map<String, Object> healthCheck() {
        Map<String, Object> healthDetails = new HashMap<>();
        healthDetails.put("status", "Photo-Service가 실행 중 입니다.");
        healthDetails.put("serverTime", System.currentTimeMillis());
        healthDetails.put("freeMemory", Runtime.getRuntime().freeMemory());

        return healthDetails;
    }

    @Override
    public ResponseEntity<CreatePhotoResponse> createPhoto(
        CreatePhotoRequest createPhotoRequest) {
        log.info("Creating photo : uploadUserId = {}", createPhotoRequest.requestUserId());

        return new ResponseEntity<>(
            photoApplicationUseCase.createPhoto(createPhotoRequest)
            , HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<RetrievePhotoResponse> retrievePhoto(Long photoId) {
        log.info("Retrieving photo : requestUserId = {}, photoId = {}", photoId);

        return new ResponseEntity<>(
            photoApplicationUseCase.retrievePhoto(photoId),
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
