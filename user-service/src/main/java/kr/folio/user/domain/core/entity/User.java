package kr.folio.user.domain.core.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class User {

    private String id;
    private String nickname;
    private String message;
    private String profileImageUrl;
    private LocalDate birthday;

    public void setId(String id) {
        this.id = id;
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
