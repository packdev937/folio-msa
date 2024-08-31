package kr.folio.photo.application.handler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import kr.folio.photo.application.ports.output.PhotoRepository;
import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.domain.service.PhotoDomainService;
import kr.folio.photo.domain.core.vo.AgeGroup;
import kr.folio.photo.domain.service.PhotoDomainUseCase;
import kr.folio.photo.presentation.dto.response.TrendResponse.TrendItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class PhotoTrendHandler {

    private final PhotoDomainUseCase photoDomainUseCase;
    private final PhotoRepository photoRepository;

    public List<Photo> findLatestPhotos(final int count) {
        // todo : 나중에 QueryDSL로 동적으로 count 개수에 맞게 가져올 수 있도록 설정한다
        return photoRepository.findLatestPhotos();
    }

    public List<TrendItem> getRecommendedPhotos(List<Photo> latestPhotos, AgeGroup ageGroup,
        Map<String, Integer> userAges) {
        List<Photo> recommendedPhotos = photoDomainUseCase.retrieveCachedRecommendedPhotos(
            ageGroup);

        if (recommendedPhotos.isEmpty()) {
            recommendedPhotos = photoDomainUseCase.filterAndSortPhotosByAgeGroup(
	latestPhotos, userAges, ageGroup);
            photoDomainUseCase.cacheRecommendedPhotos(recommendedPhotos, ageGroup);
        }

        return recommendedPhotos.stream()
            .map(photo ->
	new TrendItem(photo.getPhotoId(),
	    photo.getPhotoImageUrl(),
	    photo.getBrandType(),
	    LocalDateTime.now()
//                    photo.getUpdatedAt()
	))
            .collect(Collectors.toList());
    }
}
