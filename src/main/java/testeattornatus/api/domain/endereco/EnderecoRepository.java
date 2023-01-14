package testeattornatus.api.domain.endereco;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Page<Endereco> findAllByPessoaId(Long id, Pageable page);
}

