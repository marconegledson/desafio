package br.com.desafio.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.desafio.domain.entities.Calculo;

public interface CalculoRepository extends JpaRepository<Calculo, Long>{

	@Query("Select c From Calculo c Inner Join Fetch c.usuario u Where u.id = :idUsuario")
	List<Calculo> findByUsuario(Long idUsuario);

}
