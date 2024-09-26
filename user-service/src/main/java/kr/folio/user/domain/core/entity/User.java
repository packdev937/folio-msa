package kr.folio.user.domain.core.entity;

import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class User {

    private Long id;
    private String folioId;
    private String nickname;
    private String message;
    private String profileImageUrl;
    private LocalDate birthday;

    public void setFolioId(String folioId) {
        this.folioId = folioId;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateMessage(String message) {
        this.message = message;
    }

    public void updateBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
