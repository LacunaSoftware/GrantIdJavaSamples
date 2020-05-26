package lacuna.sample.api;

import java.security.PublicKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.auth0.jwk.InvalidPublicKeyException;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {

    public PublicKey extractPublicKey(String token, JwkProvider jwkProvider) throws InvalidPublicKeyException, JwkException {
        final DecodedJWT jwt = JWT.decode(token);
        return jwkProvider.get(jwt.getKeyId()).getPublicKey();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> extractHeader(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getHeader();
    }
    
    public Claims extractAllClaims(String token, PublicKey publicKey) {   
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
    }

    public <T> T extractClaim(String token, PublicKey publicKey, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, publicKey);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token, PublicKey publicKey) {
        return extractClaim(token, publicKey, Claims::getExpiration);
    }

    public Date extractNotBefore(String token, PublicKey publicKey) {
        return extractClaim(token, publicKey, Claims::getNotBefore);
    }

    public String extractIssuer(String token, PublicKey publicKey) {
        return extractClaim(token, publicKey, Claims::getIssuer);
    }

    public Boolean isTokenExpired(String token, PublicKey publicKey) {
        return extractExpiration(token, publicKey).before(new Date());
    }

    public Collection<SimpleGrantedAuthority> extractGrantedAuthorities(Claims claims) {
        Collection<SimpleGrantedAuthority> grantedAuthorities = null;

        if (claims.containsKey("scope")) {
            @SuppressWarnings("unchecked") List<String> scope = (List<String>) claims.get("scope", List.class);
            grantedAuthorities = scope.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }
        return grantedAuthorities;
    }

    public Boolean verifyToken(String token, PublicKey publicKey, Algorithm algorithm) {
        DecodedJWT jwt = JWT.decode(token);
        algorithm.verify(jwt);
        return !isTokenExpired(token, publicKey);
    }

    public Boolean hasScope(String token, PublicKey publicKey, String scope) {
        Claims claims = extractAllClaims(token, publicKey);
        Collection<SimpleGrantedAuthority> grantedAuthorities = extractGrantedAuthorities(claims);

        return grantedAuthorities.contains(new SimpleGrantedAuthority(scope));
    }
}