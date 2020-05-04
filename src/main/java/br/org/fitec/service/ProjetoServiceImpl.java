package br.org.fitec.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.fitec.model.Gestor;
import br.org.fitec.model.Projeto;
import br.org.fitec.repositories.ProjetoRepository;
import br.org.fitec.util.SortArrayList;

import javax.transaction.Transactional;


@Service("ProjetoService")
@Transactional
public class ProjetoServiceImpl implements ProjetoService{

    @Autowired
    private ProjetoRepository projetoRepository;

    @Override
    public Projeto findById(Long id) {
        return projetoRepository.findOne(id);
    }
    

    @Override
    public void saveProjeto(Projeto Projeto) {
        projetoRepository.save(Projeto);
    }

    @Override
    public void updateProjeto(Projeto Projeto) {
        saveProjeto(Projeto);
    }

    @Override
    public void deleteProjeto(Projeto Projeto) {
        projetoRepository.delete(Projeto);
    }

    @Override
    public ArrayList<Projeto> findAllProjetos() {
        ArrayList<Projeto> projetos = (ArrayList<Projeto>) projetoRepository.findAll();
        projetos = SortArrayList.sort(projetos, "Projeto");
        return projetos;
    
    }

    @Override
    public boolean isProjetoExist(Projeto Projeto) {
        return findByName(Projeto.getNome()) != null;
    }

    @Override
    public void deleteProjetoById(long id) {
          projetoRepository.delete(id);
        
    }

    @Override
    public void deleteAllProjetos() {
        // TODO Auto-generated method stub
        
    }


    @Override
    public Projeto findByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void deleteAllprojetos() {
        // TODO Auto-generated method stub
        
    }


    @Override
    public boolean isprojetoExist(Projeto projeto) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public List<Projeto> findByGestor(Gestor gestor) {
        return projetoRepository.findByGestor(gestor);
    }


    @Override
    public List<Projeto> findByGestorMatriculaIsNull() {
        return projetoRepository.findByGestorMatriculaIsNull();
    }


    @Override
    public void updateProjetoGestorMatricula(Projeto projetoNew) {
        projetoRepository.updateProjetoGestorMatricula(projetoNew.getProjetoId());
    }    
    
    
}
