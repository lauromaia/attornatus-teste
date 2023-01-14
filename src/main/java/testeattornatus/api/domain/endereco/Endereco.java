package testeattornatus.api.domain.endereco;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import testeattornatus.api.domain.pessoa.Pessoa;

@Entity(name = "endereco")
@Table(name = "enderecos")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logradouro;
    private String cep;
    private String numero;
    private String cidade;

    private EnderecoPrincipal status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Pessoa pessoa;


    public Endereco(DadosEndereco dados, Pessoa novaPessoa) {
        this.cidade = dados.cidade();
        this.numero = dados.numero();
        this.cep = dados.cep();
        this.logradouro = dados.logradouro();
        this.pessoa = novaPessoa;
        this.status = dados.status();
    }


    public void atualizar(DadosEndereco dados) {
        if(dados.logradouro() != null){
            this.logradouro = dados.logradouro();
        }
        if(dados.cep() != null){
            this.cep = dados.cep();
        }
        if(dados.numero() != null){
            this.numero = dados.numero();
        }
        if(dados.cidade() != null){
            this.cidade = dados.cidade();
        }
    }

    public void atualizaStatus(EnderecoPrincipal novoStatus){
        this.status = novoStatus;
    }

}
