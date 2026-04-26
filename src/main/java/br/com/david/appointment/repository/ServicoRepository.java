package br.com.david.appointment.repository;

import br.com.david.appointment.model.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    Page<Servico> findByAtivoTrue(Pageable pageable);
    Optional<Servico> findByIdAndAtivoTrue(Long id);

}
