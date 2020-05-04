package br.org.fitec.service;

import java.util.List;

import br.org.fitec.model.Gestor;
import br.org.fitec.model.Projeto;

public interface ProjetoService {
	
	Projeto findById(Long id);
	
	Projeto findByName(String name);
	
	List<Projeto> findByGestor(Gestor gestor);
	
	void deleteAllprojetos();
	
	boolean isprojetoExist(Projeto projeto);

	void saveProjeto(Projeto Projeto);

	void updateProjeto(Projeto Projeto);

	void deleteProjeto(Projeto Projeto);

	List<Projeto> findAllProjetos();

	boolean isProjetoExist(Projeto Projeto);

	void deleteProjetoById(long id);

	void deleteAllProjetos();

    List<Projeto> findByGestorMatriculaIsNull();

    void updateProjetoGestorMatricula(Projeto projetoNew);
	
}
