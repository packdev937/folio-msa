package kr.folio.user.domain.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import kr.folio.user.domain.core.entity.User;
import kr.folio.user.domain.core.event.CreatedUserEvent;
import kr.folio.user.infrastructure.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class UserDomainService implements UserDomainUseCase {
    private final String UTC = "UTC";

    @Override
    public CreatedUserEvent validateUser(User user) {
        // 순수 도메인 로직을 검증하는 공간
        return new CreatedUserEvent(user, ZonedDateTime.now(ZoneId.of(UTC)));
    }
}
