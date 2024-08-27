package kr.folio.user.persistence.mapper;

import kr.folio.user.domain.core.entity.User;
import kr.folio.user.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {
    public UserEntity toEntity(User user) {
        return null;
    }

    public User toDomain(UserEntity userEntity) {
        return User.builder()
            .id(userEntity.getId())
            .nickname(userEntity.getNickname())
            .message(userEntity.getMessage())
            .profileImageUrl(userEntity.getProfileImageUrl())
            .birthday(userEntity.getBirthday())
            .build();
    }
}
