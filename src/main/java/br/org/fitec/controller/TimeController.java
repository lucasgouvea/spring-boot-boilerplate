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
import br.org.fitec.model.Time;
import br.org.fitec.service.FuncionarioService;
import br.org.fitec.service.TimeService;
import br.org.fitec.service.UsuarioService;
import br.org.fitec.util.ValidateUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@CrossOrigin(origins = "*" )
public class TimeController {
 
    public static final Logger logger = LoggerFactory.getLogger(TimeController.class);
    public static final ValidateUtil validateUtil = ValidateUtil.getInstance();

    @Autowired
    TimeService timeService;
    @Autowired
    FuncionarioService funcionarioService;    
    @Autowired
    UsuarioService userService;

    /********** Criar Time **********/    
  
    @RequestMapping(value = "/time-criar/", method = RequestMethod.POST)
    public ResponseEntity<String> createTime(@RequestHeader String authenticationToken,
    		@RequestHeader int role, @RequestBody Time Time) throws IOException {
    	
    	if(!validateUtil.validate(authenticationToken, role)) {
    		return new ResponseEntity(HttpStatus.FORBIDDEN);
    	}
    	
        logger.info("Criando Time");
        timeService.saveTime(Time);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    
    /********** Buscar todos os times (cuidado com performance) **********/    
    
    @RequestMapping(value = "/time/", method = RequestMethod.GET) 
    public ResponseEntity<List<Time>> listAllTimes(@RequestHeader String authenticationToken) 
            throws IOException {
        
        if(!validateUtil.validateUsuario(authenticationToken)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);           
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        List<Time> times = timeService.findAllTimes();
        if (times.isEmpty()) {
            logger.info("Returning " + HttpStatus.NO_CONTENT);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("Returning " + HttpStatus.OK);
        return new ResponseEntity<List<Time>>(times, HttpStatus.OK);
    }
    
    /********** Buscar todos os times por projeto **********/    
    
    @RequestMapping(value = "/time/", method = RequestMethod.POST) 
    public ResponseEntity<List<Time>> listTimesByProjeto(@RequestHeader String authenticationToken,
    		@RequestBody String projetoNome) throws IOException {
    	
    	if(!validateUtil.validateUsuario(authenticationToken)) {
        	logger.info("Returning " + HttpStatus.FORBIDDEN);    		
    		return new ResponseEntity(HttpStatus.FORBIDDEN);
    	}
    	List<Time> times = timeService.findByProjeto(projetoNome);
        if (times.isEmpty()) {
        	logger.info("Returning " + HttpStatus.NO_CONTENT);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    	logger.info("Returning " + HttpStatus.OK);
        return new ResponseEntity<List<Time>>(times, HttpStatus.OK);
    }
    
    /********** Buscar times com projeto_id = null **********/    
    
    @RequestMapping(value = "/time-projetoidnull/", method = RequestMethod.GET) 
    public ResponseEntity<List<Time>> listTimesProjetoIdNull(@RequestHeader String authenticationToken) 
            throws IOException {
        if(!validateUtil.validateUsuario(authenticationToken)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        List<Time> times = timeService.findByProjetoIsNull();
        if (times.isEmpty()) {
            logger.info("Returning " + HttpStatus.NO_CONTENT);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("Returning " + HttpStatus.OK);
        return new ResponseEntity<List<Time>>(times, HttpStatus.OK);
    }
    
    /********** Pegar Time único **********/
    
	@RequestMapping(value = "/time/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTime(@RequestHeader String authenticationToken, 
    		@RequestHeader int role, @PathVariable("id") long id) {
		
    	if(!validateUtil.validate(authenticationToken, role)) {
    		return new ResponseEntity(HttpStatus.FORBIDDEN);
    	}    	
        logger.info("Buscando Time com o id {}", id);
        Time Time = timeService.findById(id);
        if (Time == null) {
            logger.error("Time com id {} não foi encontrado.", id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Time>(Time, HttpStatus.OK);
    }
	
	/********** Atualizar Time **********/
    
    @RequestMapping(value = "/time/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTime(@RequestHeader String authenticationToken,
    		@RequestHeader int role, @PathVariable("id") long id, @RequestBody Time TimeNew) {
    	
    	if(!validateUtil.validate(authenticationToken, role)) {
    		return new ResponseEntity(HttpStatus.FORBIDDEN);
    	}
    	
        Time TimeOld = timeService.findById(id);
        if (TimeOld == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        timeService.updateTime(TimeNew);
        return new ResponseEntity<Time>(HttpStatus.ACCEPTED);
    }

    /********** Atualizar ProjetoId do Time (settar null) **********/
    
    @RequestMapping(value = "/time-projetoid/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTimeProjetoId(@RequestHeader String authenticationToken,
            @RequestHeader int role, @PathVariable("id") long id, @RequestBody Time timeNew) {
        
        if(!validateUtil.validate(authenticationToken, role)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        
        Time timeOld = timeService.findById(id);
        if (timeOld == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        timeService.updateTimeProjetoId(timeNew);
        return new ResponseEntity<Time>(HttpStatus.ACCEPTED);
    }

    /********** Deletar Time **********/

    @RequestMapping(value = "/time/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTime(@RequestHeader String authenticationToken,
    @RequestHeader int role, @PathVariable("id") long id) {

        if(!validateUtil.validate(authenticationToken, role)){
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        List<Funcionario> funcionarios = funcionarioService.findByTimeId(id);
        if(funcionarios.isEmpty()) {
            timeService.deleteTimeById(id);
            logger.info("Returning " + HttpStatus.OK);
            return new ResponseEntity(HttpStatus.OK);
        }else {
            logger.info("Returning " + HttpStatus.CONFLICT);
            return new ResponseEntity<List<Funcionario>>(funcionarios, HttpStatus.CONFLICT);
        }
    }
}