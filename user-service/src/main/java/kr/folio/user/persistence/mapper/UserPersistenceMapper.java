package kr.folio.user.persistence.mapper;

import java.util.UUID;
import kr.folio.user.domain.core.entity.User;
import kr.folio.user.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public UserEntity toEntity(User user) {
        return UserEntity.builder()
            .id(user.getId())
            .folioId(user.getFolioId())
            .nickname(user.getNickname())
            .birthday(user.getBirthday())
            .build();
    }

    public User toDomain(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return User.builder()
            .id(userEntity.getId())
            .folioId(userEntity.getFolioId())
            .nickname(userEntity.getNickname())
            .message(userEntity.getMessage())
            .profileImageUrl(userEntity.getProfileImageUrl())
            .birthday(userEntity.getBirthday())
            .build();
    }
}
