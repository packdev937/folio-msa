package kr.folio.feed.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "photo-service")
public interface PhotoServiceClient {

    // 단순 호출만 필요하기 때문에 ResponseEntity를 사용하지 않습니다.
    @DeleteMapping("/{photoId}")
    void deletePhoto(@PathVariable(name = "photoId") Long photoId);

}
