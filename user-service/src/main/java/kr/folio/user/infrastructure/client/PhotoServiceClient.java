package kr.folio.user.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;

// Eureka에 등록된 마이크로서비스 중에서 name에 해당하는 서비스를 검증
@FeignClient(name = "photo-service")
public interface PhotoServiceClient {

}
