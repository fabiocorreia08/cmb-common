package br.gov.cmb.common.security;

import java.util.Collection;
import java.util.HashSet;

import org.jboss.logging.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import br.gov.cmb.common.ejb.vo.PerfilVO;
import br.gov.cmb.common.ejb.vo.PerfisVO;
import br.gov.cmb.common.ejb.vo.UsuarioVO;
import br.gov.cmb.common.security.rest.client.UsuarioClient;
import br.gov.cmb.common.security.rest.request.UsuarioRequest;
import br.gov.cmb.common.util.CollectionUtils;
import br.gov.cmb.common.util.StreamUtils;
import br.gov.cmb.common.util.XMLUtils;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {
	private static final Logger logger = Logger.getLogger(UsuarioClient.class);
	private static final String PERFIS_XML = "perfis.xml";
	
	private Long idSistema;
	
    public AuthenticationProviderImpl() {
    }
    
    @Override
    public Authentication authenticate(Authentication autenticacao) throws AuthenticationException {
        String login = autenticacao.getName();
        String senha = (String) autenticacao.getCredentials();
        UsuarioVO usuario = autenticar(login, senha);
        logger.infof("[CPA] - usuario [%s]", usuario);
        if (usuario == null || CollectionUtils.isNullOrEmpty(usuario.getPermissao())) {
        	logger.warnv("[CPA] - Usuario ou senha invalidos [%s]", usuario);
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }
        
        return getAuthentication(usuario);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

    private UsuarioVO autenticar(String login, String senha) {
        UsuarioRequest usuarioRequest = new UsuarioRequest(login, senha, idSistema);
        UsuarioClient usuarioService = new UsuarioClient();
        return usuarioService.autenticar(usuarioRequest);
    }

    private Authentication getAuthentication(UsuarioVO usuario) {
        return getAuthentication(usuario, PERFIS_XML);
    }
    
    protected Authentication getAuthentication(UsuarioVO usuario, String caminhoArquivoDeConfiguracao) {
        UsernamePasswordAuthenticationToken usuarioAutenticado = new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(), autorizar(usuario, caminhoArquivoDeConfiguracao));
        usuarioAutenticado.setDetails(usuario);

        return usuarioAutenticado;
    }
    
    private Collection<GrantedAuthority> autorizar(UsuarioVO usuario, String caminhoArquivoDeConfiguracao) {
		Collection<GrantedAuthority> autorizacoes = new HashSet<GrantedAuthority>();
    	PerfisVO perfis = XMLUtils.parse(StreamUtils.recuperarStreamDoProjeto(caminhoArquivoDeConfiguracao), PerfisVO.class);
        
        for (Integer identificador : usuario.getPermissao()) {
            
        	PerfilVO perfil = perfis.obterPerfil(identificador);
        	
            if (perfil != null) {
            	usuario.adicionarAcesso(perfil);
                autorizacoes.add(new SimpleGrantedAuthority(perfil.getNome()));
            }
        }
        
        return autorizacoes;
    }

	public Long getIdSistema() {
		return idSistema;
	}

	public void setIdSistema(Long idSistema) {
		this.idSistema = idSistema;
	}
    
}
