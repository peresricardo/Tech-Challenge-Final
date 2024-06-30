package br.com.fiap.srvpagamento.exception;

public class MensagemNotFoundException extends RuntimeException {
    public MensagemNotFoundException(String ex) { super(ex); }
}
