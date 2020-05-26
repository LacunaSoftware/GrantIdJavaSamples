package lacuna.sample.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.security.oauth2.jwt.Jwt;

@RestController
public class ResourceController extends BaseController {
    
    @GetMapping("/home")
    public String home() {
        return "Home";
    }

    @GetMapping("/secret") 
    public String secret() {
        return "Secret";
    }

    @GetMapping("/claims") 
    public Map<String, Object> claims() {
        Jwt token = getCurrentToken(); 
        return token.getClaims();
    }
}