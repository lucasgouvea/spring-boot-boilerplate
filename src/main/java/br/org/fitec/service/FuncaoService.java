package br.org.fitec.service;

import java.util.List;

import br.org.fitec.model.Funcao;

public interface FuncaoService {
	
	Funcao findById(Long id);
	
	Funcao findByName(String name);
	
	void saveFuncao(Funcao Funcao);
	
	void updateFuncao(Funcao Funcao);
	
	void deleteAllFuncaos();
	
	List<Funcao> findAllFuncaos();
	
	boolean isFuncaoExist(Funcao Funcao);

	void deleteFuncaoById(long id);

	void deleteFuncao(Funcao Funcao);

	boolean isfuncaoExist(Funcao funcao);

	void deleteAllfuncaos();
	
}
