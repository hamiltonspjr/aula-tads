package br.edu.ifsul.cstsi.aulatads;

import br.edu.ifsul.cstsi.aulatads.api.autenticacao.AutenticacaoService;
import br.edu.ifsul.cstsi.aulatads.api.infra.security.TokenService;
import br.edu.ifsul.cstsi.aulatads.api.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpMethod.*;

@SpringBootTest(classes = AulaTadsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseAPIIntegracaoTest {
    @Autowired
    protected TestRestTemplate rest;
    @Autowired
    private AutenticacaoService authService;
    @Autowired
    private TokenService tokenService;

    private String jwtToken = "";

    @BeforeEach
    public void setupTest() {
        Usuario user = (Usuario) authService.loadUserByUsername("admin@gmail.com");
        assertNotNull(user);
        jwtToken = tokenService.geraToken(user);
        assertNotNull(jwtToken);
    }

    protected HttpHeaders getHeaders() {
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add(org.springframework.http.HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
        headers.add(org.springframework.http.HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }

    protected  <T> ResponseEntity<T> post(String url, Object body, Class<T> responseType) {
        HttpHeaders headers = getHeaders();

        return rest.exchange(url, POST, new HttpEntity<>(body, headers), responseType);
    }

    protected <T> ResponseEntity<T> put(String url, Object body, Class<T> responseType) {
        HttpHeaders headers = getHeaders();

        return rest.exchange(url, PUT, new HttpEntity<>(body, headers), responseType);
    }
    protected <T> ResponseEntity<T> get(String url, Class<T> responseType) {

        HttpHeaders headers = getHeaders();

        return rest.exchange(url, GET, new HttpEntity<>(headers), responseType);
    }
    protected <T> ResponseEntity<T> delete(String url, Class<T> responseType) {

        HttpHeaders headers = getHeaders();

        return rest.exchange(url, DELETE, new HttpEntity<>(headers), responseType);
    }
}
