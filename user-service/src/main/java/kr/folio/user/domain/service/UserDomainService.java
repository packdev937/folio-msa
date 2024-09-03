package kr.folio.user.domain.service;

import java.time.LocalDate;
import kr.folio.user.domain.core.entity.User;
import kr.folio.user.infrastructure.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class UserDomainService implements UserDomainUseCase {

    @Override
    public void updateUserNickname(User user, String nickname) {
        user.updateNickname(nickname);
    }

    @Override
    public void updateUserBirthday(User user, LocalDate birthday) {
        user.updateBirthday(birthday);
    }

    @Override
    public void updateUserMessage(User user, String message) {
        user.updateMessage(message);
    }

    @Override
    public void updateUserProfileImage(User user, String profileImageUrl) {
        user.updateProfileImageUrl(profileImageUrl);
    }

}
