package koxanybak.springframework.cardgame.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
		String redisHost = Arrays.asList(environment.getActiveProfiles()).contains("prod") ? "redis" : "localhost";
        RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration(redisHost, 6379);
		return new JedisConnectionFactory(conf);
	}

    @Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}
}
