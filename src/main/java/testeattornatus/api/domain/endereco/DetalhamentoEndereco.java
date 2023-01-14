package testeattornatus.api.domain.endereco;

public record DetalhamentoEndereco(String logradouro, String numero, String cidade, String cep, String nomePessoa,
                                   Long idPessoa, EnderecoPrincipal status) {
    public DetalhamentoEndereco(Endereco endereco) {
        this(endereco.getLogradouro(), endereco.getNumero(), endereco.getCidade(), endereco.getCep(),
        endereco.getPessoa().getNome(), endereco.getPessoa().getId(), endereco.getStatus());
    }
}
