package br.com.midiaconverte.rabbitmq.configuracao;

import br.com.midiaconverte.rabbitmq.util.EnumConstantes;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DownloadConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public DirectExchange exchangeDownloadDirect(){
        return new DirectExchange(
            EnumConstantes.EXCHANGE_DOWNLOAD_DIRECT.valor,
            true,
            false
        );
    }

    @Bean
    public Queue filaDownload(){
        return QueueBuilder.durable(EnumConstantes.FILA_DOWNLOAD.valor)
                .deadLetterExchange(EnumConstantes.DLX_EXCHANGE_DIRECT.valor)
                .deadLetterRoutingKey(EnumConstantes.DLX_ROUTING_KEY.valor)
                .ttl(60000)
                .build();
    }

    @Bean
    public Binding bindingDownload(@Qualifier("filaDownload") Queue filaDownload, DirectExchange exchangeDownloadDirect){
        return BindingBuilder.bind(filaDownload)
                .to(exchangeDownloadDirect)
                .with(EnumConstantes.ROUTING_KEY_DOWNLOAD.valor);
    }

    @Bean
    public Queue dlqDownload() {
        return QueueBuilder.durable(EnumConstantes.DLX_FILA_DOWNLOAD.valor)
                .ttl(300000)
                .build();
    }


    @Bean
    public DirectExchange dlxExchangeDirect() {
        return new DirectExchange(
                EnumConstantes.DLX_EXCHANGE_DIRECT.valor,
                true,
                false
        );
    }

    @Bean
    public Binding bindingDLQ(@Qualifier("dlqDownload") Queue dlqDownload, DirectExchange dlxExchangeDirect) {

        return BindingBuilder.bind(dlqDownload)
                .to(dlxExchangeDirect)
                .with(EnumConstantes.DLX_ROUTING_KEY.valor);
    }

}
