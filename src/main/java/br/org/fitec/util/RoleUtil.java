package br.org.fitec.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.org.fitec.controller.FuncionarioController;

public final class RoleUtil {
	
	private static final RoleUtil INSTANCE = new RoleUtil();
    public static final Logger logger = LoggerFactory.getLogger(FuncionarioController.class);
    public RoleUtil() {
    	
    }
    public static RoleUtil getInstance() {
    	return INSTANCE;
    }
    public boolean roleCheck(int role) {
    	logger.info("Checando role: " + role);
    	if(role == 2) {
    		logger.info("Role válida");
    		return true;
    	}
    	logger.info("Role inválida");
		return false;
    }
}
