package kr.folio.photo.domain.core.entity;

import java.time.LocalDateTime;
import java.util.List;
import kr.folio.photo.domain.core.vo.BrandType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Photo {

    private Long photoId;
    private String photoImageUrl;
    @Builder.Default
    private Long viewCount = 0L;
    private BrandType brandType;
    private List<String> taggedUserIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void increateViewcount() {
        this.viewCount++;
    }

    public void updatePhotoImageUrl(String newPhotoImageUrl) {
        this.photoImageUrl = newPhotoImageUrl;
    }

    public boolean isTaggedBy(String requestUserId) {
        return taggedUserIds.contains(requestUserId);
    }

    public void setId(long id) {
        this.photoId = id;
    }

    public void deleteUserFromTag(String userId) {
        if (taggedUserIds.contains(userId)) {
            taggedUserIds.remove(userId);
        }
    }
}
