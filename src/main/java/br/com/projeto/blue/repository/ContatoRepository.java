package br.com.projeto.blue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.projeto.blue.model.Contato;

import java.util.Optional;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long>{
	Optional<Contato> findByEmail(String email);
}

