package kr.folio.user.persistence.adapter;

import java.util.Optional;
import kr.folio.user.application.ports.output.UserRepository;
import kr.folio.user.domain.core.entity.User;
import kr.folio.user.infrastructure.annotation.Adapter;
import kr.folio.user.persistence.mapper.UserPersistenceMapper;
import kr.folio.user.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Adapter
public class UserPersistenceAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    public User saveUser(User user) {
        log.info("Save User with id : {} at {}", user.getId(), this.getClass().getSimpleName());

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
    public Optional<User> findUserByFolioId(String folioId) {
        return Optional.ofNullable(
            userPersistenceMapper.toDomain(
	userJpaRepository.findByFolioId(folioId).orElse(null)
            )
        );
    }

    @Override
    public void deleteUserByFolioId(String requestUserId) {
        userJpaRepository.deleteByFolioId(requestUserId);
    }

    @Override
    public Optional<User> findUserById(Long id) {
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
