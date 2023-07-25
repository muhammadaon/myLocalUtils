package com.personal.mylocalutils.Configurations;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        return connectionFactory;
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        return new RabbitTemplate(connectionFactory());
//    }
}
