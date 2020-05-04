package br.org.fitec.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.fitec.model.Funcionario;
import br.org.fitec.model.Usuario;
import br.org.fitec.repositories.FuncionarioRepository;
import br.org.fitec.service.FuncionarioService;
import br.org.fitec.service.UsuarioService;
import br.org.fitec.util.AuthenticationList;


@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@CrossOrigin(origins = "*" )
public class RestApiController {
 
    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
    public static final AuthenticationList authenticationList = AuthenticationList.getInstance();
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    FuncionarioService funcionarioService;
    
    /********** Login 
     * @throws NoSuchAlgorithmException **********/
    @CrossOrigin(origins = "*" )
    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    public ResponseEntity<Usuario> login(@RequestBody Usuario userParam) throws IOException, 
    NoSuchAlgorithmException {
        List<Usuario> usuariosDb = usuarioService.findAllUsuarios();
        Usuario user = new Usuario();
        user = authenticationList.authenticate(usuariosDb, userParam);
        logger.info(String.valueOf(user.getRole()));
        switch(user.getAuthenticationToken()) {
            case "404":
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            case "400":
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity<Usuario>(user, HttpStatus.ACCEPTED);
        }
    }
    
    /********** Logout
     * @throws NoSuchAlgorithmException **********/
    @CrossOrigin(origins = "*" )
    @RequestMapping(value = "/logout/", method = RequestMethod.GET)
    public ResponseEntity logout(@RequestHeader String authenticationToken) throws IOException, NoSuchAlgorithmException {
        
        logger.info("Procurando autenticação na lista...");
        Iterator<String> itr = AuthenticationList.getList().iterator();
        while(itr.hasNext())
        {
            if (itr.next().compareTo(authenticationToken) == 0)
            {
                logger.info("Autenticação encontrada, deletando...");
                itr.remove();
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        logger.info("Token de autenticação inválido.");
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
