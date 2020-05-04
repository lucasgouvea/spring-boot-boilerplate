package br.org.fitec.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.fitec.controller.FuncionarioController;
import br.org.fitec.model.Funcao;
import br.org.fitec.model.Funcionario;
import br.org.fitec.repositories.FuncionarioRepository;
import br.org.fitec.util.SortArrayList;

import javax.transaction.Transactional;


@Service("funcionarioService")
@Transactional
public class FuncionarioServiceImpl implements FuncionarioService{

    public static final Logger logger = LoggerFactory.getLogger(FuncionarioController.class);
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    

    @Override
    public Funcionario findById(Long id) {
        return funcionarioRepository.findOne(id);
    }
    

    @Override
    public void saveFuncionario(Funcionario funcionario) {
        logger.info("Salvando funcionario: " + funcionario); 
        funcionarioRepository.save(funcionario);
    }

    @Override
    public void updateFuncionario(Funcionario funcionario) {
        saveFuncionario(funcionario);
    }

    @Override
    public void deleteFuncionario(Funcionario funcionario) {
        funcionarioRepository.delete(funcionario);
    }


    @Override
    public boolean isFuncionarioExist(Funcionario funcionario) {
        return findByName(funcionario.getNome()) != null;
    }

    @Override
    public void deleteFuncionarioById(long id) {
          funcionarioRepository.delete(id);
        
    }

    @Override
    public void deleteAllFuncionarios() {
    }

    @Override
    public Funcionario findByMatricula(long matricula) {
        return funcionarioRepository.findByMatricula(matricula);
    }

    @Override
    public List<Funcionario> findByTime(String nomeTime) {
        ArrayList<Funcionario> funcionarios = 
                (ArrayList<Funcionario>) funcionarioRepository.findByTimeNome(nomeTime);
        funcionarios = SortArrayList.sort(funcionarios, "Funcionario");
        return funcionarios;
    }

    @Override
    public List<Funcionario> findByTimeId(long id) {
        ArrayList<Funcionario> funcionarios =
                (ArrayList<Funcionario>) funcionarioRepository.findByTimeId(id);
        funcionarios = SortArrayList.sort(funcionarios, "Funcionario");
        return funcionarios;
    }

    @Override
    public Funcionario findByName(String name) {
        return null;
    }


    @Override
    public List<Funcionario> findByProjetoId(long id) {
        return null;
    }





    @Override
    public List<Funcionario> findByTimeIsNull() {
        return funcionarioRepository.findByTimeIsNull();
    }


    @Override
    public List<Funcionario> findByFuncao(Funcao funcao) {
        return funcionarioRepository.findByFuncao(funcao);
    }


    @Override
    public List<Funcionario> findByFormacaoId(long id) {
        logger.info("Buscando funcionarios pelo id da formacao: " + id);
        return funcionarioRepository.findByFormacaoId(id);
    }

    @Override
    public List<Funcionario> findByFuncaoId(long id) {
        logger.info("Buscando funcionarios pelo id da funcao: " + id);
        return funcionarioRepository.findByFuncaoId(id);
        
    }


    @Override
    public List<Funcionario> findByProjeto(String projetoNome) {
        logger.info("Buscando funcionarios pelo nome do projeto: " + projetoNome);
        return funcionarioRepository.findByProjetoNome(projetoNome);
    }


    @Override
    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }
    

    
}
