package testeattornatus.api.domain.pessoa;

import jakarta.validation.constraints.NotNull;
import testeattornatus.api.domain.endereco.DadosEndereco;
import testeattornatus.api.domain.endereco.Endereco;

import java.time.LocalDate;

public record DadosAtualizaPessoa(
        @NotNull
        Long id,
        String nome,
        LocalDate dataNascimento,
        DadosEndereco endereco
        ) {
}
