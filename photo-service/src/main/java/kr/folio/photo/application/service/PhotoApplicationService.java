package kr.folio.photo.application.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import kr.folio.photo.application.handler.PhotoApplicationHandler;
import kr.folio.photo.application.handler.PhotoTrendHandler;
import kr.folio.photo.application.mapper.PhotoDataMapper;
import kr.folio.photo.application.ports.input.PhotoApplicationUseCase;
import kr.folio.photo.application.ports.output.PhotoMessagePublisher;
import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.domain.core.event.CreatedPhotoEvent;
import kr.folio.photo.domain.core.event.RetrievedPhotoEvent;
import kr.folio.photo.domain.core.vo.AgeGroup;
import kr.folio.photo.infrastructure.annotation.ApplicationService;
import kr.folio.photo.infrastructure.client.UserServiceClient;
import kr.folio.photo.persistence.entity.PhotoEntity;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
public class PhotoApplicationService implements PhotoApplicationUseCase {

    private final PhotoApplicationHandler photoApplicationHandler;
    private final PhotoTrendHandler photoTrendHandler;

    private final PhotoDataMapper photoDataMapper;

    private final UserServiceClient userServiceClient;

    @Qualifier("CreatedPhotoEventKafkaPublisher")
    private final PhotoMessagePublisher createdPhotoMessagePublisher;

    @Qualifier("RetrievedPhotoEventKafkaPublisher")
    private final PhotoMessagePublisher retrievedPhotoMessagePublisher;

    @Override
    public CreatePhotoResponse createPhoto(CreatePhotoRequest createPhotoRequest) {
        CreatedPhotoEvent createdPhotoEvent = photoApplicationHandler
            .createPhoto(createPhotoRequest);
        createdPhotoMessagePublisher.publish(createdPhotoEvent);
        return photoDataMapper
            .toCreateResponse(createdPhotoEvent.photo(), "User saved successfully!");
    }

    @Override
    public RetrievePhotoResponse retrievePhoto(
        String requestUserId,
        Long photoId) {
        // todo : AccessRange를 조회하는 로직
        RetrievedPhotoEvent retrievedPhotoEvent = photoApplicationHandler
            .retrievePhoto(requestUserId, photoId);
        retrievedPhotoMessagePublisher.publish(retrievedPhotoEvent);
        return photoDataMapper.toRetrieveResponse(retrievedPhotoEvent.photo());
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
            .flatMap(photo -> photo.getUserIds().stream())
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

}