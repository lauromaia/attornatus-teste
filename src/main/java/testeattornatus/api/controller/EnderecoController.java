package testeattornatus.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import testeattornatus.api.domain.endereco.*;
import testeattornatus.api.domain.pessoa.PessoaRepository;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    //Cadastra endereço com o ID da pessoa, checa se é o endereço principal e atualiza o cadastro.
    @PostMapping
    public ResponseEntity cadastrarEndereco(@RequestBody @Valid DadosEndereco dadosEndereco, UriComponentsBuilder uriBuilder){
        var pessoa = pessoaRepository.getReferenceById(dadosEndereco.idPessoa());
        var endereco = new Endereco(dadosEndereco, pessoa);
        var listaEnderecos = pessoa.getListaEnderecos();
        if(endereco.getStatus() == EnderecoPrincipal.SIM){
            for (Endereco end:listaEnderecos) {
                end.atualizaStatus(EnderecoPrincipal.NAO);
            }
        }
        enderecoRepository.save(endereco);
        pessoa.adicionaEndereco(endereco);
        var uri = uriBuilder.path("/endereco/{id}").buildAndExpand(endereco.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhamentoEndereco(endereco));
    }
    // Lista os endereços da pessoa, mostrando qual o endereço principal
    @GetMapping("/{id}")
    public ResponseEntity<Page<DetalhamentoEndereco>> listaEnderecosPorPessoa(@PathVariable Long id,
            @PageableDefault(size = 10) Pageable paginacao){
        var page =  enderecoRepository.findAllByPessoaId(id, paginacao).map(DetalhamentoEndereco::new);
        return ResponseEntity.ok(page);
    }



}
