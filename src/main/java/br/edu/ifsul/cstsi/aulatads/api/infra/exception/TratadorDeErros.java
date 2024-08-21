package br.edu.ifsul.cstsi.aulatads.api.infra.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class TratadorDeErros extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity trataErro400(ConstraintViolationException ex){ //400 - Bad Request para Erro de Validação da Validation
        var erros = ex.getConstraintViolations();
        return ResponseEntity.badRequest().body(erros.stream().map(ErroValidation::new).toList());
    }

    private record ErroValidation(
            String campo,
            String mensagem) {
        public ErroValidation(ConstraintViolation<?> erro){
            this(erro.getPropertyPath().toString(), erro.getMessage()); //qual campo e qual a mensagem do Validation
        }
    }
}
