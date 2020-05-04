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

import br.org.fitec.model.Funcao;
import br.org.fitec.model.Funcionario;
import br.org.fitec.service.FuncaoService;
import br.org.fitec.service.FuncionarioService;
import br.org.fitec.service.UsuarioService;
import br.org.fitec.util.ValidateUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@CrossOrigin(origins = "*" )
public class FuncaoController {
 
    public static final Logger logger = LoggerFactory.getLogger(FuncaoController.class);
    public static final ValidateUtil validateUtil = ValidateUtil.getInstance();
    
    @Autowired
    FuncaoService funcaoService;
    @Autowired
    UsuarioService userService;
    @Autowired
    FuncionarioService funcionarioService;

    /********** Criar Funcao **********/    
  
    @RequestMapping(value = "/funcao/", method = RequestMethod.POST)
    public ResponseEntity<String> createFuncao(@RequestHeader String authenticationToken,
            @RequestHeader int role, @RequestBody Funcao Funcao) throws IOException {
        
        logger.info("Recebendo token de autenticação: " + authenticationToken)    ;
        if(!validateUtil.validate(authenticationToken, role)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        
        logger.info("Criando Funcao");
        funcaoService.saveFuncao(Funcao);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    
    /********** Buscar todos as Funcaos **********/   

    @RequestMapping(value = "/funcao/", method = RequestMethod.GET)
    public ResponseEntity<List<Funcao>> listAllFuncaos(@RequestHeader String authenticationToken) {
        
        List<Funcao> funcaos = funcaoService.findAllFuncaos();
        if (funcaos.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Funcao>>(funcaos, HttpStatus.OK);
    }
    
    /********** Pegar Funcao único **********/
    
    @RequestMapping(value = "/funcao/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getFuncao(@RequestHeader String authenticationToken, 
            @RequestHeader int role, @PathVariable("id") long id) {
        if(!validateUtil.validate(authenticationToken, role)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        logger.info("Buscando Funcao com o id {}", id);
        Funcao Funcao = funcaoService.findById(id);
        if (Funcao == null) {
            logger.error("Funcao com id {} não foi encontrado.", id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Funcao>(Funcao, HttpStatus.OK);
    }
    
    /********** Atualizar Funcao **********/
    
    @RequestMapping(value = "/funcao/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateFuncao(@RequestHeader String authenticationToken,
            @RequestHeader int role, @PathVariable("id") long id, @RequestBody Funcao FuncaoNew) {
        
        if(!validateUtil.validate(authenticationToken, role)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        
        logger.info("Buscando o Funcao com o id {}", id);
        Funcao FuncaoOld = funcaoService.findById(id);
        if (FuncaoOld == null) {
            logger.error("Não foi possível atualizar. O Funcao com id {} não foi encontrado.", id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        funcaoService.updateFuncao(FuncaoNew);
        return new ResponseEntity<Funcao>(HttpStatus.ACCEPTED);
    }
    
    /********** Deletar Função **********/
    
    @RequestMapping(value = "/funcao/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFormacao(@RequestHeader String authenticationToken,
    @RequestHeader int role, @PathVariable("id") long id) {
        if(!validateUtil.validate(authenticationToken, role)){
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        List<Funcionario> funcionarios = funcionarioService.findByFuncaoId(id);
        if(funcionarios.isEmpty()) {
            funcaoService.deleteFuncaoById(id);
            logger.info("Returning " + HttpStatus.OK);
            return new ResponseEntity(HttpStatus.OK);
        }else {
            logger.info("Returning " + HttpStatus.CONFLICT);
            return new ResponseEntity<List<Funcionario>>(funcionarios, HttpStatus.CONFLICT);
        }
    }

}
