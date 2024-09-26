package kr.folio.user.persistence.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import kr.folio.user.application.ports.output.UserRepository;
import kr.folio.user.domain.core.entity.User;

public class FakeUserRepository implements UserRepository {

    private final Map<String, User> usersById = new HashMap<>();
    private final Map<String, User> usersByNickname = new HashMap<>();

    @Override
    public User saveUser(User user) {
        if (usersById.containsKey(user.getId())) {
            User existingUser = usersById.get(user.getId());
            usersByNickname.remove(existingUser.getNickname());
        }

        usersById.put(user.getFolioId(), user);
        usersByNickname.put(user.getNickname(), user);

        return user;
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return null;
    }

    @Override
    public Optional<User> findUserByNickname(String nickname) {
        return Optional.ofNullable(usersByNickname.get(nickname));
    }

    @Override
    public void deleteUser(User user) {
        usersById.remove(user.getId());
        usersByNickname.remove(user.getNickname());
    }

    @Override
    public Optional<User> findUserByFolioId(String id) {
        return usersById.values().stream()
            .filter(user -> user.getFolioId().equals(id))
            .findFirst();
    }

    @Override
    public void deleteUserByFolioId(String requestUserId) {
        usersById.remove(requestUserId);
    }
}
