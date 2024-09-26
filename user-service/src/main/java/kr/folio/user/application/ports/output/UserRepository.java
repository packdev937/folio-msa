package kr.folio.user.application.ports.output;

import java.util.Optional;
import kr.folio.user.domain.core.entity.User;

public interface UserRepository {

    User saveUser(User user);

    Optional<User> findUserById(Long id);

    Optional<User> findUserByNickname(String nickname);

    void deleteUser(User user);

    Optional<User> findUserByFolioId(String id);

    void deleteUserByFolioId(String requestUserId);
}
