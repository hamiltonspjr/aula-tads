package br.edu.ifsul.cstsi.aulatads.api.advogado;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/advogados")
public class AdvogadoController {
    @Autowired
    private AdvogadoService service;

    @GetMapping
    public ResponseEntity<Page<AdvogadoDTOResponse>> selectAll(@PageableDefault(size = 5, sort = "nome")Pageable paginacao) {
        return ResponseEntity.ok(service.getAdvogados(paginacao).map(AdvogadoDTOResponse::new));
    }

    @GetMapping("{codadvogado}")
    public ResponseEntity<AdvogadoDTOResponse> selectByCodAdvogado(@PathVariable("codadvogado") Integer codadvogado) {
        var advogado = service.getAdvogadoByCodadvogado(codadvogado);
        if(advogado.isPresent()){
            return ResponseEntity.ok(new AdvogadoDTOResponse(advogado.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<AdvogadoDTOResponse>> selectByNome(@PathVariable("nome") String nome) {
        var advogados = service.getAdvogadosByNome(nome);
        return advogados.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(advogados.stream().map(AdvogadoDTOResponse::new).collect(Collectors.toList()));
    }
    @PostMapping
    public ResponseEntity<URI> insert(@Valid @RequestBody AdvogadoDTOPost advogadoDTOPost, UriComponentsBuilder uriComponentsBuilder) {
        var advogado = service.insert(new Advogado(
                null,
                advogadoDTOPost.oab(),
                advogadoDTOPost.nome(),
                advogadoDTOPost.endereco(),
                advogadoDTOPost.bairro(),
                advogadoDTOPost.cep(),
                advogadoDTOPost.email(),
                null
        ));
        var location = uriComponentsBuilder.path("api/advogados/{codadvogado}").buildAndExpand(advogado.getCodadvogado()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{codadvogado}")
    public ResponseEntity<AdvogadoDTOResponse> update(@PathVariable("codadvogado") Integer codadvogado, @Valid @RequestBody AdvogadoDTOPut advogadoDTOPut) {
        var advogado = service.update(new Advogado(
                codadvogado,
                advogadoDTOPut.oab(),
                advogadoDTOPut.nome(),
                advogadoDTOPut.endereco(),
                advogadoDTOPut.bairro(),
                advogadoDTOPut.cep(),
                advogadoDTOPut.email(),
                null
        ));
        return advogado != null ?
                ResponseEntity.ok(new AdvogadoDTOResponse(advogado)):
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("{codadvogado}")
    public ResponseEntity delete(@PathVariable("codadvogado") Integer codAdvogado) {
        return service.delete(codAdvogado) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
