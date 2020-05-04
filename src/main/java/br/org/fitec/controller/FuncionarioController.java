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
import br.org.fitec.model.Projeto;
import br.org.fitec.service.FuncionarioService;
import br.org.fitec.service.UsuarioService;
import br.org.fitec.util.AuthenticationList;
import br.org.fitec.util.RoleUtil;
import br.org.fitec.util.ValidateUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@CrossOrigin(origins = "*" )
public class FuncionarioController {
 
    public static final Logger logger = LoggerFactory.getLogger(FuncionarioController.class);
    public static final ValidateUtil validateUtil = ValidateUtil.getInstance();
    @Autowired
    FuncionarioService funcionarioService;
    @Autowired
    UsuarioService userService;

    
    /********** Criar Funcionario **********/ 
  
    @RequestMapping(value = "/funcionario-criar/", method = RequestMethod.POST)
    public ResponseEntity<String> createFuncionario(@RequestHeader String authenticationToken,
            @RequestHeader int role, @RequestBody Funcionario funcionario) throws IOException {
        
        if(!validateUtil.validate(authenticationToken, role)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        funcionarioService.saveFuncionario(funcionario);
        logger.info("Returning " + HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.CREATED);
    }
   
    /********** Buscar todos os Funcionarios (por time) **********/   
    @RequestMapping(value = "/funcionario/", method = RequestMethod.POST) 
    public ResponseEntity<List<Funcionario>> listAllFuncionarios(@RequestHeader String authenticationToken,
            @RequestBody String timeNome) throws IOException {
        
        if(!validateUtil.validateUsuario(authenticationToken)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        List<Funcionario> funcionarios = funcionarioService.findByTime(timeNome);
        if (funcionarios.isEmpty()) {
            logger.info("Returning " + HttpStatus.NO_CONTENT);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("Returning " + HttpStatus.OK);
        return new ResponseEntity<List<Funcionario>>(funcionarios, HttpStatus.OK);
    }

    /********** Fetch all Funcionarios by Projeto **********/   
    @RequestMapping(value = "/funcionario/projeto", method = RequestMethod.POST) 
    public ResponseEntity<List<Funcionario>> listAllFuncionariosByProjeto
        (@RequestHeader String authenticationToken, @RequestBody String projetoNome) 
        throws IOException {

        if(!validateUtil.validateUsuario(authenticationToken)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        List<Funcionario> funcionarios = funcionarioService.findByProjeto(projetoNome);
        if (funcionarios.isEmpty()) {
            logger.info("Returning " + HttpStatus.NO_CONTENT);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("Returning " + HttpStatus.OK);
        return new ResponseEntity<List<Funcionario>>(funcionarios, HttpStatus.OK);
    }

    /********** Buscar todos os Funcionarios sem time/projeto **********/   
    @RequestMapping(value = "/funcionario-timenull/", method = RequestMethod.GET) 
    public ResponseEntity<List<Funcionario>> listAllFuncionariosTimeNull(
            @RequestHeader String authenticationToken) throws IOException {
        if(!validateUtil.validateUsuario(authenticationToken)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        List<Funcionario> funcionarios = funcionarioService.findByTimeIsNull();
        if (funcionarios.isEmpty()) {
            logger.info("Returning " + HttpStatus.NO_CONTENT);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("Returning " + HttpStatus.OK);
        return new ResponseEntity<List<Funcionario>>(funcionarios, HttpStatus.OK);
    
    }
    
    /********** Pegar Funcionario único **********/
    
    @RequestMapping(value = "/funcionario/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getFuncionario(@RequestHeader String authenticationToken, 
            @RequestHeader int role, @PathVariable("id") long id) {
        
        if(!validateUtil.validate(authenticationToken, role)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        Funcionario funcionario = funcionarioService.findById(id);
        if (funcionario == null) {
            logger.info("Returning " + HttpStatus.NOT_FOUND);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        logger.info("Returning " + HttpStatus.OK);
        return new ResponseEntity<Funcionario>(funcionario, HttpStatus.OK);
    }
    
    /********** Atualizar Funcionario **********/
    
    @RequestMapping(value = "/funcionario/{matricula}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateFuncionario(@RequestHeader String authenticationToken, 
            @RequestHeader int role, @PathVariable("matricula") long matricula,
            @RequestBody Funcionario funcionarioNew) {
        
        if(!validateUtil.validate(authenticationToken, role)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        logger.info(String.valueOf(matricula));
        Funcionario funcionarioOld = funcionarioService.findByMatricula(matricula);
        if (funcionarioOld == null) {
            logger.info("Returning " + HttpStatus.NOT_FOUND);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        funcionarioService.updateFuncionario(funcionarioNew);
        logger.info("Returning " + HttpStatus.ACCEPTED);
        return new ResponseEntity<Funcionario>(HttpStatus.ACCEPTED);
    }
    
    /********** Deletar Funcionario **********/
    
    @RequestMapping(value = "/funcionario/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFuncionario(@RequestHeader String authenticationToken,
            @RequestHeader int role, @PathVariable("id") long id) {
        
        if(!validateUtil.validate(authenticationToken, role)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        
        Funcionario Funcionario = funcionarioService.findById(id);
        if (Funcionario == null) {
            logger.info("Returning " + HttpStatus.NOT_FOUND);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        funcionarioService.deleteFuncionarioById(id);
        logger.info("Returning " + HttpStatus.NO_CONTENT);
        return new ResponseEntity<Funcionario>(HttpStatus.NO_CONTENT);
    }
}
