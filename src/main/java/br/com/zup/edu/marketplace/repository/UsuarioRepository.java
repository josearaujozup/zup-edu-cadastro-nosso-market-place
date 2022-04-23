package br.com.zup.edu.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.edu.marketplace.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
