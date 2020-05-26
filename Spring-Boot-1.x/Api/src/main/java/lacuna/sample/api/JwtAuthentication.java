package lacuna.sample.api;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;


public class JwtAuthentication extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 1L;

    public JwtAuthentication(Principal principal, String token, Collection<? extends GrantedAuthority> authorities) {
        super(principal, token, authorities);        
    }
}