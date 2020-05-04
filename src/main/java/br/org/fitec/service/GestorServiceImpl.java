package br.org.fitec.service;

import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.fitec.model.Funcionario;
import br.org.fitec.model.Gestor;
import br.org.fitec.model.Projeto;
import br.org.fitec.model.Time;
import br.org.fitec.repositories.GestorRepository;

import javax.transaction.Transactional;


@Service("GestorService")
@Transactional
public class GestorServiceImpl implements GestorService{

	@Autowired
	private GestorRepository gestorRepository;

	@Override
	public Gestor findByGestorMatricula(Long gestorMatricula) {
	   return gestorRepository.findGestorById(gestorMatricula);
	}


	@Override
	public void saveGestor(Gestor Gestor) {
		gestorRepository.save(Gestor);
	}

	@Override
	public void updateGestor(Gestor Gestor) {
		saveGestor(Gestor);
	}

	@Override
	public void deleteGestor(Gestor Gestor) {
		gestorRepository.delete(Gestor);
	}

	@Override
	public List<Gestor> findAllGestors() {
		return gestorRepository.findAll();
	}

	@Override
	public boolean isGestorExist(Gestor Gestor) {
		return findByName(Gestor.getNome()) != null;
	}

	@Override
	public void deleteGestorById(long id) {
	      gestorRepository.delete(id);
		
	}

	@Override
	public void deleteAllGestors() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Gestor findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}


    @Override
    public Gestor findByProjetoNome(String projetoNome) {
        Gestor gestor = gestorRepository.findByProjetoNome(projetoNome);
        return gestor;
    }
	
}
