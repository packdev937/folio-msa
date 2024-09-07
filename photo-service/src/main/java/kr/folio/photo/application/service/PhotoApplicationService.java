package kr.folio.photo.application.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import kr.folio.photo.application.handler.PhotoApplicationHandler;
import kr.folio.photo.application.handler.PhotoTrendHandler;
import kr.folio.photo.application.ports.input.PhotoApplicationUseCase;
import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.domain.core.vo.AgeGroup;
import kr.folio.photo.infrastructure.annotation.ApplicationService;
import kr.folio.photo.infrastructure.client.UserServiceClient;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import kr.folio.photo.presentation.dto.request.UpdatePhotoImageRequest;
import kr.folio.photo.presentation.dto.response.CreatePhotoResponse;
import kr.folio.photo.presentation.dto.response.DeletePhotoResponse;
import kr.folio.photo.presentation.dto.response.RetrievePhotoResponse;
import kr.folio.photo.presentation.dto.response.TrendResponse;
import kr.folio.photo.presentation.dto.response.TrendResponse.TrendItem;
import kr.folio.photo.presentation.dto.response.UpdatePhotoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
public class PhotoApplicationService implements PhotoApplicationUseCase {

    private final PhotoApplicationHandler photoApplicationHandler;
    private final PhotoTrendHandler photoTrendHandler;
    private final UserServiceClient userServiceClient;

    @Override
    public CreatePhotoResponse createPhoto(CreatePhotoRequest createPhotoRequest) {

        log.info("Creating photo with request: {}", createPhotoRequest);

        return photoApplicationHandler.createPhoto(createPhotoRequest);
    }

    @Override
    public RetrievePhotoResponse retrievePhoto(Long photoId) {

        log.info("Retrieving photo with photoId: {}", photoId);

        return photoApplicationHandler.retrievePhoto(photoId);
    }

    @Override
    public UpdatePhotoResponse updatePhotoImage(
        String requestUserId,
        UpdatePhotoImageRequest updatePhotoRequest
    ) {

        return photoApplicationHandler.updatePhotoImage(requestUserId, updatePhotoRequest);
    }

    @Override
    public DeletePhotoResponse deletePhoto(Long photoId) {

        return photoApplicationHandler.deletePhoto(photoId);
    }

    public TrendResponse retrievePhotoTrend(String requestUserId) {
        // AgeGroup 별로 추천 사진을 캐싱
        List<Photo> latestPhotos = photoTrendHandler.findLatestPhotos(500);

        Set<String> userIds = latestPhotos.stream()
            .flatMap(photo -> photo.getTaggedUserIds().stream())
            .collect(Collectors.toSet());

        // User-Service에서 각 사용자의 나이 정보를 가져옴
        Map<String, Integer> userAges = userServiceClient.getUserAges(userIds);

        // User-Service에서 AgeGroup 정보를 가져옴
        AgeGroup ageGroup = userServiceClient.getAgeGroupByUserId(requestUserId);

        // Handler로 데이터를 전달하여 추천 로직을 처리
        List<TrendItem> recommendedPhotos = photoTrendHandler.getRecommendedPhotos(latestPhotos,
            ageGroup, userAges);

        return new TrendResponse(recommendedPhotos);
    }

    @Override
    public void deleteUserFromTag(String userId) {

        photoApplicationHandler.deleteUserFromTag(userId);
    }

}