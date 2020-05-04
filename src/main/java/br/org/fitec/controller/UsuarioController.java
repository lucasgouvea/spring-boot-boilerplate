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
import br.org.fitec.model.Projeto;
import br.org.fitec.model.Usuario;
import br.org.fitec.service.FuncionarioService;
import br.org.fitec.service.UsuarioService;
import br.org.fitec.util.AuthenticationList;
import br.org.fitec.util.RoleUtil;
import br.org.fitec.util.ValidateUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@CrossOrigin(origins = "*" )
public class UsuarioController {
 
    public static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    public static final ValidateUtil validateUtil = ValidateUtil.getInstance();
    @Autowired
    FuncionarioService funcionarioService;
    @Autowired
    UsuarioService usuarioService;

    
    /********** Criar Usuário **********/ 

    @RequestMapping(value = "/usuario/", method = RequestMethod.POST)
    public ResponseEntity<String> createFuncionario(@RequestHeader String authenticationToken,
        @RequestHeader int role, @RequestBody Usuario usuario) throws IOException {
        if(!validateUtil.validate(authenticationToken, role)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        usuarioService.saveUsuario(usuario);
        logger.info("Returning " + HttpStatus.CREATED);
                return new ResponseEntity(HttpStatus.CREATED);
    }


    /********** Fetch all Usuários **********/ 
    
    @RequestMapping(value = "/usuario/", method = RequestMethod.GET)
    public ResponseEntity<List<Usuario>> readFuncionarios(@RequestHeader String authenticationToken,
        @RequestHeader int role) throws IOException {
        logger.info("Recebendo token de autenticação: " + authenticationToken);
        if(!validateUtil.validate(authenticationToken, role)){
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        List<Usuario> usuarios = usuarioService.findAllUsuarios();
        if (usuarios.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }
    
    /********** Delete Usuario **********/
    
    @RequestMapping(value = "/usuario/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUsuario(@RequestHeader String authenticationToken,
    @RequestHeader int role, @PathVariable("id") long id) {
        if(!validateUtil.validate(authenticationToken, role)){
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        usuarioService.deleteUsuarioById(id);
        logger.info("Returning " + HttpStatus.OK);
        return new ResponseEntity(HttpStatus.OK);
    }
    
}
