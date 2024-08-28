package kr.folio.photo.application.service;

import kr.folio.photo.application.handler.PhotoApplicationHandler;
import kr.folio.photo.application.mapper.PhotoDataMapper;
import kr.folio.photo.application.ports.input.PhotoApplicationUseCase;
import kr.folio.photo.application.ports.output.PhotoMessagePublisher;
import kr.folio.photo.domain.core.event.CreatedPhotoEvent;
import kr.folio.photo.domain.core.event.RetrievedPhotoEvent;
import kr.folio.photo.infrastructure.annotation.ApplicationService;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import kr.folio.photo.presentation.dto.response.CreatePhotoResponse;
import kr.folio.photo.presentation.dto.response.RetrievePhotoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
public class PhotoApplicationService implements PhotoApplicationUseCase {

    private final PhotoApplicationHandler photoApplicationHandler;
    private final PhotoDataMapper photoDataMapper;

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
    public RetrievePhotoResponse retrievePhoto(String requestUserId, Long photoId) {
        // todo : AccessRange를 조회하는 로직
        RetrievedPhotoEvent retrievedPhotoEvent = photoApplicationHandler
            .retrievePhoto(requestUserId, photoId);
        retrievedPhotoMessagePublisher.publish(retrievedPhotoEvent);
        return photoDataMapper.toRetrieveResponse(retrievedPhotoEvent.photo());
    }
}