package kr.folio.feed.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "photo-service")
public interface PhotoServiceClient {

    // 단순 호출만 필요하기 때문에 ResponseEntity를 사용하지 않습니다.
    @DeleteMapping("/{photoId}")
    void deletePhoto(@PathVariable(name = "photoId") Long photoId);

    // 하나의 피드에서 사진을 업데이트 하면 -> 포토로 업데이트 내용을 반영하고 -> 해당 반영을 나머지 피드에 반영
    // 피드가 포토의 내용을 따로 저장하고 있다는 가정

    // 아니라면 피드와 포토 내용을 분리하고 피드를 조회할 때 마다 포토 아이디를 사용해서 @Feign Client 해야됨
}
