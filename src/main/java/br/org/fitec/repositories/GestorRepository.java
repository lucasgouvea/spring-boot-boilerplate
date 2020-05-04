package br.org.fitec.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.org.fitec.model.Gestor;

public interface GestorRepository extends JpaRepository<Gestor, Long>{


    @Query("SELECT NEW br.org.fitec.model.Gestor(g.gestorMatricula, g.nome, g.fileId, g.fileName) " +
    "FROM Gestor g")
    ArrayList<Gestor> findAll();
    
    Gestor findByNome(String nome);
    
    @Query("SELECT NEW Gestor(g.gestorMatricula, g.nome) FROM Gestor g" +
    " WHERE g.gestorMatricula = ?#{[0]}")
    Gestor findGestorById(long id);
    
    @Query("SELECT NEW Gestor(g.gestorMatricula, g.nome) FROM Gestor g")
    List<Gestor> findAllCustom();

    @Query("SELECT NEW br.org.fitec.model.Gestor(g.gestorMatricula, g.nome, g.fileId, g.fileName) " +
            "FROM Gestor g WHERE g.gestorMatricula = "
            + "(SELECT p.gestor.gestorMatricula FROM Projeto p WHERE p.nome = ?#{[0]})")
    Gestor findByProjetoNome(String projetoNome);

}