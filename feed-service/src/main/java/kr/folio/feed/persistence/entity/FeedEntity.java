package kr.folio.feed.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import kr.folio.feed.domain.core.vo.AccessRange;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
public class FeedEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "photo_id")
    private Long photoId;

    @Column(name = "photo_image_url")
    private String photoImageUrl;

    @Builder.Default // 기본 값을 유지하고 싶을 때
    @Enumerated(EnumType.STRING)
    private AccessRange accessRange = AccessRange.PUBLIC;

    @Builder.Default
    @ElementCollection
    private List<String> taggedUsers = new ArrayList<>();
}
