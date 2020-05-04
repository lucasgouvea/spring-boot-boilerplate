package br.org.fitec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.fitec.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Usuario findById(long id);
}
