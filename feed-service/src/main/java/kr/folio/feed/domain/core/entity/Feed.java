package kr.folio.feed.domain.core.entity;

import java.time.LocalDateTime;
import java.util.List;
import kr.folio.feed.domain.core.vo.AccessRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Feed {

    private Long id;
    private String userId;
    private Long photoId;
    private String imageUrl;
    private AccessRange accessRange;
    private List<String> taggedUsers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void updateAccessRange(String updatedAccessRange) {
        this.accessRange = AccessRange.from(updatedAccessRange);
    }

    public void updateImageUrl(String updatedImageUrl) {
        this.imageUrl = updatedImageUrl;
    }
}
