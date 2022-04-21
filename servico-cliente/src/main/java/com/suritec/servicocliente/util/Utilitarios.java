package com.suritec.servicocliente.util;

public class Utilitarios {

    public static String retiraMascaraCpf(String cpf) {
	    if (cpf==null) {
	      return "";
	    }
	    return cpf.replaceAll("\\.|-|/", "");
    }
	  
    public static String adicionaMascaraCpf(String cpf) {
	    return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
    }
    
    public static String retirarMascaraCep(String cep) {
    	if (cep==null) {
  	      return "";
  	    }
    	return cep.replaceAll("\\.|-|/", "");
    }
    
    public static String retirarMascaraTelefone(String telefone) {
    	if (telefone==null) {
  	      return "";
  	    }
    	return telefone.replaceAll("[^0-9]", "");
    }
	
}
