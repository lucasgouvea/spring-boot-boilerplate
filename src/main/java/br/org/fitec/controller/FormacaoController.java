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

import br.org.fitec.model.Formacao;
import br.org.fitec.model.Funcionario;
import br.org.fitec.service.FormacaoService;
import br.org.fitec.service.FuncionarioService;
import br.org.fitec.service.UsuarioService;
import br.org.fitec.util.AuthenticationList;
import br.org.fitec.util.ValidateUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@CrossOrigin(origins = "*" )
public class FormacaoController {

    public static final Logger logger = LoggerFactory.getLogger(FormacaoController.class);
    public static final ValidateUtil validateUtil = ValidateUtil.getInstance();

    @Autowired
    FuncionarioService funcionarioService;
    @Autowired
    FormacaoService formacaoService;
    @Autowired
    UsuarioService userService;

    /********** Criar Formacao **********/    
  
    @RequestMapping(value = "/formacao/", method = RequestMethod.POST)
    public ResponseEntity<String> createFormacao(@RequestHeader String authenticationToken,
    		@RequestBody Formacao Formacao) throws IOException {
    	
    	logger.info("Recebendo token de autenticação: " + authenticationToken)	;
    	if(!AuthenticationList.getInstance().checkAuthentication(authenticationToken)){ 
    		return new ResponseEntity(HttpStatus.FORBIDDEN); 
    	}
    	
        logger.info("Criando Formacao");
        formacaoService.saveFormacao(Formacao);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    
    /********** Buscar todos as Formacoes **********/    
    
    @RequestMapping(value = "/formacao/", method = RequestMethod.GET)
    public ResponseEntity<List<Formacao>> listAllFormacaos(@RequestHeader String authenticationToken) {
    	
    	logger.info("Recebendo token de autenticação: " + authenticationToken);
    	
    	logger.info("Recebendo token de autenticação: " + authenticationToken)	;
    	if(!AuthenticationList.getInstance().checkAuthentication(authenticationToken)){ 
    		return new ResponseEntity(HttpStatus.FORBIDDEN); 
    	}
    	   	
    	List<Formacao> formacaos = formacaoService.findAllFormacaos();
        if (formacaos.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Formacao>>(formacaos, HttpStatus.OK);
    }
    
    /********** Pegar Formacao único **********/
    
	@RequestMapping(value = "/formacao/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getFormacao(@RequestHeader String authenticationToken, @PathVariable("id") long id) {
		
    	logger.info("Recebendo token de autenticação: " + authenticationToken)	;
    	if(!AuthenticationList.getInstance().checkAuthentication(authenticationToken)){ 
    		return new ResponseEntity(HttpStatus.FORBIDDEN); 
    	}   	
        logger.info("Buscando Formacao com o id {}", id);
        Formacao Formacao = formacaoService.findById(id);
        if (Formacao == null) {
            logger.error("Formacao com id {} não foi encontrado.", id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Formacao>(Formacao, HttpStatus.OK);
    }
	
	/********** Atualizar Formacao **********/
    
    @RequestMapping(value = "/formacao/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateFormacao(@RequestHeader String authenticationToken,
    		@PathVariable("id") long id,@RequestBody Formacao FormacaoNew) {
    	
    	logger.info("Recebendo token de autenticação: " + authenticationToken)	;
    	if(!AuthenticationList.getInstance().checkAuthentication(authenticationToken)){ 
    		return new ResponseEntity(HttpStatus.FORBIDDEN); 
    	}
        logger.info("Buscando o Formacao com o id {}", id);
        Formacao FormacaoOld = formacaoService.findById(id);
        if (FormacaoOld == null) {
            logger.error("Não foi possível atualizar. O Formacao com id {} não foi encontrado.", id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        formacaoService.updateFormacao(FormacaoNew);
        return new ResponseEntity<Formacao>(HttpStatus.ACCEPTED);
    }

	/********** Deletar Formacao **********/

    @RequestMapping(value = "/formacao/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFormacao(@RequestHeader String authenticationToken,
    @RequestHeader int role, @PathVariable("id") long id) {
        if(!validateUtil.validate(authenticationToken, role)){
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        List<Funcionario> funcionarios = funcionarioService.findByFormacaoId(id);
        if(funcionarios.isEmpty()) {
            formacaoService.deleteFormacaoById(id);
            logger.info("Returning " + HttpStatus.OK);
            return new ResponseEntity(HttpStatus.OK);
        }else {
            logger.info("Returning " + HttpStatus.CONFLICT);
            return new ResponseEntity<List<Funcionario>>(funcionarios, HttpStatus.CONFLICT);
        }
    }
 
}
