package kr.folio.user.persistence.adapter;

import java.util.Optional;
import kr.folio.user.application.ports.output.UserRepository;
import kr.folio.user.domain.core.entity.User;
import kr.folio.user.persistence.mapper.UserPersistenceMapper;
import kr.folio.user.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// todo : @Adapter으로 대체
@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    public User createUser(User user) {
        return userPersistenceMapper.toDomain(
            userJpaRepository.save(
	userPersistenceMapper.toEntity(user)
            )
        );
    }

    @Override
    public User updateUser(User user) {
        return userPersistenceMapper.toDomain(
            userJpaRepository.save(
	userPersistenceMapper.toEntity(user)
            )
        );
    }

    @Override
    public void deleteUser(User user) {
        userJpaRepository.delete(
            userPersistenceMapper.toEntity(user)
        );
    }

    @Override
    public Optional<User> findUserById(String id) {
        return Optional.ofNullable(
            userPersistenceMapper.toDomain(
	userJpaRepository.findById(id).orElse(null)
            )
        );
    }

    @Override
    public Optional<User> findUserByNickname(String nickname) {
        return Optional.ofNullable(
            userPersistenceMapper.toDomain(
	userJpaRepository.findByNickname(nickname).orElse(null)
            )
        );
    }
}
