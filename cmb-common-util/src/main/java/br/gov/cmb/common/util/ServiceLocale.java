package br.gov.cmb.common.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.gov.cmb.common.exception.runtime.ServiceLocaleException;

public final class ServiceLocale {
    
    private static final String NOME_APLICACAO = "nome.EAR";
    private static final String NOME_MODULO = "nome.EJB";
    private static final String GLOBAL = "global";
    private static final String JAVA = "java";
    private static final String DOIS_PONTOS = ":";
    private static final String BARRA = "/";
    private static final String EXCLAMACAO = "!";
    
    private ServiceLocale(){
        
    }
    
    public static <T> Object recuperarServico(Class<T> sessionBean){
        StringBuilder urlLookup = new StringBuilder(JAVA).append(DOIS_PONTOS)
                                            .append(GLOBAL).append(BARRA)
                                            .append(PropertiesUtils.getProperty(NOME_APLICACAO)).append(BARRA)
                                            .append(PropertiesUtils.getProperty(NOME_MODULO)).append(BARRA);
        
        urlLookup.append(sessionBean.getSimpleName());
        urlLookup.append(EXCLAMACAO);
        urlLookup.append(sessionBean.getName());
        
        try {
            Context context = new InitialContext();
            return context.lookup(urlLookup.toString());
        } catch (NamingException e) {
            throw new ServiceLocaleException(e);
        }
    }
}
