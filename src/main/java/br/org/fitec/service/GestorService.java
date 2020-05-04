package br.org.fitec.service;

import java.util.List;

import br.org.fitec.model.Gestor;

public interface GestorService {
	
	Gestor findByGestorMatricula(Long gestorMatricula);
	
	Gestor findByName(String name);
	
	void saveGestor(Gestor Gestor);
	
	void updateGestor(Gestor Gestor);
	
	void deleteAllGestors();
	
	List<Gestor> findAllGestors();
	
	boolean isGestorExist(Gestor Gestor);

	void deleteGestorById(long id);

	void deleteGestor(Gestor Gestor);

	Gestor findByProjetoNome(String projetoNome);
	
}
