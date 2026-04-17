package br.gov.cmb.common.security.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.gov.cmb.common.ejb.infra.UsuarioManager;
import br.gov.cmb.common.ejb.vo.UsuarioVO;

public class AutenticacaoFilter implements Filter{
	
	@Inject
	private UsuarioManager usuarioManager;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws ServletException, IOException {

		Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();
		if(autenticacao != null && autenticacao.getDetails() instanceof UsuarioVO){
			usuarioManager.registrar((UsuarioVO) autenticacao.getDetails());
		}
		 
		 chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
