package kr.folio.photo.infrastructure.client;

import java.util.Set;
import kr.folio.photo.domain.core.vo.AgeGroup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FeignClient(name = "user-service", path = "/users")
public interface UserServiceClient {

    @GetMapping("/{userId}/age-group")
    AgeGroup getAgeGroupByUserId(String userId);

    @GetMapping("/ages")
    Map<String, Integer> getUserAges(Set<String> userIds);
}
