package kr.folio.photo.domain.core.entity;

import java.util.ArrayList;
import java.util.List;
import kr.folio.photo.domain.core.vo.BrandType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Photo {

    private Long id;
    private String photoUrl;
    private Long viewCount = 0L;
    private BrandType brandType;
    private ArrayList<String> userIds;

    public void increateViewcount() {
        this.viewCount++;
    }

    public void updatePhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
