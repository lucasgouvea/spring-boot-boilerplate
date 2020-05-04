package br.org.fitec.service;

import java.util.ArrayList;
import java.util.List;

import br.org.fitec.model.Formacao;

public interface FormacaoService {
	
	Formacao findById(Long id);
	
	Formacao findByName(String name);
	
	void saveFormacao(Formacao Formacao);
	
	void updateFormacao(Formacao Formacao);

	void deleteAllFormacaos();
	
	List<Formacao> findAllFormacaos();
	
	boolean isFormacaoExist(Formacao Formacao);

	void deleteFormacaoById(long id);

	void deleteFormacao(Formacao Formacao);

	boolean isformacaoExist(Formacao formacao);

	void deleteAllformacaos();
	
}
