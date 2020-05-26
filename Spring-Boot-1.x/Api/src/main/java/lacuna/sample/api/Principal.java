package lacuna.sample.api;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;

public class Principal {
    
    private Claims claims;

    private Map<String, Object> header;
    
    private Collection<SimpleGrantedAuthority> grantedAuthorities;

    public Principal(Claims claims, Map<String, Object> header, Collection<SimpleGrantedAuthority> grantedAuthorities) {
        this.claims = claims;
        this.header = header;
        this.grantedAuthorities = grantedAuthorities;
    }

    public void setClaims(Claims claims) {
        this.claims = claims;
    }

    public Claims getClaims() {
        return claims;
    }

    public void setHeader(Map<String, Object> header) {
        this.header = header;
    }

    public Map<String, Object> getHeader() {
        return header;
    }

    public void setGrantedAuthorities(Collection<SimpleGrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    public Collection<SimpleGrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }
}