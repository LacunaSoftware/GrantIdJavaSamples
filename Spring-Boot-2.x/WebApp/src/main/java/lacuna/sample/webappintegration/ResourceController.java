package lacuna.sample.webappintegration;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResourceController extends BaseController {
    
    @GetMapping("/Home/Privacy")
    public String privacy() {
        return "privacy";
    }

    @GetMapping("/Home/PrivateRoute")
    public String privateRoute(Model model, OAuth2AuthenticationToken authentication, @AuthenticationPrincipal OidcUser principal) {
        OAuth2AuthorizedClient authorizedClient = getAuthorizedClient(authentication);
   
        model.addAttribute("claims", authentication.getPrincipal().getAttributes());
        model.addAttribute("id_token", principal.getIdToken().getTokenValue());
        model.addAttribute("access_token", authorizedClient.getAccessToken().getTokenValue());
        return "privateRoute";
    }
}