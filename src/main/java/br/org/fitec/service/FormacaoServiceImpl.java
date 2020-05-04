package br.org.fitec.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.fitec.model.Formacao;
import br.org.fitec.repositories.FormacaoRepository;
import br.org.fitec.util.SortArrayList;
import br.org.fitec.util.comparator.FormacaoComparator;

import javax.transaction.Transactional;


@Service("FormacaoService")
@Transactional
public class FormacaoServiceImpl implements FormacaoService, Comparator<Formacao>{

    
    @Autowired
    private FormacaoRepository formacaoRepository;

    @Override
    public Formacao findById(Long id) {
        return formacaoRepository.findOne(id);
    }
    

    @Override
    public void saveFormacao(Formacao Formacao) {
        formacaoRepository.save(Formacao);
    }

    @Override
    public void updateFormacao(Formacao Formacao) {
        saveFormacao(Formacao);
    }

    @Override
    public void deleteFormacao(Formacao Formacao) {
        formacaoRepository.delete(Formacao);
    }

    @Override
    public ArrayList<Formacao> findAllFormacaos() {
        ArrayList<Formacao> formacoes = (ArrayList<Formacao>) formacaoRepository.findAll();
        formacoes = SortArrayList.sort(formacoes, "Formacao");
        return formacoes;
    }

    @Override
    public boolean isFormacaoExist(Formacao Formacao) {
        return findByName(Formacao.getNome()) != null;
    }

    @Override
    public void deleteFormacaoById(long id) {
          formacaoRepository.delete(id);
        
    }

    @Override
    public void deleteAllFormacaos() {
        // TODO Auto-generated method stub
        
    }


    @Override
    public Formacao findByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void deleteAllformacaos() {
        // TODO Auto-generated method stub
        
    }


    @Override
    public boolean isformacaoExist(Formacao formacao) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public int compare(Formacao formacao1, Formacao formacao2) {
        String formacaoNome1 = formacao1.getNome();
        String formacaoNome2 = formacao2.getNome();
        return formacaoNome1.compareTo(formacaoNome2);
    }
}
