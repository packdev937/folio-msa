package kr.folio.photo.infrastructure.publisher;

import kr.folio.photo.application.ports.output.PhotoMessagePublisher;

public class FakePhotoMessagePublisher implements PhotoMessagePublisher {

    @Override
    public void publish(Object event) {
        // do nothing
    }
}
