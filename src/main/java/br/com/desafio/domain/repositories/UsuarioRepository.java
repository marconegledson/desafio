package br.com.desafio.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafio.domain.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
