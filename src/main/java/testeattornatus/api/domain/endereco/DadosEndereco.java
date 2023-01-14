package testeattornatus.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
        @NotNull
        Long idPessoa,

        @NotBlank
        String logradouro,

        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String cep,

        @NotBlank
        String numero,

        @NotBlank
        String cidade,
        @NotNull
        EnderecoPrincipal status

) {
}
