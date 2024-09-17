package br.edu.ifsul.cstsi.aulatads.api;

import br.edu.ifsul.cstsi.aulatads.AulaTadsApplication;
import br.edu.ifsul.cstsi.aulatads.BaseAPIIntegracaoTest;
import br.edu.ifsul.cstsi.aulatads.CustomPageImplementacao;
import br.edu.ifsul.cstsi.aulatads.api.advogado.Advogado;
import br.edu.ifsul.cstsi.aulatads.api.advogado.AdvogadoDTOPost;
import br.edu.ifsul.cstsi.aulatads.api.advogado.AdvogadoDTOPut;
import br.edu.ifsul.cstsi.aulatads.api.advogado.AdvogadoDTOResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

@SpringBootTest(classes = AulaTadsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AdvogadoControllerIntegracaoTest extends BaseAPIIntegracaoTest {

    private ResponseEntity<AdvogadoDTOResponse> getAdvogado(String url) {
        return get(url, AdvogadoDTOResponse.class);
    }

    private ResponseEntity<CustomPageImplementacao<AdvogadoDTOResponse>> getAdvogadosPageble(String url) {
        var headers = getHeaders();

        return rest.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {}
        );
    }

    private ResponseEntity<List<AdvogadoDTOResponse>> getAdvogadosList(String url) {
        var headers = getHeaders();

        return rest.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});
    }

    @Test
    @DisplayName("Espera uma página, testa se tem 5 objetos, busca por página, de tamanho 5, e testa se tem 5 objetos")
    public void selectAllEsperaUmPaginaCom5ObjetosEUmaPaginaDe5objetos() {
        var page = getAdvogadosPageble("/api/advogados").getBody();

        assertNotNull(page);
        assertEquals(5, page.stream().count());

        page = getAdvogadosPageble("/api/advogados?page=0&size=5").getBody();

        assertNotNull(page);
        assertEquals(5, page.stream().count());
    }

    @Test
    public void selectByNome() {
        assertEquals(1, getAdvogadosList("/api/advogados/nome/Rodrigo").getBody().size());
        assertEquals(1, getAdvogadosList("/api/advogados/nome/Yuri").getBody().size());
        assertEquals(2, getAdvogadosList("/api/advogados/nome/Gabriel").getBody().size());
        assertEquals(1, getAdvogadosList("/api/advogados/nome/Vinicius").getBody().size());
        assertEquals(1, getAdvogadosList("/api/advogados/nome/Gabriel fiss").getBody().size());

        assertEquals(HttpStatus.NO_CONTENT, getAdvogadosList("/api/advogados/nome/xxx").getStatusCode());
    }
    @Test
    public void selectByCodAdvogado() {
        assertNotNull(getAdvogado("/api/advogados/1"));
        assertNotNull(getAdvogado("/api/advogados/2"));
        assertNotNull(getAdvogado("/api/advogados/3"));
        assertNotNull(getAdvogado("/api/advogados/4"));
        assertNotNull(getAdvogado("/api/advogados/5"));
        assertEquals(HttpStatus.NOT_FOUND, getAdvogado("/api/advogados/100").getStatusCode());
    }

    @Test
    public void testInsertEspera204CreatedE404ENotFound() {
        var AdvogadoDTOPost = new AdvogadoDTOPost(
                "98765",
                "hamilton",
                "rua rubi",
                "sapucaias",
                "32071182",
                "hamiltonjunior111@gmail.com"
        );

        var response = post("/api/advogados", AdvogadoDTOPost, null);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        var location = response.getHeaders().get("location").get(0);
        var ad = getAdvogado(location).getBody();
        assertNotNull(ad);
        assertEquals("98765", ad.oab());
        assertEquals("hamilton", ad.nome());
        assertEquals("rua rubi", ad.endereco());
        assertEquals("sapucaias", ad.bairro());
        assertEquals("32071182", ad.cep());
        assertEquals("hamiltonjunior111@gmail.com", ad.email());
        delete(location, null);

        assertEquals(HttpStatus.NOT_FOUND, getAdvogado(location).getStatusCode());

    }

    @Test
    public void testUpdateEspera200OkE404ENotFound() {
        var AdvogadoDTOPost = new AdvogadoDTOPost(
                "98765",
                "hamilton",
                "rua rubi",
                "sapucaias",
                "32071182",
                "hamiltonjunior111@gmail.com"
        );
        var responsePost = post("/api/advogados", AdvogadoDTOPost, null);
        assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());
        var location = responsePost.getHeaders().get("location").get(0);
        var adDto = getAdvogado(location).getBody();
        assertNotNull(adDto);
        assertEquals("98765", adDto.oab());
        assertEquals("hamilton", adDto.nome());
        assertEquals("rua rubi", adDto.endereco());
        assertEquals("sapucaias", adDto.bairro());
        assertEquals("32071182", adDto.cep());
        assertEquals("hamiltonjunior111@gmail.com", adDto.email());

        var AdvogadoDTOPut = new AdvogadoDTOPut(
                "987654",
                "hamilton de souza",
                "rua rubi, 85",
                "sapucaias",
                "32071182",
                "hamiltonjunior111@gmail.com"
        );

        var responsePut = put(location, AdvogadoDTOPut, AdvogadoDTOResponse.class);
        assertEquals(HttpStatus.OK, responsePut.getStatusCode());
        delete(location, null);

        assertEquals(HttpStatus.NOT_FOUND, getAdvogado(location).getStatusCode());


    }

    @Test
    public void testDeleteEspera200OkE404NotFound() {
        var advogado = new Advogado();
        advogado.setNome("Hamilton");
        advogado.setOab("136");
        advogado.setEmail("hamilton@gmail.com");
        advogado.setEndereco("rua rubi");
        advogado.setCep("96077460");
        advogado.setBairro("Centro");
        var responsePost = post("/api/advogados", advogado, null);
        assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());
        var location = responsePost.getHeaders().get("location").get(0);
        var ad = getAdvogado(location).getBody();
        assertNotNull(ad);
        assertEquals("Hamilton", ad.nome());
        assertEquals("rua rubi", ad.endereco());

        var responseDelete = delete(location, null);

        assertEquals(HttpStatus.OK, responseDelete.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, getAdvogado(location).getStatusCode());

    }
    @Test
    public void testGetNotFoundEspera404NotFound() {
        var response = getAdvogado("/api/advogados/1000");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
