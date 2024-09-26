package kr.folio.user.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_users")
@Entity
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_folio_id", unique = true, nullable = false)
    private String folioId;

    @Column(name = "user_nickname", unique = true, nullable = false)
    private String nickname;

    @Builder.Default
    @Column(name = "user_message")
    private String message = "";

    @Builder.Default
    @Column(name = "user_profile_image_url")
    private String profileImageUrl = "DEFAULT_URL";

    @Column(name = "user_birthday", nullable = false)
    private LocalDate birthday;
}
