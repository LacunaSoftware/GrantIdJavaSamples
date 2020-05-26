package lacuna.sample.webappgrantid;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ResourceController {
         
    @Autowired
    private OAuth2RestTemplate oauth2RestTemplate;

    @Autowired
    private TokenUtil tokenUtil;
    
    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    @RequestMapping("/Home/Privacy")
    public String privacy() {
        return "privacy";
    }

    @RequestMapping("/Home/PrivateRoute")
    public String privateRoute(Model model, OAuth2Authentication authentication) throws IOException {     
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        Map<String, Object> tokenAcessor = oauth2RestTemplate.getAccessToken().getAdditionalInformation();

        model.addAttribute("claims", tokenUtil.parseToken(details.getTokenValue()));
        model.addAttribute("access_token", details.getTokenValue());
        model.addAttribute("id_token", tokenAcessor.get("id_token"));
        return "privateRoute";
    }

}