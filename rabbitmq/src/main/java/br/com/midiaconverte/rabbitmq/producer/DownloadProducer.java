package br.com.midiaconverte.rabbitmq.producer;

import br.com.midiaconverte.rabbitmq.listener.DownloadListener;
import br.com.midiaconverte.rabbitmq.util.EnumConstantes;
import br.com.midiaconverte.rabbitmq.model.Download;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DownloadProducer {

    private static final Logger log = LoggerFactory.getLogger(DownloadProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarDownloadParaFila(Download download){

        this.rabbitTemplate.convertAndSend(
                EnumConstantes.EXCHANGE_DOWNLOAD_DIRECT.valor,
                EnumConstantes.ROUTING_KEY_DOWNLOAD.valor,
                download
        );

        log.info("MENSAGEM DE DOWNLOAD ENVIADA PARA A FILA DO RABBITMQ");
    }
}
