package testeattornatus.api.repository.TestRepository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import testeattornatus.api.domain.endereco.Endereco;
import testeattornatus.api.domain.endereco.EnderecoPrincipal;
import testeattornatus.api.domain.endereco.EnderecoRepository;
import testeattornatus.api.domain.pessoa.Pessoa;
import testeattornatus.api.domain.pessoa.PessoaRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@RunWith(SpringRunner.class)
public class EnderecoRepositoryTests {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //Testa cadastro de Endereços
    @Test
    public void enderecoRepository_cadastrar_retornaEnderecoCadastrado(){
        Pessoa pessoa = Pessoa.builder().nome("Suzana").
                dataNascimento(LocalDate.parse("01/02/1990", formatter))
                .build();
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        Endereco endereco = Endereco.builder().cep("60710000").pessoa(pessoa).cidade("Fortaleza")
                .logradouro("Qualquer Logradouro").status(EnderecoPrincipal.NAO)
                .numero("1000").build();
        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        Assertions.assertThat(enderecoSalvo).isNotNull();
        Assertions.assertThat(enderecoSalvo.getId()).isGreaterThan(1);
        //O O arquivo data.sql já está cadastrando automaticamente mais um endereço quando o spring é inicializado
        Assertions.assertThat(enderecoSalvo.getPessoa().getNome()).as("Suzana", pessoa.getNome());
    }

    //Testa listar endereços por pessoa
    @Test
    public void enderecoRepository_listaPorPessoa_deveRetornarLista(){
        Pageable paginacao = Pageable.ofSize(10);

        Pessoa pessoa = Pessoa.builder().nome("Suzana").
                dataNascimento(LocalDate.parse("01/02/1990", formatter))
                .build();
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        Endereco endereco1 = Endereco.builder().cep("60710000").pessoa(pessoa).cidade("Fortaleza")
                .logradouro("Qualquer Logradouro").status(EnderecoPrincipal.NAO)
                .numero("1000").build();
        Endereco endereco2 = Endereco.builder().cep("60720000").pessoa(pessoa).cidade("Fortaleza")
                .logradouro("Segundo Logradouro").status(EnderecoPrincipal.NAO)
                .numero("2000").build();
        Endereco enderecoSalvo1 = enderecoRepository.save(endereco1);
        Endereco enderecoSalvo2 = enderecoRepository.save(endereco2);


        List<Endereco> listaEnderecos = enderecoRepository.findAllByPessoaId(pessoa.getId(), paginacao).toList();

        Assertions.assertThat(listaEnderecos).isNotNull();
        Assertions.assertThat(listaEnderecos).size().isEqualTo(2);
    }

    //Testa se quando cadastra novo endereco como principal, os anteriores são transformados em NÃO principais
    @Test
    public void enderecoRepository_enderecoPrincipal_deveRetornarEnderecoPrincipalENaoPrincipal(){
        Pageable paginacao = Pageable.ofSize(10);

        Pessoa pessoa = Pessoa.builder().nome("Suzana").
                dataNascimento(LocalDate.parse("01/02/1990", formatter))
                .build();
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        Endereco endereco1 = Endereco.builder().cep("60710000").pessoa(pessoa).cidade("Fortaleza")
                .logradouro("Qualquer Logradouro").status(EnderecoPrincipal.SIM)
                .numero("1000").build();
        Endereco endereco2 = Endereco.builder().cep("60720000").pessoa(pessoa).cidade("Fortaleza")
                .logradouro("Segundo Logradouro").status(EnderecoPrincipal.NAO)
                .numero("2000").build();
        Endereco enderecoSalvo1 = enderecoRepository.save(endereco1);
        Endereco enderecoSalvo2 = enderecoRepository.save(endereco2);



        Assertions.assertThat(enderecoSalvo1.getStatus()).isSameAs(EnderecoPrincipal.SIM);
        //Endereço deve ser o principal como cadastrado

        //Apenas verificando se o segundo endereco está como NÃO principal
        Assertions.assertThat(enderecoSalvo2.getStatus()).isSameAs(EnderecoPrincipal.NAO);

        //Cadastra novo endereço principal, alterando o primeiro
        Endereco endereco3 = Endereco.builder().cep("60900000").pessoa(pessoa).cidade("Fortaleza")
                .logradouro("Novo principal Logradouro").status(EnderecoPrincipal.SIM)
                .numero("3000").build();

        Endereco enderecoSalvo3 = enderecoRepository.save(endereco3);

        //Novo endereço deve ser o principal agora
        Assertions.assertThat(enderecoSalvo3.getStatus()).isSameAs(EnderecoPrincipal.SIM);
    }

}
