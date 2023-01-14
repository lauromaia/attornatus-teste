package testeattornatus.api.repository.TestRepository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import testeattornatus.api.domain.pessoa.DadosAtualizaPessoa;
import testeattornatus.api.domain.pessoa.Pessoa;
import testeattornatus.api.domain.pessoa.PessoaRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@RunWith(SpringRunner.class)
public class PessoaRepositoryTests {

    @Autowired
    private PessoaRepository pessoaRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //Testa cadastro de Pessoas
    @Test
    public void pessoaRepository_cadastrar_retornaPessoaCadastrada(){
        Pessoa pessoa = Pessoa.builder().nome("Suzana").
                dataNascimento(LocalDate.parse("01/02/1990", formatter))
                .build();
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        Assertions.assertThat(pessoaSalva).isNotNull();
        Assertions.assertThat(pessoaSalva.getId()).isGreaterThan(0);
    }

    //Testa listar pessoas

    @Test
    public void pessoaRepository_lista_deveRetornarLista(){
        Pessoa pessoa1 = Pessoa.builder().nome("Suzana").
                dataNascimento(LocalDate.parse("01/02/1990", formatter))
                .build();
        Pessoa pessoa2 = Pessoa.builder().nome("Joao").
                dataNascimento(LocalDate.parse("22/10/1994", formatter))
                .build();

        Pessoa pessoaSalva1 = pessoaRepository.save(pessoa1);
        Pessoa pessoaSalva2 = pessoaRepository.save(pessoa2);

        List<Pessoa> listaPessoas = pessoaRepository.findAll();

        Assertions.assertThat(listaPessoas).isNotNull();
        Assertions.assertThat(listaPessoas).size().isEqualTo(3);
        //O arquivo data.sql já está cadastrando automaticamente mais uma pessoa quando o spring é inicializado
    }

    //Testa detalhar pessoa
    @Test
    public void pessoaRepository_detalha_retornaPessoaporId(){
        Pessoa pessoa = Pessoa.builder().nome("Suzana").
                dataNascimento(LocalDate.parse("01/02/1990", formatter))
                .build();
        pessoaRepository.save(pessoa);

        Pessoa pessoaPorId = pessoaRepository.getReferenceById(pessoa.getId());

        Assertions.assertThat(pessoaPorId).isNotNull();
        Assertions.assertThat(pessoaPorId.getNome()).isEqualTo("Suzana");
    }

    //Testa editar dados da pessoa
    @Test
    public void pessoaRepository_editar_retornaPessoaporEditada() {
        Pessoa pessoa = Pessoa.builder().nome("Suzana").
                dataNascimento(LocalDate.parse("01/02/1990", formatter))
                .build();
        pessoaRepository.save(pessoa);

        Pessoa pessoaPorId = pessoaRepository.getReferenceById(pessoa.getId());

        pessoaPorId.atualizar(new DadosAtualizaPessoa(pessoaPorId.getId(), "Novo nome",
                pessoaPorId.getDataNascimento(), null));

        Assertions.assertThat(pessoaPorId).isNotNull();
        Assertions.assertThat(pessoaPorId.getNome()).isEqualTo("Novo nome");
    }



}
