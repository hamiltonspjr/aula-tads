
package br.edu.ifsul.cstsi.aulatads.api;

import br.edu.ifsul.cstsi.aulatads.api.advogado.Advogado;
import br.edu.ifsul.cstsi.aulatads.api.advogado.AdvogadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AdvogadoServiceIntegracaoTest {

    @Autowired
    private AdvogadoService advogadoService;

    @Test
    @DisplayName("Busca os advogados na base de dados, espera 5")
    public void testGetAdvogadosEspera5() {
        var pageable = PageRequest.of(0, 5);
        var advogados = advogadoService.getAdvogados(pageable);
        assertEquals(5, advogados.getContent().size());

    }
    @Test
    public void testGetAdvogadoByCodadvogado() {
        var advogado = advogadoService.getAdvogadoByCodadvogado(1);

        assertNotNull(advogado);
        assertEquals("Rodrigo", advogado.get().getNome());
        assertEquals("123", advogado.get().getOab());
        assertEquals("rodrigoifsul@gmail.com", advogado.get().getEmail());
        assertEquals("96077140", advogado.get().getCep());
        assertEquals("centro", advogado.get().getBairro());
        assertEquals("rua teste", advogado.get().getEndereco());
    }

    @Test
    public void testGetAdvogadoByNome() {
        assertEquals(1, advogadoService.getAdvogadosByNome("Rodrigo").size());
        assertEquals(1, advogadoService.getAdvogadosByNome("Yuri").size());
        assertEquals(2, advogadoService.getAdvogadosByNome("Gabriel").size());
        assertEquals(1, advogadoService.getAdvogadosByNome("Vinicius").size());
        assertEquals(1, advogadoService.getAdvogadosByNome("Gabriel Fiss").size());

    }

    @Test
    public void testInsertAdvogadoEoDeleta() {
        var advogado = new Advogado();
        advogado.setNome("Hamilton");
        advogado.setOab("136");
        advogado.setEmail("hamilton@gmail.com");
        advogado.setEndereco("rua rubi");
        advogado.setCep("96077460");
        advogado.setBairro("Centro");

        var ad = advogadoService.insert(advogado);

        assertNotNull(ad);
        Integer id = ad.getCodadvogado();
        assertNotNull(id);
        ad = advogadoService.getAdvogadoByCodadvogado(id).get();
        assertNotNull(ad);

        assertEquals("Hamilton", ad.getNome());
        assertEquals("136", ad.getOab());
        assertEquals("hamilton@gmail.com", ad.getEmail());
        assertEquals("rua rubi", ad.getEndereco());
        assertEquals("96077460", ad.getCep());
        assertEquals("Centro", ad.getBairro());

        advogadoService.delete(id);
        if(advogadoService.getAdvogadoByCodadvogado(id).isPresent()){
            fail("O advogado não foi excluído");
        }
    }

    @Test
    public void testUpdateAdvogado() {
        var advogadoOriginal = advogadoService.getAdvogadoByCodadvogado(1).get();
        var advogadoMock = new Advogado();
        advogadoMock.setCodadvogado(advogadoOriginal.getCodadvogado());
        advogadoMock.setNome("Rodrigo if");
        advogadoMock.setOab("321");
        advogadoMock.setEmail("rodrigo@gmail.com");
        advogadoMock.setEndereco("rua teste 3");
        advogadoMock.setCep("96077460");
        advogadoMock.setBairro("Fragata");

        var advogadoAlterado = advogadoService.update(advogadoMock);

        assertNotNull(advogadoAlterado);
        assertEquals("Rodrigo if", advogadoAlterado.getNome());
        assertEquals("321", advogadoAlterado.getOab());
        assertEquals("rodrigo@gmail.com", advogadoAlterado.getEmail());
        assertEquals("96077460", advogadoAlterado.getCep());
        assertEquals("Fragata", advogadoAlterado.getBairro());
        assertEquals("rua teste 3", advogadoAlterado.getEndereco());

        var advogadoOrigin = advogadoService.update(advogadoOriginal);
        assertNotNull(advogadoOrigin);
    }

}
