package kr.folio.photo.application.handler;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import kr.folio.infrastructure.kafka.producer.GenericEventPublisher;
import kr.folio.photo.application.mapper.PhotoDataMapper;
import kr.folio.photo.application.ports.output.PhotoRepository;
import kr.folio.photo.application.service.PhotoEventService;
import kr.folio.photo.domain.core.vo.BrandType;
import kr.folio.photo.domain.service.PhotoDomainService;
import kr.folio.photo.domain.service.PhotoDomainUseCase;
import kr.folio.photo.infrastructure.publisher.FakePhotoMessagePublisher;
import kr.folio.photo.persistence.FakePhotoRepository;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import kr.folio.photo.presentation.dto.request.UpdatePhotoImageRequest;
import kr.folio.photo.presentation.dto.response.CreatePhotoResponse;
import kr.folio.photo.presentation.dto.response.DeletePhotoResponse;
import kr.folio.photo.presentation.dto.response.RetrievePhotoResponse;
import kr.folio.photo.presentation.dto.response.UpdatePhotoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhotoApplicationHandlerTest {

    private PhotoDomainUseCase photoDomainService;
    private PhotoRepository photoRepository;
    private PhotoDataMapper photoDataMapper;
    private PhotoEventService photoEventService;
    private PhotoApplicationHandler photoApplicationHandler;

    @BeforeEach
    void setUp() {
        photoDomainService = new PhotoDomainService();
        photoRepository = new FakePhotoRepository();
        photoDataMapper = new PhotoDataMapper();
        photoEventService= new PhotoEventService(null);
        photoApplicationHandler = new PhotoApplicationHandler(photoDomainService, photoRepository,
            photoDataMapper, photoEventService);
    }

    @Test
    void 포토를_생성한다() {
        // Given
        CreatePhotoRequest createPhotoRequest = CreatePhotoRequest.builder()
            .requestUserId("packdev937")
            .photoImageUrl("https://packdev937.github.io/assets/images/profile.jpg")
            .taggedUserIds(new ArrayList<>(Arrays.asList("yeddin", "siyeon")))
            .brandType(BrandType.PHOTOISM)
            .build();

        // When
        CreatePhotoResponse response = photoApplicationHandler.createPhoto(createPhotoRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.message()).isEqualTo("포토가 정상적으로 생성되었습니다.");
    }

    @Test
    void 포토를_조회한다() {
        // Given
        Long photoId = 포토_생성();

        // When
        RetrievePhotoResponse retrievePhotoResponse = photoApplicationHandler.retrievePhoto(
            photoId);

        // Then
        assertThat(retrievePhotoResponse).isNotNull();
        assertThat(retrievePhotoResponse.photoId()).isEqualTo(photoId);
    }

    @Test
    void 포토_이미지를_업데이트한다() {
        // Given
        Long photoId = 포토_생성();

        String updatedImageUrl = "https://packdev937.github.io";
        UpdatePhotoImageRequest updatePhotoImageRequest = new UpdatePhotoImageRequest(
            photoId,
            updatedImageUrl
        );

        // When
        UpdatePhotoResponse response = photoApplicationHandler.updatePhotoImage(
            "packdev937",
            updatePhotoImageRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.photoId()).isEqualTo(photoId);
        assertThat(response.updatedField()).isEqualTo(updatedImageUrl);
        assertThat(response.message()).isEqualTo("포토가 정상적으로 수정되었습니다");
    }

    @Test
    void 포토를_삭제한다() {
        // Given
        Long photoId = 포토_생성();

        // When
        DeletePhotoResponse response = photoApplicationHandler.deletePhoto(photoId);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.photoId()).isEqualTo(photoId);
        assertThat(response.message()).isEqualTo("포토가 정상적으로 삭제되었습니다");
        assertThat(photoRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    void 태그된_유저를_삭제한다() {
        // Given
        Long photoId = 포토_생성();

        // When
        photoApplicationHandler.deleteUserFromTag("yeddin");

        // Then
        assertThat(photoRepository.findPhotosByTaggedUserId("yeddin").size()).isEqualTo(0);
        assertThat(photoRepository.findPhotosByTaggedUserId("siyeon").size()).isEqualTo(1);
    }

    private Long 포토_생성() {

        CreatePhotoRequest createPhotoRequest = CreatePhotoRequest.builder()
            .requestUserId("packdev937")
            .photoImageUrl("https://packdev937.github.io/assets/images/profile.jpg")
            .taggedUserIds(new ArrayList<>(Arrays.asList("yeddin", "siyeon")))
            .brandType(BrandType.PHOTOISM)
            .build();

        CreatePhotoResponse createPhotoResponse = photoApplicationHandler.createPhoto(
            createPhotoRequest);
        Long photoId = createPhotoResponse.photoId();

        return photoId;
    }

}