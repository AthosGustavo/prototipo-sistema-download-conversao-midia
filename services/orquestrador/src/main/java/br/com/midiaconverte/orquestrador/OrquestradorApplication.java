package br.com.midiaconverte.orquestrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.midiaconverte.rabbitmq", "br.com.midiaconverte.orquestrador",})
public class OrquestradorApplication {
    //O controlador só foi encontrador após adicionar o segundo caminho do scan
	public static void main(String[] args) {
		SpringApplication.run(OrquestradorApplication.class, args);
	}

}
