package kr.folio.photo.domain.service;

import java.util.List;
import java.util.Map;
import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.domain.core.vo.AgeGroup;

public interface PhotoDomainUseCase {

    List<Photo> retrieveCachedRecommendedPhotos(AgeGroup ageGroup);

    List<Photo> filterAndSortPhotosByAgeGroup(List<Photo> latestPhotos,
        Map<String, Integer> userAges, AgeGroup ageGroup);

    List<Photo> cacheRecommendedPhotos(List<Photo> recommendedPhotos, AgeGroup ageGroup);

    void updatePhotoImageUrl(Photo photo, String updatedPhotoImageUrl);

    Photo deleteUserFromTag(Photo photo, String userId);
}
