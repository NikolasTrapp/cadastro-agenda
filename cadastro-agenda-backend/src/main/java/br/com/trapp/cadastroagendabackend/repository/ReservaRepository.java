package br.com.trapp.cadastroagendabackend.repository;

import br.com.trapp.cadastroagendabackend.domain.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, UUID> {


}
