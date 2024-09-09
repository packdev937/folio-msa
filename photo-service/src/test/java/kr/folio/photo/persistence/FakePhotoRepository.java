package kr.folio.photo.persistence;

import kr.folio.photo.application.ports.output.PhotoRepository;
import kr.folio.photo.domain.core.entity.Photo;

import java.util.*;

public class FakePhotoRepository implements PhotoRepository {

    private final Map<Long, Photo> photoStorage = new HashMap<>();
    private long currentPhotoId = 1L; // Photo ID를 자동 생성하는 필드

    @Override
    public Photo save(Photo photo) {
        if (photo.getPhotoId() == null) {
            photo.setId(currentPhotoId++);
        }
        photoStorage.put(photo.getPhotoId(), photo);
        return photo;
    }

    @Override
    public Optional<Photo> findPhotoById(Long photoId) {
        return Optional.ofNullable(photoStorage.get(photoId));
    }

    @Override
    public void deletePhotoById(Long photoId) {
        photoStorage.remove(photoId);
    }

    @Override
    public List<Photo> findLatestPhotos() {
        return new ArrayList<>(photoStorage.values()).subList(0,
            Math.min(500, photoStorage.size()));
    }

    public List<Photo> findAll() {
        return new ArrayList<>(photoStorage.values());
    }

    @Override
    public List<Photo> findPhotosByTaggedUserId(String userId) {
        return photoStorage.values().stream()
            .filter(photo -> photo.getTaggedUserIds().contains(userId))
            .toList();
    }

    @Override
    public void saveAll(List<Photo> photos) {
        photos.forEach(this::save);
    }
}
