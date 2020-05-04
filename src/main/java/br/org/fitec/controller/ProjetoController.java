package br.org.fitec.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.fitec.model.Funcionario;
import br.org.fitec.model.Gestor;
import br.org.fitec.model.Projeto;
import br.org.fitec.model.Time;
import br.org.fitec.service.FuncionarioService;
import br.org.fitec.service.ProjetoService;
import br.org.fitec.service.TimeService;
import br.org.fitec.service.UsuarioService;
import br.org.fitec.util.ValidateUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@CrossOrigin(origins = "*" )
public class ProjetoController {
 
    public static final Logger logger = LoggerFactory.getLogger(ProjetoController.class);
    public static final ValidateUtil validateUtil = ValidateUtil.getInstance();
    
    @Autowired
    FuncionarioService funcionarioService;
    @Autowired
    ProjetoService projetoService;
    @Autowired
    TimeService timeService;    
    @Autowired
    UsuarioService userService;

    /********** Criar Projeto **********/    
  
    @RequestMapping(value = "/projeto/", method = RequestMethod.POST)
    public ResponseEntity<String> createProjeto(@RequestHeader String authenticationToken,
    		@RequestHeader int role, @RequestBody Projeto projeto) throws IOException {
    	
    	if(!validateUtil.validate(authenticationToken, role)) {
    		return new ResponseEntity(HttpStatus.FORBIDDEN);
    	}
    	
        logger.info("Criando Projeto: " + projeto.getNome());
        projetoService.saveProjeto(projeto);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    
    /********** Buscar todos os Projetos **********/    
    
    @RequestMapping(value = "/projeto/", method = RequestMethod.GET)
    public ResponseEntity<List<Projeto>> listAllProjetos(@RequestHeader String authenticationToken) {
    	
    	if(!validateUtil.validateUsuario(authenticationToken)) {
    		return new ResponseEntity(HttpStatus.FORBIDDEN);
    	}
    	List<Projeto> projetos = projetoService.findAllProjetos();
        if (projetos.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Projeto>>(projetos, HttpStatus.OK);
    }
    
    /* Read Projetos by Gestor */
    
    @RequestMapping(value = "/projeto-by-gestor/", method = RequestMethod.POST) 
    public ResponseEntity<List<Projeto>> listAllProjetosByGestor(@RequestHeader String authenticationToken,
            @RequestBody Gestor gestor) throws IOException {
        
        if(!validateUtil.validateUsuario(authenticationToken)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);           
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        List<Projeto> projetos = projetoService.findByGestor(gestor);
        List<Projeto> projetosDAO = projetos;
        for(Projeto projeto : projetosDAO) {
            projeto.setGestor(null);
            projeto.setTimes(null);
        }
        if (projetos.isEmpty()) {
            logger.info("Returning " + HttpStatus.NO_CONTENT);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("Returning " + HttpStatus.OK);
        return new ResponseEntity<List<Projeto>>(projetos, HttpStatus.OK);
    }
    
    /********** Pegar Projeto único **********/
    
	@RequestMapping(value = "/projeto/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProjeto(@RequestHeader String authenticationToken, 
    		@RequestHeader int role, @PathVariable("id") long id) {
		
    	if(!validateUtil.validate(authenticationToken, role)) {
    		return new ResponseEntity(HttpStatus.FORBIDDEN);
    	}    	
        logger.info("Buscando Projeto com o id {}", id);
        Projeto Projeto = projetoService.findById(id);
        if (Projeto == null) {
            logger.error("Projeto com id {} não foi encontrado.", id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Projeto>(Projeto, HttpStatus.OK);
    }
	
    /********** Fetch Projetos with gestor_matricula = null **********/    
    
    @RequestMapping(value = "/projeto-gestor-matricula-null/", method = RequestMethod.GET) 
    public ResponseEntity<List<Projeto>> listProjetosGestorMatriculaNull(@RequestHeader String authenticationToken) 
            throws IOException {
        if(!validateUtil.validateUsuario(authenticationToken)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        List<Projeto> projetos = projetoService.findByGestorMatriculaIsNull();
        if (projetos.isEmpty()) {
            logger.info("Returning " + HttpStatus.NO_CONTENT);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("Returning " + HttpStatus.OK);
        return new ResponseEntity<List<Projeto>>(projetos, HttpStatus.OK);
    }
	
	/********** Atualizar Projeto **********/
    
    @RequestMapping(value = "/projeto/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProjeto(@RequestHeader String authenticationToken,
    		@RequestHeader int role, @PathVariable("id") long id, @RequestBody Projeto projetoNew) {
    	if(!validateUtil.validate(authenticationToken, role)) {
    		return new ResponseEntity(HttpStatus.FORBIDDEN);
    	}
    	
        logger.info("Buscando o Projeto com o id {}", id);
        Projeto projetoOld = projetoService.findById(id);
        if (projetoOld == null) {
            logger.error("Não foi possível atualizar. O Projeto com id {} não foi encontrado.", id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        projetoService.updateProjeto(projetoNew);
        return new ResponseEntity<Projeto>(projetoNew, HttpStatus.OK);
    }
    
    /********** Update Projeto's gestorMatricula (set to null) **********/
    
    @RequestMapping(value = "/projeto-gestormatricula/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTimeProjetoId(@RequestHeader String authenticationToken,
            @RequestHeader int role, @PathVariable("id") long id, @RequestBody Projeto projetoNew) {
        
        if(!validateUtil.validate(authenticationToken, role)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        
        Projeto projetoOld = projetoService.findById(id);
        if (projetoOld == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        projetoService.updateProjetoGestorMatricula(projetoNew);
        return new ResponseEntity<Time>(HttpStatus.ACCEPTED);
    }
    
	/********** Deletar Projeto **********/
    
    @RequestMapping(value = "/projeto/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProjeto(@RequestHeader String authenticationToken,
    		@RequestHeader int role, @PathVariable("id") long id) {
    	
    	if(!validateUtil.validate(authenticationToken, role)) {
    		return new ResponseEntity(HttpStatus.FORBIDDEN);
    	}
    	
    	Projeto p = projetoService.findById(id);
    	String projetoNome = p.getNome();
    	List<Time> times = timeService.findByProjeto(projetoNome);
        List<Funcionario> funcionarios = funcionarioService.findByProjeto(projetoNome);
        logger.info(funcionarios.toString());
    	if(times.isEmpty() && funcionarios.isEmpty()) {
            Projeto Projeto = projetoService.findById(id);
            projetoService.deleteProjetoById(id);
            return new ResponseEntity<Projeto>(Projeto, HttpStatus.OK);    		
    	}else {
    	    return new ResponseEntity<List<String>>(HttpStatus.CONFLICT);   
             
    	}
    }
   
 
}
