package kr.folio.feed.application.ports.input;

import kr.folio.common.domain.core.event.user.UserDeletedExternalEvent;

public interface UserDeletedMessageListener {

    void handleEvent(UserDeletedExternalEvent userDeletedExternalEvent);
}
