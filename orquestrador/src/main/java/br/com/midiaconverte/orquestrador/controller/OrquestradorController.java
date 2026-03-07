package br.com.midiaconverte.orquestrador.controller;


import br.com.midiaconverte.rabbitmq.model.Download;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.midiaconverte.rabbitmq.producer.DownloadProducer;

@RestController
@RequestMapping("/orquestrador")
public class OrquestradorController {

    @Autowired
    DownloadProducer producer;

    @PostMapping("/enviar-mensagem")
    public void enviarMensagemDownload(@RequestBody String msg){
        Download download = new Download();
        download.setTeste(msg);
        this.producer.enviarDownloadParaFila(download);
    }

}
