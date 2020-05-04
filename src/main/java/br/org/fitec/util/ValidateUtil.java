package br.org.fitec.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ValidateUtil {
	
    public static final AuthenticationList authenticationList = AuthenticationList.getInstance();
    public static final RoleUtil roleUtil = RoleUtil.getInstance();
	private static final ValidateUtil INSTANCE = new ValidateUtil();
	public ValidateUtil() {
		
	}
    public static ValidateUtil getInstance() {
    	return INSTANCE;
    }
    public boolean validate(String authenticationToken, int role) {
    	if(!authenticationList.checkAuthentication(authenticationToken) || !roleUtil.roleCheck(role)){
    		return false; 
    	}
    	return true;
    }
    public boolean validateUsuario(String authenticationToken) {
    	if(!authenticationList.checkAuthentication(authenticationToken)){
    		return false; 
    	}
    	return true;
    }    
}
