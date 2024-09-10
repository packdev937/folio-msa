package kr.folio.photo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "kr.folio")
@EnableDiscoveryClient
public class PhotoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoServiceApplication.class, args);
    }

}
