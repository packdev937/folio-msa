package kr.folio.user.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import kr.folio.user.domain.core.vo.OAuthProvider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_users")
@Entity
public class UserEntity extends BaseEntity {

    @Id
    @Column(name = "user_id")
    private String id;

    @Column(name = "user_nickname")
    private String nickname;

    @Column(name = "user_message")
    private String message = "";

    @Column(name = "user_profile_image_url")
    private String profileImageUrl = "default url";

    @Column(name = "user_birthday")
    private LocalDate birthday;
}
