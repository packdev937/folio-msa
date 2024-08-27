package kr.folio.user.persistence.adapter;

import io.swagger.v3.oas.annotations.Operation;
import java.util.Optional;
import kr.folio.user.application.ports.output.UserRepository;
import kr.folio.user.domain.core.entity.User;
import kr.folio.user.domain.core.exception.UserAlreadyExistsException;
import kr.folio.user.persistence.entity.UserEntity;
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
        UserEntity savedUser = userJpaRepository.save(
            userPersistenceMapper.toEntity(user)
        );
        System.out.println(savedUser.toString());

        return userPersistenceMapper.toDomain(
            savedUser
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
