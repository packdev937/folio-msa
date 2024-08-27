package kr.folio.user.domain.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import kr.folio.user.application.ports.output.UserRepository;
import kr.folio.user.domain.core.entity.User;
import kr.folio.user.domain.core.event.UserCreatedEvent;
import kr.folio.user.domain.core.exception.UserAlreadyExistsException;
import kr.folio.user.infrastructure.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class UserDomainService implements UserDomainUseCase {

    private final UserRepository userRepository;
    private final String UTC = "UTC";

    @Override
    public UserCreatedEvent validateUser(User user) {
        validateDuplicateId(user.getId());
        validateDuplicateNickname(user.getNickname());
        return new UserCreatedEvent(user, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    public void validateDuplicateId(String id) {
        userRepository.findUserById(id).ifPresent(
            user -> {
	throw new UserAlreadyExistsException("아이디", id);
            });
    }

    public void validateDuplicateNickname(String nickname) {
        userRepository.findUserByNickname(nickname).ifPresent(
            user -> {
	throw new UserAlreadyExistsException("닉네임", nickname);
            });
    }

}
