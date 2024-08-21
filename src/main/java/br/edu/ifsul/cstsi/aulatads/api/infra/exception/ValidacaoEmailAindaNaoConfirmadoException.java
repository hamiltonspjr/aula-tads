package br.edu.ifsul.cstsi.aulatads.api.infra.exception;

public class ValidacaoEmailAindaNaoConfirmadoException extends RuntimeException{
    public ValidacaoEmailAindaNaoConfirmadoException(String message) {
        super(message);
    }
}
