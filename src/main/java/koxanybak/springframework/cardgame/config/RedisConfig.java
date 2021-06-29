package koxanybak.springframework.cardgame.config;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter.EnableKeyspaceEvents;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(
	enableKeyspaceEvents = EnableKeyspaceEvents.OFF,
	basePackages = "koxanybak.springframework.cardgame.repository.redis"
)
public class RedisConfig {

	@Autowired
	private Environment environment;

    @Bean
	public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration(getRedisHost(), getRedisPort());
		return new JedisConnectionFactory(conf);
	}

    @Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}

	@Bean
	public RedisCacheConfiguration cacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig()
			.entryTtl(Duration.ofMinutes(1))
			.disableCachingNullValues();
	}

	// TODO: Get these from .properties
	
	private String getRedisHost() {
		List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
		if (activeProfiles.contains("prod")) {
			return "cache_prod";
		}
		if (activeProfiles.contains("test")) {
			return "localhost";
		}
		return "localhost";
	}

	private int getRedisPort() {
		List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
		if (activeProfiles.contains("prod")) {
			return 6379;
		}
		if (activeProfiles.contains("test")) {
			return 6377;
		}
		return 6378;
	}
}
