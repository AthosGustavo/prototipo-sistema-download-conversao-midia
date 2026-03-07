package br.com.midiaconverte.download.controller;

import br.com.midiaconverte.download.model.Download;
import br.com.midiaconverte.rabbitmq.listener.DownloadListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    DownloadListener downloadListener;

    @RabbitListener(queues = "fila-download")
    public void baixarMidia(Download download){
        System.out.println(download.getTeste());
    }
}
