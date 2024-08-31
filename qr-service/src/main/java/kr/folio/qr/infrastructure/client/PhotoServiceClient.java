package kr.folio.qr.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "photo-service")
public interface PhotoServiceClient {

    @PostMapping("/photos")
    CreatePhotoResponse createPhoto(CreatePhotoRequest createPhotoRequest);
}
