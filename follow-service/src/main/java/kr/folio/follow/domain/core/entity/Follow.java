package kr.folio.follow.domain.core.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Follow {
    private String followerId;
    private String followeeId;
}
