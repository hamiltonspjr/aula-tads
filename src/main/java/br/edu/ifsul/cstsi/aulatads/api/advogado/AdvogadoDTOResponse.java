package br.edu.ifsul.cstsi.aulatads.api.advogado;

public record AdvogadoDTOResponse(Integer codAdvogado,
                                  String oab,
                                  String nome,
                                  String endereco,
                                  String bairro,
                                  String cep,
                                  String email
                                  ) {
    public AdvogadoDTOResponse(Advogado advogado) {
        this(advogado.getCodadvogado(), advogado.getOab(), advogado.getNome(), advogado.getEndereco(), advogado.getBairro(), advogado.getCep(), advogado.getEmail());
    }
}
