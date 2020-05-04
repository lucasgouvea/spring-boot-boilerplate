package br.org.fitec.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.fitec.model.Formacao;
import br.org.fitec.model.Funcao;
import br.org.fitec.repositories.FuncaoRepository;
import br.org.fitec.util.SortArrayList;
import br.org.fitec.util.comparator.FuncaoComparator;

import javax.transaction.Transactional;


@Service("FuncaoService")
@Transactional
public class FuncaoServicecImpl implements FuncaoService{

    @Autowired
    private FuncaoRepository funcaoRepository;

    @Override
    public Funcao findById(Long id) {
        return funcaoRepository.findOne(id);
    }
    

    @Override
    public void saveFuncao(Funcao Funcao) {
        funcaoRepository.save(Funcao);
    }

    @Override
    public void updateFuncao(Funcao Funcao) {
        saveFuncao(Funcao);
    }

    @Override
    public void deleteFuncao(Funcao Funcao) {
        funcaoRepository.delete(Funcao);
    }

    @Override
    public ArrayList<Funcao> findAllFuncaos() {
        ArrayList<Funcao> funcoes = (ArrayList<Funcao>)funcaoRepository.findAll();
        funcoes = SortArrayList.sort(funcoes, "Funcao");
        return funcoes;
    }

    @Override
    public boolean isFuncaoExist(Funcao Funcao) {
        return findByName(Funcao.getNome()) != null;
    }

    @Override
    public void deleteFuncaoById(long id) {
          funcaoRepository.delete(id);
        
    }

    @Override
    public void deleteAllFuncaos() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Funcao findByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public boolean isfuncaoExist(Funcao funcao) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void deleteAllfuncaos() {
        // TODO Auto-generated method stub
        
    }
}
