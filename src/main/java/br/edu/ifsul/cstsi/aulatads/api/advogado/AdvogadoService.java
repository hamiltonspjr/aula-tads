package br.edu.ifsul.cstsi.aulatads.api.advogado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class AdvogadoService {
    @Autowired
    private AdvogadoRepository rep;

    public Page<Advogado> getAdvogados(Pageable pageable) {
        return rep.findAll(pageable);
    }
    public Optional<Advogado> getAdvogadoByCodadvogado(Integer codadvogado) {
        return rep.findByCodadvogado(codadvogado);
    }
    public List<Advogado> getAdvogadosByNome(String nome) {
        return rep.findByNomeStartsWithOrderByNome(nome);
    }

    public Advogado insert(Advogado advogado) {
        Assert.isNull(advogado.getCodadvogado(), "Não foi possível cadastrar um advogado");
        return rep.save(advogado);
    }
    public Advogado update(Advogado advogado) {

        Assert.notNull(advogado.getCodadvogado(), "Não foi possível atualizar o advogado");
        var optional = rep.findById(advogado.getCodadvogado());
        if (optional.isPresent()) {
            var db = optional.get();
            System.out.print("teste");
            System.out.println(db);
            db.setOab(advogado.getOab());
            db.setNome(advogado.getNome());
            db.setEndereco(advogado.getEndereco());
            db.setBairro(advogado.getBairro());
            db.setCep(advogado.getCep());
            db.setEmail(advogado.getEmail());
            return rep.save(db);
        }
        return null;
    }

    public boolean delete(Integer codadvogado) {
        var optional = rep.findByCodadvogado(codadvogado);
        if (optional.isPresent()) {
            rep.deleteById(codadvogado);
            return true;
        }
        return false;
    }
}
