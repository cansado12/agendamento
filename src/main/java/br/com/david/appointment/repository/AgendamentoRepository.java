package br.com.david.appointment.repository;

import br.com.david.appointment.model.Agendamento;
import br.com.david.appointment.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {


    boolean existsByInicioLessThanAndTerminoGreaterThan(LocalDateTime fim,
                                                        LocalDateTime inicio);



    Page<Agendamento> findByInicioGreaterThanEqualAndInicioLessThan(LocalDateTime inicio, LocalDateTime fim, Pageable pageable );


    List<Agendamento> findByStatus(Status status);

    Page<Agendamento> findByClienteId(Long clienteId, Pageable pageable);

    boolean existsByIdNotAndInicioLessThanAndTerminoGreaterThan(
            Long id,
            LocalDateTime termino,
            LocalDateTime inicio
    );

}



