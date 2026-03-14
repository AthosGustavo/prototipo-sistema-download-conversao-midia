package br.com.midiaconverte.rabbitmq.listener;

import br.com.midiaconverte.rabbitmq.model.Download;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DownloadListener {



    @RabbitListener(queues = "download-fila")
    public Download obterDownload(@Payload Download download){
        return download;
    }
}
