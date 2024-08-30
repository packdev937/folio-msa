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
    private Long viewCount = 0L;
    private BrandType brandType;
    private List<String> userIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void increateViewcount() {
        this.viewCount++;
    }

    public void photoImageUrl(String newPhotoImageUrl) {
        this.photoImageUrl = newPhotoImageUrl;
    }
}
