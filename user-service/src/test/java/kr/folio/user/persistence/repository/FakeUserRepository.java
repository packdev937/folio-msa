package kr.folio.user.persistence.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import kr.folio.user.application.ports.output.UserRepository;
import kr.folio.user.domain.core.entity.User;

public class FakeUserRepository implements UserRepository {

    private final Map<String, User> usersById = new HashMap<>();
    private final Map<String, User> usersByNickname = new HashMap<>();
    private long lastUserId = 0;

    @Override
    public User saveUser(User user) {
        if (usersById.containsKey(user.getId())) {
            User existingUser = usersById.get(user.getId());
            usersByNickname.remove(existingUser.getNickname());
        }

        usersById.put(user.getId(), user);
        usersByNickname.put(user.getNickname(), user);

        return user;
    }

    @Override
    public Optional<User> findUserById(String id) {
        return Optional.ofNullable(usersById.get(id));
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
}
