package kr.folio.photo.domain.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.domain.core.event.CreatedPhotoEvent;
import kr.folio.photo.domain.core.vo.AgeGroup;
import kr.folio.photo.infrastructure.annotation.DomainService;
import kr.folio.photo.presentation.dto.event.CreatedPhotoEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

@RequiredArgsConstructor
@DomainService
public class PhotoDomainService implements PhotoDomainUseCase {

    private final String UTC = "UTC";
    private static final double W1 = 0.5; // Age Difference Weight
    private static final double W2 = 0.5; // View Count Weight
    private static final int MAX_AGE_DIFFERENCE = 30; // 가정
    private static final int MAX_VIEW_COUNT = 10000; // 가정

    public List<Photo> filterAndSortPhotosByAgeGroup(List<Photo> photos,
        Map<String, Integer> userAges, AgeGroup ageGroup) {
        return photos.stream()
            .sorted((p1, p2) -> Double.compare(
	calculateScore(p2, userAges, ageGroup),
	calculateScore(p1, userAges, ageGroup))
            )
            .limit(100)
            .collect(Collectors.toList());
    }

    private double calculateScore(Photo photo, Map<String, Integer> userAges, AgeGroup ageGroup) {
        double avgTaggedUserAge = photo.getUserIds().stream()
            .mapToDouble(userId -> userAges.get(userId))
            .average().orElse(0);

        int ageDifference = Math.abs(ageGroup.getAge() - (int) avgTaggedUserAge);
        double normalizedAgeDifference = (double) ageDifference / MAX_AGE_DIFFERENCE;
        double normalizedViewCount = (double) photo.getViewCount() / MAX_VIEW_COUNT;

        return (W1 * (1 - normalizedAgeDifference)) + (W2 * normalizedViewCount);
    }

    @CachePut(value = "trend", key = "#ageGroup.name().toLowerCase()") // 캐시는 10분 간 유효
    public List<Photo> cacheRecommendedPhotos(List<Photo> photos, AgeGroup ageGroup) {
        return photos;
    }

    @Cacheable(value = "trend", key = "#ageGroup.name().toLowerCase()")
    public List<Photo> retrieveCachedRecommendedPhotos(AgeGroup ageGroup) {
        return List.of();
    }

    @Override
    public CreatedPhotoEvent createPhotoEvent(Photo photo) {
        CreatedPhotoEventDTO createdPhotoEventDTO =
            new CreatedPhotoEventDTO(
	photo.getPhotoId(),
	photo.getPhotoImageUrl(),
	photo.getUserIds());

        return new CreatedPhotoEvent(createdPhotoEventDTO, ZonedDateTime.now(ZoneId.of(UTC)));
    }
}
