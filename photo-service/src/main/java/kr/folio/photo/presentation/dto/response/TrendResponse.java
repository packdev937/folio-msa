package kr.folio.photo.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kr.folio.photo.domain.core.vo.BrandType;

public record TrendResponse(
    List<TrendItem> photos
) {

    public record TrendItem(
        Long photoId,
        String photoImageUrl,
        BrandType brandType,
        LocalDateTime updatedAt
        // todo : 좋아요 수도 추가할 수 있음
    ) {

    }
}

