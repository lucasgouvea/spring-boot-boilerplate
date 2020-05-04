package br.org.fitec.service;

import java.util.List;

import br.org.fitec.model.Usuario;

public interface UsuarioService {

	Usuario findById(long id);
	void saveUsuario(Usuario usuario);
	void updateUsuario(Usuario usuario);
	void deleteUsuario(Usuario usuario);
	List<Usuario> findAllUsuarios();
	void deleteUsuarioById(long id);
	
}
