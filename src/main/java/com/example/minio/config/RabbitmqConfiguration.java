package com.example.minio.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//rabbitmq ile konuşmak için kullanacağız
@Configuration//inject ettik
@RequiredArgsConstructor
public class RabbitmqConfiguration {



    //kuyruğu oluşturduk
    @Bean
    public Queue initQueue(){
        return new Queue("queue");
    }

    //direkt exchange oluşturduk
    @Bean
    public DirectExchange initDirectExchange(){
        return new DirectExchange("exchange");
    }

    //kuyruğu ve exchangeyi ilişkilerdirdik
    //routingi oluşturduk
    @Bean
    public Binding initBinding(Queue queue, DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("routing-key");//kuyruğu exchangeye bağladık
    }

    //kuyruğa json şeklinde veri göndermek için kullanılır.default byte[] gönderiliyor
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
       RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}