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

import br.org.fitec.model.Gestor;
import br.org.fitec.model.Projeto;
import br.org.fitec.service.GestorService;
import br.org.fitec.service.ProjetoService;
import br.org.fitec.service.UsuarioService;
import br.org.fitec.util.AuthenticationList;
import br.org.fitec.util.RoleUtil;
import br.org.fitec.util.ValidateUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@CrossOrigin(origins = "*" )
public class GestorController {
 
    public static final Logger logger = LoggerFactory.getLogger(GestorController.class);
    public static final ValidateUtil validateUtil = ValidateUtil.getInstance();
    @Autowired
    GestorService gestorService;
    @Autowired
    UsuarioService userService;
    @Autowired
    ProjetoService projetoService;
    
    /********** Criar Gestor **********/ 
  
    @RequestMapping(value = "/gestor/", method = RequestMethod.POST)
    public ResponseEntity<String> createGestor(@RequestHeader String authenticationToken,
        @RequestHeader int role, @RequestBody Gestor gestor) throws IOException {
        if(!validateUtil.validate(authenticationToken, role)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        gestorService.saveGestor(gestor);
        logger.info("Returning " + HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    
    /********** Buscar Gestor único **********/
    
    @RequestMapping(value = "/gestor/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getGestor(@RequestHeader String authenticationToken, 
            @RequestHeader int role, @PathVariable("gestorMatricula") long gestorMatricula) {
        
        if(!validateUtil.validate(authenticationToken, role)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        Gestor gestor = gestorService.findByGestorMatricula(gestorMatricula);
        if (gestor == null) {
            logger.info("Returning " + HttpStatus.NOT_FOUND);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        logger.info("Returning " + HttpStatus.OK);
        return new ResponseEntity<Gestor>(gestor, HttpStatus.OK);
    }
    
    /* Read Gestor by Projeto */
    @RequestMapping(value = "/gestor-projeto/{projeto}", method = RequestMethod.GET)
    public ResponseEntity<?> getGestor(@RequestHeader String authenticationToken, 
            @RequestHeader int role, @PathVariable("projeto") String projetoNome) {
        
        if(!validateUtil.validate(authenticationToken, role)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        Gestor gestor = gestorService.findByProjetoNome(projetoNome);
        if (gestor == null) {
            logger.info("Returning " + HttpStatus.NOT_FOUND);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        logger.info("Returning " + HttpStatus.OK);
        return new ResponseEntity<Gestor>(gestor, HttpStatus.OK);
    }
    /********** Buscar todos os Gestores **********/   
    @RequestMapping(value = "/gestor/", method = RequestMethod.GET) 
    public ResponseEntity<List<Gestor>> listAllGestors(@RequestHeader String authenticationToken) throws IOException {

        if(!validateUtil.validateUsuario(authenticationToken)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        List<Gestor> gestors = gestorService.findAllGestors();
        if (gestors.isEmpty()) {
            logger.info("Returning " + HttpStatus.NO_CONTENT);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("Returning " + HttpStatus.OK);
        return new ResponseEntity<List<Gestor>>(gestors, HttpStatus.OK);

    }
    
    /********** Deletar Gestor **********/
    
    @RequestMapping(value = "/gestor/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteGestor(@RequestHeader String authenticationToken,
            @RequestHeader int role, @PathVariable("id") long gestorMatricula) {
        
        if(!validateUtil.validate(authenticationToken, role)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        
        Gestor gestor = gestorService.findByGestorMatricula(gestorMatricula);
        if (gestor == null) {
            logger.info("Returning " + HttpStatus.NOT_FOUND);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        List<Projeto> projetos = projetoService.findByGestor(gestor);
        List<Projeto> projetosDAO = projetos; //The system is persisting the changes in ProjetoServiceImpl, so i had to do this
        for(Projeto projeto : projetosDAO) {
            projeto.setGestor(null);
            projeto.setTimes(null);
        }
        if(projetosDAO.isEmpty()) {
            gestorService.deleteGestorById(gestorMatricula);
            logger.info("Returning " + HttpStatus.OK);
            return new ResponseEntity<Gestor>(HttpStatus.OK);
        } else {
            logger.info("Returning " + HttpStatus.CONFLICT);
            return new ResponseEntity<>(projetosDAO, HttpStatus.CONFLICT);
        }

    }
    
    /********** Atualizar Gestor **********/
    
    @RequestMapping(value = "/gestor/{matricula}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateGestor(@RequestHeader String authenticationToken, 
            @RequestHeader int role, @PathVariable("matricula") long matricula,
            @RequestBody Gestor gestorNew) {
        
        if(!validateUtil.validate(authenticationToken, role)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        logger.info(String.valueOf(matricula));
        Gestor gestorOld = gestorService.findByGestorMatricula(matricula);
        if (gestorOld == null) {
            logger.info("Returning " + HttpStatus.NOT_FOUND);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        gestorService.updateGestor(gestorNew);
        logger.info("Returning " + HttpStatus.ACCEPTED);
        return new ResponseEntity<Gestor>(HttpStatus.ACCEPTED);
    }
    
    
    /********** Buscar todos os Gestors sem time/projeto **********/   
    /*
    @RequestMapping(value = "/gestor-timenull/", method = RequestMethod.GET) 
    public ResponseEntity<List<Gestor>> listAllGestorsTimeNull(
            @RequestHeader String authenticationToken) throws IOException {
        if(!validateUtil.validateUsuario(authenticationToken)) {
            logger.info("Returning " + HttpStatus.FORBIDDEN);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        List<Gestor> gestors = gestorService.findByTimeIsNull();
        if (gestors.isEmpty()) {
            logger.info("Returning " + HttpStatus.NO_CONTENT);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("Returning " + HttpStatus.OK);
        return new ResponseEntity<List<Gestor>>(gestors, HttpStatus.OK);
    }
    */

}
