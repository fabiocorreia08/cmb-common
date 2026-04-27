package br.gov.cmb.common.rest.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import br.gov.cmb.common.exception.runtime.CMBRuntimeException;
import br.gov.cmb.common.rest.security.Authentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public final class JwtTokenHelper {
	
	public static final String CLAIM_PERMISSAO = "permissions";
	
	public static final String CLAIM_DETAILS = "details";

	public static final String HEADER_TOKEN = "Authorization";

	private int tempoExpiracao;

	private String signKey;
	
	private static final int REFRESH_TOKEN_MINUTES = 5;

	public JwtTokenHelper(Integer tempoExpiracao, String signKey) {
		this.tempoExpiracao = tempoExpiracao;
		this.signKey = signKey;
	}
	
	public String generateToken(Authentication authentication) {
		return generateToken(authentication.getUsername(), authentication.getPermissions(), authentication.getDetails());
	}

	public String generateToken(String id, List<String> permissoes, Object details) {
	    try {
	        DateTime now = new DateTime();

	        return Jwts.builder()
	            .setId(id)
	            .setIssuedAt(now.toDate())
	            .setExpiration(now.plusMinutes(this.tempoExpiracao).toDate())
	            .claim(CLAIM_PERMISSAO, permissoes)
	            .claim(CLAIM_DETAILS, details)
	            .signWith(
	                SignatureAlgorithm.HS512,
	                this.signKey.getBytes(StandardCharsets.UTF_8) // ✅ byte[]
	            )
	            .compact();

	    } catch (Exception e) {
	        throw new CMBRuntimeException("Erro ao gerar Token", e);
	    }
	}


	public String getUsername(String token) {
		return getClaims(token).getId();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getPermissions(String token) {
		return (List<String>) getClaimValue(token, CLAIM_PERMISSAO);
	}	
	
	public Object getDetails(String token) {
		return getClaimValue(token, CLAIM_DETAILS);
	}
	
	public String handleRefreshToken(String token) {
		return refreshToken(token);
	}
	
	public boolean isValid(String token) {
		try {
			getClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private String refreshToken(String token) {
		String username = getUsername(token);
		List<String> permissoes = getPermissions(token);
		Object details = getDetails(token);
		return generateToken(username, permissoes, details);
	}
	


	@SuppressWarnings("unused")
	private boolean isTokenRefresh(String token) {
		DateTime expiration = new DateTime(getExpiration(token));
		return new DateTime().minusMinutes(REFRESH_TOKEN_MINUTES).isAfter(expiration);
	}
	
	private Claims getClaims(String token) {
        final Claims claims = Jwts.parser()
        		.setSigningKey(signKey)
        		.parseClaimsJws(token)
        		.getBody();
        return claims;
	}
	

	private Object getClaimValue(String token, String chave) {
		return getClaims(token).get(chave);
	}
	
	private Date getExpiration(String token) {
		return	getClaims(token).getExpiration();
	} 
}
