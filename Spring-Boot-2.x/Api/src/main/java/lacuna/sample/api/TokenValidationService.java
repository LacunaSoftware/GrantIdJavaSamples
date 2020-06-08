package lacuna.sample.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

@Component
public class TokenValidationService {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Value("${grantid.api-scope}") 
    private String apiScope;

    @Value("${grantid.issuer}") 
    private String issuer;
    
    private JwkProvider jwkProvider;

    @PostConstruct
    public void postContruct() throws MalformedURLException {
        URL url = new URL(issuer + "/.well-known/openid-configuration/jwks");
        jwkProvider = new JwkProviderBuilder(url).build();
    }

    public Boolean isTokenValid(String token) {
        try {
            PublicKey publicKey = jwtUtil.extractPublicKey(token, jwkProvider);
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);

            Boolean tokenIsValid = jwtUtil.verifyToken(token, publicKey, algorithm);
            tokenIsValid &= jwtUtil.hasScope(token, publicKey, apiScope);
            tokenIsValid &= jwtUtil.extractIssuer(token, publicKey).equals(issuer);

            return tokenIsValid;
        } catch (Exception exception) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        try {
            PublicKey publicKey = jwtUtil.extractPublicKey(token, jwkProvider);
            Map<String, Object> header = jwtUtil.extractHeader(token, publicKey);
            Instant issuedAt = jwtUtil.extractNotBefore(token, publicKey).toInstant();
            Instant expiration = jwtUtil.extractExpiration(token, publicKey).toInstant();
            Claims claims = jwtUtil.extractAllClaims(token, publicKey);

            Jwt jwt = new Jwt(token, issuedAt, expiration, header, claims);
            Collection<SimpleGrantedAuthority> grantedAuthorities = jwtUtil.extractGrantedAuthorities(claims);

            return new JwtAuthenticationToken(jwt, grantedAuthorities);
        }
        catch (Exception exception) {
            return null;
        }
    }
}