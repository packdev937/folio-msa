package kr.folio.user.application.ports.output;

import java.util.Optional;
import kr.folio.user.domain.core.entity.User;

public interface UserRepository {
    User createUser(User user);
    User updateUser(User user);
    Optional<User> findUserById(String id);
    Optional<User> findUserByNickname(String nickname);

    void deleteUser(User user);
}
