package kr.folio.user.domain.service;

import java.time.LocalDate;
import kr.folio.user.domain.core.entity.User;

public interface UserDomainUseCase {

    void updateUserNickname(User user, String nickname);

    void updateUserBirthday(User user, LocalDate birthday);

    void updateUserMessage(User user, String message);

    void updateUserProfileImage(User user, String profileImageUrl);
}
