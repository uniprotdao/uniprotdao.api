package org.uniprot.api.async.download.messaging.config.common;

import org.redisson.api.RedissonClient;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.uniprot.api.async.download.model.job.DownloadJob;

/**
 * @author uniprotdao

 */
@Configuration
@EnableRedisRepositories(basePackages = "org.uniprot.api.async.download.messaging.repository")
public class RedisConfiguration {

    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

    @Bean
    public RedisTemplate<String, DownloadJob> redisTemplate(
            RedissonConnectionFactory redissonConnectionFactory) {
        RedisTemplate<String, DownloadJob> template = new RedisTemplate<>();
        template.setConnectionFactory(redissonConnectionFactory);
        return template;
    }
}
