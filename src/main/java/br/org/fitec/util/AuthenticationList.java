package br.org.fitec.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.org.fitec.controller.RestApiController;
import br.org.fitec.model.Usuario;

//Singleton
public final class AuthenticationList {
	private static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
	private static final AuthenticationList INSTANCE = new AuthenticationList();
    public static final AuthenticationList authenticationList = AuthenticationList.getInstance();
	private static final List<String> list = new ArrayList<>();
	private AuthenticationList() {
		
	}
	public static AuthenticationList getInstance() {
		return INSTANCE;
	}
	public static List<String> getList(){
		return list;
	}
	
	//Autenticação
	public Usuario authenticate(List<Usuario> usuariosFromDb, Usuario usuario) throws NoSuchAlgorithmException {

        logger.info("Buscando usuário: " + usuario.getNome() + " "+ usuario.getPassword());
        Iterator<Usuario> itr = usuariosFromDb.iterator();
        Usuario usuarioFromDb = new Usuario(); 
        Usuario usuarioReturn = new Usuario();
        usuarioReturn.setNome(usuario.getNome());
        usuarioReturn.setPassword(usuario.getPassword());
        boolean usuarioFound = false;
        while(itr.hasNext()) {
        	usuarioFromDb = (Usuario) itr.next();
        	if (usuarioFromDb.getNome().compareTo(usuario.getNome()) == 0)
        	{
        		logger.info("Usuário encontrado.");
        		if (usuario.getPassword().compareTo(usuarioFromDb.getPassword()) == 0)
        		{
        			usuarioFound = true;
        			logger.info("Password coressponde!");
        			String token = generateToken();
        			list.add(token);
        			usuarioReturn.setAuthenticationToken(token);
        			usuarioReturn.setRole(usuarioFromDb.getRole());
        			return usuarioReturn;
        		}
        		else
        		{
        			logger.info("Password não corresponde.");
        			usuarioReturn.setAuthenticationToken("400");
        			return usuarioReturn;
        		}
        	}
        }
        if(!usuarioFound)
        {
        	logger.info("Usuário não encontrado");
        	usuarioReturn.setAuthenticationToken("404");
            return usuarioReturn;
        }
		return usuarioReturn;
	}
	
	//Checa se autenticação é válida
	public boolean checkAuthentication(String authenticationToken)
	{
		logger.info("Checando token de autenticação...");
		Iterator<String> itr = list.iterator();
		while(itr.hasNext())
		{
			if (itr.next().compareTo(authenticationToken) == 0)
			{
				logger.info("Token de autenticação válido.");
				return true;
			}
		}
		logger.info("Token de autenticação inválido.");
		return false;	
	}
	
	//Gera token usando SHA-1
	public String generateToken() throws NoSuchAlgorithmException
	{
	    Random random = new Random();
	    String toBeDigested = String.valueOf(random);
	    MessageDigest messageDigest;
	    messageDigest = MessageDigest.getInstance("SHA-1");
	    messageDigest.update(toBeDigested.getBytes());
	    byte[] authCodeMD5 = messageDigest.digest();	
	    StringBuffer stringBuffer = new StringBuffer();
	    for (byte bytes : authCodeMD5)
	    {
	      stringBuffer.append(String.format("%02x", bytes & 0xff));
	    }
	    logger.info("Gerado token de autenticação:" + stringBuffer.toString());
	    return stringBuffer.toString();		
	}
	
	public String tokenToJson(String token) {
		return "{ \"token\" : "+"\""+ token +"\" }";
	}

		
}
