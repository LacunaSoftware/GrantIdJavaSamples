package lacuna.sample.api;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Principal principal = getPrincipal();
        return principal.getClaims();
    }

}