package testeattornatus.api.domain.pessoa;

import testeattornatus.api.domain.endereco.Endereco;

import java.time.LocalDate;

public record DetalhamentoPessoa(String nome, LocalDate dataNascimento) {
    public DetalhamentoPessoa(Pessoa pessoa){
        this(pessoa.getNome(), pessoa.getDataNascimento());
    }
}
