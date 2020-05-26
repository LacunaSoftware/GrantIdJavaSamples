package lacuna.sample.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

@Component
public class TokenValidationService {

    @Autowired
    private Environment env;

    @Autowired
    private JwtUtil jwtUtil;

    private JwkProvider jwkProvider;
    
    @Value("${grantid.api-scope}") 
    private String apiScope;
    
    @Value("${grantid.issuer}") 
    private String issuer;

    @PostConstruct
    public void postContruct() throws MalformedURLException {
        URL url = new URL(env.getProperty("grantid.jwks-uri"));
        jwkProvider = new JwkProviderBuilder(url).build();
    }

    public Boolean isTokenValid(String token) {
        try {
            PublicKey publicKey = jwtUtil.extractPublicKey(token, jwkProvider);
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);

            Boolean tokenIsValid = jwtUtil.VerifyToken(token, publicKey, algorithm);
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
            Claims claims = jwtUtil.extractAllClaims(token, publicKey);
            Map<String, Object> header = jwtUtil.extractHeader(token, publicKey);
            
            Collection<SimpleGrantedAuthority> grantedAuthorities = jwtUtil.extractGrantedAuthorities(claims);
            Principal principal = new Principal(claims, header, grantedAuthorities);
            return new JwtAuthentication(principal, token, grantedAuthorities);
        }
        catch (Exception exception) {
            return null;
        }
    }

}