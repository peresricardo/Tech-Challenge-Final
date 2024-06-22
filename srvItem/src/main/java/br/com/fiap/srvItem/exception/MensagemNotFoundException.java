package br.com.fiap.srvItem.exception;

public class MensagemNotFoundException extends RuntimeException {
    public MensagemNotFoundException(String ex) {
        super(ex);
    }
}