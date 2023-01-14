package testeattornatus.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import testeattornatus.api.domain.pessoa.*;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

//Cadastra pessoa, utilizando nome e data de nascimento como obrigatórios.
    @PostMapping
    public ResponseEntity cadastraPessoa(@RequestBody @Valid DadosPessoa dados, UriComponentsBuilder uriBuilder){
        var pessoa = new Pessoa(dados);
        pessoaRepository.save(pessoa);
        var uri = uriBuilder.path("/pessoas/{id}").buildAndExpand(pessoa.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhamentoPessoa(pessoa));
    }
//Lista pessoas, devolvendo paginação customizável
    @GetMapping
    public ResponseEntity<Page<DetalhamentoPessoa>> listarPessoa(@PageableDefault(size = 10, sort={"nome"}) Pageable paginacao){
        var page = pessoaRepository.findAll(paginacao).map(DetalhamentoPessoa::new);
        return ResponseEntity.ok(page);
    }
//Edita pessoas de acordo com o id
    @PutMapping
    @Transactional
    public ResponseEntity editarPessoa(@RequestBody @Valid DadosAtualizaPessoa dados){
        var pessoa = pessoaRepository.getReferenceById(dados.id());
        pessoa.atualizar(dados);

        return ResponseEntity.ok(new DetalhamentoPessoa(pessoa));
    }
    //Detalha dados de cada pessoa
    @GetMapping("/{id}")
    public ResponseEntity detalhaPessoa(@PathVariable Long id){
        var pessoa = pessoaRepository.getReferenceById(id);
        return ResponseEntity.ok(new DetalhamentoPessoa(pessoa));
    }




}
