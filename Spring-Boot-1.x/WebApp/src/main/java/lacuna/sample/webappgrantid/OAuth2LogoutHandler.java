package lacuna.sample.webappgrantid;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.util.UriComponentsBuilder;

public class OAuth2LogoutHandler extends SecurityContextLogoutHandler {
    
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private OAuth2ClientContext clientContext;
    private URI postLogoutRedirectUri;
    private URI externalServiceLogoutUri;

    @Autowired
    public OAuth2LogoutHandler(String externalServiceLogoutUrl, String postLogoutRedirectUri, OAuth2ClientContext clientContext) throws URISyntaxException {
        this.postLogoutRedirectUri = new URI(postLogoutRedirectUri);
        this.externalServiceLogoutUri = new URI(externalServiceLogoutUrl);
        this.clientContext = clientContext;
    }

    private String getTargetUrl() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(externalServiceLogoutUri);

        OAuth2AccessToken tokenProvider = clientContext.getAccessToken();
        String idToken = (String) tokenProvider.getAdditionalInformation().get("id_token");

        builder.queryParam("id_token_hint", idToken);
        builder.queryParam("post_logout_redirect_uri", postLogoutRedirectUri);

        String targetUrl = builder.build().toUriString();
        return targetUrl;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {      
		try {
            String targetUrl = getTargetUrl();
            super.logout(request, response, authentication);
			redirectStrategy.sendRedirect(request, response, targetUrl);
        } 
        catch (Exception e) {
			e.printStackTrace();
		}
    }

}