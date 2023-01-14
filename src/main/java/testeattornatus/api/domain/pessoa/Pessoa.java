package testeattornatus.api.domain.pessoa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import testeattornatus.api.domain.endereco.Endereco;
import testeattornatus.api.domain.endereco.EnderecoPrincipal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Table(name = "pessoas")
@Entity(name = "pessoa")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    static private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoa", fetch = FetchType.LAZY)
    private List<Endereco> listaEnderecos;

    public Pessoa(DadosPessoa dados) {
        this.nome = dados.nome();
        this.dataNascimento = LocalDate.parse(dados.dataNascimento(), formatter);
    }

    public void atualizar(DadosAtualizaPessoa dados) {
        if(dados.nome() != null){
            this.nome = dados.nome();
        }
        if(dados.dataNascimento() != null){
            this.dataNascimento = dados.dataNascimento();
        }
    }

    public void adicionaEndereco(Endereco endereco){
        listaEnderecos.add(endereco);
    }
}
