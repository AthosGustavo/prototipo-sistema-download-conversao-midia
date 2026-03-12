package br.com.midiaconverte.rabbitmq.util;

public enum EnumConstantes {

    EXCHANGE_DOWNLOAD_DIRECT("exchange-download-direct"),
    FILA_DOWNLOAD("download-fila"),
    ROUTING_KEY_DOWNLOAD("download"),
    DLX_EXCHANGE_DIRECT("dlx-exchange-direct"),
    DLX_FILA_DOWNLOAD("dlx-fila-download"),
    DLX_ROUTING_KEY("dlx.download");

    EnumConstantes(String valor){
        this.valor = valor;
    }

    public final String valor;
}
