package br.com.fiap.srvcliente.exception;

public class MensagemNotFoundException extends RuntimeException {
    public MensagemNotFoundException(String ex) {
        super(ex);
    }
}
