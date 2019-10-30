package br.com.desafio.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafio.domain.entities.Resultado;

public interface ResultadoRepository extends JpaRepository<Resultado, Long>{

}
