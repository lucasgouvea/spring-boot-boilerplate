package br.org.fitec.service;

import java.util.List;

import br.org.fitec.model.Funcao;
import br.org.fitec.model.Funcionario;
import br.org.fitec.model.Time;

public interface FuncionarioService {
	
	Funcionario findById(Long id);
	
	Funcionario findByName(String name);
	
	void saveFuncionario(Funcionario Funcionario);
	
	void updateFuncionario(Funcionario Funcionario);
	
	void deleteAllFuncionarios();
	
	boolean isFuncionarioExist(Funcionario Funcionario);

	void deleteFuncionarioById(long id);

	void deleteFuncionario(Funcionario Funcionario);

	Funcionario findByMatricula(long matricula);

	List<Funcionario> findByTimeId(long id);

	List<Funcionario> findByTime(String nomeTime);

	List<Funcionario> findByProjetoId(long id);

    List<Funcionario> findByFuncaoId(long id);
    
    List<Funcionario> findByTimeIsNull();

    List<Funcionario> findByFuncao(Funcao funcao);

    List<Funcionario> findByFormacaoId(long id);

    List<Funcionario> findByProjeto(String projetoNome);

    List<Funcionario> findAll();
	
}
