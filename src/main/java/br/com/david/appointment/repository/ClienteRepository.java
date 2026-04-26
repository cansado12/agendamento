package br.com.david.appointment.repository;


import br.com.david.appointment.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {



    boolean existsByTelefone(String telefone);
}
