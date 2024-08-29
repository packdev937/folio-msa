package kr.folio.photo.infrastructure.client;

import java.util.Set;
import kr.folio.photo.domain.core.vo.AgeGroup;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String userServiceUrl = "http://users"; // User-Service의 기본 URL

    public AgeGroup getAgeGroupByUserId(String userId) {
        String url = userServiceUrl + "/" + userId + "/age-group";
        return restTemplate.getForObject(url, AgeGroup.class);
    }

    public Map<String, Integer> getUserAges(Set<String> userIds) {
        String url = userServiceUrl + "/ages";
        // REST API 호출하여 사용자 ID 목록에 대한 나이 정보를 가져옴
        return restTemplate.postForObject(url, userIds, HashMap.class);
    }
}
