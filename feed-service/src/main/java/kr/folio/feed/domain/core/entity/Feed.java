package kr.folio.feed.domain.core.entity;

import java.time.LocalDateTime;
import java.util.List;
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
    private AccessRange accessRange;
    private List<TaggedUser> taggedUsers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static class TaggedUser {

        private String userId;
        private String name;
        private String profileImageUrl;
    }
}
