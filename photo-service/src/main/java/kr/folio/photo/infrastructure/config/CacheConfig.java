package kr.folio.photo.infrastructure.config;

import java.time.Duration;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
            // 캐시 만료 시간
            .entryTtl(Duration.ofMinutes(10))
            // 캐싱할 때 null 값을 허용 안함
            .disableCachingNullValues()
            // Key를 직렬화 할 때 사용하는 규칙
            .serializeKeysWith(
	RedisSerializationContext
	    .SerializationPair
	    .fromSerializer(new StringRedisSerializer())
            )
            // Value를 직렬화 할 때 사용하는 규칙
            .serializeValuesWith(
	RedisSerializationContext
	    .SerializationPair
	    .fromSerializer(new GenericJackson2JsonRedisSerializer())
            );
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> {
            RedisCacheConfiguration defaultConfig = redisCacheConfiguration();

            // AgeGroup에 대한 개별 설정
            builder.withCacheConfiguration("child", defaultConfig);
            builder.withCacheConfiguration("teen", defaultConfig);
            builder.withCacheConfiguration("young_adult", defaultConfig);
            builder.withCacheConfiguration("adult", defaultConfig);
        };
    }
}
