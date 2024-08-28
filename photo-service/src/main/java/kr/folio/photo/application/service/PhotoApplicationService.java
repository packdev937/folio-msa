package kr.folio.photo.application.service;

import kr.folio.photo.application.handler.PhotoApplicationHandler;
import kr.folio.photo.application.mapper.PhotoDataMapper;
import kr.folio.photo.application.ports.input.PhotoApplicationUseCase;
import kr.folio.photo.application.ports.output.PhotoMessagePublisher;
import kr.folio.photo.domain.core.event.PhotoCreatedEvent;
import kr.folio.photo.infrastructure.annotation.ApplicationService;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import kr.folio.photo.presentation.dto.response.CreatePhotoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
public class PhotoApplicationService implements PhotoApplicationUseCase {

    private final PhotoApplicationHandler photoApplicationHandler;
    private final PhotoDataMapper photoDataMapper;
    @Qualifier("photoCreatedEventKafkaPublisher")
    private final PhotoMessagePublisher photoCreatedMessagePublisher;

    @Override
    public CreatePhotoResponse createPhoto(CreatePhotoRequest createPhotoRequest) {
        PhotoCreatedEvent photoCreatedEvent = photoApplicationHandler
            .createPhoto(createPhotoRequest);
        photoCreatedMessagePublisher.publish(photoCreatedEvent);
        return photoDataMapper
            .toCreateResponse(photoCreatedEvent.photo(), "User saved successfully!");
    }
}