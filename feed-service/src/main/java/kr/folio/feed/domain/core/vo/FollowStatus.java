package kr.folio.feed.domain.core.vo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FollowStatus {
    SELF("자기 자신", 3),
    FOLLOW("팔로우", 2),
    UNFOLLOW("언 팔로우", 1);

    private final String value;
    private final int level;

    public boolean isAuthorized() {
        return this.level >= FOLLOW.level;
    }
}
