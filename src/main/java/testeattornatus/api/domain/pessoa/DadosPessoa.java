package testeattornatus.api.domain.pessoa;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import testeattornatus.api.domain.endereco.DadosEndereco;

public record DadosPessoa(
        @NotBlank
        String nome,

        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$")
        @NotBlank
        String dataNascimento
) {
}
