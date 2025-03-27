package br.com.trapp.cadastroagendabackend.repository;

import br.com.trapp.cadastroagendabackend.domain.PropriedadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropriedadeRepository extends JpaRepository<PropriedadeEntity, String> {
}
