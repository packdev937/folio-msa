package kr.folio.feed.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * CreateFeedRequest
 *
 * @param userId        피드를 추가 할 유저 아이디 (유저 정보)
 * @param photoId       피드에 추가 할 포토 아이디 (사진 정보)
 * @param photoImageUrl 피드에 추가 할 포토 이미지 URL (사진 정보)
 */
public record CreateFeedRequest(
    @NotNull
    String userId,
    @NotNull
    Long photoId,
    @NotNull
    String photoImageUrl,
    @NotNull
    List<String> taggedUserIds
) {

}
