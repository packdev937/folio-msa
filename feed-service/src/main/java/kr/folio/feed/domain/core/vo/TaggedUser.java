package kr.folio.feed.domain.core.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TaggedUser {

    private String userId;
    private String name;
    private String profileImageUrl;
}
