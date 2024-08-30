package kr.folio.feed.domain.core.vo;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessRange {
    PUBLIC("전체 공개", 3),
    FRIEND("친구 공개", 2),
    PRIVATE("비공개", 1);

    private final String value;
    private final int level;

    public static AccessRange from(String accessRange) {
        return Arrays.stream(AccessRange.values())
            .filter(a -> a.name().equalsIgnoreCase(accessRange))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 접근 범위입니다."));
    }

    public boolean isAccessible(AccessRange other) {
        return this.level >= other.level;
    }

    public boolean isPublic() {
        return this == PUBLIC;
    }

    public boolean isFriend() {
        return this == FRIEND;
    }

    public boolean isPrivate() {
        return this == PRIVATE;
    }
}
